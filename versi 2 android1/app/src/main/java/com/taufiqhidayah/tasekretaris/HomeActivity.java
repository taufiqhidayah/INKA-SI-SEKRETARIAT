package com.taufiqhidayah.tasekretaris;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class HomeActivity extends AppCompatActivity {

    @BindView(R.id.suratInternal)
    LinearLayout suratInternal;
    @BindView(R.id.suratMemo)
    LinearLayout suratMemo;
    @BindView(R.id.suratExternal)
    LinearLayout suratExternal;
    @BindView(R.id.about)
    LinearLayout about;
    private ButterKnife butterKnife;
    private SessionManager sessionManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        ButterKnife.bind(this);
        sessionManager = new SessionManager(this);
        butterKnife.bind(this);
        final SessionManager sessionManager = new SessionManager(this);
    }
    @OnClick({R.id.suratInternal, R.id.suratMemo, R.id.suratExternal, R.id.about})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.suratInternal:
                Intent i = new Intent(getApplicationContext(), DetailSuratInternalActivity.class);
                startActivity(i);
                break;
            case R.id.suratMemo:
                Intent q = new Intent(getApplicationContext(), DetailSuratMemoActivity.class);
                startActivity(q);
                break;
            case R.id.suratExternal:


                Intent o = new Intent(getApplicationContext(), DetailSuratExternalActivity.class);
                startActivity(o);
                break;
            case R.id.about:
                Intent k = new Intent(getApplicationContext(),ProfilActivity.class);
                startActivity(k);
                break;
        }
    }
}
