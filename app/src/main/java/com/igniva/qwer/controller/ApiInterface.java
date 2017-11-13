package com.igniva.qwer.controller;


import com.igniva.qwer.model.GooglePlaceApiResponsePojo;
import com.igniva.qwer.model.LanguagesResponsePojo;
import com.igniva.qwer.model.PostDetailPojo;
import com.igniva.qwer.model.PostPojo;
import com.igniva.qwer.model.PrefInputPojo;
import com.igniva.qwer.model.ProfileResponsePojo;
import com.igniva.qwer.model.ResponsePojo;

import java.util.HashMap;
import java.util.Map;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit.Callback;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.PartMap;
import retrofit2.http.Query;

public interface ApiInterface {

    String BASE_URL = "https://maps.googleapis.com/";

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

    @POST("/api/users/prefrences")
    Call<ResponsePojo> setPrefrences(@Body PrefInputPojo params);

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

    @GET("/api/languages")
    Call<LanguagesResponsePojo> getLanguages();

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

    @GET("maps/api/place/autocomplete/json")
    Call<GooglePlaceApiResponsePojo> getCityResults(@Query("types") String types, @Query("input") String input, @Query("location") String location, @Query("radius") Integer radius, @Query("key") String key);

    @FormUrlEncoded
    @POST("api/users/post/teaching")
    Call<ResponsePojo> createTeachingPost(@FieldMap HashMap<String, String> changePasswordHashMap);

    @GET("/api/nonUsers/post")
    Call<PostPojo> getPosts(@Query("page") int pageNumber);

    @FormUrlEncoded
    @POST("/api/users/post/postFavUnfav")
    Call<PostPojo> markFavOrUnfav(@FieldMap HashMap<String, String> signupHash);

    @Multipart
    @POST("/api/users/post/other")
    Call<ResponsePojo> createOtherPost(@Part() MultipartBody.Part body,@PartMap Map<String, RequestBody> postOtherPayload);

    @FormUrlEncoded
    @POST("api/users/post/meeting")
    Call<ResponsePojo> createMeetingPost(@FieldMap HashMap<String, String> changePasswordHashMap);

    @GET("/api/users/searchPost")
    Call<PostPojo> getSearchResults(@Query("q") String searchString);

    @FormUrlEncoded
    @POST("api/users/post/reportPost")
    Call<ResponsePojo> reportAbuse(HashMap<String, String> changePasswordHashMap);


    @GET("/api/users/post")
    Call<PostPojo> getUserPosts(@Query("page") int pageNumber,@Query("post") String post);

//    http://tongy.ignivastaging.com/api/users/favPost
    @GET("/api/users/favPost")
    Call<PostPojo> getFavPosts(@Query("page") int pageNumber);

    @GET("/api/users/singlePost")
    Call<PostDetailPojo> singlePostDetail(@Query("post_id") int post_id);
}
