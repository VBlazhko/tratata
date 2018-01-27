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
        mRetrofitAuthApi.getRequestPost(function, params).enqueue(new Callback<ResponseBody>() {
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


    public JSONObject getResultSms(final String function, final int idUser,final  String token,final int pinCode,final String hash, final Request.CallBack callBack) {
        mRetrofitAuthApi.getRequestPostSms(function,idUser,token,pinCode,hash ).enqueue(new Callback<ResponseBody>() {

            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                Log.w("log", "onResponse: " + response.raw());
                Log.w("log", "onResponseSMSCODE: " + pinCode );

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
