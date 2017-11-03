package com.igniva.qwer.controller;

import android.app.Activity;
import android.content.Context;
import android.widget.EditText;
import android.widget.Toast;

import com.igniva.qwer.R;
import com.igniva.qwer.model.ResponsePojo;
import com.igniva.qwer.ui.activities.ChangePasswordActivity;
import com.igniva.qwer.ui.views.CallProgressWheel;
import com.igniva.qwer.utils.Utility;
import com.igniva.qwer.utils.Validation;

import java.util.HashMap;

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


}

