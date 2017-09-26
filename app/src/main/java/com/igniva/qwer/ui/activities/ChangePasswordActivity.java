package com.igniva.qwer.ui.activities;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.view.Window;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.igniva.qwer.R;
import com.igniva.qwer.ui.views.TextViewBold;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

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
    @BindView(R.id.tv_change_pass)
    TextViewBold mTvChangePass;

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
            }
        });
        dialog.setTitle("Custom Dialog");


        dialog.show();


    }

    @OnClick({R.id.iv_back, R.id.tv_change_pass})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.iv_back:


                InputMethodManager inputManager = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                inputManager.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);

                finish();
                break;
            case R.id.tv_change_pass:
                callSuccessPopUp(this, getResources().getString(R.string.pass_update_success));
                break;
        }
    }
}
