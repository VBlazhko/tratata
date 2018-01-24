package com.aaa.politindex;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.aaa.politindex.connection.RequestVK;
import com.vk.sdk.VKAccessToken;
import com.vk.sdk.VKCallback;
import com.vk.sdk.VKScope;
import com.vk.sdk.VKSdk;
import com.vk.sdk.api.VKError;
import com.vk.sdk.util.VKUtil;

public class AuthVKActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_auth_vk);
        VKSdk.login(this, VKScope.DIRECT);
        String[] fingerprints = VKUtil.getCertificateFingerprint(this, this.getPackageName());

        Log.w("log", "onCreate:  finger"+fingerprints.toString() );



        RequestVK.getInstance().getResult(null,null,null);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (!VKSdk.onActivityResult(requestCode, resultCode, data, new VKCallback<VKAccessToken>() {
            @Override
            public void onResult(VKAccessToken res) {
                Log.w("log", "onResult: -----------------    VK OK  TOKEN = "+res.accessToken);
                Log.w("log", "onResult: -----------------    VK OK  Email = "+res.email);
                Log.w("log", "onResult: -----------------    VK OK  userID = "+res.secret);
            }
            @Override
            public void onError(VKError error) {
// Произошла ошибка авторизации (например, пользователь запретил авторизацию)
            }
        })) {
            super.onActivityResult(requestCode, resultCode, data);
        }
    }






}
