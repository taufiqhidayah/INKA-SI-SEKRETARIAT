package com.taufiqhidayah.tasekretaris;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity {
    SessionManager sessionManager;
    EditText edname, edpassword;
    Button login;
    ProgressBar loading;
    static String URL_LOGIN = "http://192.168.1.8/apisek/login.php";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        loading = findViewById(R.id.loading);
        edname = findViewById(R.id.name);
        edpassword = findViewById(R.id.password);
        login = findViewById(R.id.login);
        sessionManager = new SessionManager(this);
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Login();
            }
        });
    }


    private void Login() {
        loading.setVisibility(View.VISIBLE);
//        login.setVisibility(View.GONE);

        final String name = edname.getText().toString().trim();
        final String password = edname.getText().toString().trim();

        StringRequest stringRequest = new StringRequest(Request.Method.POST, URL_LOGIN, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.d("respon", response);
                try {
                    JSONObject json = new JSONObject(response);
                    String hasil = json.getString("success");
                    Toast.makeText(MainActivity.this, hasil, Toast.LENGTH_SHORT).show();
                    if (hasil.equalsIgnoreCase("true")) {

                        Intent i = new Intent(getApplicationContext(), HomeActivity.class);
                        startActivity(i);
                        sessionManager.cerateLoginSession(name);
//                                sessionManager.setNama(json.getString("nip"));
                        sessionManager.setNama(name);
                        sessionManager.setIduser(json.getString("id"));
                        Toast.makeText(MainActivity.this, "Register Success!", Toast.LENGTH_SHORT).show();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                    Toast.makeText(MainActivity.this, "Register Error!" + e.toString(), Toast.LENGTH_SHORT).show();
                    loading.setVisibility(View.GONE);
                    login.setVisibility(View.VISIBLE);
                }
            }
        },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(MainActivity.this, "Register Error!" + error.toString(), Toast.LENGTH_SHORT).show();
                        loading.setVisibility(View.GONE);
                        login.setVisibility(View.VISIBLE);
                    }
                }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("nip", edname.getText().toString());
                params.put("password", edpassword.getText().toString());
                return params;
            }
        };

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }
}
