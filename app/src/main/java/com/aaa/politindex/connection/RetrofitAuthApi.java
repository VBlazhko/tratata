package com.aaa.politindex.connection;

import java.util.Map;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.HeaderMap;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;
import retrofit2.http.QueryMap;

/**
 * Created by 11 on 26.01.2018.
 */

public interface RetrofitAuthApi {
    @FormUrlEncoded
    @POST("/{server}")
    Call<ResponseBody> getRequestPost(@Path(value = "server", encoded = true) String server, @FieldMap Map<String,String> map  );

    @FormUrlEncoded
    @POST("/{server}")
    Call<ResponseBody> getRequestPostSms(@Path(value = "server", encoded = true) String server, @Field("id_user") int idUser,
                                         @Field("token") String token,
                                         @Field("sms_code") int pinCode,
                                         @Field("hash") String hash);


    @GET("/{server}")
    Call<ResponseBody> getRequestGet(@Path(value = "server", encoded = true) String server, @HeaderMap Map<String, String> headers, @QueryMap Map<String, String> queryParams);
}
