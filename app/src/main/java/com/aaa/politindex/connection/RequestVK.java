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
 * Created by 11 on 24.01.2018.
 */

public class RequestVK {
    private RetrofitVKApi mRetrofitApi;
    private static RequestVK request;

    private RequestVK() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Const.VKSERVER)
                .client(HttpHelper.getHttpClient())
                .addConverterFactory(ScalarsConverterFactory.create())
                .build();
        mRetrofitApi = retrofit.create(RetrofitVKApi.class);
    }

    public static RequestVK getInstance() {
        if (request == null) {
            request = new RequestVK();
        }
        return request;
    }

    public JSONObject getResult(final String function, final Map<String, String> params, final RequestVK.CallBack callBack) {


        Log.w("log", "getResult:  start method" );

        Map<String, String> map = new HashMap<>();


        map.put("client_id", "6219905");
        map.put("display", "page");
        map.put("redirect_uri", "http://politindex.nowords.ru/v1/vk/auth.api");
        map.put("response_type", "code");
        map.put("v", "5.68");

        mRetrofitApi.getRequestPost("authorize", map).enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                try {
                    JSONObject result = new JSONObject(response.body().string());
                    callBack.onResponse(result);
                    Log.w("log", "onResponse: ------- GET TO VK "+result.toString());

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
