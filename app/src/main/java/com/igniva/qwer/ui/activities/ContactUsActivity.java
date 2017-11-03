package com.igniva.qwer.ui.activities;

import android.os.Bundle;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.igniva.qwer.R;
import com.igniva.qwer.controller.ApiControllerClass;
import com.igniva.qwer.utils.Constants;
import com.igniva.qwer.utils.Global;
import com.igniva.qwer.utils.Utility;
import com.igniva.qwer.utils.Validation;

import java.util.HashMap;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit2.Retrofit;

public class ContactUsActivity extends BaseActivity {

    @BindView(R.id.iv_back)
    ImageView ivBack;
    @BindView(R.id.et_contactUs_title)
    EditText etContactUsTitle;
    @BindView(R.id.et_contactUs_description)
    EditText etContactUsDescription;
    @BindView(R.id.ll_ContactUs_Post_Now)
    LinearLayout llContactUsPostNow;

    @Inject
    Retrofit retrofit;
    @OnClick(R.id.iv_back)
    public  void back(){
        Utility.hideSoftKeyboard(ContactUsActivity.this);
        onBackPressed();
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        ((Global) getApplication()).getNetComponent().inject(this);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contact_us);
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

    /**
     * click event on
     */

    @OnClick(R.id.ll_ContactUs_Post_Now)
    public void onViewClicked() {
        if (Validation.validateDescription(ContactUsActivity.this, etContactUsTitle, etContactUsDescription)) {
            HashMap<String, String> contact_us = new HashMap<>();
            contact_us.put(Constants.TITLE, etContactUsTitle.getText().toString());
            contact_us.put(Constants.DESCRIPTION, etContactUsTitle.getText().toString());
            ApiControllerClass.sendUserFeedBackToAdmin(ContactUsActivity.this,contact_us,retrofit);

        }

    }


}
