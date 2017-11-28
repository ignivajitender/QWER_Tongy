package com.igniva.qwer.ui.activities;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v7.widget.SwitchCompat;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.igniva.qwer.R;
import com.igniva.qwer.controller.ApiControllerClass;
import com.igniva.qwer.controller.ApiInterface;
import com.igniva.qwer.model.ProfileResponsePojo;
import com.igniva.qwer.model.ResponsePojo;
import com.igniva.qwer.ui.views.CallProgressWheel;
import com.igniva.qwer.utils.Constants;
import com.igniva.qwer.utils.FieldValidators;
import com.igniva.qwer.utils.Global;
import com.igniva.qwer.utils.PreferenceHandler;
import com.igniva.qwer.utils.Utility;

import java.util.HashMap;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit2.Call;
import retrofit2.Retrofit;


public class SettingsActivity extends BaseActivity {


    @BindView(R.id.iv_back)
    ImageView mIvBack;
    @BindView(R.id.ll_back)
    FrameLayout mLlBack;
    @BindView(R.id.tv_myProfile)
    TextView mTvMyProfile;
    @BindView(R.id.ll_myProfile)
    LinearLayout mLlMyProfile;
    @BindView(R.id.tv_changePassword)
    TextView mTvChangePassword;
    @BindView(R.id.ll_changePassword)
    LinearLayout mLlChangePassword;
    @BindView(R.id.tv_changeEmail)
    TextView mTvChangeEmail;
    @BindView(R.id.ll_changeEmail)
    LinearLayout mLlChangeEmail;
    @BindView(R.id.tv_aboutQwer)
    TextView mTvAboutQwer;
    @BindView(R.id.ll_aboutQwer)
    LinearLayout mLlAboutQwer;
    @BindView(R.id.tv_DeleteAccount)
    TextView mTvDeleteAccount;
    @BindView(R.id.ll_DeleteAccount)
    LinearLayout mLlDeleteAccount;
    @BindView(R.id.tv_ContactUs)
    TextView mTvContactUs;
    @BindView(R.id.ll_contactUs)
    LinearLayout mLlContactUs;
    @BindView(R.id.tv_RateUs)
    TextView mTvRateUs;
    @BindView(R.id.ll_rate_us)
    LinearLayout mLlRateUs;
    @BindView(R.id.ll_pushNotifications)
    LinearLayout mLlPushNotifications;
    @BindView(R.id.ll_voiceCall)
    LinearLayout mLlVoiceCall;
    @BindView(R.id.ll_videoCall)
    LinearLayout mLlVideoCall;
    @BindView(R.id.ll_logout)
    LinearLayout mLlLogout;
    @BindView(R.id.tv_tandC)
    TextView mTvTandC;
    @BindView(R.id.tv_privacyPolicy)
    TextView mTvPrivacyPolicy;
    @BindView(R.id.ll_termsPrivacy)
    LinearLayout mLlTermsPrivacy;
    @BindView(R.id.llMain)
    LinearLayout mLlMain;

    @BindView(R.id.switchPushnotification)
    SwitchCompat mswitchPushnotification;

    @BindView(R.id.switchVoiceCall)
    SwitchCompat mswitchVoiceCall;

    @BindView(R.id.switchVideoCall)
    SwitchCompat mswitchVideoCall;

    int isPushNotification,isVideoCall,isVoicecall;

    @Inject
    Retrofit retrofit;
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        ((Global) getApplication()).getNetComponent().inject(this);
        super.onCreate(savedInstanceState);


        setContentView(R.layout.activity_settings);
        ButterKnife.bind(this);


        setUpLayout();
        setDataInViewObjects();
        getProfileApiSettings();
    }
    // call get profile api
    private void getProfileApiSettings() {
        try {
            if (Utility.isInternetConnection(this)) {
                CallProgressWheel.showLoadingDialog(this, "Loading...");
                Call<ProfileResponsePojo> posts = retrofit.create(ApiInterface.class).getProfile();
                posts.enqueue(new retrofit2.Callback<ProfileResponsePojo>() {
                    @Override
                    public void onResponse(Call<ProfileResponsePojo> call, retrofit2.Response<ProfileResponsePojo> response) {
                        if (response.body().getStatus() == 200) {
                            CallProgressWheel.dismissLoadingDialog();
                            //callSuccessPopUp(MyProfileActivity.this, responsePojo.getDescription());
                            // Utility.showToastMessageShort(MyProfileActivity.this,responsePojo.getDescription());
                            //setDataInView(response.body());
                           if(response.body().getData().is_push_notification==1) {
                               mswitchPushnotification.setChecked(true);
                               isPushNotification=1;
                           }
                           else {
                               mswitchPushnotification.setChecked(false);
                               isPushNotification=0;
                           }
                           if(response.body().getData().is_videocall==1) {
                               mswitchVideoCall.setChecked(true);
                               isVideoCall=1;
                           }
                           else{
                               mswitchVideoCall.setChecked(false);
                               isVideoCall=0;
                           }

                            if(response.body().getData().is_voicecall==1) {
                                mswitchVoiceCall.setChecked(true);
                                isVoicecall=1;
                            }
                            else {
                                mswitchVoiceCall.setChecked(false);
                                isVoicecall=0;
                            }

                        } else if (response.body().getStatus() == 400) {
                            CallProgressWheel.dismissLoadingDialog();
                            Log.e("profile", response.body().getDescription());
                            // Toast.makeText(MyProfileActivity.this, responsePojo.getDescription(), Toast.LENGTH_SHORT).show();
                        } else {
                            CallProgressWheel.dismissLoadingDialog();
                            // Log.e("profile",responsePojo.getDescription());
                            //Toast.makeText(MyProfileActivity.this, responsePojo.getDescription(), Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onFailure(Call<ProfileResponsePojo> call, Throwable t) {
                        CallProgressWheel.dismissLoadingDialog();
                        Toast.makeText(SettingsActivity.this, getString(R.string.something_went_wrong), Toast.LENGTH_SHORT).show();
                    }
                });
            }
        } catch (Exception e) {
            CallProgressWheel.dismissLoadingDialog();
            Toast.makeText(SettingsActivity.this, getString(R.string.something_went_wrong), Toast.LENGTH_SHORT).show();
            e.printStackTrace();
        }

    }

    public void callConfirmDeletionPopUp() {

        // Create custom dialog object
        final Dialog dialog = new Dialog(this, R.style.Theme_Dialog);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.setCancelable(true);
        dialog.setCanceledOnTouchOutside(true);

        dialog.setContentView(R.layout.delete_account_pop_up);

        Button mBtnConfirm = (Button) dialog.findViewById(R.id.btn_confirm);
        Button mBtnCancel = (Button) dialog.findViewById(R.id.btn_cancel);
       final EditText delete_account_password = (EditText) dialog.findViewById(R.id.delete_account_password);


//        dialog.getWindow().getAttributes().windowAnimations = R.style.DialogAnimation;

        mBtnConfirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               if(FieldValidators.isNullOrEmpty(delete_account_password)){
                   delete_account_password.setError("Please enter password");
                   delete_account_password.setFocusable(true);

               }
               else {
                   Log.e("Tag", "Here----------" + delete_account_password.getText().toString());
                   if (!delete_account_password.getText().toString().isEmpty() && delete_account_password.getText().toString().length() > 0) {
                       dialog.dismiss();
                       HashMap<String, String> deleteAccount = new HashMap<>();
                       deleteAccount.put(com.igniva.qwer.utils.Constants.PASSWORD, delete_account_password.getText().toString());
                       callDeleteMyAccountApi(deleteAccount);
                   }
               }
            }
        });
        mBtnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();


            }
        });
        dialog.setTitle("Custom Dialog");


        dialog.show();


    }

    /**
     * Call Delete my account api
     *
     * @param password
     */

    private void callDeleteMyAccountApi(HashMap<String, String> password) {

        try {
            if (Utility.isInternetConnection(this)) {

                CallProgressWheel.showLoadingDialog(this, "Loading...");
                Call<ResponsePojo> posts = retrofit.create(ApiInterface.class).deleteAccount(password);
                posts.enqueue(new retrofit2.Callback<ResponsePojo>() {
                    @Override
                    public void onResponse(Call<ResponsePojo> call, retrofit2.Response<ResponsePojo> response) {
                        if(response.body().getStatus() == 200){
                            finishAffinity();
                            PreferenceHandler.writeBoolean(SettingsActivity.this, com.igniva.qwer.utils.PreferenceHandler.IS_ALREADY_LOGIN, false);
                            Intent intent = new Intent(SettingsActivity.this, LoginActivity.class);
                            startActivity(intent);
                        }
                        else
                        {
                            CallProgressWheel.dismissLoadingDialog();
                            Utility.showToastMessageLong(SettingsActivity.this,response.body().getDescription());
                        }
                    }

                    @Override
                    public void onFailure(Call<ResponsePojo> call, Throwable t) {
                        CallProgressWheel.dismissLoadingDialog();
                        Toast.makeText(SettingsActivity.this, getString(R.string.something_went_wrong), Toast.LENGTH_SHORT).show();

                    }
                });

            }

        } catch (Exception e) {
            CallProgressWheel.dismissLoadingDialog();
            Toast.makeText(SettingsActivity.this, getString(R.string.something_went_wrong), Toast.LENGTH_SHORT).show();
            e.printStackTrace();
        }


    }

    @Override
    protected void setUpLayout() {
        String isSocialLogin=PreferenceHandler.readString(SettingsActivity.this,PreferenceHandler.PREF_KEY_IS_SOCIAL_LOGIN,"false");
    if(isSocialLogin.equalsIgnoreCase("true")){
        mLlChangePassword.setVisibility(View.GONE);
        mLlChangeEmail.setVisibility(View.GONE);
        mLlDeleteAccount.setVisibility(View.GONE);
    }
    else {
        mLlChangePassword.setVisibility(View.VISIBLE);
        mLlChangeEmail.setVisibility(View.VISIBLE);
        mLlDeleteAccount.setVisibility(View.VISIBLE);
    }
    }

    @Override
    protected void setDataInViewObjects() {

    }

    @Override
    protected void setUpToolbar() {

    }

    @OnClick({R.id.iv_back, R.id.tv_tandC, R.id.tv_privacyPolicy, R.id.ll_back, R.id.ll_myProfile,
            R.id.ll_changePassword, R.id.ll_changeEmail, R.id.ll_aboutQwer, R.id.ll_DeleteAccount,
            R.id.ll_contactUs, R.id.ll_rate_us, R.id.ll_pushNotifications, R.id.ll_voiceCall,
            R.id.ll_videoCall, R.id.ll_logout, R.id.ll_termsPrivacy,R.id.switchPushnotification,R.id.switchVideoCall,R.id.switchVoiceCall})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.ll_back:
                break;
            case R.id.ll_myProfile:
                Intent intentprofile = new Intent(SettingsActivity.this, MyProfileActivity.class);
                intentprofile.putExtra(Constants.MYPROFILEEDITABLE, Constants.INNER_PROFILE);
                startActivity(intentprofile);
                break;
            case R.id.ll_changePassword:
                Intent intentchange_pass = new Intent(SettingsActivity.this, ChangePasswordActivity.class);
                startActivity(intentchange_pass);
                break;
            case R.id.ll_changeEmail:
                Intent intentchange_email = new Intent(SettingsActivity.this, ChangeEmailActivity.class);
                startActivity(intentchange_email);
                break;
            case R.id.ll_aboutQwer:
                Intent intentAbout = new Intent(SettingsActivity.this, AboutTongyActivity.class);
                startActivity(intentAbout);
                break;
            case R.id.ll_DeleteAccount:
                callConfirmDeletionPopUp();
                break;
            case R.id.ll_contactUs:
                   startActivity(new Intent(this,ContactUsActivity.class));
                break;
            case R.id.ll_rate_us:
                break;
            case R.id.ll_pushNotifications:
                break;
            case R.id.ll_voiceCall:
                break;
            case R.id.ll_videoCall:
                break;
            case R.id.ll_logout:
                callConfirmLogoutPopUp();
                break;
            case R.id.ll_termsPrivacy:

                break;
            case R.id.tv_tandC:
                Intent intentTandC = new Intent(SettingsActivity.this, TermsandConditionsActivity.class);
                startActivity(intentTandC);
                break;
            case R.id.tv_privacyPolicy:
                Intent intentPrivacy = new Intent(SettingsActivity.this, PrivacyPolicyActivity.class);
                startActivity(intentPrivacy);
                break;
            case R.id.iv_back:
                finish();
                break;

            case R.id.switchPushnotification:
                Log.e("status",isPushNotification+"");
                if(mswitchPushnotification.isChecked())
                    isPushNotification=1;
                else
                    isPushNotification=0;
                ApiControllerClass.changeIsPushNotification(retrofit,SettingsActivity.this,isPushNotification,mswitchPushnotification);
                break;
            case R.id.switchVideoCall:
                Log.e("status",isVideoCall+"");
                if(mswitchVideoCall.isChecked())
                    isVideoCall=1;
                else
                    isVideoCall=0;
                ApiControllerClass.changeIsVideoCall(retrofit,SettingsActivity.this,isVideoCall,mswitchVideoCall);


                break;
            case R.id.switchVoiceCall:
                Log.e("status",isVoicecall+"");
                if(mswitchVoiceCall.isChecked())
                    isVoicecall=1;
                else
                    isVoicecall=0;
                ApiControllerClass.changeIsVoiceCall(retrofit,SettingsActivity.this,isVoicecall,mswitchVideoCall);


                break;
            default:
                break;
        }
    }

    private void callConfirmLogoutPopUp() {
        // Create custom dialog object
        final Dialog dialog = new Dialog(this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.setCancelable(true);
        dialog.setCanceledOnTouchOutside(true);

        dialog.setContentView(R.layout.logout_account_pop_up);

        Button mBtnConfirm = (Button) dialog.findViewById(R.id.btn_confirm);
        Button mBtnCancel = (Button) dialog.findViewById(R.id.btn_cancel);

        mBtnConfirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
                callLogoutMyAccountApi();

            }
        });
        mBtnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
             }
        });
        dialog.setTitle("Custom Dialog");
        dialog.show();
    }
    // call logout api
    private void callLogoutMyAccountApi() {
        try {
            if (Utility.isInternetConnection(this)) {

                CallProgressWheel.showLoadingDialog(this, "Loading...");
                Call<ResponsePojo> posts = retrofit.create(ApiInterface.class).logoutAccount();
                posts.enqueue(new retrofit2.Callback<ResponsePojo>() {
                    @Override
                    public void onResponse(Call<ResponsePojo> call, retrofit2.Response<ResponsePojo> response) {
                        if(response.body().getStatus() == 200){
                            finishAffinity();
                            Utility.showToastMessageShort(SettingsActivity.this,response.body().getDescription());
                            PreferenceHandler.getEditor(SettingsActivity.this).clear().commit();
                            Intent intent = new Intent(SettingsActivity.this, LoginActivity.class);
                            startActivity(intent);
                        }
                        else if(response.body().getStatus()==1000)
                        {
                            CallProgressWheel.dismissLoadingDialog();
                            Utility.showToastMessageShort(SettingsActivity.this,response.body().getDescription());
                        }
                        else
                        {
                            CallProgressWheel.dismissLoadingDialog();
                            Utility.showToastMessageShort(SettingsActivity.this,response.body().getDescription());
                        }
                    }
                    @Override
                    public void onFailure(Call<ResponsePojo> call, Throwable t) {
                        CallProgressWheel.dismissLoadingDialog();
                        Toast.makeText(SettingsActivity.this, getString(R.string.something_went_wrong), Toast.LENGTH_SHORT).show();

                    }
                });

            }

        } catch (Exception e) {
            CallProgressWheel.dismissLoadingDialog();
            Toast.makeText(SettingsActivity.this, getString(R.string.something_went_wrong), Toast.LENGTH_SHORT).show();
            e.printStackTrace();
        }

    }

}
