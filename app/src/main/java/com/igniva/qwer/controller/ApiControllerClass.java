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
import com.igniva.qwer.model.PostPojo;
import com.igniva.qwer.model.PrefInputPojo;
import com.igniva.qwer.model.ResponsePojo;
import com.igniva.qwer.ui.activities.ChangePasswordActivity;
import com.igniva.qwer.ui.activities.SetPreferrencesActivity;
import com.igniva.qwer.ui.fragments.NewsFeedFragment;
import com.igniva.qwer.ui.views.CallProgressWheel;
import com.igniva.qwer.utils.Utility;
import com.igniva.qwer.utils.Validation;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

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


                        } else  {
                            CallProgressWheel.dismissLoadingDialog();
                            Utility.showToastMessageShort((Activity) context,response.body().getDescription());
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
            if (Validation.validateChangePassword((Activity) context, mEtCuurentPass, mEtNewPass, mEtConfirmPass )) {
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
                               ((ChangePasswordActivity)context).callSuccessPopUp(context, response.body().getDescription());



                           } else if (response.body().getStatus() == 400) {
                               CallProgressWheel.dismissLoadingDialog();
                               Toast.makeText(context, response.body().getDescription(), Toast.LENGTH_SHORT).show();
                           }
                           else
                           {
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
                Call<ResponsePojo> posts= retrofit.create(ApiInterface.class).contactUs(contact_us);
                posts.enqueue(new retrofit2.Callback<ResponsePojo>() {
                    @Override
                    public void onResponse(Call<ResponsePojo> call, retrofit2.Response<ResponsePojo> response) {
                        CallProgressWheel.dismissLoadingDialog();
                        Utility.showToastMessageShort((Activity) context, response.body().getDescription());
                        ((Activity)context).onBackPressed();
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
            if (Validation.validateCreatePost((Activity) context, mEtTitle, mEtDescription, mEtPrice,metScheduleStartDate,metScheduleEndDate,mEtStartTime,mEtEndTime,typeOfClass)) {
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
                    changePasswordHashMap.put("start_time", "10:00 AM");
                    changePasswordHashMap.put("end_date", "20-11-2017");
                    changePasswordHashMap.put("end_time", "11:00 AM");
                    changePasswordHashMap.put("start_date", "20-11-2017");
                    changePasswordHashMap.put("currency", "usd");
                    changePasswordHashMap.put("price", "20");
                    changePasswordHashMap.put("description", "teaching section");
                     changePasswordHashMap.put("title", "teaching section");
                     changePasswordHashMap.put("post_type", "teaching");


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
                            }
                            else
                            {
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
                            if(context instanceof SetPreferrencesActivity){
                                ArrayList<String> tempArr=new ArrayList<>();
                                ((SetPreferrencesActivity) context).mAlLangList=response.body().getData();
                                for (LanguagesResponsePojo.LanguagesPojo languagesPojo:response.body().getData()
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

    public static void setPrefrences(final Context context,Retrofit retrofit, PrefInputPojo prefInputPojo) {
         try {
            if (Utility.isInternetConnection(context)) {
                CallProgressWheel.showLoadingDialog(context, "Loading...");
                //Create a retrofit call object
                Call<ResponsePojo> posts= retrofit.create(ApiInterface.class).setPrefrences(prefInputPojo);
                posts.enqueue(new retrofit2.Callback<ResponsePojo>() {
                    @Override
                    public void onResponse(Call<ResponsePojo> call, retrofit2.Response<ResponsePojo> response) {
                        CallProgressWheel.dismissLoadingDialog();
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

    public static void getAllFeedsApi(Retrofit retrofit,final Context context,final NewsFeedFragment fragment) {
        try {


            if (Utility.isInternetConnection(context)) {

                CallProgressWheel.showLoadingDialog(context, "Loading...");

                //Create a retrofit call object
                Call<PostPojo> posts = retrofit.create(ApiInterface.class).getPosts();
                posts.enqueue(new retrofit2.Callback<PostPojo>() {
                    @Override
                    public void onResponse(Call<PostPojo> call, retrofit2.Response<PostPojo> response) {
                        if (response.body().getStatus() == 200) {
                            CallProgressWheel.dismissLoadingDialog();
                            fragment.setDataInViewObjects(response.body().getData());
                            //callSuccessPopUp((Activity)context, response.body().getDescription());
                            // Utility.showToastMessageShort(ChangePasswordActivity.this,responsePojo.getDescription());


                        } else  {
                            CallProgressWheel.dismissLoadingDialog();
                            Utility.showToastMessageShort((Activity) context,response.body().getDescription());
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

    public static void markFavoriteUnfavorite(Retrofit retrofit, final Context context, final List<?> post_fav, ImageView mIbfav) {
        try {


            if (Utility.isInternetConnection(context)) {

                CallProgressWheel.showLoadingDialog(context, "Loading...");

                //Create a retrofit call object
                Call<PostPojo> posts = retrofit.create(ApiInterface.class).markFavOrUnfav();
                posts.enqueue(new retrofit2.Callback<PostPojo>() {
                    @Override
                    public void onResponse(Call<PostPojo> call, retrofit2.Response<PostPojo> response) {
                        if (response.body().getStatus() == 200) {
                            CallProgressWheel.dismissLoadingDialog();
                            //fragment.setDataInViewObjects(response.body().getData());
                            //callSuccessPopUp((Activity)context, response.body().getDescription());
                            // Utility.showToastMessageShort(ChangePasswordActivity.this,responsePojo.getDescription());
                            Log.e("success",response.message());

                        } else  {
                            CallProgressWheel.dismissLoadingDialog();
                            Utility.showToastMessageShort((Activity) context,response.body().getDescription());
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


}

