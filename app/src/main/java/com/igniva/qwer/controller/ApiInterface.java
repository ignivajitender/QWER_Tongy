package com.igniva.qwer.controller;


import com.igniva.qwer.model.ResponsePojo;

import java.util.Map;

import retrofit.Callback;
import retrofit.http.FieldMap;
import retrofit.http.FormUrlEncoded;
import retrofit.http.Headers;
import retrofit.http.POST;
import retrofit.http.Query;
import retrofit2.http.Url;

public interface ApiInterface {

    @FormUrlEncoded
    @POST("/api/Manager/login")
    public void login(@FieldMap Map<String, String> params, Callback<ResponsePojo> callback);

    @FormUrlEncoded
    @Headers({
            "Content-type: application/json"
    })
    @POST("/users/login")
    void accessTokenLogin(@FieldMap Map<String, String> params, Callback<ResponsePojo> callback);

    @retrofit2.http.POST
    void profilePicture(@Url String url, @FieldMap Map<String, String> params, Callback<ResponsePojo> abc);

    @FormUrlEncoded
    @POST("/api/v1/posts/post")
    void getComment(@Query("page") String pageNumber,@FieldMap Map<String, String> params, Callback<ResponsePojo> callback);

    @FormUrlEncoded
    @POST("/v1/Admin/searchArticle")
    void article(@FieldMap Map<String, String> params, Callback<ResponsePojo> callback);


}
