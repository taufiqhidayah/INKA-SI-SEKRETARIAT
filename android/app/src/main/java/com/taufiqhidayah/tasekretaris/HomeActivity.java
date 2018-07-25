package com.taufiqhidayah.tasekretaris;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class HomeActivity extends AppCompatActivity {
    @BindView(R.id.btlogout)
    Button btlogout;
    @BindView(R.id.btSrInternal)
    Button btSrInternal;
    @BindView(R.id.btSrExternal)
    Button btSrExternal;
    @BindView(R.id.btSrMemo)
    Button btSrMemo;
    private Button bt;
    private ButterKnife butterKnife;
    private  SessionManager sessionManager;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        sessionManager = new SessionManager(this);
        butterKnife.bind(this);
        final SessionManager sessionManager = new SessionManager(this);
        bt = (Button) findViewById(R.id.btlogout);
        bt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sessionManager.logout();
                startActivity(new Intent(getApplicationContext(), MainActivity.class));
            }
        });
    }

    @OnClick({R.id.btSrInternal, R.id.btSrExternal, R.id.btSrMemo})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.btSrInternal:
               Intent i = new Intent(getApplicationContext(),DetailSuratInternalActivity.class);
               startActivity(i);
                break;
            case R.id.btSrExternal:
                break;
            case R.id.btSrMemo:
                break;
        }
    }
}
