package com.aaa.politindex.connection;

/**
 * Created by 11 on 21.01.2018.
 */

import com.aaa.politindex.App;

import java.util.Map;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.HeaderMap;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.QueryMap;

public interface RetrofitApi {


    @GET("/{server}")
    Call<ResponseBody> getRequestGet(@Path(value = "server", encoded = true) String server, @HeaderMap Map<String, String> headers, @QueryMap Map<String, String> queryParams);


    @POST("/{server}")
    Call<ResponseBody> getRequestPost(@Path(value = "server", encoded = true) String server, @HeaderMap Map<String, String> headers, @QueryMap Map<String, String> queryParams);


}
