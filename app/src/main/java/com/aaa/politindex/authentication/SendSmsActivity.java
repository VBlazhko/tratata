package com.aaa.politindex.authentication;

import android.content.Intent;
import android.graphics.Typeface;
import android.os.Handler;
import android.support.v4.content.res.ResourcesCompat;
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
import com.aaa.politindex.main_screen_for_auth_user.MainAuthUserActivity;
import com.alimuzaffar.lib.pin.PinEntryEditText;
import com.chaos.view.PinView;
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

    @BindView(R.id.back)
    TextView mBack;
    @BindView(R.id.spinner)
    CamomileSpinner mSpinner;

    @BindView(R.id.phoneNumber)
    TextView mPhoneNumber;

    @BindView(R.id.sendSms)
    TextView mSendSMS;
    @BindView(R.id.title)
    TextView mTitle;
    @BindView(R.id.btnSend)
    ImageView mBtnSend;
    @BindView(R.id.pinView)
    PinView pinCode;


    Handler myHandler;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_send_sms);
        mUnbinder = ButterKnife.bind(this);
        myHandler = new Handler();
        mPhoneNumber.setText("+" + getIntent().getExtras().getString("phone"));
        mSpinner.start();
        stopwatch(myHandler, 60);

        pinCode.setLineColor(getResources().getColor(R.color.grayDefault));
        pinCode.setAnimationEnable(true);


        mBack.setText(App.getApp().getValue("back_button"));
        mTitle.setText(App.getApp().getValue("title_sms"));
        mSendSMS.setText(App.getApp().getValue("btn_send_sms").replaceAll("\\\\n", "\n"));

    }


    public void stopwatch(final Handler handler, int number) {
        final int newCounter = --number;
        if (newCounter == 0) return;
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
        Map<String, String> params = new HashMap<>();

        params.put("id_user", App.getApp().getSharedPreferences(Const.ID_USER));
        params.put("token", App.getApp().getSharedPreferences(Const.TOKEN));
        params.put("phone", mPhoneNumber.getText().toString().replace("+", "").replace(" ", ""));

        RequestAuth.getInstance().getResult("v1/phone/auth.api", params, new Request.CallBack() {
            @Override
            public void onResponse(JSONObject jsonObject) {
                showSend(true);
                if (jsonObject.optString("status").equals("OK")) {
                    showSend(true);
                    stopwatch(myHandler, 60);
                } else if (jsonObject.optString("status").equals("limitauthorization")) {
                    makeToast(App.getApp().getValue("sms_error_limit"));
                    showSend(true);
                    stopwatch(myHandler, 60);
                } else if (jsonObject.optString("status").equals("smsdidntsend")) {
                    makeToast(App.getApp().getValue("sms_error_fail"));
                    showSend(true);
                    stopwatch(myHandler, 60);
                } else if (jsonObject.optString("status").equals("failuser")) {
                    makeToast("failuser");
                    showSend(true);
                    stopwatch(myHandler, 60);
                }
            }
        });

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

    @OnTextChanged(R.id.pinView)
    protected void pinEntry() {

        pinCode.setTextColor(getResources().getColor(R.color.blackText));
        if (pinCode.getText().toString().length() == 4) {
            Log.w(TAG, "pinEntry: " + pinCode.toString());
            showSend(false);
            RequestAuth.getInstance().getResultSms("v1/sms/auth.api", Integer.parseInt(App.getApp().getSharedPreferences(Const.ID_USER)),
                    App.getApp().getSharedPreferences(Const.TOKEN),
                    Integer.parseInt(pinCode.getText().toString()),
                    Md5Helper.md5(App.getApp().getSharedPreferences(Const.ID_USER) + ":" + App.getApp().getSharedPreferences(Const.TOKEN) + ":" + pinCode.getText().toString()), new Request.CallBack() {
                        @Override
                        public void onResponse(JSONObject jsonObject) {
                            if (jsonObject.optString("status").equals("OK")) {
                                App.getApp().setSharedPreferences(Const.ID_TOKEN, jsonObject.optString("idToken"));
                                Const.setLog("auth ok start main auth activity");
                                startActivity(new Intent(SendSmsActivity.this, MainAuthUserActivity.class));
                                finish();
                            } else if (jsonObject.optString("status").equals("wrongsmscode")) {
                                pinCode.setTextColor(getResources().getColor(R.color.redDefault));
                                showSend(true);
                            } else if (jsonObject.optString("status").equals("error")) {
                                if (this != null) {
                                    Toast toast = Toast.makeText(getApplicationContext(), jsonObject.toString(), Toast.LENGTH_SHORT); //?????????????????????????????
                                    toast.show();
                                    showSend(true);
                                }
                            }
                        }
                    });
        }
    }

    private void makeToast(String errorText) {
        if (this != null) {
            Toast toast = Toast.makeText(this,
                    errorText, Toast.LENGTH_SHORT);
            toast.show();
            showSend(true);
        }
    }


}
