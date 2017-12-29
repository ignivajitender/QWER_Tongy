package com.igniva.qwer.ui.activities;

import android.app.Dialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TextInputLayout;
import android.view.View;
import android.view.Window;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.igniva.qwer.R;
import com.igniva.qwer.controller.ApiInterface;
import com.igniva.qwer.model.ResponsePojo;
import com.igniva.qwer.ui.views.CallProgressWheel;
import com.igniva.qwer.utils.Global;
import com.igniva.qwer.utils.Utility;
import com.igniva.qwer.utils.Validation;

import java.util.HashMap;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit2.Call;
import retrofit2.Retrofit;

/**
 * Created by karanveer on 26/9/17.
 */

public class ForgotPasswordActivity extends BaseActivity {
    @BindView(R.id.iv_back)
    ImageView mIvBack;
    @BindView(R.id.ll_back)
    FrameLayout mLlBack;
    @BindView(R.id.tv_submit)
    TextView mTvSubmit;
    @BindView(R.id.ll_lets_start)
    LinearLayout mLlLetsStart;

    @BindView(R.id.etEmail)
    EditText metEmail;

    @OnClick(R.id.ll_back)
    public void back(){
        onBackPressed();
    }
    @BindView(R.id.til_email)
    TextInputLayout mtilEmail;
    @Inject
    Retrofit retrofit;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        ((Global) getApplication()).getNetComponent().inject(this);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.forgot_password);
        ButterKnife.bind(this);
        Utility.hideSoftKeyboard(ForgotPasswordActivity.this);
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

    public void callSuccessPopUp(ForgotPasswordActivity forgotPasswordActivity, String description) {

        // Create custom dialog object
        final Dialog dialog = new Dialog(this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.setCancelable(false);
        dialog.setCanceledOnTouchOutside(false);

        dialog.setContentView(R.layout.succuess_pop_up);

        TextView mBtnOk = (TextView) dialog.findViewById(R.id.btn_ok);
        TextView text_message = (TextView) dialog.findViewById(R.id.tv_success_message);
        text_message.setText(description);
//        dialog.getWindow().getAttributes().windowAnimations = R.style.DialogAnimation;

        mBtnOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
                finish();

            }
        });
        dialog.setTitle("Custom Dialog");


        dialog.show();


    }

    @OnClick(R.id.tv_submit)
    public void onViewClicked() {
        //callSuccessPopUp();

        try {
            // check validations for current password,new password and confirm password
            if (Validation.validateForgotPassword(this, metEmail,mtilEmail )) {
                if (Utility.isInternetConnection(this)) {

                    CallProgressWheel.showLoadingDialog(this, "Loading...");
                           /*
                            payload
                            {
                                email:- mohit@yopmail.com
                            }*/



                    HashMap<String, String> forgotPasswordHashMap = new HashMap<>();
                    forgotPasswordHashMap.put("email", metEmail.getText().toString().trim());


                    Call<ResponsePojo> posts = retrofit.create(ApiInterface.class).forgotPassword(forgotPasswordHashMap);
                    posts.enqueue(new retrofit2.Callback<ResponsePojo>() {
                        @Override
                        public void onResponse(Call<ResponsePojo> call, retrofit2.Response<ResponsePojo> response) {
                            if (response.body().getStatus() == 200) {
                                CallProgressWheel.dismissLoadingDialog();
                                callSuccessPopUp(ForgotPasswordActivity.this, response.body().getDescription());
                                // Utility.showToastMessageShort(ChangePasswordActivity.this,responsePojo.getDescription());


                            } else if (response.body().getStatus() == 400) {
                                CallProgressWheel.dismissLoadingDialog();
                                Toast.makeText(ForgotPasswordActivity.this, response.body().getDescription(), Toast.LENGTH_SHORT).show();
                            }
                            else
                            {
                                CallProgressWheel.dismissLoadingDialog();
                                Toast.makeText(ForgotPasswordActivity.this, response.body().getDescription(), Toast.LENGTH_SHORT).show();
                            }
                        }

                        @Override
                        public void onFailure(Call<ResponsePojo> call, Throwable t) {
                            CallProgressWheel.dismissLoadingDialog();
                            Toast.makeText(ForgotPasswordActivity.this, getString(R.string.something_went_wrong), Toast.LENGTH_SHORT).show();

                        }
                    });

                }
            }
        } catch (Exception e) {
            CallProgressWheel.dismissLoadingDialog();
            Toast.makeText(ForgotPasswordActivity.this, getString(R.string.something_went_wrong), Toast.LENGTH_SHORT).show();
            e.printStackTrace();
        }

    }
}
