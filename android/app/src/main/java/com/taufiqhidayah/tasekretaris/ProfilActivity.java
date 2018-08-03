package com.taufiqhidayah.tasekretaris;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class ProfilActivity extends AppCompatActivity {
    ButterKnife butterKnife;

    @BindView(R.id.tvNam)
    TextView tvNam;
    @BindView(R.id.btlog)
    Button btlog;
SessionManager sessionManager;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profil);
        ButterKnife.bind(this);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        butterKnife.bind(this);
       sessionManager = new SessionManager(this);
       tvNam.setText(sessionManager.getNama());


        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    @OnClick(R.id.btlog)
    public void onViewClicked() {
        sessionManager.logout();
        startActivity(new Intent(getApplicationContext(), MainActivity.class));
    }
}
