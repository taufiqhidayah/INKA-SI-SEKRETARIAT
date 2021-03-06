package com.taufiqhidayah.tasekretaris;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
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

public class DetailSuratMemoActivity extends AppCompatActivity implements SwipeRefreshLayout.OnRefreshListener {
    public static ArrayList<Data> data;
    SessionManager sesi;
    Data user;
    ListView lsOrder;
    EditText edNoUrut, edTglMasuk,edUnitPengirim,edNomerMemo,edDok,edJenisDok,edT1,edT2,edT3,edT4;
    Button btSave,btDel;
    SwipeRefreshLayout mSwipeRefreshLayout;

    boolean status = true;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_surat_memo);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);

        lsOrder = (ListView) findViewById(R.id.lsMemo);
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

                final Dialog dialog = new Dialog(DetailSuratMemoActivity.this);
                dialog.setContentView(R.layout.insert_memo);



                edNoUrut = (EditText) dialog.findViewById(R.id.memNomorUrutDokumen);
                edTglMasuk = (EditText) dialog.findViewById(R.id.memTanggalMasukDokumen);
                edUnitPengirim = (EditText) dialog.findViewById(R.id.memUnitPengirim);
                edNomerMemo = (EditText) dialog.findViewById(R.id.memNomorMemo);
                edDok = (EditText) dialog.findViewById(R.id.memDokumen);
                edJenisDok = (EditText) dialog.findViewById(R.id.memJenisDokumen);
                edT1 = (EditText) dialog.findViewById(R.id.memTujuanPertama);
                edT2 = (EditText) dialog.findViewById(R.id.memTujuanKedua);
                edT3 = (EditText) dialog.findViewById(R.id.memTujuanKetiga);
                edT4 = (EditText) dialog.findViewById(R.id.memTujuanKeempat);
                btSave = (Button) dialog.findViewById(R.id.memSimpan);
                btDel =(Button)dialog.findViewById(R.id.memDel);
                final Data map = data.get(position);

                edTglMasuk.setText(map.getTgl_masuk());
                edNomerMemo.setText(map.getNo_dok());
                edT1.setText(map.getDireksi());
                edNoUrut.setText(map.getNo_dok());
                edUnitPengirim.setText(map.getPengirim());
                edDok.setText(map.getPerihal());

                btSave.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        String Url =  Konstant.URL +"apisek/updateData.php";
                        final ProgressDialog progressDialog = new ProgressDialog(DetailSuratMemoActivity.this);
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
                                        AlertDialog.Builder la = new AlertDialog.Builder(DetailSuratMemoActivity.this);
                                        la.setTitle("Terima Kasih");
                                        la.setMessage("");
                                        la.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                                    @Override
                                                    public void onClick(DialogInterface dialogInterface, int i) {
                                                        startActivity(new Intent(DetailSuratMemoActivity.this, DetailSuratMemoActivity.class));
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
                                Toast.makeText(DetailSuratMemoActivity.this, "Data gagal di kitim", Toast.LENGTH_SHORT).show();
                            }
                        }) {
                            @Override
                            protected Map<String, String> getParams() throws AuthFailureError {
                                HashMap<String, String> param = new HashMap<>();
                                param.put("id_user",sesi.getIdUser());
                                param.put("nomor_dokumen",map.getNo_dok());
                                param.put("perihal", edDok.toString());
                                param.put("pengirim", edUnitPengirim.getText().toString());
                                param.put("urutan_ke", edNoUrut.getText().toString());
                                param.put("dest_direksi_id",edT1.getText().toString());
                                param.put("tipe_dok_id", "3");
                                param.put("id",map.getId());;
//                        param.put("deposit_jumlah",edTgl.getText().toString());
                                return param;

                            }
                        };
                        RequestQueue queue = Volley.newRequestQueue(DetailSuratMemoActivity.this);
                        queue.add(stringRequest);

                    }
                });


                btDel.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        String Url =  Konstant.URL +"apisek/deleteData.php";
                        final ProgressDialog progressDialog = new ProgressDialog(DetailSuratMemoActivity.this);
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
                                        AlertDialog.Builder la = new AlertDialog.Builder(DetailSuratMemoActivity.this);
                                        la.setTitle("Terima Kasih");
                                        la.setMessage("");
                                        la.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                                    @Override
                                                    public void onClick(DialogInterface dialogInterface, int i) {
                                                        startActivity(new Intent(DetailSuratMemoActivity.this, DetailSuratMemoActivity.class));
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
                                Toast.makeText(DetailSuratMemoActivity.this, "Data gagal di kitim", Toast.LENGTH_SHORT).show();
                            }
                        }) {
                            @Override
                            protected Map<String, String> getParams() throws AuthFailureError {
                                HashMap<String, String> param = new HashMap<>();
                                param.put("id",map.getId());
//                        param.put("deposit_jumlah",edTgl.getText().toString());
                                return param;

                            }
                        };
                        RequestQueue queue = Volley.newRequestQueue(DetailSuratMemoActivity.this);
                        queue.add(stringRequest);

                    }
                });

                dialog.show();
            }
        });

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AddData();
            }
        });
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

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
                        CustemAdapterMemo adapter = new CustemAdapterMemo(DetailSuratMemoActivity.this, data);
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

    private void AddData() {

        final Dialog dialog = new Dialog(this);
        dialog.setContentView(R.layout.insert_memo);



        edNoUrut = (EditText) dialog.findViewById(R.id.memNomorUrutDokumen);
        edTglMasuk = (EditText) dialog.findViewById(R.id.memTanggalMasukDokumen);
        edUnitPengirim = (EditText) dialog.findViewById(R.id.memUnitPengirim);
        edNomerMemo = (EditText) dialog.findViewById(R.id.memNomorMemo);
        edDok = (EditText) dialog.findViewById(R.id.memDokumen);
        edJenisDok = (EditText) dialog.findViewById(R.id.memJenisDokumen);
        edT1 = (EditText) dialog.findViewById(R.id.memTujuanPertama);
        edT2 = (EditText) dialog.findViewById(R.id.memTujuanKedua);
        edT3 = (EditText) dialog.findViewById(R.id.memTujuanKetiga);
        edT4 = (EditText) dialog.findViewById(R.id.memTujuanKeempat);
        btSave = (Button) dialog.findViewById(R.id.memSimpan);
        btSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String Url =  Konstant.URL +"apisek/insertMemo.php";
                final ProgressDialog progressDialog = new ProgressDialog(DetailSuratMemoActivity.this);
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
                                AlertDialog.Builder la = new AlertDialog.Builder(DetailSuratMemoActivity.this);
                                la.setTitle("Terima Kasih");
                                la.setMessage("");
                                la.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                            @Override
                                            public void onClick(DialogInterface dialogInterface, int i) {
                                                startActivity(new Intent(DetailSuratMemoActivity.this, DetailSuratMemoActivity.class));
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
                        Toast.makeText(DetailSuratMemoActivity.this, "Data gagal di kitim", Toast.LENGTH_SHORT).show();
                    }
                }) {
                    Random rand = new Random();

                    int  n = rand.nextInt(50) + 1;
                    @Override

                    protected Map<String, String> getParams() throws AuthFailureError {
                        HashMap<String, String> param = new HashMap<>();
                        param.put("id_user",sesi.getIdUser());
                        param.put("nomor_dokumen",edNomerMemo.getText().toString());
                        param.put("perihal", edDok.getText().toString());
                        param.put("pengirim", edUnitPengirim.getText().toString());
                        param.put("urutan_ke", edNoUrut.getText().toString());
                        param.put("dest_direksi_id",edT1.getText().toString());
                        param.put("tipe_dok_id", "2");
//                param.put("konfirmasi_nama", konfirmasinama.getText().toString());
//                param.put("kon_bank", textspinner.getText().toString());
//                param.put("kon_rekening", konfirmasirekening.getText().toString());
//                param.put("deposit_driver", sesi.getIdUser());
                        return param;

                    }
                };
                RequestQueue queue = Volley.newRequestQueue(DetailSuratMemoActivity.this);
                queue.add(stringRequest);


            }
        });
        dialog.show();
    }

    private void getData() {
        String url =  Konstant.URL +"apisek/getData.php";

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
                        CustemAdapterMemo adapter = new CustemAdapterMemo(DetailSuratMemoActivity.this, data);
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
                params.put("tipe_dok_id","3");


                //   params.put("idorder", booking.getIdbook());
                //  params.put("f_type",String.valueOf(type));

                return params;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }

    @Override
    public void onRefresh()
    {
        if (status) {
            getData();
        } else if (!status) {
            getDataAdmin();
        }
    }
}
