package com.igniva.qwer.ui.activities;

import android.os.Bundle;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.igniva.qwer.R;
import com.igniva.qwer.controller.ApiInterface;
import com.igniva.qwer.controller.RetrofitClient;
import com.igniva.qwer.model.ResponsePojo;
import com.igniva.qwer.ui.views.CallProgressWheel;
import com.igniva.qwer.utils.Constants;
import com.igniva.qwer.utils.Utility;
import com.igniva.qwer.utils.Validation;

import java.util.HashMap;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

public class ContactUsActivity extends BaseActivity {

    @BindView(R.id.iv_back)
    ImageView ivBack;
    @BindView(R.id.et_contactUs_title)
    EditText etContactUsTitle;
    @BindView(R.id.et_contactUs_description)
    EditText etContactUsDescription;
    @BindView(R.id.ll_ContactUs_Post_Now)
    LinearLayout llContactUsPostNow;

    @OnClick(R.id.iv_back)
    public  void back(){
        Utility.hideSoftKeyboard(ContactUsActivity.this);
        onBackPressed();
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
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
            sendUserFeedBackToAdmin(contact_us);

        }

    }

    private void sendUserFeedBackToAdmin(HashMap<String, String> contact_us) {
        try {

            if (Utility.isInternetConnection(this)) {
                ApiInterface mWebApi = RetrofitClient.createService(ApiInterface.class, this);
                CallProgressWheel.showLoadingDialog(this, "Loading...");
                mWebApi.contactUs(contact_us, new Callback<ResponsePojo>() {
                    @Override
                    public void success(ResponsePojo responsePojo, Response response) {
                        CallProgressWheel.dismissLoadingDialog();
                        Utility.showToastMessageShort(ContactUsActivity.this, responsePojo.getDescription());
                        onBackPressed();
                    }

                    @Override
                    public void failure(RetrofitError error) {
                        CallProgressWheel.dismissLoadingDialog();
                    }
                });


            }

        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}
