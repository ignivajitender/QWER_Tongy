package com.igniva.qwer.ui.activities;

import android.app.Dialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.igniva.qwer.R;
import com.igniva.qwer.controller.ApiInterface;
import com.igniva.qwer.controller.RetrofitClient;
import com.igniva.qwer.model.ResponsePojo;
import com.igniva.qwer.ui.views.CallProgressWheel;
import com.igniva.qwer.utils.Utility;
import com.igniva.qwer.utils.Validation;

import java.util.HashMap;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

import static com.igniva.qwer.utils.Utility.callSuccessPopUp;

/**
 * Created by karanveer on 22/9/17.
 */

public class ChangeEmailActivity extends BaseActivity {


    @BindView(R.id.iv_back)
    ImageView mIvBack;
    @BindView(R.id.ll_change_email)
    LinearLayout ll_change_email;
    @BindView(R.id.et_current_email)
    EditText etCurrentEmail;
    @BindView(R.id.et_new_email)
    EditText etNewEmail;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.update_email_activity);
        ButterKnife.bind(this);
        Utility.hideSoftKeyboard(ChangeEmailActivity.this);

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

    public void callVerifyPassPopUp(final EditText etCurrentEmail, final EditText etNewEmail) {

        // Create custom dialog object
        final Dialog dialog = new Dialog(this,
                R.style.Theme_Dialog);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.setCancelable(true);
        dialog.setCanceledOnTouchOutside(true);

        dialog.setContentView(R.layout.update_email_verify_pass_pop_up);
//        dialog.getWindow().getAttributes().windowAnimations = R.style.DialogAnimation;

        Button btn_confirm = (Button) dialog.findViewById(R.id.btn_confirm);
        Button btn_cancel = (Button) dialog.findViewById(R.id.btn_cancel);
        final EditText et_verify_password = (EditText) dialog.findViewById(R.id.et_verify_password);
        btn_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Utility.hideSoftKeyboard(ChangeEmailActivity.this);
                dialog.dismiss();
            }
        });

        btn_confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Utility.hideSoftKeyboard(ChangeEmailActivity.this);
                if(et_verify_password.getText().toString().trim().length()==0)
                {
                    et_verify_password.setFocusable(true);
                    et_verify_password.requestFocus();
                    et_verify_password.setError(getString(R.string.please_enter_pass));

                }
                else if(et_verify_password.getText().length()<6)
                {
                    et_verify_password.setFocusable(true);
                    et_verify_password.requestFocus();
                    et_verify_password.setError(getString(R.string.please_enter_pass_minimum));

                }
                else {

                    dialog.dismiss();
                    callChangeEmailApi(etCurrentEmail, etNewEmail, et_verify_password);
                }

            }
        });

        dialog.setTitle("Custom Dialog");


        dialog.show();


    }

    /**
     * Call api to change the current email
     */

    private void callChangeEmailApi(EditText etCurrentEmail, EditText etNewEmail,EditText verifyPassword) {
        try {


                if (Utility.isInternetConnection(this)) {

                        HashMap<String, String> changeEmailpayload = new HashMap<>();
                        changeEmailpayload.put("email", etCurrentEmail.getText().toString().trim());
                        changeEmailpayload.put("new_email", etNewEmail.getText().toString().trim());
                        changeEmailpayload.put("password", verifyPassword.getText().toString().trim());


                        ApiInterface mWebApi = RetrofitClient.createService(ApiInterface.class, this);
                        CallProgressWheel.showLoadingDialog(this, "Loading...");
                        mWebApi.changeEmail(changeEmailpayload, new Callback<ResponsePojo>() {
                            @Override
                            public void success(ResponsePojo responsePojo, Response response) {
                                if (responsePojo.getStatus() == 200) {
                                    CallProgressWheel.dismissLoadingDialog();
                                    callSuccessPopUp(ChangeEmailActivity.this, responsePojo.getDescription());
                                    // Utility.showToastMessageShort(ChangePasswordActivity.this,responsePojo.getDescription());


                                } else  {
                                    CallProgressWheel.dismissLoadingDialog();
                                    Utility.showToastMessageShort(ChangeEmailActivity.this,responsePojo.getDescription());
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


    @OnClick({R.id.iv_back, R.id.ll_change_email})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.iv_back:
                finish();
                break;
            case R.id.ll_change_email:
                if (Validation.validateNewEmail(ChangeEmailActivity.this, etCurrentEmail, etNewEmail)) {
                    callVerifyPassPopUp(etCurrentEmail, etNewEmail);
                }

                break;
        }
    }
}
