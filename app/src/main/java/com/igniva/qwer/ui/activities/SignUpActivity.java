package com.igniva.qwer.ui.activities;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.design.widget.TextInputLayout;
import android.support.v7.widget.AppCompatCheckBox;
import android.view.View;
import android.view.Window;
import android.widget.Button;
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
 * A login screen that offers login via email/password.
 */
public class SignUpActivity extends BaseActivity implements FacebookResponse, Utility.OnAlertOkClickListener {


    @BindView(R.id.iv_back)
    ImageView mIvBack;
    @BindView(R.id.ll_back)
    FrameLayout mLlBack;
    @BindView(R.id.et_name)
    EditText mEtName;
    @BindView(R.id.et_email)
    EditText mEtEmail;
    @BindView(R.id.et_password)
    EditText mEtPassword;
    @BindView(R.id.tv_changePassword)
    AppCompatCheckBox mTvChangePassword;
    @BindView(R.id.tv_TandC)
    TextView mTvTandC;
    @BindView(R.id.ll_changePassword)
    LinearLayout mLlChangePassword;
    @BindView(R.id.btn_signUp)
    Button mBtnSignUp;
    @BindView(R.id.ll_signUp)
    LinearLayout mLlSignUp;
    @BindView(R.id.iv_fb)
    ImageView mIvFb;
    @BindView(R.id.tv_fb)
    TextView mTvFb;
    @BindView(R.id.ll_fbSignUp)
    LinearLayout mLlFbSignUp;
    @BindView(R.id.llMain)
    LinearLayout mLlMain;
    @BindView(R.id.til_name)
    TextInputLayout mTilName;
    @BindView(R.id.til_email)
    TextInputLayout mTilEmail;
    @BindView(R.id.til_pass)
    TextInputLayout mTilPass;

    @OnClick(R.id.iv_back)
            public void back(){
        Utility.hideSoftKeyboard(SignUpActivity.this);
        onBackPressed();
    }
    // facebook helper init
    FacebookHelper mFbHelper;

    @Inject
    Retrofit retrofit;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        requestWindowFeature(Window.FEATURE_NO_TITLE);
//        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
//                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        super.onCreate(savedInstanceState);
        ((Global) getApplicationContext()).getNetComponent().inject(this);
        setContentView(R.layout.activity_signup);
        ButterKnife.bind(this);
        Utility.printKeyHash(getApplicationContext());
        setUpLayout();
        setUpToolbar();
        setDataInViewObjects();
    }


    @Override
    public void setUpLayout() {
        mFbHelper = new FacebookHelper(SignUpActivity.this, "id,name,email,gender,birthday,picture,cover", SignUpActivity.this);
    }


    @Override
    public void setDataInViewObjects() {

        if(getIntent().getExtras()!=null && getIntent().getStringExtra("comingFrom").equalsIgnoreCase("login")){
            if(getIntent().getStringExtra("name")!=null)
                mEtName.setText(getIntent().getStringExtra("name"));
            if(getIntent().getStringExtra("email")!=null)
                mEtEmail.setText(getIntent().getStringExtra("email"));
        }
    }

    @Override
    protected void setUpToolbar() {

    }

    public void callSuccessPopUp(Context context, String message) {
        // Create custom dialog object
        final Dialog dialog = new Dialog(context);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.setCancelable(false);
        dialog.setCanceledOnTouchOutside(false);

        dialog.setContentView(R.layout.succuess_pop_up);
        TextView text_message = (TextView) dialog.findViewById(R.id.tv_success_message);
        TextView mBtnOk = (TextView) dialog.findViewById(R.id.btn_ok);
        text_message.setText(message);
//        dialog.getWindow().getAttributes().windowAnimations = R.style.DialogAnimation;

        mBtnOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
                Intent intent = new Intent(SignUpActivity.this, LoginActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
            }
        });
        dialog.setTitle("Custom Dialog");
        dialog.show();


    }


    @OnClick({R.id.ll_back, R.id.btn_signUp, R.id.ll_fbSignUp})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.ll_back:
                finish();
                break;
            case R.id.btn_signUp:
                signUpWithOutFacebook();
                break;
            case R.id.ll_fbSignUp:
                loginWithFacebook();
                break;
            default:
                break;
        }
    }

    /**
     * Sign up with out facebook
     */
    private void signUpWithOutFacebook() {
        if (Validation.isValidatedSignup(this, mEtName, mTilName, mEtEmail, mTilEmail, mEtPassword, mTilPass, mTvChangePassword)) {
            if (Utility.isInternetConnection(this)) {
                CallProgressWheel.showLoadingDialog(this, "Loading...");

                HashMap<String, String> signupHash = new HashMap<>();
                signupHash.put(Constants.Name, mEtName.getText().toString());
                signupHash.put(Constants.EMAIL, mEtEmail.getText().toString());
                signupHash.put(Constants.PASSWORD, mEtPassword.getText().toString());

                if (PreferenceHandler.readFCM_KEY(SignUpActivity.this, Constants.FCM_TOKEN, "").length()>0) {
                    signupHash.put(Constants.DEVICE_ID,PreferenceHandler.readFCM_KEY(SignUpActivity.this, Constants.FCM_TOKEN, ""));

                } else {
                    signupHash.put(Constants.DEVICE_ID, "1234567890");
                }
                signupHash.put(Constants.DEVICE_TYPE,"android");
                //Create a retrofit call object
               Call<ResponsePojo> posts= retrofit.create(ApiInterface.class).signup(signupHash);
                posts.enqueue(new retrofit2.Callback<ResponsePojo>() {
                    @Override
                    public void onResponse(       retrofit2.Call<ResponsePojo> call, retrofit2.Response<ResponsePojo> response) {
                        if (response.body().getStatus() == 200) {
                            CallProgressWheel.dismissLoadingDialog();
                            callSuccessPopUp(SignUpActivity.this, getResources().getString(R.string.emailVerification));
                        }
                        else
                        {
                            CallProgressWheel.dismissLoadingDialog();
                            Utility.showToastMessageShort(SignUpActivity.this,response.body().getDescription());
                        }
                    }

                    @Override
                    public void onFailure(       retrofit2.Call<ResponsePojo> call, Throwable t) {
                        CallProgressWheel.dismissLoadingDialog();
                        Toast.makeText(SignUpActivity.this, getString(R.string.something_went_wrong), Toast.LENGTH_SHORT).show();
                    }
                });


            }
        }

    }

    /**
     * Login with facebook
     */
    private void loginWithFacebook() {
        if (Utility.isInternetConnection(SignUpActivity.this)) {
            mFbHelper.performSignIn(this);
        } else {
            Toast.makeText(this, Constants.NOINTERNET, Toast.LENGTH_SHORT).show();
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
        Log.e("Person dob: ", facebookUser.facebookID + "");
        Log.e("Person dob: ", facebookUser.profilePic + "");


        if(facebookUser.name==null || facebookUser.email==null){
          //  startActivity(new Intent(this,SignUpActivity.this).putExtra("comingFrom","facebook").p);
            if(facebookUser.name!=null)
            mEtName.setText(facebookUser.name);
            if(facebookUser.email!=null)
            mEtEmail.setText(facebookUser.email);
        }
        else {
            HashMap<String, String> signInFacebook = new HashMap<>();
            signInFacebook.put(Constants.SOCIAL_ID, facebookUser.facebookID);
            signInFacebook.put(Constants.EMAIL, facebookUser.email);
            signInFacebook.put(Constants.GENDER, facebookUser.gender);
            signInFacebook.put(Constants.NAME, facebookUser.name);
            signInFacebook.put(Constants.PROFILE_PIC, facebookUser.profilePic);
            if (!PreferenceHandler.readFCM_KEY(SignUpActivity.this, Constants.FCM_TOKEN, "").equalsIgnoreCase("")) {
                signInFacebook.put(Constants.DEVICE_ID,PreferenceHandler.readFCM_KEY(SignUpActivity.this, Constants.FCM_TOKEN, ""));

            } else {
                signInFacebook.put(Constants.DEVICE_ID, "1234567890");
            }
            signInFacebook.put(Constants.DEVICE_TYPE,"android");
            callSinInFacebookApi(signInFacebook);
        }
    }




    /**
    * Send facebook sign up data to server
    * */

    private void callSinInFacebookApi(HashMap<String, String> signInFacebook) {
        try {

            if (Utility.isInternetConnection(this)) {

                CallProgressWheel.showLoadingDialog(this, "Loading...");
                retrofit2.Call<ResponsePojo> posts = retrofit.create(ApiInterface.class).loginFaceBook(signInFacebook);
                posts.enqueue(new        retrofit2.Callback<ResponsePojo>() {
                    @Override
                    public void onResponse(Call<ResponsePojo> call, retrofit2.Response<ResponsePojo> response) {
                        if (response.body().getStatus() == 200) {
                            CallProgressWheel.dismissLoadingDialog();
                            PreferenceHandler.writeBoolean(SignUpActivity.this, PreferenceHandler.IS_ALREADY_LOGIN, true);
                            for (int i = 0; i < response.headers().size(); i++) {

                                    String loginToken = response.headers().get("x-logintoken");
                                    Log.e("loginActivity", loginToken);
                                    PreferenceHandler.writeString(SignUpActivity.this, PreferenceHandler.PREF_KEY_LOGIN_USER_TOKEN, loginToken);
                                    PreferenceHandler.writeString(SignUpActivity.this,PreferenceHandler.PREF_KEY_IS_SOCIAL_LOGIN,"true");

                            }


                            Intent intent = new Intent(SignUpActivity.this, MainActivity.class);
                            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                            startActivity(intent);
                        }
                        else {
                            CallProgressWheel.dismissLoadingDialog();
                            Utility.showToastMessageLong(SignUpActivity.this,response.body().getDescription());
                        }
                    }

                    @Override
                    public void onFailure(retrofit2.Call<ResponsePojo> call, Throwable t) {
                        CallProgressWheel.dismissLoadingDialog();
                        PreferenceHandler.writeBoolean(SignUpActivity.this, PreferenceHandler.IS_ALREADY_LOGIN, false);
                        Toast.makeText(SignUpActivity.this, getString(R.string.something_went_wrong), Toast.LENGTH_SHORT).show();
                    }
                });
            }

        } catch (Exception e) {
            CallProgressWheel.dismissLoadingDialog();
            Toast.makeText(SignUpActivity.this, getString(R.string.something_went_wrong), Toast.LENGTH_SHORT).show();
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

    }
}

