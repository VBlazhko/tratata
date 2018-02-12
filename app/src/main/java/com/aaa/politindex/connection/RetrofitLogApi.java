package com.aaa.politindex.connection;

import java.util.Map;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.HeaderMap;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.QueryMap;

/**
 * Created by 11 on 11.02.2018.
 */

public interface RetrofitLogApi {


    @GET("/{server}")
    Call<ResponseBody> getRequestPost(@Path(value = "server", encoded = true) String server, @QueryMap Map<String, String> fields);
        }
