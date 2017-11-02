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

import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

import static com.igniva.qwer.utils.Utility.callSuccessPopUp;

public class ApiControllerClass {
    public static String TAG = "ApiControllerClass";


    /**
     * Call api to change the current email
     */

    public static void callChangeEmailApi(final Context context,EditText etCurrentEmail, EditText etNewEmail, EditText verifyPassword) {
        try {


            if (Utility.isInternetConnection(context)) {

                HashMap<String, String> changeEmailpayload = new HashMap<>();
                changeEmailpayload.put("email", etCurrentEmail.getText().toString().trim());
                changeEmailpayload.put("new_email", etNewEmail.getText().toString().trim());
                changeEmailpayload.put("password", verifyPassword.getText().toString().trim());


                ApiInterface mWebApi = RetrofitClient.createService(ApiInterface.class, context);
                CallProgressWheel.showLoadingDialog(context, "Loading...");
                mWebApi.changeEmail(changeEmailpayload, new Callback<ResponsePojo>() {
                    @Override
                    public void success(ResponsePojo responsePojo, Response response) {
                        if (responsePojo.getStatus() == 200) {
                            CallProgressWheel.dismissLoadingDialog();
                            callSuccessPopUp(context, responsePojo.getDescription());
                            // Utility.showToastMessageShort(ChangePasswordActivity.this,responsePojo.getDescription());


                        } else  {
                            CallProgressWheel.dismissLoadingDialog();
                            Utility.showToastMessageShort((Activity) context,responsePojo.getDescription());
                        }
                    }

                    @Override
                    public void failure(RetrofitError error) {
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

    public static void callChangePasswordApi(final Context context,EditText mEtCuurentPass, EditText mEtNewPass, EditText mEtConfirmPass) {
        try {
            Utility.hideSoftKeyboard((Activity) context);
            // check validations for current password,new password and confirm password
            if (Validation.validateChangePassword((Activity) context, mEtCuurentPass, mEtNewPass, mEtConfirmPass )) {
                if (Utility.isInternetConnection(context)) {
                    ApiInterface mWebApi = RetrofitClient.createService(ApiInterface.class, context);
                    CallProgressWheel.showLoadingDialog(context, "Loading...");
                    HashMap<String, String> changePasswordHashMap = new HashMap<>();
                    changePasswordHashMap.put("old_password", mEtCuurentPass.getText().toString().trim());
                    changePasswordHashMap.put("new_password", mEtNewPass.getText().toString());
                    changePasswordHashMap.put("confirm_new_password", mEtConfirmPass.getText().toString());



                    mWebApi.changePassword(changePasswordHashMap, new Callback<ResponsePojo>() {
                        @Override
                        public void success(ResponsePojo responsePojo, Response response) {

                            if (responsePojo.getStatus() == 200) {
                                CallProgressWheel.dismissLoadingDialog();
                                ((ChangePasswordActivity)context).callSuccessPopUp(context, responsePojo.getDescription());



                            } else if (responsePojo.getStatus() == 400) {
                                CallProgressWheel.dismissLoadingDialog();
                                Toast.makeText(context, responsePojo.getDescription(), Toast.LENGTH_SHORT).show();
                            }
                            else
                            {
                                CallProgressWheel.dismissLoadingDialog();
                                Toast.makeText(context, responsePojo.getDescription(), Toast.LENGTH_SHORT).show();
                            }

                        }

                        @Override
                        public void failure(RetrofitError error) {
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





}

