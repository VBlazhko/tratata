package com.aaa.politindex.authentication;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.aaa.politindex.App;
import com.aaa.politindex.BaseActivity;
import com.aaa.politindex.Const;
import com.aaa.politindex.R;
import com.aaa.politindex.connection.Request;
import com.aaa.politindex.connection.RequestAuth;
import com.aaa.politindex.main_screen.MainActivity;
import com.gmail.samehadar.iosdialog.CamomileSpinner;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import br.com.sapereaude.maskedEditText.MaskedEditText;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class AuthPhoneActivity extends BaseActivity {


    @BindView(R.id.icon_back)
    ImageView mIconBack;
    @BindView(R.id.back)
    TextView mBack;
    @BindView(R.id.sendSms)
    TextView mSendSMS;
    @BindView(R.id.btnSend)
    ImageView mBtnSend;
    @BindView(R.id.phoneInput)
    MaskedEditText mEditPhone;
    @BindView(R.id.spinner)
    CamomileSpinner mSpinner;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_auth_phone);
        mUnbinder = ButterKnife.bind(this);
        mSpinner.start();





    }

    @OnClick({R.id.btnSend,R.id.sendSms})
    protected void clickSend(){
        showSend(false);
        Map<String, String> params = new HashMap<>();
        params.put("id_user", App.getApp().getSharedPreferences(Const.ID_USER));
        params.put("token", App.getApp().getSharedPreferences(Const.TOKEN));
        params.put("phone", mEditPhone.getText().toString().replace("+","").replace(" ","") );
        Log.w(TAG, "clickSend: "+  mEditPhone.getText().toString().replace("+",""));
        RequestAuth.getInstance().getResult("v1/phone/auth.api", params, new Request.CallBack() {
            @Override
            public void onResponse(JSONObject jsonObject) {
                showSend(true);
                Log.w("log", "onResponse: " + jsonObject);
                if (jsonObject.optString("status").equals("OK")){
                    startActivity(new Intent(AuthPhoneActivity.this, SendSmsActivity.class).putExtra("phone", mEditPhone.getText().toString()));
                }
            }
        });

    }

    @OnClick({R.id.back,R.id.icon_back})
    protected void clickBack(){
        onBackPressed();
    }

    private void showSend(boolean status){
        if(mSpinner!=null && mBtnSend!=null&& mSendSMS!=null){
            mSpinner.setVisibility(status?View.INVISIBLE:View.VISIBLE);
            mBtnSend.setVisibility(!status?View.INVISIBLE:View.VISIBLE);
            mSendSMS.setVisibility(!status?View.INVISIBLE:View.VISIBLE);
        }
    }
}
