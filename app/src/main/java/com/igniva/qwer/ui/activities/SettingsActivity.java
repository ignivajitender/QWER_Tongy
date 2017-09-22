package com.igniva.qwer.ui.activities;

import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.igniva.qwer.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;


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

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        super.onCreate(savedInstanceState);


        setContentView(R.layout.activity_settings);
        ButterKnife.bind(this);


        setUpLayout();
        setDataInViewObjects();

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

    @OnClick({R.id.ll_back, R.id.ll_myProfile, R.id.ll_changePassword, R.id.ll_changeEmail, R.id.ll_aboutQwer, R.id.ll_DeleteAccount, R.id.ll_contactUs, R.id.ll_rate_us, R.id.ll_pushNotifications, R.id.ll_voiceCall, R.id.ll_videoCall, R.id.ll_logout, R.id.ll_termsPrivacy})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.ll_back:
                break;
            case R.id.ll_myProfile:
                break;
            case R.id.ll_changePassword:
                break;
            case R.id.ll_changeEmail:
                break;
            case R.id.ll_aboutQwer:
                break;
            case R.id.ll_DeleteAccount:
                break;
            case R.id.ll_contactUs:
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
                break;
            case R.id.ll_termsPrivacy:
                break;
            default:
                break;
        }
    }
}
