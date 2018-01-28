package com.aaa.politindex.authentication;

import android.content.Intent;
import android.graphics.Bitmap;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.webkit.ValueCallback;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ProgressBar;

import com.aaa.politindex.App;
import com.aaa.politindex.Const;
import com.aaa.politindex.R;
import com.aaa.politindex.main_screen.MainActivity;
import com.aaa.politindex.main_screen_for_auth_user.MainAuthUserActivity;

public class AuthActivity extends AppCompatActivity {


    WebView wb;
    ProgressBar mProgressBar;

    String status;
    String idUser;
    String idToken;
    boolean verification;
    String token;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_auth_vk);

        mProgressBar = (ProgressBar) findViewById(R.id.progressBar);

        Intent intent = getIntent();
        String selectSocialNetwork = intent.getStringExtra("authorization");

        wb = (WebView) findViewById(R.id.webView);
        wb.setWebViewClient(new MyWebClient());
        wb.getSettings().setJavaScriptEnabled(true);

        if (selectSocialNetwork.equals(Const.FACEBOOK)) wb.loadUrl(Const.FBSERVER);
        if (selectSocialNetwork.equals(Const.VKONTAKTE)) wb.loadUrl(Const.VKSERVER);


//        VKSdk.login(this, VKScope.DIRECT);
    }


    class MyWebClient extends WebViewClient {

        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            return false;
        }

        @Override
        public void onPageFinished(WebView view, String url) {
            super.onPageFinished(view, url);
            mProgressBar.setVisibility(View.INVISIBLE);
            if (url.contains("politindex") && !url.contains("error_code=") && !url.contains("redirect_uri")) {
                mProgressBar.setVisibility(View.VISIBLE);
                wb.setVisibility(View.INVISIBLE);
                wb.evaluateJavascript(
                        "(function() { return (document.getElementsByTagName('html')[0].innerHTML); })();",
                        new ValueCallback<String>() {
                            @Override
                            public void onReceiveValue(String html) {
                                if (html != null) {
                                    String result = html.replace("\\", "");
                                    Log.w("log", "onReceiveValue: " + result);
                                    if (result.contains("error")) {
                                        finish();
                                        return;
                                    }
                                    parseString(result);
                                    changeActivity();
                                }
                            }
                        });
            } else if (url.contains("error_code=")) {
                Log.w("log", "onPageFinished: " + "----------------------ERROR");
                finish();
            }


        }

        public void changeActivity() {
            if (status.equals("OK") && !verification) {
                startActivity(new Intent(AuthActivity.this, AuthPhoneActivity.class));
                finish();
            } else if (status.equals("OK") && verification) {
                startActivity(new Intent(AuthActivity.this, MainAuthUserActivity.class));
                finish();
            }
        }


        @Override
        public void onReceivedLoginRequest(WebView view, String realm, String account, String args) {
            super.onReceivedLoginRequest(view, realm, account, args);
            Log.w("log", "onReceivedLoginRequest: " + args);
        }

        public void parseString(String string) {
            if (string.contains("false")) {
                String[] array = string.split("\"status\":\"");
                status = array[1].split("\",")[0];
                idUser = array[1].split("\"idUser\":")[1].split(",")[0];
                verification = array[1].split("\"verification\":")[1].split(",")[0].contains("true");
                token = array[1].split("\"token\":\"")[1].split("\"")[0];
                token = array[1].split("\"token\":\"")[1].split("\"")[0];

            } else if (string.contains("true")) {

                String[] array = string.split("\"status\":\"");
                status = array[1].split("\",")[0];
                token = array[1].split("\"token\":\"")[1].split("\"")[0];
                idUser = array[1].split("\"idUser\":")[1].split(",")[0];
                idToken = array[1].split("\"idToken\":")[1].split(",")[0];
                verification = array[1].split("\"verification\":")[1].split(",")[0].contains("true");

            }

            App.getApp().setSharedPreferences(Const.ID_USER, idUser);
            App.getApp().setSharedPreferences(Const.TOKEN, token);
            if (verification) App.getApp().setSharedPreferences(Const.ID_TOKEN, idToken);


            Log.w("log", "parseString: Status = " + status);
            Log.w("log", "parseString: idUser = " + idUser);
            Log.w("log", "parseString: verification = " + verification);
            Log.w("log", "parseString: token = " + token);
            if (verification) Log.w("log", "parseString: idToken = " + idToken);


        }

    }
}
