package com.igniva.qwer.ui.activities;

import android.os.Bundle;
import android.support.v7.widget.AppCompatCheckBox;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.igniva.qwer.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * A login screen that offers login via email/password.
 */
public class SignUpActivity extends BaseActivity {


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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_signup);
        ButterKnife.bind(this);
        setUpLayout();
        setUpToolbar();
        setDataInViewObjects();
    }


    @Override
    public void setUpLayout() {

    }


    @Override
    public void setDataInViewObjects() {

    }

    @Override
    protected void setUpToolbar() {

    }


    @OnClick({R.id.ll_back, R.id.btn_signUp, R.id.ll_fbSignUp})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.ll_back:
                finish();
                break;
            case R.id.btn_signUp:
                break;
            case R.id.ll_fbSignUp:
                break;
            default:
                break;
        }
    }
}

