package com.igniva.qwer.ui.activities;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
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


                Intent intent = new Intent(SignUpActivity.this, NoResultsActivity.class);
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
                callSuccessPopUp(this, getResources().getString(R.string.emailVerification));
                break;
            case R.id.ll_fbSignUp:
                break;
            default:
                break;
        }
    }
}

