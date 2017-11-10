package com.igniva.qwer.ui.activities;

import android.content.Intent;
import android.os.Bundle;

import com.igniva.qwer.R;
import com.igniva.qwer.utils.Global;
import com.igniva.qwer.utils.Utility;

import butterknife.ButterKnife;
import butterknife.OnClick;

public class CreateNewPostActivity extends BaseActivity {


    @OnClick(R.id.ivbackIcon)
    public void back(){
        onBackPressed();
    }
    @OnClick(R.id.llCreateTeachingPost)
    public void openPost(){
        startActivity(new Intent(CreateNewPostActivity.this,CreateTeachingPostActivity.class));
    }
    @OnClick(R.id.llCreateMeetingPost)
    public void openMeetingPost(){
        Utility.showToastMessageLong(CreateNewPostActivity.this,getString(R.string.coming_soon));
        //startActivity(new Intent(CreateNewPostActivity.this,CreateTeachingPostActivity.class));
    }
    @OnClick(R.id.llCreateOtherPost)
    public void openOtherPost(){
        //Utility.showToastMessageLong(CreateNewPostActivity.this,getString(R.string.coming_soon));
        startActivity(new Intent(CreateNewPostActivity.this,CreateOtherPostActivity.class));
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        ((Global) getApplication()).getNetComponent().inject(this);
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
