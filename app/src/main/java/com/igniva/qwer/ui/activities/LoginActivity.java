package com.igniva.qwer.ui.activities;


import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.design.widget.TextInputEditText;
import android.support.design.widget.TextInputLayout;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Window;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.igniva.qwer.R;
import com.igniva.qwer.controller.ApiInterface;
import com.igniva.qwer.model.ResponsePojo;
import com.igniva.qwer.ui.views.CallProgressWheel;
import com.igniva.qwer.ui.views.TextViewRegular;
import com.igniva.qwer.utils.Constants;
import com.igniva.qwer.utils.Global;
import com.igniva.qwer.utils.Log;
import com.igniva.qwer.utils.PreferenceHandler;
import com.igniva.qwer.utils.Utility;
import com.igniva.qwer.utils.Validation;
import com.igniva.qwer.utils.facebookSignIn.FacebookHelper;
import com.igniva.qwer.utils.facebookSignIn.FacebookResponse;
import com.igniva.qwer.utils.facebookSignIn.FacebookUser;

import java.util.HashMap;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit2.Call;
import retrofit2.Retrofit;
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
    private String TAG1="LoginActivity";
    @Inject
    Retrofit retrofit;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        /*requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);*/
        ((Global) getApplication()).getNetComponent().inject(this);
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_login);

        /**
         * Init all the methods and butter knife injection
         * */
        ButterKnife.bind(this);
        Utility.printKeyHash(getApplicationContext());
        setUpLayout();
        setUpToolbar();
        setDataInViewObjects();
    }

    @Override
    protected void setUpLayout() {
        mTvForgotPassword.setPaintFlags(Paint.UNDERLINE_TEXT_FLAG);
        mFbHelper = new FacebookHelper(LoginActivity.this, "id,name,email,gender,birthday,picture,cover", LoginActivity.this);
        Utility.hideSoftKeyboard(LoginActivity.this);
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
        Utility.hideSoftKeyboard(LoginActivity.this);
    }


    /**
     * View click event
     */
    @OnClick({R.id.btn_login, R.id.ll_fbSignUp, R.id.tv_SignUp, R.id.ll_NewSignUp, R.id.tv_ForgotPassword})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.btn_login:
                Utility.hideSoftKeyboard(LoginActivity.this);
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

                    CallProgressWheel.showLoadingDialog(this, "Loading...");
                    HashMap<String, String> signupHash = new HashMap<>();
                    signupHash.put(Constants.EMAIL, mEtEmail.getText().toString());
                    signupHash.put(Constants.PASSWORD, mEtPassword.getText().toString());
                    Log.e("token value",PreferenceHandler.readFCM_KEY(LoginActivity.this, Constants.FCM_TOKEN, "")+ "bgjkhbkjh");
                    if (PreferenceHandler.readFCM_KEY(LoginActivity.this, Constants.FCM_TOKEN, "").length()>0) {
                        signupHash.put(Constants.DEVICE_ID,PreferenceHandler.readFCM_KEY(LoginActivity.this, Constants.FCM_TOKEN, ""));

                    } else {
                        signupHash.put(Constants.DEVICE_ID,SplashActivity.token);
                    }
                    signupHash.put(Constants.DEVICE_TYPE,"android");
                    //Create a retrofit call object
                    retrofit2.Call<ResponsePojo> posts= retrofit.create(ApiInterface.class).login(signupHash);

                    posts.enqueue(new retrofit2.Callback<ResponsePojo>() {
                        @Override
                        public void onResponse(retrofit2.Call<ResponsePojo> call, retrofit2.Response<ResponsePojo> response) {
                            if (response.body().getStatus() == 200) {
                                CallProgressWheel.dismissLoadingDialog();
                                PreferenceHandler.writeBoolean(LoginActivity.this, PreferenceHandler.IS_ALREADY_LOGIN, true);
                                PreferenceHandler.writeBoolean(LoginActivity.this, PreferenceHandler.IS_PROFILE_SET, true);
                                PreferenceHandler.writeBoolean(LoginActivity.this, PreferenceHandler.IS_PREF_SET, true);
                                PreferenceHandler.writeString(LoginActivity.this,PreferenceHandler.PREF_KEY_USER_ID,response.body().getData().id);
                                for (int i = 0; i < response.headers().size(); i++) {

                                        String loginToken = response.headers().get("x-logintoken");
                                        Log.e(TAG1, loginToken);
                                        PreferenceHandler.writeString(LoginActivity.this, PreferenceHandler.PREF_KEY_LOGIN_USER_TOKEN, loginToken);
                                        PreferenceHandler.writeString(LoginActivity.this,PreferenceHandler.PREF_KEY_IS_SOCIAL_LOGIN,"false");


                                }
                                Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                                startActivity(intent);

                            }else if (response.body().getStatus() == 800) {
                                CallProgressWheel.dismissLoadingDialog();
                                PreferenceHandler.writeString(LoginActivity.this,PreferenceHandler.PREF_KEY_USER_ID,response.body().getData().id);
                                 PreferenceHandler.writeBoolean(LoginActivity.this, PreferenceHandler.IS_ALREADY_LOGIN, true);
                                PreferenceHandler.writeBoolean(LoginActivity.this, PreferenceHandler.IS_PROFILE_SET, false);
                                PreferenceHandler.writeBoolean(LoginActivity.this, PreferenceHandler.IS_PREF_SET, false);
                                for (int i = 0; i < response.headers().size(); i++) {

                                    String loginToken = response.headers().get("x-logintoken");
                                    Log.e(TAG1, loginToken);
                                    PreferenceHandler.writeString(LoginActivity.this, PreferenceHandler.PREF_KEY_LOGIN_USER_TOKEN, loginToken);
                                    PreferenceHandler.writeString(LoginActivity.this,PreferenceHandler.PREF_KEY_IS_SOCIAL_LOGIN,"false");

                                }
                                Intent intent = new Intent(LoginActivity.this, MyProfileActivity.class);
//                                intent.putExtra(com.igniva.qwer.fcm.Constants.MYPROFILEEDITABLE, com.igniva.qwer.fcm.Constants.INNER_PROFILE);
                                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                                startActivity(intent);

                            }else if (response.body().getStatus() == 900) {
                                CallProgressWheel.dismissLoadingDialog();
                                PreferenceHandler.writeString(LoginActivity.this,PreferenceHandler.PREF_KEY_USER_ID,response.body().getData().id);

                                PreferenceHandler.writeBoolean(LoginActivity.this, PreferenceHandler.IS_ALREADY_LOGIN, true);
                                PreferenceHandler.writeBoolean(LoginActivity.this, PreferenceHandler.IS_PROFILE_SET, true);
                                PreferenceHandler.writeBoolean(LoginActivity.this, PreferenceHandler.IS_PREF_SET, false);
                                for (int i = 0; i < response.headers().size(); i++) {

                                    String loginToken = response.headers().get("x-logintoken");
                                    Log.e(TAG1, loginToken);
                                    PreferenceHandler.writeString(LoginActivity.this, PreferenceHandler.PREF_KEY_LOGIN_USER_TOKEN, loginToken);
                                    PreferenceHandler.writeString(LoginActivity.this,PreferenceHandler.PREF_KEY_IS_SOCIAL_LOGIN,"false");

                                }
                                Intent intent = new Intent(LoginActivity.this, SetPreferrencesActivity.class);
                                intent.putExtra(Constants.TO_EDIT_PREFERENCES, "Yes");
                                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                                startActivity(intent);

                            } else if (response.body().getStatus() == 600) {
                                CallProgressWheel.dismissLoadingDialog();
                                callResendVerificationPopUp(LoginActivity.this,response.body().getDescription());
                                // Toast.makeText(LoginActivity.this, responsePojo.getDescription(), Toast.LENGTH_SHORT).show();
                            }
                         /*   else if (response.body().getStatus()==900){
                                CallProgressWheel.dismissLoadingDialog();
                                Intent intent = new Intent(LoginActivity.this, NoResultsActivity.class);
                                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                                startActivity(intent);
                            }*/

                            else{
                                CallProgressWheel.dismissLoadingDialog();
                                Utility.showToastMessageShort(LoginActivity.this,response.body().getDescription());
                            }
                        }

                        @Override
                        public void onFailure(retrofit2.Call<ResponsePojo> call, Throwable t) {
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
       // Toast.makeText(this, "Facebook user data: name= " + facebookUser.name + " email= " + facebookUser.email, Toast.LENGTH_SHORT).show();

        Log.e("Person name: ", facebookUser.name + "");
        Log.e("Person gender: ", facebookUser.gender + "");
        Log.e("Person email: ", facebookUser.email + "");
        Log.e("Person image: ", facebookUser.profilePic + "");
        Log.e("Person dob: ", facebookUser.facebookID + "");

        if(facebookUser.name==null || facebookUser.email==null){
            //  startActivity(new Intent(this,SignUpActivity.this).putExtra("comingFrom","facebook").p);
           startActivity(new Intent(LoginActivity.this,SignUpActivity.class).putExtra("name",facebookUser.name).putExtra("email",facebookUser.email).putExtra("comingFrom","login"));
        }else {
            HashMap<String, String> signInFacebook = new HashMap<>();
            signInFacebook.put(Constants.SOCIAL_ID, facebookUser.facebookID);
            signInFacebook.put(Constants.EMAIL, facebookUser.email);
            signInFacebook.put(Constants.GENDER, facebookUser.gender);
            signInFacebook.put(Constants.NAME, facebookUser.name);
            signInFacebook.put(Constants.PROFILE_PIC, facebookUser.profilePic);
            if (!PreferenceHandler.readFCM_KEY(LoginActivity.this, Constants.FCM_TOKEN, "").equalsIgnoreCase("")) {
                signInFacebook.put(Constants.DEVICE_ID,PreferenceHandler.readFCM_KEY(LoginActivity.this, Constants.FCM_TOKEN, ""));

            } else {
                signInFacebook.put(Constants.DEVICE_ID, "1234567890");
            }
            signInFacebook.put(Constants.DEVICE_TYPE, "android");
            callSinInFacebookApi(signInFacebook);
        }


    }

    private void callSinInFacebookApi(HashMap<String, String> signInFacebook) {
        try {

                if (Utility.isInternetConnection(this)) {
                    CallProgressWheel.showLoadingDialog(this, "Loading...");
                    Call<ResponsePojo> posts = retrofit.create(ApiInterface.class).loginFaceBook(signInFacebook);
                    posts.enqueue(new retrofit2.Callback<ResponsePojo>() {
                        @Override
                        public void onResponse(Call<ResponsePojo> call, retrofit2.Response<ResponsePojo> response) {
                            if (response.body().getStatus() == 200) {
                                CallProgressWheel.dismissLoadingDialog();
                                PreferenceHandler.writeBoolean(LoginActivity.this, PreferenceHandler.IS_ALREADY_LOGIN, true);
                                PreferenceHandler.writeString(LoginActivity.this,PreferenceHandler.PREF_KEY_USER_ID,response.body().getData().id);
                                for (int i = 0; i < response.headers().size(); i++) {
                                        String loginToken = response.headers().get("x-logintoken");
                                        Log.e("loginActivity", loginToken);
                                        PreferenceHandler.writeString(LoginActivity.this, PreferenceHandler.PREF_KEY_LOGIN_USER_TOKEN, loginToken);
                                        PreferenceHandler.writeString(LoginActivity.this,PreferenceHandler.PREF_KEY_IS_SOCIAL_LOGIN,"true");

                                }
                                Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                                startActivity(intent);
                            }
                            else
                            {
                                CallProgressWheel.dismissLoadingDialog();
                                Utility.showToastMessageShort(LoginActivity.this,response.body().getDescription());
                            }
                        }

                        @Override
                        public void onFailure(Call<ResponsePojo> call, Throwable t) {
                            CallProgressWheel.dismissLoadingDialog();
                            PreferenceHandler.writeBoolean(LoginActivity.this, PreferenceHandler.IS_ALREADY_LOGIN, false);
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

    public void callResendVerificationPopUp(Activity activity, String description) {

        // Create custom dialog object
        final Dialog dialog = new Dialog(this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.setCancelable(true);
        dialog.setCanceledOnTouchOutside(true);

        dialog.setContentView(R.layout.succuess_pop_up);

        TextView mtvDialogTitle=(TextView)dialog.findViewById(R.id.tvDialogTitle);
        mtvDialogTitle.setText(activity.getString(R.string.resend_Link));
        TextView mBtnOk = (TextView) dialog.findViewById(R.id.btn_ok);
        mBtnOk.setText(getString(R.string.resend_verification_Link));
        TextView text_message = (TextView) dialog.findViewById(R.id.tv_success_message);
        text_message.setText(description);
//        dialog.getWindow().getAttributes().windowAnimations = R.style.DialogAnimation;

        mBtnOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
                callResendVerificationLinkApi();
                //finish();

            }
        });
        dialog.setTitle("Custom Dialog");


        dialog.show();


    }

    private void callResendVerificationLinkApi() {

        try {
            // check validations for current password,new password and confirm password

            if (Utility.isInternetConnection(this)) {

                CallProgressWheel.showLoadingDialog(this, "Loading...");
                           /*
                            payload
                            {
                                email:- mohit@yopmail.com
                            }*/


                HashMap<String, String> forgotPasswordHashMap = new HashMap<>();
                forgotPasswordHashMap.put("email", mEtEmail.getText().toString().trim());

                Call<ResponsePojo> posts = retrofit.create(ApiInterface.class).resendVerificationLink(forgotPasswordHashMap);
               posts.enqueue(new retrofit2.Callback<ResponsePojo>() {
                   @Override
                   public void onResponse(Call<ResponsePojo> call, retrofit2.Response<ResponsePojo> response) {
                       if (response.body().getStatus() == 200) {
                           CallProgressWheel.dismissLoadingDialog();
                           // callSuccessPopUp(LoginActivity.this, responsePojo.getDescription());
                           Utility.showToastMessageShort(LoginActivity.this, response.body().getDescription());


                       } else {
                           CallProgressWheel.dismissLoadingDialog();
                           Toast.makeText(LoginActivity.this, response.body().getDescription(), Toast.LENGTH_SHORT).show();
                       }
                   }

                   @Override
                   public void onFailure(Call<ResponsePojo> call, Throwable t) {
                       CallProgressWheel.dismissLoadingDialog();
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


}
