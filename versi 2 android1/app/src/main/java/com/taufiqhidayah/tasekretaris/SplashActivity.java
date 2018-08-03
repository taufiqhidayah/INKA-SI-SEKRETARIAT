package com.taufiqhidayah.tasekretaris;

import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class SplashActivity extends AppCompatActivity {
    SessionManager sessionManager;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        sessionManager = new SessionManager(this);
        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                if (sessionManager.isLogin()){
                    startActivity(new Intent(getApplicationContext(),HomeActivity.class));
                    finish();
                }
                else {
                    startActivity(new Intent(getApplicationContext(),MainActivity.class));
                }
            }
        },3000);
    }
}
