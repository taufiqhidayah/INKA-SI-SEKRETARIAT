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

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class DetailSuratExternalActivity extends AppCompatActivity implements SwipeRefreshLayout.OnRefreshListener {
    ButterKnife butterKnife;
    @BindView(R.id.lsSrEx)
    ListView lsSrEx;
    SessionManager sessionManager;
    public  static ArrayList<Data> data;
    Data user;
    EditText edNoUrut, edTglMasuk,edNamaPengirim,edTglDoc,edNoSurat,edPerihal,edJenisDok,EdT1,edT2,edT3,edT4;
    Button btSave,btDel;
    SwipeRefreshLayout mSwipeRefreshLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_surat_external);
        ButterKnife.bind(this);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        butterKnife.bind(this);
        mSwipeRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.swipe);
        mSwipeRefreshLayout.setOnRefreshListener(this);
        sessionManager = new SessionManager(this);
        data = new ArrayList<>();
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);

        user = new Data();
        if (!sessionManager.getIdUser().equals("2") && !sessionManager.getIdUser().equals("3") && !sessionManager.getIdUser().equals("4") && !sessionManager.getIdUser().equals("5") )
        {
//        if (sesi.getNama()!= "sekt_dirut" && sesi.getNama()!="sekt_dirkeu" && sesi.getNama()!="sekt_dirkomtek" & sesi.getNama()!= "sekt_dirprod"){
            lsSrEx.setOnItemClickListener(null);
            lsSrEx.setEnabled(false);
            fab.setVisibility(View.GONE);
            fab.setOnClickListener(null);
        }
        getData();
        lsSrEx.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Dialog dialog = new Dialog(DetailSuratExternalActivity.this);
                dialog.setContentView(R.layout.insert_ext);

                edNoUrut = (EditText) dialog.findViewById(R.id.exNoUrutDokumen);
                edTglMasuk = (EditText) dialog.findViewById(R.id.exTanggalmasukDokumen);
                edNamaPengirim = (EditText) dialog.findViewById(R.id.exNamaPengirim);
                edTglDoc = (EditText) dialog.findViewById(R.id.TanggalDokumen);
                edNoSurat = (EditText) dialog.findViewById(R.id.exNomorSurat);
                edPerihal = (EditText) dialog.findViewById(R.id.exPerihal);
                edJenisDok = (EditText) dialog.findViewById(R.id.exJenisDokumen);
                EdT1 = (EditText) dialog.findViewById(R.id.exTujuanPertama);
                edT2 = (EditText) dialog.findViewById(R.id.exTujuanKedua);
                edT3 = (EditText) dialog.findViewById(R.id.exTujuanKetiga);
                edT4 = (EditText) dialog.findViewById(R.id.exTujuanKeempat);
                btSave = (Button) dialog.findViewById(R.id.exSimpan);
                btDel =(Button)dialog.findViewById(R.id.exdelete);


                final Data map = data.get(position);
                Toast.makeText(DetailSuratExternalActivity.this, map.getId(), Toast.LENGTH_SHORT).show();
                edNoUrut.setText(map.getNo_dok());
                edTglMasuk.setText(map.getTgl_masuk());
                edNamaPengirim.setText(map.getPengirim());
                edTglDoc.setText(map.getTgl_dok_reff());
                edNoUrut.setText(map.getNo_dok());
                edJenisDok.setText(map.getId());
                edTglDoc.setText(map.getPenerima());


                btDel.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        String Url =  Konstant.URL +"apisek/deleteData.php";
                        final ProgressDialog progressDialog = new ProgressDialog(DetailSuratExternalActivity.this);
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
                                        AlertDialog.Builder la = new AlertDialog.Builder(DetailSuratExternalActivity.this);
                                        la.setTitle("Terima Kasih");
                                        la.setMessage("");
                                        la.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                                    @Override
                                                    public void onClick(DialogInterface dialogInterface, int i) {
                                                        startActivity(new Intent(DetailSuratExternalActivity.this, DetailSuratExternalActivity.class));
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
                                Toast.makeText(DetailSuratExternalActivity.this, "Data gagal di kitim", Toast.LENGTH_SHORT).show();
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
                        RequestQueue queue = Volley.newRequestQueue(DetailSuratExternalActivity.this);
                        queue.add(stringRequest);

                    }
                });
                btSave.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        String Url =  Konstant.URL +"apisek/updateEx.php";
                        final ProgressDialog progressDialog = new ProgressDialog(DetailSuratExternalActivity.this);
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
                                        AlertDialog.Builder la = new AlertDialog.Builder(DetailSuratExternalActivity.this);
                                        la.setTitle("Terima Kasih");
                                        la.setMessage("");
                                        la.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                                    @Override
                                                    public void onClick(DialogInterface dialogInterface, int i) {
                                                        startActivity(new Intent(DetailSuratExternalActivity.this, DetailSuratExternalActivity.class));
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
                                Toast.makeText(DetailSuratExternalActivity.this, "Data gagal di kitim", Toast.LENGTH_SHORT).show();
                            }
                        }) {
                            @Override
                            protected Map<String, String> getParams() throws AuthFailureError {
                                HashMap<String, String> param = new HashMap<>();
                                param.put("nomor_dokumen", edNoUrut.getText().toString()+ "/SME/"+sessionManager.getNama()+"2018");
                                param.put("penerima", edNamaPengirim.getText().toString());
                                param.put("nama_dokumen", edJenisDok.getText().toString());
                                param.put("perihal", edPerihal.getText().toString());
                                param.put("pengirim", edT2.getText().toString());
                                param.put("pengirim", edT3.getText().toString());
                                param.put("pengirim", edT4.getText().toString());
                                param.put("pengirim", edT4.getText().toString());
                                param.put("pengirim", EdT1.getText().toString());

                                param.put("id_user", sessionManager.getIdUser());
                                param.put("tipe_dok_id", "2");
                                param.put("id",map.getId());
                                return param;

                            }
                        };
                        RequestQueue queue = Volley.newRequestQueue(DetailSuratExternalActivity
                                .this);
                        queue.add(stringRequest);

                    }
                });



                dialog.show();
            }
        });
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addData();
            }
        });
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    private void addData() {
        final Dialog dialog = new Dialog(this);
        dialog.setContentView(R.layout.insert_ext);
        edNoUrut = (EditText) dialog.findViewById(R.id.exNoUrutDokumen);
        edTglMasuk = (EditText) dialog.findViewById(R.id.exTanggalmasukDokumen);
        edNamaPengirim = (EditText) dialog.findViewById(R.id.exNamaPengirim);
        edTglDoc = (EditText) dialog.findViewById(R.id.TanggalDokumen);
        edNoSurat = (EditText) dialog.findViewById(R.id.exNomorSurat);
        edPerihal = (EditText) dialog.findViewById(R.id.exPerihal);
        edJenisDok = (EditText) dialog.findViewById(R.id.exJenisDokumen);
        EdT1 = (EditText) dialog.findViewById(R.id.exTujuanPertama);
        edT2 = (EditText) dialog.findViewById(R.id.exTujuanKedua);
        edT3 = (EditText) dialog.findViewById(R.id.exTujuanKetiga);
        edT4 = (EditText) dialog.findViewById(R.id.exTujuanKeempat);
        btSave = (Button) dialog.findViewById(R.id.exSimpan);

        btSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String Url =  Konstant.URL +"apisek/insertExt.php";
                final ProgressDialog progressDialog = new ProgressDialog(DetailSuratExternalActivity.this);
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
                                AlertDialog.Builder la = new AlertDialog.Builder(DetailSuratExternalActivity.this);
                                la.setTitle("Terima Kasih");
                                la.setMessage("");
                                la.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                            @Override
                                            public void onClick(DialogInterface dialogInterface, int i) {
                                                startActivity(new Intent(DetailSuratExternalActivity.this, DetailSuratExternalActivity.class));
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
                        Toast.makeText(DetailSuratExternalActivity.this, "Data gagal di kitim", Toast.LENGTH_SHORT).show();
                    }
                }) {
                    @Override
                    protected Map<String, String> getParams() throws AuthFailureError {
                        HashMap<String, String> param = new HashMap<>();


//                        EditText edNoUrut, edTglMasuk,edNamaPengirim,edTglDoc,edNoSurat,edPerihal,edJenisDok,EdT1,edT2,edT3,edT4;
                        param.put("nomor_dokumen", edNoUrut.getText().toString());
                        param.put("penerima", edNamaPengirim.getText().toString());
//                param.put("deposit_jumlah",edTgl.getText().toString());
                        param.put("nama_dokumen", edJenisDok.getText().toString());
                        param.put("perihal", edPerihal.getText().toString());
                        param.put("pengirim", edT2.getText().toString());
                        param.put("pengirim", edT3.getText().toString());
                        param.put("pengirim", edT4.getText().toString());
                        param.put("pengirim", edT4.getText().toString());
                        param.put("pengirim", EdT1.getText().toString());

                        param.put("id_user", sessionManager.getIdUser());
                        param.put("tipe_dok_id", "2");
//                param.put("konfirmasi_nama", konfirmasinama.getText().toString());
//                param.put("kon_bank", textspinner.getText().toString());
//                param.put("kon_rekening", konfirmasirekening.getText().toString());
//                param.put("deposit_driver", sesi.getIdUser());
                        return param;

                    }
                };
                RequestQueue queue = Volley.newRequestQueue(DetailSuratExternalActivity.this);
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
                        u.setId(json.getString("id"));
                        u.setNo_dok(json.getString("nomor_dokumen"));
                        u.setTgl_masuk(json.getString("tgl_masuk"));
                        u.setTipe_dok(json.getString("tipe_dok_id"));
                        u.setNama_dok(json.getString("nama_dokumen"));
                        u.setPengirim(json.getString("pengirim"));
                        u.setPenerima(json.getString("penerima"));
//                        u.setPengirim(json.getString("tanggal"));
                        u.setPerihal(json.getString("perihal"));

                        data.add(u);
                        CustemAdapterEx adapter = new CustemAdapterEx(DetailSuratExternalActivity.this, data);
                        lsSrEx.setAdapter(adapter);
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
                params.put("nip", sessionManager.getNama());
                params.put("tipe_dok_id","2");
                //   params.put("idorder", booking.getIdbook());
                //  params.put("f_type",String.valueOf(type));

                return params;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }

    @Override
    public void onRefresh() {
        getData();
    }
}
