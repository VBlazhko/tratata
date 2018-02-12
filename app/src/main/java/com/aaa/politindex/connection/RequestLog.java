package com.aaa.politindex.connection;

import android.util.Log;

import com.aaa.politindex.Const;
import com.aaa.politindex.helper.HttpHelper;

import org.json.JSONObject;

import java.util.Map;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.scalars.ScalarsConverterFactory;

/**
 * Created by 11 on 11.02.2018.
 */

public class RequestLog {
    private RetrofitLogApi mRetrofitLogApi;
    private static RequestLog requestLog;


    private RequestLog() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Const.SERVER_LOG)
                .client(HttpHelper.getHttpClient())
                .addConverterFactory(ScalarsConverterFactory.create())
                .build();
        mRetrofitLogApi = retrofit.create(RetrofitLogApi.class);
    }

    public static RequestLog getInstance() {
        if (requestLog == null) {
            requestLog = new RequestLog();
        }
        return requestLog;
    }


    public JSONObject setLog(final String function, final Map<String, String> params, final RequestLog.CallBack callBack) {
        mRetrofitLogApi.getRequestPost(function, params).enqueue(new Callback<ResponseBody>() {
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

