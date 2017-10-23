package com.igniva.qwer.ui.activities;


import android.content.Intent;
import android.graphics.Paint;
import android.os.Bundle;
import android.support.design.widget.TextInputEditText;
import android.support.design.widget.TextInputLayout;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.igniva.qwer.R;
import com.igniva.qwer.controller.ApiInterface;
import com.igniva.qwer.controller.RetrofitClient;
import com.igniva.qwer.model.ResponsePojo;
import com.igniva.qwer.ui.views.CallProgressWheel;
import com.igniva.qwer.ui.views.TextViewRegular;
import com.igniva.qwer.utils.Constants;
import com.igniva.qwer.utils.Log;
import com.igniva.qwer.utils.PreferenceHandler;
import com.igniva.qwer.utils.Utility;
import com.igniva.qwer.utils.Validation;
import com.igniva.qwer.utils.facebookSignIn.FacebookHelper;
import com.igniva.qwer.utils.facebookSignIn.FacebookResponse;
import com.igniva.qwer.utils.facebookSignIn.FacebookUser;

import java.util.HashMap;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

/**
 * Created by igniva-andriod-11 on 7/11/16.
 */

public class LoginActivity extends BaseActivity implements FacebookResponse, Utility.OnAlertOkClickListener {


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
    TextInputEditText mEtEmail;
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
    @BindView(R.id.til_email)
    TextInputLayout mTilEmail;
    @BindView(R.id.til_pass)
    TextInputLayout mTilPass;

    // facebook helper init
    FacebookHelper mFbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        /*requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);*/
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_login);

        /**
         * Init all the methods and butter knife injection
         * */
        ButterKnife.bind(this);
        setUpLayout();
        setUpToolbar();
        setDataInViewObjects();
    }

    @Override
    protected void setUpLayout() {
        mTvForgotPassword.setPaintFlags(Paint.UNDERLINE_TEXT_FLAG);
        mFbHelper = new FacebookHelper(LoginActivity.this, "id,name,email,gender,birthday,picture,cover", LoginActivity.this);

    }

    @Override
    protected void setDataInViewObjects() {
    }

    @Override
    protected void setUpToolbar() {
    }


    /**
     * On activity back press
     */
    @Override
    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(R.anim.left_in, R.anim.left_out);
    }


    /**
     * View click event
     */
    @OnClick({R.id.btn_login, R.id.ll_fbSignUp, R.id.tv_SignUp, R.id.ll_NewSignUp, R.id.tv_ForgotPassword})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.btn_login:

                loginWithoutFacebook();


                break;
            case R.id.ll_fbSignUp:

                loginWithFacebook();

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

    /**
     * Login with facebook
     */
    private void loginWithFacebook() {
        if (Utility.isInternetConnection(LoginActivity.this)) {
            mFbHelper.performSignIn(this);
        } else {
            Toast.makeText(this, Constants.NOINTERNET, Toast.LENGTH_SHORT).show();
        }

    }


    /**
     * Login with-out facebook
     */
    private void loginWithoutFacebook() {
        try {
            if (Validation.isValidatedLogin(this, mEtEmail, mTilEmail, mEtPassword, mTilPass)) {
                if (Utility.isInternetConnection(this)) {
                    ApiInterface mWebApi = RetrofitClient.createService(ApiInterface.class, this);
                    CallProgressWheel.showLoadingDialog(this, "Loading...");
                    HashMap<String, String> signupHash = new HashMap<>();
                    signupHash.put(Constants.EMAIL, mEtEmail.getText().toString());
                    signupHash.put(Constants.PASSWORD, mEtPassword.getText().toString());
                    mWebApi.login(signupHash, new Callback<ResponsePojo>() {
                        @Override
                        public void success(ResponsePojo responsePojo, Response response) {

                            if (responsePojo.getStatus() == 200) {
                                CallProgressWheel.dismissLoadingDialog();
                                PreferenceHandler.writeBoolean(LoginActivity.this, Constants.IS_ALREADY_LOGIN, true);
                                Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                                startActivity(intent);

                            } else if (responsePojo.getStatus() == 400) {
                                CallProgressWheel.dismissLoadingDialog();
                                Toast.makeText(LoginActivity.this, responsePojo.getDescription(), Toast.LENGTH_SHORT).show();
                            }

                        }

                        @Override
                        public void failure(RetrofitError error) {
                            CallProgressWheel.dismissLoadingDialog();
                            Toast.makeText(LoginActivity.this, getString(R.string.something_went_wrong), Toast.LENGTH_SHORT).show();
                        }
                    });
                }
            }
        } catch (Exception e) {
            CallProgressWheel.dismissLoadingDialog();
            Toast.makeText(LoginActivity.this, getString(R.string.something_went_wrong), Toast.LENGTH_SHORT).show();
            e.printStackTrace();
        }

    }

    @Override
    public void onFbSignInFail() {
        Toast.makeText(this, "Facebook sign in fail", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onFbSignInSuccess() {
        Toast.makeText(this, "Facebook sign in success", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onFbProfileReceived(FacebookUser facebookUser) {
        Toast.makeText(this, "Facebook user data: name= " + facebookUser.name + " email= " + facebookUser.email, Toast.LENGTH_SHORT).show();

        Log.e("Person name: ", facebookUser.name + "");
        Log.e("Person gender: ", facebookUser.gender + "");
        Log.e("Person email: ", facebookUser.email + "");
        Log.e("Person image: ", facebookUser.profilePic + "");
        Log.e("Person dob: ", facebookUser.facebookID + "");

        HashMap<String, String> signInFacebook = new HashMap<>();
        signInFacebook.put(Constants.SOCIAL_ID, facebookUser.facebookID);
        signInFacebook.put(Constants.EMAIL, facebookUser.email);
        signInFacebook.put(Constants.GENDER, facebookUser.gender);
        signInFacebook.put(Constants.NAME, facebookUser.name);
        signInFacebook.put(Constants.PROFILE_PIC, facebookUser.profilePic);


            callSinInFacebookApi(signInFacebook);



    }

    private void callSinInFacebookApi(HashMap<String, String> signInFacebook) {
        try {

                if (Utility.isInternetConnection(this)) {

                ApiInterface mWebApi = RetrofitClient.createService(ApiInterface.class, this);
                CallProgressWheel.showLoadingDialog(this, "Loading...");
                mWebApi.loginFaceBook(signInFacebook, new Callback<ResponsePojo>() {
                    @Override
                    public void success(ResponsePojo responsePojo, Response response) {
                        if (responsePojo.getStatus() == 200) {
                            CallProgressWheel.dismissLoadingDialog();
                            PreferenceHandler.writeBoolean(LoginActivity.this, Constants.IS_ALREADY_LOGIN, true);
                            Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                            startActivity(intent);
                        }
                    }

                    @Override
                    public void failure(RetrofitError error) {
                        CallProgressWheel.dismissLoadingDialog();
                        PreferenceHandler.writeBoolean(LoginActivity.this, Constants.IS_ALREADY_LOGIN, false);
                        Toast.makeText(LoginActivity.this, getString(R.string.something_went_wrong), Toast.LENGTH_SHORT).show();
                    }
                });
            }

        } catch (Exception e) {
            CallProgressWheel.dismissLoadingDialog();
            Toast.makeText(LoginActivity.this, getString(R.string.something_went_wrong), Toast.LENGTH_SHORT).show();
            e.printStackTrace();
        }


    }

    @Override
    public void onFBSignOut() {
        Toast.makeText(this, "Facebook sign out success", Toast.LENGTH_SHORT).show();
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        mFbHelper.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    public void onOkButtonClicked() {
        startActivity(new Intent(this, SignUpActivity.class));
    }
}
