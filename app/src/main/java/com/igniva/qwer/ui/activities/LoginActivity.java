package com.igniva.qwer.ui.activities;


import android.graphics.Paint;
import android.os.Bundle;
import android.widget.TextView;

import com.igniva.qwer.R;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by igniva-andriod-11 on 7/11/16.
 */

public class LoginActivity extends BaseActivity {


    @BindView(R.id.tv_ForgotPassword)
    TextView mTvForgotPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
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


}
