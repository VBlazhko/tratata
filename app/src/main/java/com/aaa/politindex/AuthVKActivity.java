package com.aaa.politindex;

import android.content.Intent;
import android.graphics.Bitmap;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.webkit.ValueCallback;
import android.webkit.WebResourceError;
import android.webkit.WebResourceRequest;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ProgressBar;

import com.aaa.politindex.main_screen.MainActivity;
import com.vk.sdk.VKAccessToken;
import com.vk.sdk.VKCallback;
import com.vk.sdk.VKScope;
import com.vk.sdk.VKSdk;
import com.vk.sdk.api.VKError;
import com.vk.sdk.util.VKUtil;

public class AuthVKActivity extends AppCompatActivity {


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
            wb.evaluateJavascript(
                    "(function() { return (document.getElementsByTagName('html')[0].innerHTML); })();",
                    new ValueCallback<String>() {
                        @Override
                        public void onReceiveValue(String html) {
                            if(html!=null){
                            String result = html.replace("\\", "");
                            Log.w("log", "onReceiveValue: "+result );
                            parseString(result);

                            mProgressBar.setVisibility(View.INVISIBLE);

                            if(status.equals("OK")&&verification.equals("false")){
                                Log.w("log", "onPageFinished: "+"GO TO SMS" );
                            }else if(status.equals("OK")&&verification.equals("true")){
                                Log.w("log", "onPageFinished: "+" auth OK" );
                            }
                        }}
                    });


        }



        public void parseString(String string){
            String[] array = string.split("\"status\":\"");
            status = array[1].split("\",")[0];
            idUser = array[1].split("\"idUser\":")[1].split(",")[0];
            verification = array[1].split("\"verification\":")[1].split(",")[0];
            token = array[1].split("\"token\":\"")[1].split("\"")[0];

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
