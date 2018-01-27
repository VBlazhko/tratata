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

public class AuthActivity extends AppCompatActivity {


    WebView wb;
    ProgressBar mProgressBar;

    String status;
    String idUser;
    String verification;
    String token;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_auth_vk);

        mProgressBar=(ProgressBar)findViewById(R.id.progressBar);

        Intent intent = getIntent();
        String selectSocialNetwork=intent.getStringExtra("authorization");

        wb = (WebView) findViewById(R.id.webView);
        wb.setWebViewClient(new MyWebClient());
        wb.getSettings().setJavaScriptEnabled(true);

        if(selectSocialNetwork.equals(Const.FACEBOOK))wb.loadUrl(Const.FBSERVER);
        if(selectSocialNetwork.equals(Const.VKONTAKTE))wb.loadUrl(Const.VKSERVER);



//        VKSdk.login(this, VKScope.DIRECT);
    }


    class MyWebClient extends  WebViewClient{

        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            return false;
        }

        @Override
        public void onPageFinished(WebView view, String url) {
            super.onPageFinished(view, url);
            mProgressBar.setVisibility(View.INVISIBLE);
            if(url.contains("politindex")&&!url.contains("error_code=")&&!url.contains("redirect_uri")) {
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
                                    if(result.contains("error")){
                                        finish();
                                        return;
                                    }
                                    parseString(result);
                                    changeActivity();
                                }
                            }
                        });
            }else if(url.contains("error_code=")){
                Log.w("log", "onPageFinished: "+"----------------------ERROR" );
                finish();
            }



        }

        public void changeActivity(){
            if (status.equals("OK") && verification.equals("false")) {
                startActivity(new Intent(AuthActivity.this, AuthPhoneActivity.class));
                finish();
            } else if (status.equals("OK") && verification.equals("true")) {
                Log.w("log", "onPageFinished: " + " auth OK");
            }
        }


        @Override
        public void onReceivedLoginRequest(WebView view, String realm, String account, String args) {
            super.onReceivedLoginRequest(view, realm, account, args);
            Log.w("log", "onReceivedLoginRequest: " + args );
        }

        public void parseString(String string){
            String[] array = string.split("\"status\":\"");
            status = array[1].split("\",")[0];
            idUser = array[1].split("\"idUser\":")[1].split(",")[0];
            verification = array[1].split("\"verification\":")[1].split(",")[0];
            token = array[1].split("\"token\":\"")[1].split("\"")[0];

            App.getApp().setSharedPreferences(Const.ID_USER,idUser);
            App.getApp().setSharedPreferences(Const.TOKEN,token);



            Log.w("log", "parseString: Status = "+status );
            Log.w("log", "parseString: idUser = "+idUser );
            Log.w("log", "parseString: verification = "+verification );
            Log.w("log", "parseString: token = "+token );
        }

        @Override
        public void onPageStarted(WebView view, String url, Bitmap favicon) {
            super.onPageStarted(view, url, favicon);

        }





    }




//    @Override
//    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
//        if (!VKSdk.onActivityResult(requestCode, resultCode, data, new VKCallback<VKAccessToken>() {
//            @Override
//            public void onResult(VKAccessToken res) {
//
//            }
//            @Override
//            public void onError(VKError error) {
//
//            }
//        })) {
//            super.onActivityResult(requestCode, resultCode, data);
//        }
//    }







}
