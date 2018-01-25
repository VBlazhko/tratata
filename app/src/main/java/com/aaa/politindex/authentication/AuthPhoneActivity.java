package com.aaa.politindex.authentication;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.aaa.politindex.R;
import com.aaa.politindex.main_screen.MainActivity;

import butterknife.BindView;
import butterknife.ButterKnife;

public class AuthPhoneActivity extends AppCompatActivity {

    View.OnClickListener myListner;

    @BindView(R.id.icon_back)
    ImageView mIconBack;
    @BindView(R.id.back)
    TextView mBack;
    @BindView(R.id.sendSms)
    TextView mSendSMS;
    @BindView(R.id.btnSend)
    ImageView mBtnSend;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_auth_phone);
        ButterKnife.bind(this);



        myListner = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switch (v.getId()) {
                    case R.id.back :
                        onBackPressed();
                        break;
                    case R.id.icon_back :
                        onBackPressed();
                        break;
                    case R.id.btnSend :
                        Log.w("log", "onClick: "+"Send SMS" );
                        startActivity(new Intent(AuthPhoneActivity.this, SendSmsActivity.class));
                        break;
                    case R.id.sendSms :
                        Log.w("log", "onClick: "+"Send SMS" );
                        break;
                }
            }
        };

        mIconBack.setOnClickListener(myListner);
        mBack.setOnClickListener(myListner);
        mBtnSend.setOnClickListener(myListner);
        mSendSMS.setOnClickListener(myListner);

    }
}
