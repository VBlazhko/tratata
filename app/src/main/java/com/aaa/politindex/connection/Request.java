package com.aaa.politindex.connection;

/**
 * Created by 11 on 21.01.2018.
 */

import android.net.Uri;
import android.support.v4.util.LogWriter;
import android.util.Log;
import android.webkit.MimeTypeMap;

import com.aaa.politindex.App;
import com.aaa.politindex.Const;
import com.aaa.politindex.helper.HttpHelper;

import com.aaa.politindex.helper.Md5Helper;
import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
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


    public JSONObject uploadImage(final String function,File file,final CallBack callBack) {
        Map<String, String> headers=new HashMap<>();
        headers.put("Encoding", App.getApp().getSharedPreferences(Const.LOCALE));
        headers.put("Token", App.getApp().getSharedPreferences(Const.TOKEN));
        headers.put("Authorization", App.getApp().getSharedPreferences(Const.ID_TOKEN) + "_" +
                Md5Helper.md5(App.getApp().getSharedPreferences(Const.ID_TOKEN) + ":" + App.getApp().getSharedPreferences(Const.ID_USER) + ":" + App.getApp().getSharedPreferences(Const.TOKEN)));

        RequestBody reqFile = RequestBody.create(MediaType.parse("image/jpg"), file);
        MultipartBody.Part body = MultipartBody.Part.createFormData("avatar", file.getName(), reqFile);
        RequestBody name = RequestBody.create(MediaType.parse("text/plain"), "avatar");

        mRetrofitApi.uploadFile(function, headers, body,name).enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                Log.w("log", "onResponse: " + response.raw());
                try {
                    JSONObject result = new JSONObject(response.body().string());
                    callBack.onResponse(result);
                    Log.w("log", "onResponse: " + result.toString());
                } catch (Exception e) {
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {


            }
        });

        return null;
    }


    public JSONObject getResult(final String function, final CallBack callBack) {

        Map<String, String> headers=new HashMap<>();
        headers.put("Encoding", App.getApp().getSharedPreferences(Const.LOCALE));
        headers.put("Token", App.getApp().getSharedPreferences(Const.TOKEN));
        headers.put("Authorization", App.getApp().getSharedPreferences(Const.ID_TOKEN) + "_" +
                Md5Helper.md5(App.getApp().getSharedPreferences(Const.ID_TOKEN) + ":" + App.getApp().getSharedPreferences(Const.ID_USER) + ":" + App.getApp().getSharedPreferences(Const.TOKEN)));



        mRetrofitApi.getRequestPost(function, headers).enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                Log.w("log", "onResponse: " + response.raw());
                try {
                    JSONObject result = new JSONObject(response.body().string());
                    callBack.onResponse(result);
                    Log.w("log", "onResponse: " + result.toString());
                } catch (Exception e) {
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {


            }
        });

        return null;
    }


    public JSONObject getResultLove(final String function, Map<String, String> params, final CallBack callBack) {


        Map<String, String> headers=new HashMap<>();
        headers.put("Encoding", App.getApp().getSharedPreferences(Const.LOCALE));
        headers.put("Token", App.getApp().getSharedPreferences(Const.TOKEN));
        headers.put("Authorization", App.getApp().getSharedPreferences(Const.ID_TOKEN) + "_" +
                Md5Helper.md5(App.getApp().getSharedPreferences(Const.ID_TOKEN) + ":" + App.getApp().getSharedPreferences(Const.ID_USER) + ":" + App.getApp().getSharedPreferences(Const.TOKEN)));

        mRetrofitApi.getRequestLovePost(function, headers, params).enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                Log.w("log", "onResponse: " + response.raw());
                try {
                    JSONObject result = new JSONObject(response.body().string());
                    callBack.onResponse(result);
                    Log.w("log", "onResponse: " + result.toString());
                } catch (Exception e) {
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {


            }
        });

        return null;
    }


    public interface CallBack {
        void onResponse(JSONObject jsonObject);
    }


}
