package com.taufiqhidayah.tasekretaris;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
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

import butterknife.OnClick;

public class DetailSuratInternalActivity extends AppCompatActivity {
    public static ArrayList<Data> data;
    SessionManager sesi;
    Data user;
    ListView lsOrder;
    EditText edNo, edTgl, edUnit, edDok, edJenDok, edTujuan1, edTujuan2, edTujuan3, edTujuan4;
    Button btSave,btDel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_surat);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        lsOrder = (ListView) findViewById(R.id.lsView);
        sesi = new SessionManager(this);
        data = new ArrayList<>();
        user = new Data();
        getData();
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
                btDel =(Button)dialog.findViewById(R.id.btdelete);
                final Data map = data.get(position);
                Toast.makeText(DetailSuratInternalActivity.this, map.getId(), Toast.LENGTH_SHORT).show();
                edDok.setText(map.getNama_dok());
                edNo.setText(map.getNo_dok());
                edUnit.setText(map.getPerihal());
                edTgl.setText(map.getTgl_masuk());
                edJenDok.setText(map.getPerihal());
                edTujuan1.setText(map.getPengirim());

                btDel.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        String Url = "http://192.168.1.8/apisek/deleteData.php";
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
                                param.put("id",map.getId());
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
                        String Url = "http://192.168.1.8/apisek/updateData.php";
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
                                param.put("nomor_dokumen", edNo.getText().toString());
                                param.put("nama_dokumen", edDok.getText().toString());
                                param.put("perihal", edJenDok.getText().toString());
                                param.put("pengirim", edTujuan1.getText().toString());
                                param.put("penerima", edUnit.getText().toString());
                                param.put("id",map.getId());
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
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                tampilDialog();
            }
        });
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
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
                String Url = "http://192.168.1.8/apisek/insertData.php";
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
                        param.put("nomor_dokumen", edNo.getText().toString());
                        param.put("penerima", edUnit.getText().toString());
//                param.put("deposit_jumlah",edTgl.getText().toString());
                        param.put("nama_dokumen", edDok.getText().toString());
                        param.put("perihal", edJenDok.getText().toString());
                        param.put("pengirim", edTujuan1.getText().toString());
                        param.put("pengirim", edTujuan2.getText().toString());
                        param.put("pengirim", edTujuan3.getText().toString());
                        param.put("pengirim", edTujuan4.getText().toString());
                        param.put("id_user", sesi.getIdUser());
                        param.put("tipe_dok_id", "1");
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
        String url = "http://192.168.1.8/apisek/getData.php";

        StringRequest stringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {


                try {
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
                        CustemAdapter adapter = new CustemAdapter(DetailSuratInternalActivity.this, data);
                        lsOrder.setAdapter(adapter);
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
}
