package com.aaa.politindex.connection;

/**
 * Created by 11 on 21.01.2018.
 */

import com.aaa.politindex.App;

import java.util.Map;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.HeaderMap;
import retrofit2.http.Headers;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.Path;
import retrofit2.http.QueryMap;

public interface RetrofitApi {

    @Multipart
    @POST("/{server}")
    Call<ResponseBody> uploadFile(@Path(value = "server", encoded = true) String server, @HeaderMap Map<String, String> headers, @Part MultipartBody.Part file, @Part("avatar") RequestBody name);

    @FormUrlEncoded
    @POST("/{server}")
    Call<ResponseBody> getRequestLovePost(@Path(value = "server", encoded = true) String server, @HeaderMap Map<String, String> headers, @FieldMap Map<String, String> queryParams);


    @POST("/{server}")
    Call<ResponseBody> getRequestPost(@Path(value = "server", encoded = true) String server, @HeaderMap Map<String, String> headers);


    @POST("/{server}")
    Call<ResponseBody> getRequestPostEvent(@Path(value = "server", encoded = true) String server, @HeaderMap Map<String, String> headers);

}
