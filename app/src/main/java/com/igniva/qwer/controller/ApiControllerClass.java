package com.igniva.qwer.controller;

import android.app.Activity;
import android.content.Context;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.igniva.qwer.R;
import com.igniva.qwer.model.LanguagesResponsePojo;
import com.igniva.qwer.model.PostDetailPojo;
import com.igniva.qwer.model.PostPojo;
import com.igniva.qwer.model.PrefInputPojo;
import com.igniva.qwer.model.ResponsePojo;
import com.igniva.qwer.ui.activities.ChangePasswordActivity;
import com.igniva.qwer.ui.activities.CreateTeachingPostActivity;
import com.igniva.qwer.ui.activities.PostDetailActivity;
import com.igniva.qwer.ui.activities.SetPreferrencesActivity;
import com.igniva.qwer.ui.fragments.PostsListFragment;
import com.igniva.qwer.ui.views.CallProgressWheel;
import com.igniva.qwer.utils.PreferenceHandler;
import com.igniva.qwer.utils.Utility;
import com.igniva.qwer.utils.Validation;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Retrofit;

import static com.igniva.qwer.utils.Utility.callSuccessPopUp;

public class ApiControllerClass {
    public static String TAG = "ApiControllerClass";


    /**
     * Call api to change the current email
     */

    public static void callChangeEmailApi(final Context context, EditText etCurrentEmail, EditText etNewEmail, EditText verifyPassword, Retrofit retrofit) {
        try {


            if (Utility.isInternetConnection(context)) {

                HashMap<String, String> changeEmailpayload = new HashMap<>();
                changeEmailpayload.put("email", etCurrentEmail.getText().toString().trim());
                changeEmailpayload.put("new_email", etNewEmail.getText().toString().trim());
                changeEmailpayload.put("password", verifyPassword.getText().toString().trim());


                CallProgressWheel.showLoadingDialog(context, "Loading...");

                //Create a retrofit call object
                Call<ResponsePojo> posts = retrofit.create(ApiInterface.class).changeEmail(changeEmailpayload);
                posts.enqueue(new retrofit2.Callback<ResponsePojo>() {
                    @Override
                    public void onResponse(Call<ResponsePojo> call, retrofit2.Response<ResponsePojo> response) {
                        if (response.body().getStatus() == 200) {
                            CallProgressWheel.dismissLoadingDialog();
                            callSuccessPopUp(context, response.body().getDescription());
                            // Utility.showToastMessageShort(ChangePasswordActivity.this,responsePojo.getDescription());


                        } else {
                            CallProgressWheel.dismissLoadingDialog();
                            Utility.showToastMessageShort((Activity) context, response.body().getDescription());
                        }
                    }

                    @Override
                    public void onFailure(Call<ResponsePojo> call, Throwable t) {
                        CallProgressWheel.dismissLoadingDialog();
                    }
                });
            }


        } catch (Exception e) {
            e.printStackTrace();
        }


    }

    /**
     * Call api to change the current password
     */

    public static void callChangePasswordApi(final Context context, EditText mEtCuurentPass, EditText mEtNewPass, EditText mEtConfirmPass, Retrofit retrofit) {
        try {
            Utility.hideSoftKeyboard((Activity) context);
            // check validations for current password,new password and confirm password
            if (Validation.validateChangePassword((Activity) context, mEtCuurentPass, mEtNewPass, mEtConfirmPass)) {
                if (Utility.isInternetConnection(context)) {

                    CallProgressWheel.showLoadingDialog(context, "Loading...");
                    HashMap<String, String> changePasswordHashMap = new HashMap<>();
                    changePasswordHashMap.put("old_password", mEtCuurentPass.getText().toString().trim());
                    changePasswordHashMap.put("new_password", mEtNewPass.getText().toString());
                    changePasswordHashMap.put("confirm_new_password", mEtConfirmPass.getText().toString());


                    //Create a retrofit call object
                    Call<ResponsePojo> posts = retrofit.create(ApiInterface.class).changePassword(changePasswordHashMap);
                    posts.enqueue(new retrofit2.Callback<ResponsePojo>() {
                        @Override
                        public void onResponse(Call<ResponsePojo> call, retrofit2.Response<ResponsePojo> response) {
                            if (response.body().getStatus() == 200) {
                                CallProgressWheel.dismissLoadingDialog();
                                ((ChangePasswordActivity) context).callSuccessPopUp(context, response.body().getDescription());


                            } else if (response.body().getStatus() == 400) {
                                CallProgressWheel.dismissLoadingDialog();
                                Toast.makeText(context, response.body().getDescription(), Toast.LENGTH_SHORT).show();
                            } else {
                                CallProgressWheel.dismissLoadingDialog();
                                Toast.makeText(context, response.body().getDescription(), Toast.LENGTH_SHORT).show();
                            }
                        }

                        @Override
                        public void onFailure(Call<ResponsePojo> call, Throwable t) {
                            CallProgressWheel.dismissLoadingDialog();
                            Toast.makeText(context, context.getString(R.string.something_went_wrong), Toast.LENGTH_SHORT).show();

                        }
                    });

                }
            }
        } catch (Exception e) {
            CallProgressWheel.dismissLoadingDialog();
            Toast.makeText(context, context.getString(R.string.something_went_wrong), Toast.LENGTH_SHORT).show();
            e.printStackTrace();
        }

    }

    /**
     * Call api to send feedback to admin
     */
    public static void sendUserFeedBackToAdmin(final Context context, HashMap<String, String> contact_us, Retrofit retrofit) {
        try {
            if (Utility.isInternetConnection(context)) {
                CallProgressWheel.showLoadingDialog(context, "Loading...");
                //Create a retrofit call object
                Call<ResponsePojo> posts = retrofit.create(ApiInterface.class).contactUs(contact_us);
                posts.enqueue(new retrofit2.Callback<ResponsePojo>() {
                    @Override
                    public void onResponse(Call<ResponsePojo> call, retrofit2.Response<ResponsePojo> response) {
                        CallProgressWheel.dismissLoadingDialog();
                        Utility.showToastMessageShort((Activity) context, response.body().getDescription());
                        ((Activity) context).onBackPressed();
                    }

                    @Override
                    public void onFailure(Call<ResponsePojo> call, Throwable t) {
                        CallProgressWheel.dismissLoadingDialog();
                    }
                });


            }

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    /**
     * Call api to create teaching post
     */
    public static void createTeachingPostApi(final Context context, Retrofit retrofit, EditText mEtTitle, EditText mEtDescription, EditText mEtPrice, EditText metScheduleStartDate, EditText metScheduleEndDate, EditText mEtStartTime, EditText mEtEndTime, String typeOfClass) {
        try {
            Utility.hideSoftKeyboard((Activity) context);
            // check validations for current password,new password and confirm password
            if (Validation.validateCreatePost((Activity) context, mEtTitle, mEtDescription, mEtPrice, metScheduleStartDate, metScheduleEndDate, mEtStartTime, mEtEndTime, typeOfClass)) {
                if (Utility.isInternetConnection(context)) {

                    /*
                    // payload
                    {"class_type":"online","end_time":"11:00 AM","" +
                            "start_time":"10:00 AM",
                            "end_date":"20-11-2017",
                            "start_date",
                            "value":"20-10-2017",
                            "currency":"usd",
                            "price","value":"20",
                            "description","value":"deascriii",
                            "title","value":"teaching section",
                            "post_type","value":"teaching"}*/

                    CallProgressWheel.showLoadingDialog(context, "Loading...");
                    HashMap<String, String> changePasswordHashMap = new HashMap<>();
                    changePasswordHashMap.put("class_type", typeOfClass);
                    changePasswordHashMap.put("start_time", mEtStartTime.getText().toString().trim());
                    changePasswordHashMap.put("end_time", mEtEndTime.getText().toString().trim());
                    changePasswordHashMap.put("end_date", metScheduleEndDate.getText().toString().trim());
                    changePasswordHashMap.put("start_date", metScheduleEndDate.getText().toString().trim());
                    changePasswordHashMap.put("description", mEtDescription.getText().toString().trim());
                    changePasswordHashMap.put("title", mEtTitle.getText().toString().trim());
                    changePasswordHashMap.put("currency", "usd");
                    changePasswordHashMap.put("price", mEtPrice.getText().toString().trim());
                    changePasswordHashMap.put("post_type", "teaching");

                    if (typeOfClass.equalsIgnoreCase("physical")) {
                        changePasswordHashMap.put("class_location", Utility.address);
                        changePasswordHashMap.put("lng", Utility.latitude + "");
                        changePasswordHashMap.put("lat", Utility.longitude + "");
                    }
                    //Create a retrofit call object
                    Call<ResponsePojo> posts = retrofit.create(ApiInterface.class).createTeachingPost(changePasswordHashMap);
                    posts.enqueue(new retrofit2.Callback<ResponsePojo>() {
                        @Override
                        public void onResponse(Call<ResponsePojo> call, retrofit2.Response<ResponsePojo> response) {
                            if (response.body().getStatus() == 200) {
                                CallProgressWheel.dismissLoadingDialog();
                                callSuccessPopUp(context, response.body().getDescription());
                            } else if (response.body().getStatus() == 400) {
                                CallProgressWheel.dismissLoadingDialog();
                                Toast.makeText(context, response.body().getDescription(), Toast.LENGTH_SHORT).show();
                            } else {
                                CallProgressWheel.dismissLoadingDialog();
                                Toast.makeText(context, response.body().getDescription(), Toast.LENGTH_SHORT).show();
                            }
                        }

                        @Override
                        public void onFailure(Call<ResponsePojo> call, Throwable t) {
                            CallProgressWheel.dismissLoadingDialog();
                            Toast.makeText(context, context.getString(R.string.something_went_wrong), Toast.LENGTH_SHORT).show();

                        }
                    });

                }
            }
        } catch (Exception e) {
            CallProgressWheel.dismissLoadingDialog();
            Toast.makeText(context, context.getString(R.string.something_went_wrong), Toast.LENGTH_SHORT).show();
            e.printStackTrace();
        }


    }

    // call get languages api
    public static void getLanguages(final Context context, Retrofit retrofit) {
        try {
            if (Utility.isInternetConnection(context)) {
                CallProgressWheel.showLoadingDialog(context, "Loading...");
                Call<LanguagesResponsePojo> posts = retrofit.create(ApiInterface.class).getLanguages();
                posts.enqueue(new retrofit2.Callback<LanguagesResponsePojo>() {
                    @Override
                    public void onResponse(Call<LanguagesResponsePojo> call, retrofit2.Response<LanguagesResponsePojo> response) {
                        if (response.body().getStatus() == 200) {
                            CallProgressWheel.dismissLoadingDialog();
                            if (context instanceof SetPreferrencesActivity) {
                                ArrayList<String> tempArr = new ArrayList<>();
                                ((SetPreferrencesActivity) context).mAlLangList = response.body().getData();
                                for (LanguagesResponsePojo.LanguagesPojo languagesPojo : response.body().getData()
                                        ) {
                                    tempArr.add(languagesPojo.getName());
                                }
                                ArrayAdapter<String> adapter = new ArrayAdapter<String>(context, R.layout.auto_complete_tv_item, R.id.tv_languagename, tempArr);
                                ((SetPreferrencesActivity) context).mActvLangISpeak.setAdapter(adapter);
                                ((SetPreferrencesActivity) context).mActvLangILearn.setAdapter(adapter);
                            }
                        } else {
                            CallProgressWheel.dismissLoadingDialog();
                            // Log.e("profile",responsePojo.getDescription());
                            //Toast.makeText(MyProfileActivity.this, responsePojo.getDescription(), Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onFailure(Call<LanguagesResponsePojo> call, Throwable t) {
                        CallProgressWheel.dismissLoadingDialog();
                        Toast.makeText(context, context.getString(R.string.something_went_wrong), Toast.LENGTH_SHORT).show();
                    }
                });
            }
        } catch (Exception e) {
            CallProgressWheel.dismissLoadingDialog();
            Toast.makeText(context, context.getString(R.string.something_went_wrong), Toast.LENGTH_SHORT).show();
            e.printStackTrace();
        }
    }

    public static void setPrefrences(final Context context, Retrofit retrofit, PrefInputPojo prefInputPojo) {
        try {
            if (Utility.isInternetConnection(context)) {
                CallProgressWheel.showLoadingDialog(context, "Loading...");
                //Create a retrofit call object
                Call<ResponsePojo> posts = retrofit.create(ApiInterface.class).setPrefrences(prefInputPojo);
                posts.enqueue(new retrofit2.Callback<ResponsePojo>() {
                    @Override
                    public void onResponse(Call<ResponsePojo> call, retrofit2.Response<ResponsePojo> response) {
                        CallProgressWheel.dismissLoadingDialog();
                        PreferenceHandler.writeBoolean(context, PreferenceHandler.IS_PREF_SET, true);
                        Utility.showToastMessageShort((Activity) context, response.body().getDescription());
                    }

                    @Override
                    public void onFailure(Call<ResponsePojo> call, Throwable t) {
                        CallProgressWheel.dismissLoadingDialog();
                    }
                });
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    /**
     * Call api to fetch all the news feed from database
     *
     * @param retrofit
     * @param context
     * @param fragment
     */

    public static void getAllFeedsApi(Retrofit retrofit, final Context context, final PostsListFragment fragment, final int listType) {
        try {
            if (Utility.isInternetConnection(context)) {

                CallProgressWheel.showLoadingDialog(context, "Loading...");
//    http://tongy.ignivastaging.com/api/users/post?post=upcomming     for my upcomming post
//    http://tongy.ignivastaging.com/api/users/post?post=ongoing           for my ongoing post
//    http://tongy.ignivastaging.com/api/users/post?post=other               for my other/archuive post

                //Create a retrofit call object
                Call<PostPojo> posts = null;
                if (listType == R.string.news_feed)
                    posts = retrofit.create(ApiInterface.class).getPosts(fragment.pageNo);
                else if (listType == R.string.upcomming)
                    posts = retrofit.create(ApiInterface.class).getUserPosts(fragment.pageNo, "upcomming");
                else if (listType == R.string.ongoing)
                    posts = retrofit.create(ApiInterface.class).getUserPosts(fragment.pageNo, "ongoing");
                else if (listType == R.string.archives)
                    posts = retrofit.create(ApiInterface.class).getUserPosts(fragment.pageNo, "other");
                else if (listType == R.string.my_favourites)
                    posts = retrofit.create(ApiInterface.class).getFavPosts(fragment.pageNo);

                if (posts != null)
                    posts.enqueue(new retrofit2.Callback<PostPojo>() {
                        @Override
                        public void onResponse(Call<PostPojo> call, retrofit2.Response<PostPojo> response) {
                            if (response.body().getStatus() == 200) {
                                CallProgressWheel.dismissLoadingDialog();
                                if (fragment != null)
                                    fragment.setDataInViewObjects(response.body().getData());
                                //callSuccessPopUp((Activity)context, response.body().getDescription());
                                // Utility.showToastMessageShort(ChangePasswordActivity.this,responsePojo.getDescription());
                            } else {
                                if (fragment != null)
                                    fragment.setDataInViewObjects(null);

                                CallProgressWheel.dismissLoadingDialog();
                                Utility.showToastMessageShort((Activity) context, response.body().getDescription());
                            }
                        }


                        @Override
                        public void onFailure(Call<PostPojo> call, Throwable t) {
                            if (fragment != null)
                                fragment.setDataInViewObjects(null);

                            CallProgressWheel.dismissLoadingDialog();
                        }
                    });
            }


        } catch (Exception e) {
            e.printStackTrace();
        }


    }

    /**
     * Call api to mark favorite and unfavorite
     *
     * @param retrofit
     * @param context
     * @param post_fav
     * @param mIbfav
     * @param post_id
     */
    public static void markFavoriteUnfavorite(Retrofit retrofit, final Context context, final List<?> post_fav, final ImageView mIbfav, int post_id) {
        try {
            if (Utility.isInternetConnection(context)) {

                CallProgressWheel.showLoadingDialog(context, "Loading...");
                HashMap<String, String> signupHash = new HashMap<>();
                signupHash.put("post_id", post_id + "");
                //Create a retrofit call object
                Call<PostPojo> posts = retrofit.create(ApiInterface.class).markFavOrUnfav(signupHash);
                posts.enqueue(new retrofit2.Callback<PostPojo>() {
                    @Override
                    public void onResponse(Call<PostPojo> call, retrofit2.Response<PostPojo> response) {
                        if (response.body().getStatus() == 200) {
                            CallProgressWheel.dismissLoadingDialog();
                            Log.e("success", response.message());
                            if (response.body().getDescription().equalsIgnoreCase("Post  has been favorite successfully."))
                                mIbfav.setImageResource(R.drawable.liked);
                            else
                                mIbfav.setImageResource(R.drawable.like);

                            Utility.showToastMessageShort((Activity) context, response.body().getDescription());

                        } else {
                            CallProgressWheel.dismissLoadingDialog();
                            Utility.showToastMessageShort((Activity) context, response.body().getDescription());
                        }
                    }

                    @Override
                    public void onFailure(Call<PostPojo> call, Throwable t) {
                        CallProgressWheel.dismissLoadingDialog();
                    }
                });
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Call api to sendComment
     *
     * @param retrofit
     * @param context
     * @param post_id
     */
    public static void sendComment(Retrofit retrofit, final Context context, String commentString, int post_id) {
        try {
            if (Utility.isInternetConnection(context)) {
//                 {
//                     "comment":"nice post",”post_id”:”1”
//
//                 }
                CallProgressWheel.showLoadingDialog(context, "Loading...");
                HashMap<String, String> hMap = new HashMap<>();
                hMap.put("post_id", post_id + "");
                hMap.put("commentString", commentString + "");
                //Create a retrofit call object
                Call<PostPojo> posts = retrofit.create(ApiInterface.class).sendComment(hMap);
                posts.enqueue(new retrofit2.Callback<PostPojo>() {
                    @Override
                    public void onResponse(Call<PostPojo> call, retrofit2.Response<PostPojo> response) {
                        if (response.body().getStatus() == 200) {
                            CallProgressWheel.dismissLoadingDialog();
                            Log.e("success", response.message());

                            Utility.showToastMessageShort((Activity) context, response.body().getDescription());

                        } else {
                            CallProgressWheel.dismissLoadingDialog();
                            Utility.showToastMessageShort((Activity) context, response.body().getDescription());
                        }
                    }

                    @Override
                    public void onFailure(Call<PostPojo> call, Throwable t) {
                        CallProgressWheel.dismissLoadingDialog();
                    }
                });
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * **call other post api
     *
     * @param retrofit
     * @param context
     * @param mEtDescription
     * @param mEtTitle
     * @param outPutFile
     */
    public static void callOtherPostApi(Retrofit retrofit, final Context context, EditText mEtDescription, EditText mEtTitle, File outPutFile) {


        // check internet connection
        if (Utility.isInternetConnection(context)) {
            Log.e("outputFile==", outPutFile.length() + "===");

             /*
              payload
              [{"image":"ff.jpg",
                    "description":"deascriii","title":"other section","post_type":"other"}]
            */

            // create RequestBody instance from file
            RequestBody fbody = RequestBody.create(MediaType.parse("multipart/form-data"), outPutFile);
            // MultipartBody.Part is used to send also the actual file name
            MultipartBody.Part body =
                    MultipartBody.Part.createFormData("image", outPutFile.getName(), fbody);
            //  RequestBody is_video = RequestBody.create(MediaType.parse("text/plain"), );

            RequestBody description = RequestBody.create(MediaType.parse("text/plain"), mEtDescription.getText().toString().trim());
            RequestBody title = RequestBody.create(MediaType.parse("text/plain"), mEtTitle.getText().toString().trim());
            RequestBody post_type = RequestBody.create(MediaType.parse("text/plain"), context.getResources().getString(R.string.other));


            Map<String, RequestBody> postOtherPayload = new HashMap<>();
            postOtherPayload.put("description", description);
            postOtherPayload.put("title", title);
            postOtherPayload.put("post_type", post_type);


            CallProgressWheel.showLoadingDialog(context, "Loading...");
            Call<ResponsePojo> posts = retrofit.create(ApiInterface.class).createOtherPost(body, postOtherPayload);
            posts.enqueue(new retrofit2.Callback<ResponsePojo>() {
                @Override
                public void onResponse(Call<ResponsePojo> call, retrofit2.Response<ResponsePojo> response) {
                    if (response.body().getStatus() == 200) {
                        CallProgressWheel.dismissLoadingDialog();
                        callSuccessPopUp(context, response.body().getDescription());
                        Utility.showToastMessageShort((Activity) context, response.body().getDescription());


                    } else {
                        CallProgressWheel.dismissLoadingDialog();
                        Utility.showToastMessageShort((Activity) context, response.body().getDescription());
                    }
                }

                @Override
                public void onFailure(Call<ResponsePojo> call, Throwable t) {
                    CallProgressWheel.dismissLoadingDialog();
                    Toast.makeText((Activity) context, context.getString(R.string.something_went_wrong), Toast.LENGTH_SHORT).show();

                }
            });


        }
    }

    public static void createMeetingPostApi(final Context context, Retrofit retrofit, EditText mEtTitle, EditText mEtDescription, EditText metScheduleStartDate, EditText metScheduleEndDate, EditText mEtStartTime, EditText mEtEndTime) {
        try {
            Utility.hideSoftKeyboard((Activity) context);
            // check validations for current password,new password and confirm password
            if (Validation.validateCreateMeetingPost((Activity) context, mEtTitle, mEtDescription, metScheduleStartDate, metScheduleEndDate, mEtStartTime, mEtEndTime)) {
                if (Utility.isInternetConnection(context)) {

                    /*
                    // payload
                   [{
                        "class_location": "noida",
                        "end_time": "11:00 AM",
                        "start_time": "10:00 AM",
                        "end_date": "20-11-2017",
                        "start_date": "20-10-2017",
                        "lng": "20.00",
                        "lat": "20.22",
                        "description": "deascriii",
                        "title": "meeting section",
                        "post_type": "meeting"
                    }]


                   */

                    CallProgressWheel.showLoadingDialog(context, "Loading...");
                    HashMap<String, String> changePasswordHashMap = new HashMap<>();

                    changePasswordHashMap.put("start_time", mEtStartTime.getText().toString().trim());
                    changePasswordHashMap.put("end_time", mEtEndTime.getText().toString().trim());
                    changePasswordHashMap.put("end_date", metScheduleEndDate.getText().toString().trim());
                    changePasswordHashMap.put("start_date", metScheduleEndDate.getText().toString().trim());
                    changePasswordHashMap.put("description", mEtDescription.getText().toString().trim());
                    changePasswordHashMap.put("title", mEtTitle.getText().toString().trim());
                    changePasswordHashMap.put("post_type", "meeting");
                    changePasswordHashMap.put("class_location", Utility.address);
                    changePasswordHashMap.put("lng", Utility.latitude + "");
                    changePasswordHashMap.put("lat", Utility.longitude + "");


                    //Create a retrofit call object
                    Call<ResponsePojo> posts = retrofit.create(ApiInterface.class).createMeetingPost(changePasswordHashMap);
                    posts.enqueue(new retrofit2.Callback<ResponsePojo>() {
                        @Override
                        public void onResponse(Call<ResponsePojo> call, retrofit2.Response<ResponsePojo> response) {
                            if (response.body().getStatus() == 200) {
                                CallProgressWheel.dismissLoadingDialog();
                                ((CreateTeachingPostActivity)context).callSuccessPopUp(context, response.body().getDescription());


                            } else if (response.body().getStatus() == 400) {
                                CallProgressWheel.dismissLoadingDialog();
                                Toast.makeText(context, response.body().getDescription(), Toast.LENGTH_SHORT).show();
                            } else {
                                CallProgressWheel.dismissLoadingDialog();
                                Toast.makeText(context, response.body().getDescription(), Toast.LENGTH_SHORT).show();
                            }
                        }

                        @Override
                        public void onFailure(Call<ResponsePojo> call, Throwable t) {
                            CallProgressWheel.dismissLoadingDialog();
                            Toast.makeText(context, context.getString(R.string.something_went_wrong), Toast.LENGTH_SHORT).show();

                        }
                    });

                }
            }
        } catch (Exception e) {
            CallProgressWheel.dismissLoadingDialog();
            Toast.makeText(context, context.getString(R.string.something_went_wrong), Toast.LENGTH_SHORT).show();
            e.printStackTrace();
        }


    }



    public static void callReportAbuseApi(final Context context, Retrofit retrofit, EditText metReason, EditText metComment, int post_id) {
        try {
            if (Utility.isInternetConnection(context)) {

          /*      {
                    "post_id":"1",
                     "reason":"abuse",
                    “comment”:”text”,
                }*/

                CallProgressWheel.showLoadingDialog(context, "Loading...");
                HashMap<String, String> changePasswordHashMap = new HashMap<>();
                changePasswordHashMap.put("post_id", post_id + "");
                changePasswordHashMap.put("reason", metReason.getText().toString());
                changePasswordHashMap.put("comment", metComment.getText().toString());


                //Create a retrofit call object
                Call<ResponsePojo> posts = retrofit.create(ApiInterface.class).reportAbuse(changePasswordHashMap);
                posts.enqueue(new retrofit2.Callback<ResponsePojo>() {
                    @Override
                    public void onResponse(Call<ResponsePojo> call, retrofit2.Response<ResponsePojo> response) {
                        if (response.body().getStatus() == 200) {
                            CallProgressWheel.dismissLoadingDialog();
                            ((ChangePasswordActivity) context).callSuccessPopUp(context, response.body().getDescription());


                        } else if (response.body().getStatus() == 400) {
                            CallProgressWheel.dismissLoadingDialog();
                            Toast.makeText(context, response.body().getDescription(), Toast.LENGTH_SHORT).show();
                        } else {
                            CallProgressWheel.dismissLoadingDialog();
                            Toast.makeText(context, response.body().getDescription(), Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onFailure(Call<ResponsePojo> call, Throwable t) {
                        CallProgressWheel.dismissLoadingDialog();
                        Toast.makeText(context, context.getString(R.string.something_went_wrong), Toast.LENGTH_SHORT).show();

                    }
                });

            }

        } catch (Exception e) {
            CallProgressWheel.dismissLoadingDialog();
            Toast.makeText(context, context.getString(R.string.something_went_wrong), Toast.LENGTH_SHORT).show();
            e.printStackTrace();
        }


    }

    /**
     * Call single post api to view post details
     * @param post_id
     * @param retrofit
     * @param context
     */

    public static void getPostDetail(int post_id, Retrofit retrofit, final PostDetailActivity context) {
        try {
            if (Utility.isInternetConnection(context)) {

                //Create a retrofit call object
                Call<PostDetailPojo> posts = retrofit.create(ApiInterface.class).singlePostDetail(post_id);
                posts.enqueue(new retrofit2.Callback<PostDetailPojo>() {
                    @Override
                    public void onResponse(Call<PostDetailPojo> call, retrofit2.Response<PostDetailPojo> response) {
                        if (response.body().getStatus() == 200) {
                            CallProgressWheel.dismissLoadingDialog();
                            context.setData(response.body().getData());


                        } else if (response.body().getStatus() == 400) {
                            CallProgressWheel.dismissLoadingDialog();
                            Toast.makeText(context, response.body().getDescription(), Toast.LENGTH_SHORT).show();
                        } else {
                            CallProgressWheel.dismissLoadingDialog();
                            Toast.makeText(context, response.body().getDescription(), Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onFailure(Call<PostDetailPojo> call, Throwable t) {
                        CallProgressWheel.dismissLoadingDialog();
                        Toast.makeText(context, context.getString(R.string.something_went_wrong), Toast.LENGTH_SHORT).show();

                    }
                });

            }

        } catch (Exception e) {
            CallProgressWheel.dismissLoadingDialog();
            Toast.makeText(context, context.getString(R.string.something_went_wrong), Toast.LENGTH_SHORT).show();
            e.printStackTrace();
        }


    }
}

