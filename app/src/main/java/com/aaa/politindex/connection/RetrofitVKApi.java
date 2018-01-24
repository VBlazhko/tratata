package com.aaa.politindex.connection;

import java.util.Map;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.HeaderMap;
import retrofit2.http.Path;
import retrofit2.http.Query;
import retrofit2.http.QueryMap;

/**
 * Created by 11 on 24.01.2018.
 */

public interface RetrofitVKApi {
    @GET("/{server}")
    Call<ResponseBody> getRequestPost(@Path(value = "server", encoded = true) String server, @QueryMap Map<String, String> queryParams);
}

