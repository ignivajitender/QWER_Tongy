package com.igniva.qwer.ui.activities;


import android.content.Intent;
import android.graphics.Paint;
import android.os.Bundle;
import android.support.design.widget.TextInputEditText;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.igniva.qwer.R;
import com.igniva.qwer.ui.views.TextViewRegular;
import com.igniva.qwer.utils.Constants;
import com.igniva.qwer.utils.PreferenceHandler;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by igniva-andriod-11 on 7/11/16.
 */

public class LoginActivity extends BaseActivity {


    @BindView(R.id.tv_ForgotPassword)
    TextView mTvForgotPassword;
    @BindView(R.id.cross_icon)
    ImageView mCrossIcon;
    @BindView(R.id.tv_toolbar)
    TextView mTvToolbar;
    @BindView(R.id.tv_tap_to_rename)
    TextView mTvTapToRename;
    @BindView(R.id.ll_change_title)
    LinearLayout mLlChangeTitle;
    @BindView(R.id.notification_icon)
    ImageView mNotificationIcon;
    @BindView(R.id.edit_pref_icon)
    ImageView mEditPrefIcon;
    @BindView(R.id.toolbar_top)
    Toolbar mToolbarTop;
    @BindView(R.id.iv_logo)
    ImageView mIvLogo;
    @BindView(R.id.et_email)
    EditText mEtEmail;
    @BindView(R.id.et_password)
    TextInputEditText mEtPassword;
    @BindView(R.id.email_login_form)
    LinearLayout mEmailLoginForm;
    @BindView(R.id.btn_login)
    TextViewRegular mBtnLogin;
    @BindView(R.id.iv_fb)
    ImageView mIvFb;
    @BindView(R.id.tv_fb)
    TextView mTvFb;
    @BindView(R.id.ll_fbSignUp)
    LinearLayout mLlFbSignUp;
    @BindView(R.id.tv_account)
    TextView mTvAccount;
    @BindView(R.id.tv_SignUp)
    TextView mTvSignUp;
    @BindView(R.id.ll_NewSignUp)
    LinearLayout mLlNewSignUp;
    @BindView(R.id.login_form)
    ScrollView mLoginForm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        /*requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);*/
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);
        setUpLayout();
        setUpToolbar();
        setDataInViewObjects();
    }

    @Override
    protected void setUpLayout() {
        mTvForgotPassword.setPaintFlags(Paint.UNDERLINE_TEXT_FLAG);

    }

    @Override
    protected void setDataInViewObjects() {
    }

    @Override
    protected void setUpToolbar() {
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(R.anim.left_in, R.anim.left_out);
    }


    @OnClick({R.id.btn_login, R.id.ll_fbSignUp, R.id.tv_SignUp, R.id.ll_NewSignUp, R.id.tv_ForgotPassword})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.btn_login:
                PreferenceHandler.writeBoolean(LoginActivity.this, Constants.IS_ALREADY_LOGIN, true);
                Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
                break;
            case R.id.ll_fbSignUp:
                break;
            case R.id.tv_SignUp:
                Intent signUpIntent = new Intent(LoginActivity.this, SignUpActivity.class);
                startActivity(signUpIntent);
                break;
            case R.id.tv_ForgotPassword:
                Intent forgot_intent = new Intent(LoginActivity.this, ForgotPasswordActivity.class);
                startActivity(forgot_intent);
                break;
            case R.id.ll_NewSignUp:
                break;
            default:
                break;
        }
    }
}
