package com.igniva.qwer.controller;


import com.igniva.qwer.model.ProfileResponsePojo;
import com.igniva.qwer.model.ResponsePojo;

import java.util.HashMap;
import java.util.Map;

import retrofit.Callback;
import retrofit.http.FieldMap;
import retrofit.http.FormUrlEncoded;
import retrofit.http.GET;
import retrofit.http.Headers;
import retrofit.http.Multipart;
import retrofit.http.POST;
import retrofit.http.Part;
import retrofit.http.Query;
import retrofit.mime.TypedFile;
import retrofit2.http.Url;

public interface ApiInterface {



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


    @FormUrlEncoded
    @POST("/api/signup")
    void signup(@FieldMap Map<String, String> params, Callback<ResponsePojo> callback);

    @FormUrlEncoded
    @POST("/api/login")
    void login(@FieldMap Map<String, String> params, Callback<ResponsePojo> callback);

    @FormUrlEncoded
    @POST("/api/socialSignup")
    void loginFaceBook(@FieldMap Map<String, String> params, Callback<ResponsePojo> callback);

    @FormUrlEncoded
    @POST("/api/users/status")
    void deleteAccount(@FieldMap Map<String, String> params, Callback<ResponsePojo> callback);

    @FormUrlEncoded
    @POST("/api/users/contact")
    void contactUs(@FieldMap Map<String, String> params, Callback<ResponsePojo> callback);

    @FormUrlEncoded
    @POST("/api/users/changeEmail")
    void changeEmail(@FieldMap Map<String, String> params, Callback<ResponsePojo> callback);

    @FormUrlEncoded
    @POST("/api/users/changePassword")
    void changePassword(@FieldMap Map<String, String> params, Callback<ResponsePojo> callback);

    @FormUrlEncoded
    @POST("/api/users/forgotPassword")
    void forgotPassword(@FieldMap HashMap<String, String> forgotPasswordHashMap, Callback<ResponsePojo> callback);

    @FormUrlEncoded
    @POST("/api/resendUserVerification")
    void resendVerificationLink(@FieldMap HashMap<String, String> forgotPasswordHashMap, Callback<ResponsePojo> callback);


    @GET("/api/users/getProfile")
    void getProfile(Callback<ProfileResponsePojo> callback);

    @FormUrlEncoded
    @POST("/api/users/updateProfile")
    void updateProfile(@FieldMap HashMap<String, String> updateProfilePayload, Callback<ResponsePojo> callback);

    @GET("/api/country")
    void getCountriesList(Callback<ResponsePojo> callback);

    @Multipart
    @POST("/api/users/imageUpload")
    void uploadImage(@Part("image") TypedFile typedFile, Callback<ResponsePojo> callback);
}
