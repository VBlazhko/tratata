package com.aaa.politindex.connection;

import android.util.Log;

import com.aaa.politindex.App;
import com.aaa.politindex.Const;
import com.aaa.politindex.helper.HttpHelper;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.scalars.ScalarsConverterFactory;

/**
 * Created by 11 on 26.01.2018.
 */

public class RequestAuth {
    private RetrofitAuthApi mRetrofitAuthApi;
    private static RequestAuth requestAuth;


    private RequestAuth() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Const.SERVER)
                .client(HttpHelper.getHttpClient())
                .addConverterFactory(ScalarsConverterFactory.create())
                .build();
        mRetrofitAuthApi = retrofit.create(RetrofitAuthApi.class);
    }

    public static RequestAuth getInstance() {
        if (requestAuth == null) {
            requestAuth = new RequestAuth();
        }
        return requestAuth;
    }


    public JSONObject getResult(final String function, final Map<String, String> params, final Request.CallBack callBack) {

        Map<String, String> params1 = new HashMap<>();
        params1.put("id_user", App.getApp().getSharedPreferences(Const.ID_USER));
        params1.put("token", App.getApp().getSharedPreferences(Const.TOKEN));

        Log.w("log", "getResult: " + App.getApp().getSharedPreferences(Const.ID_USER));
        Log.w("log", "getResult: " + App.getApp().getSharedPreferences(Const.TOKEN));

        params1.put("phone", "79601018088");

        mRetrofitAuthApi.getRequestPost(function, params1).enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                Log.w("log", "onResponse: " + response.raw());

                try {
                    JSONObject result = new JSONObject(response.body().string());
                    Log.w("log", "onResponse: " + result);
                    callBack.onResponse(result);
                    Log.w("log", "onResponse: " + result.toString());
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                Log.w("log", "onFailure: " + t.toString());

            }
        });

        return null;
    }

    public interface CallBack {
        void onResponse(JSONObject jsonObject);
    }
}
