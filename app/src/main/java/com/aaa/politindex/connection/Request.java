package com.aaa.politindex.connection;

/**
 * Created by 11 on 21.01.2018.
 */
import android.util.Log;

import com.aaa.politindex.App;
import com.aaa.politindex.Const;
import com.aaa.politindex.helper.HttpHelper;

import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.scalars.ScalarsConverterFactory;



public class Request {

    private RetrofitApi mRetrofitApi;
    private static Request request;


    private Request() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Const.SERVER)
                .client(HttpHelper.getHttpClient())
                .addConverterFactory(ScalarsConverterFactory.create())
                .build();
        mRetrofitApi = retrofit.create(RetrofitApi.class);
    }

    public static Request getInstance() {
        if (request == null) {
            request = new Request();
        }
        return request;
    }


    public JSONObject getResult(final String function, final Map<String, String> params, final CallBack callBack) {



            Map<String, String> headers = new HashMap<>();


            headers.put("Encoding", App.getApp().getLocale());
            mRetrofitApi.getRequestPost(function,headers).enqueue(new Callback<ResponseBody>() {
                @Override
                public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                    Log.w("log", "onResponse: " + response.raw() );
                    try {
                        JSONObject result = new JSONObject(response.body().string());
                        callBack.onResponse(result);
                        Log.w("log", "onResponse: "+result.toString() );
                    } catch (Exception e) {
                    }
                }

                @Override
                public void onFailure(Call<ResponseBody> call, Throwable t) {


                    Log.w("log", "onFailure: "  + t  );
                }
            });

        return null;
    }

    public interface CallBack {
        void onResponse(JSONObject jsonObject);
    }



}
