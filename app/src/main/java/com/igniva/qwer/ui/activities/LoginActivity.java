package com.igniva.qwer.ui.activities;


import com.igniva.qwer.R;

/**
 * Created by igniva-andriod-11 on 7/11/16.
 */

public class LoginActivity extends BaseActivity{
    @Override
    protected void setUpLayout() {
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
