package com.igniva.qwer.ui.activities;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.view.Window;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.igniva.qwer.R;
import com.igniva.qwer.controller.ApiInterface;
import com.igniva.qwer.controller.RetrofitClient;
import com.igniva.qwer.model.ResponsePojo;
import com.igniva.qwer.ui.views.CallProgressWheel;
import com.igniva.qwer.utils.Constants;
import com.igniva.qwer.utils.PreferenceHandler;
import com.igniva.qwer.utils.Utility;
import com.igniva.qwer.utils.Validation;

import java.util.HashMap;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

/**
 * Created by karanveer on 22/9/17.
 */

public class ChangePasswordActivity extends BaseActivity {

    @BindView(R.id.iv_back)
    ImageView mIvBack;
    @BindView(R.id.et_cuurent_pass)
    EditText mEtCuurentPass;
    @BindView(R.id.et_new_pass)
    EditText mEtNewPass;
    @BindView(R.id.et_confirm_pass)
    EditText mEtConfirmPass;
    @BindView(R.id.ll_change_pass)
    LinearLayout ll_change_pass;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.change_password_activity);
        ButterKnife.bind(this);
    }

    @Override
    protected void setUpLayout() {

    }

    @Override
    protected void setDataInViewObjects() {

    }

    @Override
    protected void setUpToolbar() {

    }

    public void callSuccessPopUp(Context context, String message) {

        // Create custom dialog object
        final Dialog dialog = new Dialog(context);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.setCancelable(true);
        dialog.setCanceledOnTouchOutside(true);

        dialog.setContentView(R.layout.succuess_pop_up);
        TextView text_message = (TextView) dialog.findViewById(R.id.tv_success_message);
        TextView mBtnOk = (TextView) dialog.findViewById(R.id.btn_ok);
        text_message.setText(message);
//        dialog.getWindow().getAttributes().windowAnimations = R.style.DialogAnimation;

        mBtnOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
                finish();
                PreferenceHandler.writeBoolean(ChangePasswordActivity.this, Constants.IS_ALREADY_LOGIN, true);
                Intent intent = new Intent(ChangePasswordActivity.this, MainActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
            }
        });
        dialog.setTitle("Custom Dialog");


        dialog.show();


    }

    @OnClick({R.id.iv_back, R.id.ll_change_pass})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.iv_back:


                InputMethodManager inputManager = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                inputManager.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);

                finish();
                break;
            case R.id.ll_change_pass:
                try {
                    // check validations for current password,new password and confirm password
                    if (Validation.validateChangePassword(this, mEtCuurentPass, mEtNewPass, mEtConfirmPass )) {
                        if (Utility.isInternetConnection(this)) {
                            ApiInterface mWebApi = RetrofitClient.createService(ApiInterface.class, this);
                            CallProgressWheel.showLoadingDialog(this, "Loading...");
                            HashMap<String, String> changePasswordHashMap = new HashMap<>();
                            changePasswordHashMap.put("old_password", mEtCuurentPass.getText().toString().trim());
                            changePasswordHashMap.put("new_password", mEtNewPass.getText().toString());
                            changePasswordHashMap.put("confirm_new_password", mEtConfirmPass.getText().toString());



                            mWebApi.changePassword(changePasswordHashMap, new Callback<ResponsePojo>() {
                                @Override
                                public void success(ResponsePojo responsePojo, Response response) {

                                    if (responsePojo.getStatus() == 200) {
                                        CallProgressWheel.dismissLoadingDialog();
                                        callSuccessPopUp(ChangePasswordActivity.this, responsePojo.getDescription());
                                       // Utility.showToastMessageShort(ChangePasswordActivity.this,responsePojo.getDescription());


                                    } else if (responsePojo.getStatus() == 400) {
                                        CallProgressWheel.dismissLoadingDialog();
                                        Toast.makeText(ChangePasswordActivity.this, responsePojo.getDescription(), Toast.LENGTH_SHORT).show();
                                    }
                                    else
                                    {
                                        CallProgressWheel.dismissLoadingDialog();
                                        Toast.makeText(ChangePasswordActivity.this, responsePojo.getDescription(), Toast.LENGTH_SHORT).show();
                                    }

                                }

                                @Override
                                public void failure(RetrofitError error) {
                                    CallProgressWheel.dismissLoadingDialog();
                                    Toast.makeText(ChangePasswordActivity.this, getString(R.string.something_went_wrong), Toast.LENGTH_SHORT).show();
                                }
                            });
                        }
                    }
                } catch (Exception e) {
                    CallProgressWheel.dismissLoadingDialog();
                    Toast.makeText(ChangePasswordActivity.this, getString(R.string.something_went_wrong), Toast.LENGTH_SHORT).show();
                    e.printStackTrace();
                }


                //callSuccessPopUp(this, getResources().getString(R.string.pass_update_success));
                break;
        }
    }
}
