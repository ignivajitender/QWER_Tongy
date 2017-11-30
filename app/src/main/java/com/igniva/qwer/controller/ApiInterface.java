package com.igniva.qwer.controller;


import com.igniva.qwer.model.ConnectionPojo;
import com.igniva.qwer.model.CountriesResponsePojo;
import com.igniva.qwer.model.GooglePlaceApiResponsePojo;
import com.igniva.qwer.model.LanguagesResponsePojo;
import com.igniva.qwer.model.NotificationPojo;
import com.igniva.qwer.model.OtherUserProfilePojo;
import com.igniva.qwer.model.PostDetailPojo;
import com.igniva.qwer.model.PostPojo;
import com.igniva.qwer.model.PrefInputPojo;
import com.igniva.qwer.model.ProfileResponsePojo;
import com.igniva.qwer.model.ResponsePojo;
import com.igniva.qwer.model.StateResponsePojo;
import com.igniva.qwer.model.UsersResponsePojo;

import java.util.HashMap;
import java.util.Map;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Field;
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
    Call<CountriesResponsePojo>  getCountriesList();

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

    @FormUrlEncoded
    @POST("/api/users/post/comment")
    Call<PostPojo> sendComment(@FieldMap HashMap<String, String> signupHash);

    @GET("/api/users/singlePost")
    Call<PostDetailPojo> singlePostDetail(@Query("post_id") int post_id);

    @Multipart
    @POST("/api/users/post/other")
    Call<ResponsePojo> createOtherPost(@Part() MultipartBody.Part body, @PartMap Map<String, RequestBody> postOtherPayload);

    @FormUrlEncoded
    @POST("api/users/post/meeting")
    Call<ResponsePojo> createMeetingPost(@FieldMap HashMap<String, Object> changePasswordHashMap);

    @GET("/api/users/searchPost")
    Call<PostPojo> getSearchResults(@Query("q") String searchString);

    @FormUrlEncoded
    @POST("api/users/post/reportPost")
    Call<ResponsePojo> reportAbuse(@FieldMap HashMap<String, String> changePasswordHashMap);

    @FormUrlEncoded
    @POST("api/users/post/delete")
    Call<ResponsePojo> deletePost(@Field("post_id") String post_id);

    @GET("/api/users/post")
    Call<PostPojo> getUserPosts(@Query("page") int pageNumber, @Query("post") String post);

    //    http://tongy.ignivastaging.com/api/users/favPost
    @GET("/api/users/favPost")
    Call<PostPojo> getFavPosts(@Query("page") int pageNumber);

    @GET("/api/users/getNotifications")
    Call<NotificationPojo> getNotifications(@Query("page") int pageNumber);

    @GET("/api/users/myConnection")
    Call<ConnectionPojo> getMyConnections(@Query("page") int pageNumber);

    @FormUrlEncoded
    @POST("/api/users/removeNotifications")
    Call<NotificationPojo> removeNotifications(@Field("notification_id") int post_id);

    @FormUrlEncoded
    @POST("/api/users/notificationOnOff")
    Call<ResponsePojo> notificationOnOff(@Field("is_push_notification") int is_push_notification);


    @FormUrlEncoded
    @POST("/api/users/videoOnOff")
    Call<ResponsePojo> videoOnOff(@Field("is_videocall") int is_videocall);


    @FormUrlEncoded
    @POST("/api/users/voiceOnOff")
    Call<ResponsePojo> voiceOnOff(@Field("is_voicecall") int is_voicecall);

    //http://tongy.ignivastaging.com/api/users/singleUser?user_id=59
    @GET("/api/users/singleUser")
    Call<OtherUserProfilePojo> getSingleUser(@Query("user_id") int userId);

    @FormUrlEncoded
    @POST("/api/users/blockUnblockUser")
    Call<ResponsePojo> blockUnblockUser(@Field("blocked_to") int blocked_to);
   // http://tongy.ignivastaging.com/api/users/reportUser
    @FormUrlEncoded
    @POST("api/users/reportUser")
    Call<ResponsePojo> reportUser(@FieldMap HashMap<String, String> changePasswordHashMap);

    @FormUrlEncoded
    @POST("api/users/userAction")
    Call<ResponsePojo> userAction(@FieldMap HashMap<String, String> changePasswordHashMap);

    //http://tongy.ignivastaging.com/api/users/getUsers?lat=40.120749&lng=75.860596
    @GET("/api/users/getUsers")
    Call<UsersResponsePojo> getUsers(@Query("lat") double lat, @Query("lng") double lng, @Query("page") int pageNo);

   // http://tongy.ignivastaging.com/api/users/requestSend
    @FormUrlEncoded
    @POST("api/users/requestSend")
    Call<ResponsePojo> requestSend(@Field("request_to") int request_to);

    @GET("/api/state")
    Call<StateResponsePojo>  getStateList(@Query("country_id") String country_id);

}
