package com.igniva.qwer.ui.activities;

import android.os.Bundle;

import com.igniva.qwer.R;

import javax.inject.Inject;

import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit2.Retrofit;

public class CreateNewPostActivity extends BaseActivity {

    @Inject
    Retrofit retrofit;
    @OnClick(R.id.ivbackIcon)
    public void back(){
        onBackPressed();
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_new_post);
        ButterKnife.bind(this);
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
}
