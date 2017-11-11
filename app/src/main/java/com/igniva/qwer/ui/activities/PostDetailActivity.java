package com.igniva.qwer.ui.activities;

import android.os.Bundle;
import android.widget.TextView;

import com.igniva.qwer.R;
import com.igniva.qwer.utils.Global;
import com.igniva.qwer.utils.Utility;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit2.Retrofit;

public class PostDetailActivity extends BaseActivity {

    @BindView(R.id.tv_tap_to_rename)
    TextView mtvTitle;
    @OnClick(R.id.ivbackIcon)
    public void back(){
        Utility.hideSoftKeyboard(PostDetailActivity.this);
        onBackPressed();
    }

    @Inject
    Retrofit retrofit;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        ((Global) getApplication()).getNetComponent().inject(this);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post_detail);
        ButterKnife.bind(this);
        setUpToolbar();
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
        mtvTitle.setText(getResources().getString(R.string.post_details));
    }
}
