package com.taufiqhidayah.tasekretaris;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

import butterknife.OnClick;

public class DetailSuratInternalActivity extends AppCompatActivity implements SwipeRefreshLayout.OnRefreshListener {
    public static ArrayList<Data> data;
    SessionManager sesi;
    Data user;
    ListView lsOrder;
    EditText edNo, edTgl, edUnit, edDok, edJenDok, edTujuan1, edTujuan2, edTujuan3, edTujuan4;
    Button btSave, btDel;
    SwipeRefreshLayout mSwipeRefreshLayout;
    //1. deklarasi variable satatus

    Boolean status =true;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_surat);
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        lsOrder = (ListView) findViewById(R.id.lsView);
        sesi = new SessionManager(this);
        data = new ArrayList<>();
        user = new Data();
        mSwipeRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.swipe);
        mSwipeRefreshLayout.setOnRefreshListener(this);
//        getData();
        //ini fungsi untuk ngecek user admin atau bukan
        if (!sesi.getIdUser().equals("2") && !sesi.getIdUser().equals("3") && !sesi.getIdUser().equals("4") && !sesi.getIdUser().equals("5")) {
//        if (sesi.getNama()!= "sekt_dirut" && sesi.getNama()!="sekt_dirkeu" && sesi.getNama()!="sekt_dirkomtek" & sesi.getNama()!= "sekt_dirprod"){
            lsOrder.setOnItemClickListener(null);
            lsOrder.setEnabled(false);
            fab.setVisibility(View.GONE);
//2. tambahin status false
            status = false;
        }

        //3. tambahin  kondisi ini
        if (status) {
            getData();
        } else if (!status) {
            //4, bikin method ini
            getDataAdmin();
        }



        lsOrder.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Dialog dialog = new Dialog(DetailSuratInternalActivity.this);
                dialog.setContentView(R.layout.insert_data);

                edNo = (EditText) dialog.findViewById(R.id.NomorUrutDokumen);
                edUnit = (EditText) dialog.findViewById(R.id.UnitPengirim);
                edTgl = (EditText) dialog.findViewById(R.id.TanggalMasukDokumen);
                edDok = (EditText) dialog.findViewById(R.id.Dokumen);
                edJenDok = (EditText) dialog.findViewById(R.id.JenisDokumen);
                edTujuan1 = (EditText) dialog.findViewById(R.id.TujuanPertama);
                edTujuan2 = (EditText) dialog.findViewById(R.id.TujuanKedua);
                edTujuan3 = (EditText) dialog.findViewById(R.id.TujuanKetiga);
                edTujuan4 = (EditText) dialog.findViewById(R.id.TujuanKeempat);
                btSave = (Button) dialog.findViewById(R.id.Simpan);
                btDel = (Button) dialog.findViewById(R.id.btdelete);
                final Data map = data.get(position);
                Toast.makeText(DetailSuratInternalActivity.this, map.getId(), Toast.LENGTH_SHORT).show();
                edDok.setText(map.getPerihal());
                edNo.setText(map.getNo_dok());
                edUnit.setText(map.getPengirim());
                edTgl.setText(map.getTgl_masuk());
                edJenDok.setText("INTERNAL");
                edTujuan1.setText(map.getDireksi());

                btDel.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        String Url = Konstant.URL + "apisek/deleteData.php";
                        final ProgressDialog progressDialog = new ProgressDialog(DetailSuratInternalActivity.this);
                        progressDialog.setMessage("loading..");
                        progressDialog.show();
                        StringRequest stringRequest = new StringRequest(Request.Method.POST, Url, new Response.Listener<String>() {

                            @Override
                            public void onResponse(String response) {
                                Log.d("respon", response);
                                try {
                                    JSONObject json = new JSONObject(response);
                                    String hasil = json.getString("success");
                                    if (hasil.equalsIgnoreCase("true")) {
                                        AlertDialog.Builder la = new AlertDialog.Builder(DetailSuratInternalActivity.this);
                                        la.setTitle("Terima Kasih");
                                        la.setMessage("");
                                        la.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                                    @Override
                                                    public void onClick(DialogInterface dialogInterface, int i) {
                                                        startActivity(new Intent(DetailSuratInternalActivity.this, DetailSuratInternalActivity.class));
                                                    }
                                                }
                                        );
                                        la.show();

                                        progressDialog.dismiss();
                                    }


                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }

                            }
                        }, new Response.ErrorListener() {
                            @Override
                            public void onErrorResponse(VolleyError error) {
                                Toast.makeText(DetailSuratInternalActivity.this, "Data gagal di kitim", Toast.LENGTH_SHORT).show();
                            }
                        }) {
                            @Override
                            protected Map<String, String> getParams() throws AuthFailureError {
                                HashMap<String, String> param = new HashMap<>();
                                param.put("id", map.getId());
//                        param.put("deposit_jumlah",edTgl.getText().toString());
                                return param;

                            }
                        };
                        RequestQueue queue = Volley.newRequestQueue(DetailSuratInternalActivity.this);
                        queue.add(stringRequest);

                    }
                });
                btSave.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        String Url = Konstant.URL + "apisek/updateData.php";
                        final ProgressDialog progressDialog = new ProgressDialog(DetailSuratInternalActivity.this);
                        progressDialog.setMessage("loading..");
                        progressDialog.show();
                        StringRequest stringRequest = new StringRequest(Request.Method.POST, Url, new Response.Listener<String>() {

                            @Override
                            public void onResponse(String response) {
                                Log.d("respon", response);
                                try {
                                    JSONObject json = new JSONObject(response);
                                    String hasil = json.getString("success");
                                    if (hasil.equalsIgnoreCase("true")) {
                                        AlertDialog.Builder la = new AlertDialog.Builder(DetailSuratInternalActivity.this);
                                        la.setTitle("Terima Kasih");
                                        la.setMessage("");
                                        la.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                                    @Override
                                                    public void onClick(DialogInterface dialogInterface, int i) {
                                                        startActivity(new Intent(DetailSuratInternalActivity.this, DetailSuratInternalActivity.class));
                                                    }
                                                }
                                        );
                                        la.show();

                                        progressDialog.dismiss();
                                    }


                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }

                            }
                        }, new Response.ErrorListener() {
                            @Override
                            public void onErrorResponse(VolleyError error) {
                                Toast.makeText(DetailSuratInternalActivity.this, "Data gagal di kitim", Toast.LENGTH_SHORT).show();
                            }
                        }) {
                            @Override
                            protected Map<String, String> getParams() throws AuthFailureError {
                                HashMap<String, String> param = new HashMap<>();
                                param.put("urutan_ke", edNo.getText().toString());
                                param.put("nomor_dokumen", edNo.getText().toString());
                                param.put("pengirim", edUnit.getText().toString());
                                param.put("perihal", edDok.getText().toString());
                                param.put("dest_direksi_id", edTujuan1.getText().toString());
                                param.put("id_user", sesi.getIdUser());
                                param.put("tipe_dok_id", "1");
                                param.put("id", map.getId());
//                        param.put("deposit_jumlah",edTgl.getText().toString());
                                return param;

                            }
                        };
                        RequestQueue queue = Volley.newRequestQueue(DetailSuratInternalActivity.this);
                        queue.add(stringRequest);

                    }
                });


                dialog.show();
            }
        });

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                tampilDialog();
            }
        });
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }
//5. copy paste aja ini

    private void getDataAdmin() {
        String url = Konstant.URL + "apisek/getDataAdmin.php";

        StringRequest stringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {


                try {
                    data.clear();
                    JSONObject jsonObject = new JSONObject(response);
                    JSONArray jsonArray = jsonObject.getJSONArray("tamu");
                    Log.d("response ", response);
                    for (int a = 0; a < jsonArray.length(); a++) {

                        JSONObject json = jsonArray.getJSONObject(a);

                        // proses memasukkan masing2 field ke setter getter model
                        Data u = new Data();
                        u.setUrutan(json.getString("urutan_ke"));
                        u.setDeskripsi(json.getString("description"));
                        u.setId(json.getString("id"));
                        u.setDireksi(json.getString("jabatan_direksi"));
                        u.setNo_dok(json.getString("nomor_dokumen"));
                        u.setTgl_masuk(json.getString("tgl_masuk"));
                        u.setPengirim(json.getString("pengirim"));
//                        u.setPengirim(json.getString("tanggal"));
                        u.setPerihal(json.getString("perihal"));

                        data.add(u);
                        CustemAdapter adapter = new CustemAdapter(DetailSuratInternalActivity.this, data);
                        lsOrder.setAdapter(adapter);
                        mSwipeRefreshLayout.setRefreshing(false);
//                        progressDialog.dismiss();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {


            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                HashMap<String, String> params = new HashMap<>();
                params.put("nip", sesi.getNama());
                params.put("tipe_dok_id", "1");
                //   params.put("idorder", booking.getIdbook());
                //  params.put("f_type",String.valueOf(type));

                return params;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);

    }

    private void tampilDialog() {
        final Dialog dialog = new Dialog(this);
        dialog.setContentView(R.layout.insert_data);

        edNo = (EditText) dialog.findViewById(R.id.NomorUrutDokumen);
        edUnit = (EditText) dialog.findViewById(R.id.UnitPengirim);
        edTgl = (EditText) dialog.findViewById(R.id.TanggalMasukDokumen);
        edDok = (EditText) dialog.findViewById(R.id.Dokumen);
        edJenDok = (EditText) dialog.findViewById(R.id.JenisDokumen);
        edTujuan1 = (EditText) dialog.findViewById(R.id.TujuanPertama);
        edTujuan2 = (EditText) dialog.findViewById(R.id.TujuanKedua);
        edTujuan3 = (EditText) dialog.findViewById(R.id.TujuanKetiga);
        edTujuan4 = (EditText) dialog.findViewById(R.id.TujuanKeempat);
        btSave = (Button) dialog.findViewById(R.id.Simpan);

        btSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String Url = Konstant.URL + "apisek/insertData.php";
                final ProgressDialog progressDialog = new ProgressDialog(DetailSuratInternalActivity.this);
                progressDialog.setMessage("loading..");
                progressDialog.show();
                StringRequest stringRequest = new StringRequest(Request.Method.POST, Url, new Response.Listener<String>() {

                    @Override
                    public void onResponse(String response) {
                        Log.d("respon", response);
                        try {
                            JSONObject json = new JSONObject(response);
                            String hasil = json.getString("success");
                            if (hasil.equalsIgnoreCase("true")) {
                                AlertDialog.Builder la = new AlertDialog.Builder(DetailSuratInternalActivity.this);
                                la.setTitle("Terima Kasih");
                                la.setMessage("");
                                la.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                            @Override
                                            public void onClick(DialogInterface dialogInterface, int i) {
                                                startActivity(new Intent(DetailSuratInternalActivity.this, DetailSuratInternalActivity.class));
                                            }
                                        }
                                );
                                la.show();

                                progressDialog.dismiss();
                            }


                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(DetailSuratInternalActivity.this, "Data gagal di kitim", Toast.LENGTH_SHORT).show();
                    }
                }) {
                    Random rand = new Random();

                    int n = rand.nextInt(50) + 1;

                    @Override
                    protected Map<String, String> getParams() throws AuthFailureError {

                        HashMap<String, String> param = new HashMap<>();
                        param.put("id_user", sesi.getIdUser());
                        param.put("nomor_dokumen", n + "/SMI/" + sesi.getNama() + "/2018");
                            param.put("perihal", edDok.getText().toString());
                        param.put("pengirim", edUnit.getText().toString());
                        param.put("urutan_ke", edNo.getText().toString());
                        param.put("dest_direksi_id", edTujuan1.getText().toString());
//                param.put("konfirmasi_nama", konfirmasinama.getText().toString());
//                param.put("kon_bank", textspinner.getText().toString());
//                param.put("kon_rekening", konfirmasirekening.getText().toString());
//                param.put("deposit_driver", sesi.getIdUser());
                        return param;

                    }
                };
                RequestQueue queue = Volley.newRequestQueue(DetailSuratInternalActivity.this);
                queue.add(stringRequest);


            }
        });
        dialog.show();
    }

    private void addData() {


    }

    private void getData() {
        String url = Konstant.URL + "apisek/getData.php";

        StringRequest stringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {


                try {
                    data.clear();
                    JSONObject jsonObject = new JSONObject(response);
                    JSONArray jsonArray = jsonObject.getJSONArray("tamu");
                    Log.d("response ", response);
                    for (int a = 0; a < jsonArray.length(); a++) {

                        JSONObject json = jsonArray.getJSONObject(a);

                        // proses memasukkan masing2 field ke setter getter model

                        // proses memasukkan masing2 field ke setter getter model
                        Data u = new Data();
                        u.setUrutan(json.getString("urutan_ke"));
                        u.setDeskripsi(json.getString("description"));
                        u.setId(json.getString("id"));
                        u.setDireksi(json.getString("jabatan_direksi"));
                        u.setNo_dok(json.getString("nomor_dokumen"));
                        u.setTgl_masuk(json.getString("tgl_masuk"));
                        u.setPengirim(json.getString("pengirim"));
//                        u.setPengirim(json.getString("tanggal"));
                        u.setPerihal(json.getString("perihal"));

                        data.add(u);
                        CustemAdapter adapter = new CustemAdapter(DetailSuratInternalActivity.this, data);
                        lsOrder.setAdapter(adapter);
                        mSwipeRefreshLayout.setRefreshing(false);
//                        progressDialog.dismiss();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {


            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                HashMap<String, String> params = new HashMap<>();
                params.put("nip", sesi.getNama());
                params.put("tipe_dok_id", "1");
                //   params.put("idorder", booking.getIdbook());
                //  params.put("f_type",String.valueOf(type));

                return params;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }

    @OnClick(R.id.Simpan)
    public void onViewClicked() {
    }

    //
    @Override
    public void onRefresh() {
        if (status) {
            getData();
        } else if (!status) {
            getDataAdmin();
        }
    }
}
