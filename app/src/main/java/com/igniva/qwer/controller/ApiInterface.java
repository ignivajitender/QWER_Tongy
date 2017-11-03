package com.igniva.qwer.controller;


import com.igniva.qwer.model.ProfileResponsePojo;
import com.igniva.qwer.model.ResponsePojo;

import java.util.HashMap;
import java.util.Map;

import okhttp3.MultipartBody;
import retrofit.Callback;
import retrofit2.Call;
import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;

public interface ApiInterface {

    @FormUrlEncoded
    @POST("/api/signup")
    Call<ResponsePojo> signup(@FieldMap Map<String, String> params);

    @FormUrlEncoded
    @POST("/api/login")
    Call<ResponsePojo> login(@FieldMap Map<String, String> params);

    @FormUrlEncoded
    @POST("/api/socialSignup")
    Call<ResponsePojo> loginFaceBook(@FieldMap Map<String, String> params);

    @FormUrlEncoded
    @POST("/api/users/status")
    Call<ResponsePojo> deleteAccount(@FieldMap Map<String, String> params);

    @FormUrlEncoded
    @POST("/api/users/contact")
    Call<ResponsePojo> contactUs(@FieldMap Map<String, String> params);


    @FormUrlEncoded
    @POST("/api/users/changeEmail")
    Call<ResponsePojo> changeEmail(@FieldMap Map<String, String> params);

    @FormUrlEncoded
    @POST("/api/users/changePassword")
    Call<ResponsePojo> changePassword(@FieldMap Map<String, String> params);

    @FormUrlEncoded
    @POST("/api/users/forgotPassword")
    Call<ResponsePojo> forgotPassword(@FieldMap HashMap<String, String> forgotPasswordHashMap);

    @FormUrlEncoded
    @POST("/api/resendUserVerification")
    Call<ResponsePojo> resendVerificationLink(@FieldMap HashMap<String, String> forgotPasswordHashMap);


    @GET("/api/users/getProfile")
    Call<ProfileResponsePojo> getProfile();

    @FormUrlEncoded
    @POST("/api/users/updateProfile")
    Call<ResponsePojo> updateProfile(@FieldMap HashMap<String, String> updateProfilePayload);

    @GET("/api/country")
    void getCountriesList(Callback<ResponsePojo> callback);

    @Multipart
    @POST("/api/users/imageUpload")
    Call<ResponsePojo> uploadImage(@Part() MultipartBody.Part typedFile);

    @POST("/api/users/logout")
    Call<ResponsePojo> logoutAccount();
}
