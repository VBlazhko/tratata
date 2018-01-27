package com.aaa.politindex.authentication;

import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.aaa.politindex.App;
import com.aaa.politindex.BaseActivity;
import com.aaa.politindex.Const;
import com.aaa.politindex.R;
import com.aaa.politindex.connection.Request;
import com.aaa.politindex.connection.RequestAuth;
import com.aaa.politindex.helper.Md5Helper;
import com.alimuzaffar.lib.pin.PinEntryEditText;
import com.gmail.samehadar.iosdialog.CamomileSpinner;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.OnTextChanged;

public class SendSmsActivity extends BaseActivity {

    @BindView(R.id.seconds)
    TextView mSeconds;

    @BindView(R.id.repeatedly)
    TextView mRepeatedly;

    @BindView(R.id.spinner)
    CamomileSpinner mSpinner;

    @BindView(R.id.phoneNumber)
    TextView mPhoneNumber;

    @BindView(R.id.sendSms)
    TextView mSendSMS;
    @BindView(R.id.btnSend)
    ImageView mBtnSend;
    @BindView(R.id.txt_pin_entry)
    PinEntryEditText pinCode;


    Handler myHandler;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_send_sms);
        mUnbinder = ButterKnife.bind(this);
        myHandler = new Handler();
        mPhoneNumber.setText(getIntent().getExtras().getString("phone"));
        mSpinner.start();
        stopwatch(myHandler, 60);


    }


    public void stopwatch(final Handler handler, int number) {
        final int newCounter = --number;
        if (newCounter == 0) {
            mSeconds.setVisibility(View.INVISIBLE);
            mRepeatedly.setVisibility(View.INVISIBLE);
            return;
        }

        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                if (mSeconds == null) return;
                mSeconds.setText(newCounter + "");
                stopwatch(handler, newCounter);
            }
        }, 1000);
    }

    @OnClick({R.id.btnSend, R.id.sendSms})
    protected void clickSend() {
        showSend(false);


    }


    @OnClick({R.id.back, R.id.icon_back})
    protected void clickBack() {
        onBackPressed();
    }


    private void showSend(boolean status) {
        if (mSpinner != null && mBtnSend != null && mSendSMS != null) {
            mSpinner.setVisibility(status ? View.INVISIBLE : View.VISIBLE);
            mBtnSend.setVisibility(!status ? View.INVISIBLE : View.VISIBLE);
            mSendSMS.setVisibility(!status ? View.INVISIBLE : View.VISIBLE);
        }
    }

    @OnTextChanged(R.id.txt_pin_entry)
    protected void pinEntry() {

        pinCode.setBackgroundTintList(getResources().getColorStateList(R.color.blackText));

        if (pinCode.getText().toString().length() == 4) {
            showSend(false);
            RequestAuth.getInstance().getResultSms("v1/sms/auth.api", Integer.parseInt(App.getApp().getSharedPreferences(Const.ID_USER)),
                    App.getApp().getSharedPreferences(Const.TOKEN),
                    Integer.parseInt(pinCode.getText().toString()),
                    Md5Helper.md5(App.getApp().getSharedPreferences(Const.ID_USER) + App.getApp().getSharedPreferences(Const.TOKEN)) + pinCode.getText().toString(), new Request.CallBack() {
                        // Md5Helper.md5(App.getApp().getSharedPreferences(Const.ID_USER) + ":" + App.getApp().getSharedPreferences(Const.TOKEN)) + ":" + pinCode.getText().toString(), new Request.CallBack() {
                        @Override
                        public void onResponse(JSONObject jsonObject) {
                            if (jsonObject.optString("status").equals("OK")) {

                            }else if(jsonObject.optString("status").equals("wrongsmscode")){
                                Log.w(TAG, "onResponse: "+"must change color to red" );
                                showSend(true);
                                pinCode.setTextColor(getResources().getColor(R.color.redDefault));
                            }else if(jsonObject.optString("status").equals("error")){
                                if(this!=null) {
                                    Toast toast = Toast.makeText(getApplicationContext(), jsonObject.toString(), Toast.LENGTH_SHORT); //?????????????????????????????
                                    toast.show();
                                    showSend(true);
                                }
                            }
                        }
                    });
        }
    }
}
