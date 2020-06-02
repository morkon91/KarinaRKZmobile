package com.example.karinarkzmobile.connectionUtils;

import java.util.Map;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.QueryMap;

public interface ServerConnectionAPI {

    @GET("/?command=101")
    Call<Response> fetchAlarmEventList();

//    @GET("/morkon91/server/db")
//    Call<Response> fetchAlarmEventList();
}
