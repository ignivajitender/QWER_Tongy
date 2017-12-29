package com.igniva.qwer.controller;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.support.v4.app.FragmentActivity;
import android.support.v7.widget.SwitchCompat;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;

import com.igniva.qwer.R;
import com.igniva.qwer.model.ConnectionPojo;
import com.igniva.qwer.model.CountriesPojo;
import com.igniva.qwer.model.CountriesResponsePojo;
import com.igniva.qwer.model.LanguagesResponsePojo;
import com.igniva.qwer.model.OtherUserProfilePojo;
import com.igniva.qwer.model.PostDetailPojo;
import com.igniva.qwer.model.PostPojo;
import com.igniva.qwer.model.PrefInputPojo;
import com.igniva.qwer.model.PrefsResponsePojo;
import com.igniva.qwer.model.ProfileResponsePojo;
import com.igniva.qwer.model.ResponsePojo;
import com.igniva.qwer.model.StateResponsePojo;
import com.igniva.qwer.model.TokenPojo;
import com.igniva.qwer.model.UsersResponsePojo;
import com.igniva.qwer.ui.activities.ChangePasswordActivity;
import com.igniva.qwer.ui.activities.CommentsActivity;
import com.igniva.qwer.ui.activities.ConnectionAcceptedActivity;
import com.igniva.qwer.ui.activities.CreateOtherPostActivity;
import com.igniva.qwer.ui.activities.CreateTeachingPostActivity;
import com.igniva.qwer.ui.activities.MainActivity;
import com.igniva.qwer.ui.activities.MyProfileActivity;
import com.igniva.qwer.ui.activities.OtherUserProfileActivity;
import com.igniva.qwer.ui.activities.PostDetailActivity;
import com.igniva.qwer.ui.activities.SetPreferrencesActivity;
import com.igniva.qwer.ui.activities.SettingsActivity;
import com.igniva.qwer.ui.activities.TwilioVideoActivity;
import com.igniva.qwer.ui.activities.twilio_chat.MainChatActivity;
import com.igniva.qwer.ui.activities.voice.TwilioVoiceClientActivity;
import com.igniva.qwer.ui.adapters.RecyclerviewAdapter;
import com.igniva.qwer.ui.fragments.ConnectionsFragment;
import com.igniva.qwer.ui.fragments.HomeFragment;
import com.igniva.qwer.ui.fragments.PostsListFragment;
import com.igniva.qwer.ui.views.CallProgressWheel;
import com.igniva.qwer.utils.Constants;
import com.igniva.qwer.utils.CustomExpandableListView;
import com.igniva.qwer.utils.Global;
import com.igniva.qwer.utils.PreferenceHandler;
import com.igniva.qwer.utils.Utility;
import com.igniva.qwer.utils.Validation;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.text.Normalizer;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit.mime.TypedInput;
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
    public static void createTeachingPostApi(final Context context, Retrofit retrofit, EditText mEtTitle, EditText mEtDescription, EditText mEtCurrency, EditText mEtPrice, EditText metScheduleStartDate, EditText metScheduleEndDate, EditText mEtStartTime, EditText mEtEndTime, String typeOfClass) {
        try {
            Utility.hideSoftKeyboard((Activity) context);
            // check validations for current password,new password and confirm password
            if (Validation.validateCreatePost((Activity) context, mEtTitle, mEtDescription, mEtCurrency, mEtPrice, metScheduleStartDate, metScheduleEndDate, mEtStartTime, mEtEndTime, typeOfClass)) {
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
                    changePasswordHashMap.put("start_date", metScheduleStartDate.getText().toString().trim());
                    changePasswordHashMap.put("description", mEtDescription.getText().toString().trim());
                    changePasswordHashMap.put("title", mEtTitle.getText().toString().trim());
                    changePasswordHashMap.put("currency", mEtCurrency.getText().toString().trim().toLowerCase());
                    changePasswordHashMap.put("price", mEtPrice.getText().toString().trim());
                    changePasswordHashMap.put("post_type", "teaching");

                    if (typeOfClass.equalsIgnoreCase("physical")) {
                        changePasswordHashMap.put("class_location", Utility.address);
                        changePasswordHashMap.put("lat", Utility.latitude + "");
                        changePasswordHashMap.put("lng", Utility.longitude + "");
                    }
                    //Create a retrofit call object
                    Call<ResponsePojo> posts = retrofit.create(ApiInterface.class).createTeachingPost(changePasswordHashMap);
                    posts.enqueue(new retrofit2.Callback<ResponsePojo>() {
                        @Override
                        public void onResponse(Call<ResponsePojo> call, retrofit2.Response<ResponsePojo> response) {
                            if (response.body().getStatus() == 200) {
                                CallProgressWheel.dismissLoadingDialog();
                                ((CreateTeachingPostActivity) context).callSuccessPopUp(context, response.body().getDescription());


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

    // call get prefs api
    public static void getPrefrences(final Context context, Retrofit retrofit) {
        try {
            if (Utility.isInternetConnection(context)) {
                CallProgressWheel.showLoadingDialog(context, "Loading...");
                Call<PrefsResponsePojo> posts = retrofit.create(ApiInterface.class).getPrefrences();
                posts.enqueue(new retrofit2.Callback<PrefsResponsePojo>() {
                    @Override
                    public void onResponse(Call<PrefsResponsePojo> call, retrofit2.Response<PrefsResponsePojo> response) {
                        if (response.body().getStatus() == 200) {
                            CallProgressWheel.dismissLoadingDialog();
                            ((SetPreferrencesActivity) context).setupLayout(response.body());
                        } else {
                            CallProgressWheel.dismissLoadingDialog();
                            // Log.e("profile",responsePojo.getDescription());
                            //Toast.makeText(MyProfileActivity.this, responsePojo.getDescription(), Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onFailure(Call<PrefsResponsePojo> call, Throwable t) {
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
                                //Utility.showToastMessageShort((Activity) context, response.body().getDescription());
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
                            if (response.body().getDescription().equalsIgnoreCase("Post has been favourite successfully."))
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
    public static void sendComment(final Retrofit retrofit, final Context context, String commentString, final int post_id) {
        try {
            if (Utility.isInternetConnection(context)) {
//                 {
//                     "comment":"nice post",”post_id”:”1”
//
//                 }
                CallProgressWheel.showLoadingDialog(context, "Loading...");
                HashMap<String, String> hMap = new HashMap<>();
                hMap.put("post_id", post_id + "");
                hMap.put("comment", commentString + "");
                //Create a retrofit call object
                Call<PostPojo> posts = retrofit.create(ApiInterface.class).sendComment(hMap);
                posts.enqueue(new retrofit2.Callback<PostPojo>() {
                    @Override
                    public void onResponse(Call<PostPojo> call, retrofit2.Response<PostPojo> response) {
                        if (response.body().getStatus() == 200) {
                            CallProgressWheel.dismissLoadingDialog();
                            Log.e("success", response.message());
                            getPostDetail(post_id, retrofit, (Activity) context);
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
                        ((CreateOtherPostActivity) context).callSuccessPopUp(context, response.body().getDescription());
                        //Utility.showToastMessageShort((Activity) context, response.body().getDescription());


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

    public static void createMeetingPostApi(final Context context, Retrofit retrofit, EditText mEtTitle, EditText mEtDescription, EditText metScheduleStartDate, EditText metScheduleEndDate, EditText mEtStartTime, EditText mEtEndTime, CustomExpandableListView mlvAddMembersList, EditText metAddMembers, ArrayList<String> todoList) {
        try {
            Utility.hideSoftKeyboard((Activity) context);
            // check validations for current password,new password and confirm password
            if (Validation.validateCreateMeetingPost((Activity) context, mEtTitle, mEtDescription, metScheduleStartDate, metScheduleEndDate, mEtStartTime, mEtEndTime, mlvAddMembersList, metAddMembers)) {
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
                    HashMap<String, Object> changePasswordHashMap = new HashMap<>();

                    changePasswordHashMap.put("start_time", mEtStartTime.getText().toString().trim());
                    changePasswordHashMap.put("end_time", mEtEndTime.getText().toString().trim());
                    changePasswordHashMap.put("end_date", metScheduleEndDate.getText().toString().trim());
                    changePasswordHashMap.put("start_date", metScheduleStartDate.getText().toString().trim());
                    changePasswordHashMap.put("description", mEtDescription.getText().toString().trim());
                    changePasswordHashMap.put("title", mEtTitle.getText().toString().trim());
                    changePasswordHashMap.put("post_type", "meeting");

                    JSONArray jsArray = new JSONArray(todoList);
                    Log.e("array", jsArray + "");
                    changePasswordHashMap.put("class_location", Utility.address);
                    changePasswordHashMap.put("lat", Utility.latitude + "");
                    changePasswordHashMap.put("lng", Utility.longitude + "");
                    changePasswordHashMap.put("tag", todoList.toString().trim().substring(1, todoList.toString().length() - 1));
                   /* Utility.latitude=0.0;
                    Utility.longitude=0.0;
                    Utility.address="";
*/

                    Log.e("payload", changePasswordHashMap + "");
                    //Create a retrofit call object
                    Call<ResponsePojo> posts = retrofit.create(ApiInterface.class).createMeetingPost(changePasswordHashMap);
                    posts.enqueue(new retrofit2.Callback<ResponsePojo>() {
                        @Override
                        public void onResponse(Call<ResponsePojo> call, retrofit2.Response<ResponsePojo> response) {

                            if (response.body().getStatus() == 200) {
                                CallProgressWheel.dismissLoadingDialog();
                                ((CreateTeachingPostActivity) context).callSuccessPopUp(context, response.body().getDescription());
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

    public static void callReportAbuseApi(final Context context, Retrofit retrofit, EditText metReason, EditText metComment, int post_id, final Dialog dialog) {
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
                            Utility.showToastMessageLong((Activity) context, response.body().getDescription());
                            //((ChangePasswordActivity) context).callSuccessPopUp(context, response.body().getDescription());
                            dialog.dismiss();

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

    public static void deletePost(final Context context, Retrofit retrofit, final PostDetailPojo.DataPojo postPojo, final RecyclerviewAdapter recyclerviewAdapter) {
        try {
            if (Utility.isInternetConnection(context)) {
           /*      {
                    "post_id":"1",
                 }*/
                CallProgressWheel.showLoadingDialog(context, "Loading...");
//                HashMap<String, String> changePasswordHashMap = new HashMap<>();
//                changePasswordHashMap.put("post_id", post_id + "");
                //Create a retrofit call object
                Call<ResponsePojo> posts = retrofit.create(ApiInterface.class).deletePost(postPojo.getId() + "");
                posts.enqueue(new retrofit2.Callback<ResponsePojo>() {
                    @Override
                    public void onResponse(Call<ResponsePojo> call, retrofit2.Response<ResponsePojo> response) {
                        if (response.body().getStatus() == 200) {
                            if (recyclerviewAdapter != null)
                                recyclerviewAdapter.remove(postPojo);
                            CallProgressWheel.dismissLoadingDialog();
                            Utility.showToastMessageLong((Activity) context, response.body().getDescription());
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
     *
     * @param post_id
     * @param retrofit
     * @param context
     */
    public static void getPostDetail(int post_id, Retrofit retrofit, final Activity context) {
        try {
            if (Utility.isInternetConnection(context)) {
                //Create a retrofit call object
                Call<PostDetailPojo> posts = retrofit.create(ApiInterface.class).singlePostDetail(post_id);
                posts.enqueue(new retrofit2.Callback<PostDetailPojo>() {
                    @Override
                    public void onResponse(Call<PostDetailPojo> call, retrofit2.Response<PostDetailPojo> response) {
                        if (response.body().getStatus() == 200) {
                            CallProgressWheel.dismissLoadingDialog();
                            if (context instanceof PostDetailActivity)
                                ((PostDetailActivity) context).setData(response.body().getData());
                            else {
                                ((CommentsActivity) context).setData(response.body().getData().getPost_comment());
                            }

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

    /**
     * get my connections api
     *
     * @param retrofit
     * @param activity
     * @param fragment
     */

    public static void getMyConnectionsApi(Retrofit retrofit, FragmentActivity activity, final ConnectionsFragment fragment) {
        try {
            if (Utility.isInternetConnection(activity)) {

                CallProgressWheel.showLoadingDialog(activity, "Loading...");

                Call<ConnectionPojo> posts = null;
                posts = retrofit.create(ApiInterface.class).getMyConnections(fragment.pageNo);

                if (posts != null)
                    posts.enqueue(new retrofit2.Callback<ConnectionPojo>() {
                        @Override
                        public void onResponse(Call<ConnectionPojo> call, retrofit2.Response<ConnectionPojo> response) {
                            if (response.body().getStatus() == 200) {
                                CallProgressWheel.dismissLoadingDialog();
                                if (fragment != null) {
                                    fragment.setDataInViewObjects(response.body());
                                    //callSuccessPopUp((Activity)context, response.body().getDescription());
                                    // Utility.showToastMessageShort(ChangePasswordActivity.this,responsePojo.getDescription());
                                }
                            } else {
                                if (fragment != null)
                                    fragment.setDataInViewObjects(null);

                                CallProgressWheel.dismissLoadingDialog();
                                //Utility.showToastMessageShort((Activity) context, response.body().getDescription());
                            }
                        }


                        @Override
                        public void onFailure(Call<ConnectionPojo> call, Throwable t) {
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

    public static void changeIsPushNotification(Retrofit retrofit, final SettingsActivity activity, int isPushNotification, final SwitchCompat mswitchPushnotification) {

        try {
            if (Utility.isInternetConnection(activity)) {

                //CallProgressWheel.showLoadingDialog(activity, "Loading...");

                Call<ResponsePojo> posts = null;
                posts = retrofit.create(ApiInterface.class).notificationOnOff(isPushNotification);

                if (posts != null)
                    posts.enqueue(new retrofit2.Callback<ResponsePojo>() {
                        @Override
                        public void onResponse(Call<ResponsePojo> call, retrofit2.Response<ResponsePojo> response) {
                            if (response.body().getStatus() == 200) {
                                //CallProgressWheel.dismissLoadingDialog();
                                //callSuccessPopUp((Activity)context, response.body().getDescription());
                                //Utility.showToastMessageShort(activity,response.body().getDescription());
                            } else {

                                //CallProgressWheel.dismissLoadingDialog();
                                //Utility.showToastMessageShort(activity, response.body().getDescription());

                            }
                        }

                        @Override
                        public void onFailure(Call<ResponsePojo> call, Throwable t) {


                            // CallProgressWheel.dismissLoadingDialog();
                        }
                    });
            }


        } catch (Exception e) {
            e.printStackTrace();
        }


    }

    public static void changeIsVideoCall(Retrofit retrofit, final SettingsActivity activity, int isVideoCall, SwitchCompat mswitchVideoCall) {


        try {
            if (Utility.isInternetConnection(activity)) {

                //CallProgressWheel.showLoadingDialog(activity, "Loading...");

                Call<ResponsePojo> posts = null;
                posts = retrofit.create(ApiInterface.class).videoOnOff(isVideoCall);

                if (posts != null)
                    posts.enqueue(new retrofit2.Callback<ResponsePojo>() {
                        @Override
                        public void onResponse(Call<ResponsePojo> call, retrofit2.Response<ResponsePojo> response) {
                            if (response.body().getStatus() == 200) {
                                // CallProgressWheel.dismissLoadingDialog();
                                //callSuccessPopUp((Activity)context, response.body().getDescription());
                                //Utility.showToastMessageShort(activity,response.body().getDescription());
                            } else {

                                //CallProgressWheel.dismissLoadingDialog();
                                // Utility.showToastMessageShort(activity,response.body().getDescription());

                            }
                        }

                        @Override
                        public void onFailure(Call<ResponsePojo> call, Throwable t) {


                            //CallProgressWheel.dismissLoadingDialog();
                        }
                    });
            }


        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public static void sendTwilioVoiceNotification(final Context context, Retrofit retrofit, final String roomName, String identity, final String receaverName, final String userImages) {

        try {

            if (Utility.isInternetConnection(context)) {
                CallProgressWheel.showLoadingDialog(context, "Loading...");

//                if (userImages != null && userImages.size() > 0)
//                    receiverImage = userImages.get(0).getImage();
                Call<TokenPojo> posts = null;
                JSONObject jsonObject = new JSONObject();
                String receaverNameNormalized = Normalizer.normalize(receaverName, Normalizer.Form.NFD);
                jsonObject.put("reciver_name", receaverNameNormalized.replaceAll("[^A-Za-z0-9]", ""));
                String sender_nameNormalized = Normalizer.normalize(PreferenceHandler.readString(context, PreferenceHandler.PREF_KEY_USER_NAME, "Caller").replaceAll(" ", "_"), Normalizer.Form.NFD);
                jsonObject.put("sender_name", sender_nameNormalized.replaceAll("[^A-Za-z0-9]", ""));
                jsonObject.put("room_name", roomName);
                jsonObject.put("room_id", roomName);
                JSONArray jsonArray = new JSONArray();
                ArrayList<String> arrayList = new ArrayList<>();
                jsonArray.put(new JSONObject().put("id", identity));
//                jsonArray.put(new JSONObject().put("id",  PreferenceHandler.readString(context,PreferenceHandler.PREF_KEY_USER_ID,"")));
                jsonObject.put("identity", jsonArray);
                TypedInput in = null;
                RequestBody myreqbody = null;
                try {
                    myreqbody = RequestBody.create(okhttp3.MediaType.parse("application/json; charset=utf-8"),
                            (new JSONObject(String.valueOf(jsonObject))).toString());
                    posts = retrofit.create(ApiInterface.class).sendTwilioVoiceNotification(myreqbody);

                } catch (JSONException e) {
                    e.printStackTrace();
                }
//                try {
//                    in = new TypedByteArray("application/json", jsonObject.toString().getBytes("UTF-8"));
//                    posts = retrofit.create(ApiInterface.class).getVideoToken(in);
//                 } catch (UnsupportedEncodingException e) {
//                    e.printStackTrace();
//                }
                if (posts != null)
                    posts.enqueue(new retrofit2.Callback<TokenPojo>() {
                        @Override
                        public void onResponse(Call<TokenPojo> call, retrofit2.Response<TokenPojo> response) {
                            if (response.body().getStatus() == 200) {
                                Log.e(TAG, "sendTwilioVoiceNotification token: " + response.body().getToken());
                                Log.e(TAG, "sendTwilioVoiceNotification room_name: " + roomName);
                                CallProgressWheel.dismissLoadingDialog();
                                Intent intent = new Intent(context, TwilioVoiceClientActivity.class);
                                intent.putExtra(Constants.TWILIO_TOKEN, "" + response.body().getToken());
                                intent.putExtra(Constants.TWILIO_SENDER_NAME, PreferenceHandler.readString(context, PreferenceHandler.PREF_KEY_USER_NAME, "Caller").replaceAll(" ", "_"));
                                intent.putExtra(Constants.TWILIO_RECEAVER_NAME, receaverName);
                                intent.putExtra(Constants.TWILIO_RECEAVER_IMAGE, userImages);
                                intent.putExtra(Constants.ROOM_TITLE, "Voice Call");

                                context.startActivity(intent);
                                // Utility.showToastMessageShort(ChangePasswordActivity.this,responsePojo.getDescription());
                            } else {
                                CallProgressWheel.dismissLoadingDialog();
                                //Utility.showToastMessageShort((Activity) context, response.body().getDescription());
                            }
                        }

                        @Override
                        public void onFailure(Call<TokenPojo> call, Throwable t) {
                            CallProgressWheel.dismissLoadingDialog();
                        }
                    });
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void getVideoToken(final Context context, Retrofit retrofit, final String roomName, String identity, final String userName, final String userImage) {
        try {
            if (Utility.isInternetConnection(context)) {
                CallProgressWheel.showLoadingDialog(context, "Loading...");
                Call<TokenPojo> posts = null;
                JSONObject jsonObject = new JSONObject();
                jsonObject.put("room_name", roomName);
                jsonObject.put("room_id", roomName);
                JSONArray jsonArray = new JSONArray();
                ArrayList<String> arrayList = new ArrayList<>();
                jsonArray.put(new JSONObject().put("id", identity));
                jsonArray.put(new JSONObject().put("id", PreferenceHandler.readString(context, PreferenceHandler.PREF_KEY_USER_ID, "")));
                jsonObject.put("identity", jsonArray);
//                TypedInput in = null;
                RequestBody myreqbody = null;
                try {
                    myreqbody = RequestBody.create(okhttp3.MediaType.parse("application/json; charset=utf-8"),
                            (new JSONObject(String.valueOf(jsonObject))).toString());
                    posts = retrofit.create(ApiInterface.class).getVideoToken(myreqbody);

                } catch (JSONException e) {
                    e.printStackTrace();
                }
//                try {
//                    in = new TypedByteArray("application/json", jsonObject.toString().getBytes("UTF-8"));
//                    posts = retrofit.create(ApiInterface.class).getVideoToken(in);
//                 } catch (UnsupportedEncodingException e) {
//                    e.printStackTrace();
//                }
                if (posts != null)
                    posts.enqueue(new retrofit2.Callback<TokenPojo>() {
                        @Override
                        public void onResponse(Call<TokenPojo> call, retrofit2.Response<TokenPojo> response) {
                            if (response.body().getStatus() == 200) {
                                Log.e(TAG, "getVideoToken token: " + response.body().getToken());
                                Log.e(TAG, "getVideoToken room_name: " + roomName);
                                CallProgressWheel.dismissLoadingDialog();
//                                VideoActivity.TWILIO_ACCESS_TOKEN=response.body().getToken();
//                                VideoActivity.TWILIO_ROOM_ID=roomName ;
                                context.startActivity(new Intent(context, TwilioVideoActivity.class).putExtra(Constants.TWILIO_TOKEN, response.body().getToken()).putExtra(Constants.TWILIO_ROOM, roomName).putExtra(Constants.TWILIO_RECEAVER_NAME, userName)
                                        .putExtra(Constants.TWILIO_RECEAVER_IMAGE, userImage))
                                ;

                                // Utility.showToastMessageShort(ChangePasswordActivity.this,responsePojo.getDescription());
                            } else {
                                CallProgressWheel.dismissLoadingDialog();
                                //Utility.showToastMessageShort((Activity) context, response.body().getDescription());
                            }
                        }

                        @Override
                        public void onFailure(Call<TokenPojo> call, Throwable t) {
                            CallProgressWheel.dismissLoadingDialog();
                        }
                    });
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void changeIsVoiceCall(Retrofit retrofit, SettingsActivity activity, int isVoiceCall, SwitchCompat mswitchVideoCall) {
        try {
            if (Utility.isInternetConnection(activity)) {

                //CallProgressWheel.showLoadingDialog(activity, "Loading...");

                Call<ResponsePojo> posts = null;
                posts = retrofit.create(ApiInterface.class).voiceOnOff(isVoiceCall);

                if (posts != null)
                    posts.enqueue(new retrofit2.Callback<ResponsePojo>() {
                        @Override
                        public void onResponse(Call<ResponsePojo> call, retrofit2.Response<ResponsePojo> response) {
                            if (response.body().getStatus() == 200) {
                                //CallProgressWheel.dismissLoadingDialog();
                                //callSuccessPopUp((Activity)context, response.body().getDescription());
                                //Utility.showToastMessageShort(activity,response.body().getDescription());
                            } else {

                                //CallProgressWheel.dismissLoadingDialog();
                                //Utility.showToastMessageShort(activity,response.body().getDescription());

                            }
                        }

                        @Override
                        public void onFailure(Call<ResponsePojo> call, Throwable t) {


                            //CallProgressWheel.dismissLoadingDialog();
                        }
                    });
            }


        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public static void callCountriesListApi(final MyProfileActivity activity, Retrofit retrofit, EditText mautocomTextViewCountry) {
        try {
            if (Utility.isInternetConnection(activity)) {

                CallProgressWheel.showLoadingDialog(activity, "Loading...");

                Call<CountriesResponsePojo> posts = null;
                posts = retrofit.create(ApiInterface.class).getCountriesList();

                if (posts != null)
                    posts.enqueue(new retrofit2.Callback<CountriesResponsePojo>() {
                        @Override
                        public void onResponse(Call<CountriesResponsePojo> call, retrofit2.Response<CountriesResponsePojo> response) {
                            if (response.body().getStatus() == 200) {
                                //CallProgressWheel.dismissLoadingDialog();
                                ((MyProfileActivity) activity).mCountryList = new ArrayList<>();
                                // ((MyProfileActivity)activity).countriesList(response.body().getData());
                                //                                ArrayAdapter<String> adapter = new ArrayAdapter<String>(activity, R.layout.layout_country_item, R.id.tvName, tempArr);
                                for (CountriesPojo countriesPojo : response.body().getData()
                                        ) {
                                    if (countriesPojo.getCountry_flag().length() > 0)
                                        ((MyProfileActivity) activity).mCountryList.add(countriesPojo);

                                }
                                activity.mCountryPicker.setCountriesList(((MyProfileActivity) activity).mCountryList);
                                activity.getProfileApi();
                                //                             ((MyProfileActivity) activity).etCountry.setAdapter(adapter);

                                //callSuccessPopUp((Activity)context, response.body().getDescription());
                                // Utility.showToastMessageShort(ChangePasswordActivity.this,responsePojo.getDescription());
                            } else {

                                //CallProgressWheel.dismissLoadingDialog();
                                //Utility.showToastMessageShort((Activity) context, response.body().getDescription());

                            }
                        }

                        @Override
                        public void onFailure(Call<CountriesResponsePojo> call, Throwable t) {
                            CallProgressWheel.dismissLoadingDialog();
                        }
                    });
            }


        } catch (Exception e) {
            e.printStackTrace();
        }


    }


    public static void getOtherUserProfile(Retrofit retrofit, final Activity activity, final int userId, final boolean goToChat) {
        int view = 0;
        try {
            if (activity instanceof MainActivity)
                view = 1;
            if (Utility.isInternetConnection(activity)) {

                CallProgressWheel.showLoadingDialog(activity, "Loading...");

                Call<OtherUserProfilePojo> posts = null;
                posts = retrofit.create(ApiInterface.class).getSingleUser(userId, view);

                if (posts != null)
                    posts.enqueue(new retrofit2.Callback<OtherUserProfilePojo>() {
                        @Override
                        public void onResponse(Call<OtherUserProfilePojo> call, retrofit2.Response<OtherUserProfilePojo> response) {
                            CallProgressWheel.dismissLoadingDialog();
                            if (response.body().getStatus() == 200) {
                                Global.otherUserProfilePojo = response.body();
                                if (activity instanceof OtherUserProfileActivity)
                                    ((OtherUserProfileActivity) activity).setData(response);
                                if (activity instanceof ConnectionAcceptedActivity)
                                    ((ConnectionAcceptedActivity) activity).setData(response);


                                if (goToChat)
                                    Utility.goToChatActivity(activity, userId, response.body().getUsers().getName(), response.body().getUsers().getUser_image().get(0).getImage());

                                //callSuccessPopUp((Activity)context, response.body().getDescription());
                                // Utility.showToastMessageShort(ChangePasswordActivity.this,responsePojo.getDescription());
                            } else {

                                CallProgressWheel.dismissLoadingDialog();
                                //Utility.showToastMessageShort((Activity) context, response.body().getDescription());

                            }
                        }

                        @Override
                        public void onFailure(Call<OtherUserProfilePojo> call, Throwable t) {


                            CallProgressWheel.dismissLoadingDialog();
                        }
                    });
            }


        } catch (Exception e) {
            e.printStackTrace();
        }

    }


    public static void callblockApi(final Activity activity, Retrofit retrofit, int userId, final PopupWindow popup, final TextView mtvBlockUnblock) {
        try {
            if (Utility.isInternetConnection(activity)) {

                CallProgressWheel.showLoadingDialog(activity, "Loading...");

                Call<ResponsePojo> posts = null;
                posts = retrofit.create(ApiInterface.class).blockUnblockUser(userId);
                final String[] text = {""};
                if (posts != null)
                    posts.enqueue(new retrofit2.Callback<ResponsePojo>() {
                        @Override
                        public void onResponse(Call<ResponsePojo> call, retrofit2.Response<ResponsePojo> response) {
                            if (response.body().getStatus() == 200) {
                                CallProgressWheel.dismissLoadingDialog();
                                // activity.setData(response);
                                //callSuccessPopUp((Activity)context, response.body().getDescription());
                                Utility.showToastMessageShort(activity, response.body().getDescription());
                                if (response.body().getDescription().equalsIgnoreCase("User has been blocked successfully.")) {
                                    mtvBlockUnblock.setText("Unblock");
                                    text[0] = "Unblock";
                                    if (activity instanceof OtherUserProfileActivity)
                                        ((OtherUserProfileActivity) activity).type = "Unblock";
                                    else if (activity instanceof MainChatActivity)
                                        ((MainChatActivity) activity).type = "Unblock";
                                } else {
                                    mtvBlockUnblock.setText("Block");
                                    text[0] = "Block";
                                    if (activity instanceof OtherUserProfileActivity)
                                        ((OtherUserProfileActivity) activity).type = "Block";
                                    if (activity instanceof MainChatActivity)
                                        ((MainChatActivity) activity).type = "Block";
                                }
                                // activity.openBlockMenu(text[0]);
                            } else {
                                CallProgressWheel.dismissLoadingDialog();
                                //Utility.showToastMessageShort((Activity) context, response.body().getDescription());
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

    public static void callReportUserApi(final Context context, Retrofit retrofit, EditText metReason, EditText metComment, int userId, final Dialog dialog) {

        try {
            if (Utility.isInternetConnection(context)) {
           /*      {
                    "post_id":"1",
                     "reason":"abuse",
                    “comment”:”text”,
                }*/
                CallProgressWheel.showLoadingDialog(context, "Loading...");
                HashMap<String, String> changePasswordHashMap = new HashMap<>();
                changePasswordHashMap.put("user_id", userId + "");
                changePasswordHashMap.put("reason", metReason.getText().toString());
                changePasswordHashMap.put("comment", metComment.getText().toString());
                //Create a retrofit call object
                Call<ResponsePojo> posts = retrofit.create(ApiInterface.class).reportUser(changePasswordHashMap);
                posts.enqueue(new retrofit2.Callback<ResponsePojo>() {
                    @Override
                    public void onResponse(Call<ResponsePojo> call, retrofit2.Response<ResponsePojo> response) {
                        if (response.body().getStatus() == 200) {
                            CallProgressWheel.dismissLoadingDialog();
                            Utility.showToastMessageLong((Activity) context, response.body().getDescription());
                            //((ChangePasswordActivity) context).callSuccessPopUp(context, response.body().getDescription());
                            dialog.dismiss();

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
     * User action like delete/accept/remove api
     *
     * @param context
     * @param retrofit
     * @param action
     * @param popup
     * @param userId
     * @param fragment
     */
    public static void callUserAction(final Context context, Retrofit retrofit, String action, final PopupWindow popup, final int userId, final HomeFragment fragment) {
        try {
            if (Utility.isInternetConnection(context)) {
           /*      {
                    "post_id":"1",
                     "reason":"abuse",
                    “comment”:”text”,
                }*/

                CallProgressWheel.showLoadingDialog(context, "Loading...");
                HashMap<String, String> changePasswordHashMap = new HashMap<>();
                changePasswordHashMap.put("request_from", userId + "");
                changePasswordHashMap.put("action", action);

                //Create a retrofit call object
                Call<ResponsePojo> posts = retrofit.create(ApiInterface.class).userAction(changePasswordHashMap);
                posts.enqueue(new retrofit2.Callback<ResponsePojo>() {
                    @Override
                    public void onResponse(Call<ResponsePojo> call, retrofit2.Response<ResponsePojo> response) {
                        if (response.body().getStatus() == 200) {
                            CallProgressWheel.dismissLoadingDialog();
                            Utility.showToastMessageLong((Activity) context, response.body().getDescription());
                            //((ChangePasswordActivity) context).callSuccessPopUp(context, response.body().getDescription());
                            if (popup != null)
                                popup.dismiss();


                            if (response.body().getDescription().equalsIgnoreCase("Request accepted.")) {
                                ((HomeFragment) fragment).swipeRight();
                                context.startActivity(new Intent(context, ConnectionAcceptedActivity.class).putExtra("userId", userId));
                            }
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

    public static void callAddContactApi(int request_to, Retrofit retrofit, final Context context, final List<UsersResponsePojo.UsersPojo.UserDataPojo> users, int position, final HomeFragment fragment) {
        try {
            if (Utility.isInternetConnection(context)) {
           /*      {
                    "post_id":"1",
                     "reason":"abuse",
                    “comment”:”text”,
                }*/

                CallProgressWheel.showLoadingDialog(context, "Loading...");
                //Create a retrofit call object
                Call<ResponsePojo> posts = retrofit.create(ApiInterface.class).requestSend(request_to);
                posts.enqueue(new retrofit2.Callback<ResponsePojo>() {
                    @Override
                    public void onResponse(Call<ResponsePojo> call, retrofit2.Response<ResponsePojo> response) {
                        if (response.body().getStatus() == 200) {
                            CallProgressWheel.dismissLoadingDialog();
                            Utility.showToastMessageLong((Activity) context, response.body().getDescription());
                            ((HomeFragment) fragment).swipeRight();
                            //((ChangePasswordActivity) context).callSuccessPopUp(context, response.body().getDescription());
                            //popup.dismiss();

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


    public static void imageDelete(final int pos, final List<ProfileResponsePojo.UserImageData> imageDataList, Retrofit retrofit, final Context context) {
        try {
            if (Utility.isInternetConnection(context)) {
           /*      {
                    "post_id":"1",
                     "reason":"abuse",
                    “comment”:”text”,
                }*/
                ProfileResponsePojo.UserImageData imageData = null;
                if (imageDataList != null && imageDataList.size() >= pos) {
                    imageData = imageDataList.get(pos - 1);
                } else if (imageDataList != null && imageDataList.size() >= pos - 1) {
                    imageData = imageDataList.get(pos - 2);
                } else if (imageDataList != null && imageDataList.size() >= 0) {
                    imageData = imageDataList.get(0);
                }
                CallProgressWheel.showLoadingDialog(context, "Loading...");
                //Create a retrofit call object
                Call<ResponsePojo> posts = retrofit.create(ApiInterface.class).imageDelete(imageData.getId());
                posts.enqueue(new retrofit2.Callback<ResponsePojo>() {
                    @Override
                    public void onResponse(Call<ResponsePojo> call, retrofit2.Response<ResponsePojo> response) {
                        ProfileResponsePojo.UserImageData imageData = null;
                        if (imageDataList != null && imageDataList.size() >= pos) {
                            imageData = imageDataList.get(pos - 1);
                        } else if (imageDataList != null && imageDataList.size() >= pos - 1) {
                            imageData = imageDataList.get(pos - 2);
                        } else if (imageDataList != null && imageDataList.size() >= 0) {
                            imageData = imageDataList.get(0);
                        }
                        if (response.body().getStatus() == 200) {
                            CallProgressWheel.dismissLoadingDialog();
                            ((MyProfileActivity) context).mResponsePojo.getData().getUser_image().remove(imageData);
                            if (((MyProfileActivity) context).coverIMageID.equals(imageData.getId())) {
                                ((MyProfileActivity) context).setCoverImage(-1);
                                ;
                            }
                            ((MyProfileActivity) context).setImageOnPosition(pos, null);
                            ;
                            Utility.showToastMessageLong((Activity) context, response.body().getDescription());

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


    public static void callStateListApi(final MyProfileActivity activity, Retrofit retrofit, final EditText textView, String country_id) {
        try {
            if (Utility.isInternetConnection(activity)) {
                //CallProgressWheel.showLoadingDialog(activity, "Loading...");
                Call<StateResponsePojo> posts = null;
                posts = retrofit.create(ApiInterface.class).getStateList(country_id);
                if (posts != null)
                    posts.enqueue(new retrofit2.Callback<StateResponsePojo>() {
                        @Override
                        public void onResponse(Call<StateResponsePojo> call, retrofit2.Response<StateResponsePojo> response) {
                            if (response.body().getStatus() == 200) {
                                //CallProgressWheel.dismissLoadingDialog();
                                ArrayList<String> tempArr = new ArrayList<>();
                                ((MyProfileActivity) activity).mAllStateList = response.body().getData();
                                textView.performClick();
                            } else {

                                //CallProgressWheel.dismissLoadingDialog();
                                //Utility.showToastMessageShort((Activity) context, response.body().getDescription());

                            }
                        }

                        @Override
                        public void onFailure(Call<StateResponsePojo> call, Throwable t) {
                            //CallProgressWheel.dismissLoadingDialog();

                        }
                    });
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void createChannel(final Retrofit retrofit, final Context mContext, int toUserId, final String fromUserID) {
        try {
            CallProgressWheel.showLoadingDialog(mContext, "Loading...");
            HashMap<String, String> params = new HashMap<>();
            params.put("to", "" + toUserId);
            String channelName = toUserId + "_" + fromUserID;
            if (Integer.valueOf(toUserId) > Integer.valueOf(fromUserID))
                channelName = fromUserID + "_" + toUserId;
            params.put("name", channelName);
            Call<ResponsePojo> posts = null;
//            posts = retrofit.create(ApiInterface.class).createChannelName(params);

            if (posts != null)
                posts.enqueue(new retrofit2.Callback<ResponsePojo>() {
                    @Override
                    public void onResponse(Call<ResponsePojo> call, retrofit2.Response<ResponsePojo> response) {
                        CallProgressWheel.dismissLoadingDialog();
//                        goToChatActivity(dataItem.channel_name, dataItem.gcm_id, holder.mTvUserName.getText().toString(),dataItem.id);

//                        String jsonString = new String(((TypedByteArray) response.body()).getBytes());
//                        android.util.Log.e(TAG, "Success " + jsonString);
//                        if (responseObjectPojo.status == 200) {
//                            callBack.isCreated(true,channelName);
//                        } else if (responseObjectPojo.status == 400
//                                || responseObjectPojo.status == 600) {
//                            Utility.logOutTokenExpire(mContext, responseObjectPojo.message);
//                        } else if (responseObjectPojo.status == 450) {
//                            Utility.logOutTokenExpire(mContext, responseObjectPojo.message);
//                        } else if (responseObjectPojo.status == 1000) {
//                            Utility.logOutTokenExpire(mContext, responseObjectPojo.message);
//                        } else {
//                            Utility.showDialogWithSingleButton(mContext, responseObjectPojo.message, "OK", null);
//                        }
                    }

                    @Override
                    public void onFailure(Call<ResponsePojo> call, Throwable t) {
                        //CallProgressWheel.dismissLoadingDialog();
                        CallProgressWheel.dismissLoadingDialog();
                    }
                });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void sendTwilioChatNotification(Retrofit retrofit, Activity mainActivity, String s) {
//        ApiInterface mWebApi = RetrofitClient.createService(ApiInterface.class, mContext);
//            CallProgressWheel.showLoadingDialog(mContext, "Loading...");
        try {

            RequestBody myreqbody = null;
            try {
                myreqbody = RequestBody.create(okhttp3.MediaType.parse("application/json; charset=utf-8"),
                        (new JSONObject(s)).toString());

            } catch (JSONException e) {
                e.printStackTrace();
            }


            Call<ResponsePojo> posts = null;
            posts = retrofit.create(ApiInterface.class).sendTwilioChatNotification(myreqbody);
            if (posts != null)
                posts.enqueue(new retrofit2.Callback<ResponsePojo>() {
                    @Override
                    public void onResponse(Call<ResponsePojo> call, retrofit2.Response<ResponsePojo> response) {
                        CallProgressWheel.dismissLoadingDialog();
//                    String jsonString = new String(((TypedByteArray) response.getBody()).getBytes());
//                    android.util.Log.e(TAG, "Success " + jsonString);
//                    if (responseObjectPojo.status == 200) {
//
//                    } else if (responseObjectPojo.status == 400
//                            || responseObjectPojo.status == 600) {
//                        Utility.showDialogWithSingleButton(mContext, responseObjectPojo.message, "OK", null);
//                    } else if (responseObjectPojo.status == 1000) {
//                        Utility.logOutTokenExpire(mContext, responseObjectPojo.message);
//                    } else {
//                        Utility.showDialogWithSingleButton(mContext, responseObjectPojo.message, "OK", null);
//                    }
                    }

                    @Override
                    public void onFailure(Call<ResponsePojo> call, Throwable t) {
                        //CallProgressWheel.dismissLoadingDialog();
                        CallProgressWheel.dismissLoadingDialog();
                    }
                });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

