package com.igniva.qwer.ui.activities;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.animation.AlphaAnimation;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.facebook.drawee.backends.pipeline.Fresco;
import com.facebook.drawee.view.SimpleDraweeView;
import com.igniva.qwer.R;
import com.igniva.qwer.controller.ApiInterface;
import com.igniva.qwer.model.ResponsePojo;
import com.igniva.qwer.ui.views.CallProgressWheel;
import com.igniva.qwer.ui.views.TextViewRegular;
import com.igniva.qwer.utils.Constants;
import com.igniva.qwer.utils.FieldValidators;
import com.igniva.qwer.utils.Global;
import com.igniva.qwer.utils.PreferenceHandler;
import com.igniva.qwer.utils.Utility;

import java.util.HashMap;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

import static com.igniva.qwer.utils.Constants.ALPHA_ANIMATIONS_DURATION;
import static com.igniva.qwer.utils.Constants.PERCENTAGE_TO_HIDE_TITLE_DETAILS;
import static com.igniva.qwer.utils.Constants.PERCENTAGE_TO_SHOW_TITLE_AT_TOOLBAR;


/**
 * Created by karanveer on 1/9/17.
 */

public class AboutTongyActivity extends BaseActivity implements AppBarLayout.OnOffsetChangedListener {


    @BindView(R.id.imageview_placeholder)
    ImageView mImageviewPlaceholder;
    @BindView(R.id.linearlayout_title)
    LinearLayout mLinearlayoutTitle;
    @BindView(R.id.framelayout_title)
    FrameLayout mFramelayoutTitle;
    @BindView(R.id.collapsing)
    CollapsingToolbarLayout mCollapsing;
    @BindView(R.id.appbar)
    AppBarLayout mAppbar;
    @BindView(R.id.textview_title)
    TextView mTextviewTitle;
    @BindView(R.id.toolbar)
    Toolbar mToolbar;
    @BindView(R.id.avatar1)
    SimpleDraweeView mAvatar1;
    @BindView(R.id.tv_text)
    TextView mTvText;
    @BindView(R.id.back_arrow)
    ImageView mBackArrow;
    @BindView(R.id.tv_heading)
    TextView mTvHeading;
    @BindView(R.id.tv_DeleteAccount)
    TextViewRegular mTvDeleteAccount;
    @BindView(R.id.ll_DeleteAccount)
    LinearLayout mLlDeleteAccount;
    private boolean mIsTheTitleVisible = false;
    private boolean mIsTheTitleContainerVisible = true;
    @Inject
    Retrofit retrofit;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ((Global) getApplicationContext()).getNetComponent().inject(this);
        Fresco.initialize(this);
        setContentView(R.layout.about_tongy);
        ButterKnife.bind(this);
        setUpLayout();
    }

    public void callConfirmDeletionPopUp() {
         // Create custom dialog object
        final Dialog dialog = new Dialog(this, R.style.Theme_Dialog);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.setCancelable(true);
        dialog.setCanceledOnTouchOutside(true);
        dialog.setContentView(R.layout.delete_account_pop_up);
        Button mBtnConfirm = (Button) dialog.findViewById(R.id.btn_confirm);
        Button mBtnCancel = (Button) dialog.findViewById(R.id.btn_cancel);
        final EditText delete_account_password = (EditText) dialog.findViewById(R.id.delete_account_password);

//        dialog.getWindow().getAttributes().windowAnimations = R.style.DialogAnimation;
         mBtnConfirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (FieldValidators.isNullOrEmpty(delete_account_password)) {
                    delete_account_password.setError("Please enter password");
                    delete_account_password.setFocusable(true);

                } else {
                    Log.e("Tag", "Here----------" + delete_account_password.getText().toString());
                    if (!delete_account_password.getText().toString().isEmpty() && delete_account_password.getText().toString().length() > 0) {
                        dialog.dismiss();
                        HashMap<String, String> deleteAccount = new HashMap<>();
                        deleteAccount.put(Constants.PASSWORD, delete_account_password.getText().toString());
                        callDeleteMyAccountApi(deleteAccount);
                    }
                }
            }
        });
        mBtnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();


            }
        });
        dialog.setTitle("Custom Dialog");


        dialog.show();


    }

    /**
     * Call Delete my account api
     *
     * @param password
     */

    private void callDeleteMyAccountApi(HashMap<String, String> password) {

        try {
            if (Utility.isInternetConnection(this)) {

                CallProgressWheel.showLoadingDialog(this, "Loading...");
                Call<ResponsePojo> posts = retrofit.create(ApiInterface.class).deleteAccount(password);
                posts.enqueue(new Callback<ResponsePojo>() {
                    @Override
                    public void onResponse(Call<ResponsePojo> call, Response<ResponsePojo> response) {
                        if (response.body().getStatus() == 200) {
                            finishAffinity();
                            PreferenceHandler.writeBoolean(AboutTongyActivity.this, PreferenceHandler.IS_ALREADY_LOGIN, false);
                            Intent intent = new Intent(AboutTongyActivity.this, LoginActivity.class);
                            startActivity(intent);
                        } else {
                            CallProgressWheel.dismissLoadingDialog();
                            Utility.showToastMessageLong(AboutTongyActivity.this, response.body().getDescription());
                        }
                    }

                    @Override
                    public void onFailure(Call<ResponsePojo> call, Throwable t) {
                        CallProgressWheel.dismissLoadingDialog();
                        Toast.makeText(AboutTongyActivity.this, getString(R.string.something_went_wrong), Toast.LENGTH_SHORT).show();

                    }
                });

            }

        } catch (Exception e) {
            CallProgressWheel.dismissLoadingDialog();
            Toast.makeText(AboutTongyActivity.this, getString(R.string.something_went_wrong), Toast.LENGTH_SHORT).show();
            e.printStackTrace();
        }


    }


    @Override
    public void setUpLayout() {
        mTvHeading.setText("About Tongy");
        mTextviewTitle.setText("About Tongy");
        mToolbar.setTitle("");
        mAppbar.addOnOffsetChangedListener(this);
        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(true);

        startAlphaAnimation(mTextviewTitle, 0, View.INVISIBLE);
        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(true);
        mImageviewPlaceholder.setImageResource(R.drawable.blue_skyline);
//        mAvatar1.setImageResource(R.drawable.placeholder);
        mToolbar.setNavigationIcon(android.support.v7.appcompat.R.drawable.abc_ic_ab_back_material);
        mToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });
    }

    @Override
    public void setDataInViewObjects() {

    }

    @Override
    protected void setUpToolbar() {

    }

    public static void startAlphaAnimation(View v, long duration, int visibility) {
        AlphaAnimation alphaAnimation = (visibility == View.VISIBLE)
                ? new AlphaAnimation(0f, 1f)
                : new AlphaAnimation(1f, 0f);

        alphaAnimation.setDuration(duration);
        alphaAnimation.setFillAfter(true);
        v.startAnimation(alphaAnimation);
    }

    @Override
    public void onOffsetChanged(AppBarLayout appBarLayout, int verticalOffset) {
        int maxScroll = appBarLayout.getTotalScrollRange();
        float percentage = (float) Math.abs(verticalOffset) / (float) maxScroll;
        Log.e("percntqage", percentage + "");
        if (percentage == 1.0) {
//            mToolbar2.setVisibility(View.GONE);
        }
        handleAlphaOnTitle(percentage);
        handleToolbarTitleVisibility(percentage);
    }

    private void handleToolbarTitleVisibility(float percentage) {
        if (percentage >= PERCENTAGE_TO_SHOW_TITLE_AT_TOOLBAR) {

            if (!mIsTheTitleVisible) {
                startAlphaAnimation(mTextviewTitle, ALPHA_ANIMATIONS_DURATION, View.VISIBLE);

                mIsTheTitleVisible = true;
            }

        } else {

            if (mIsTheTitleVisible) {
                startAlphaAnimation(mTextviewTitle, ALPHA_ANIMATIONS_DURATION, View.INVISIBLE);

                mIsTheTitleVisible = false;
            }
        }
    }

    private void handleAlphaOnTitle(float percentage) {
        if (percentage >= PERCENTAGE_TO_HIDE_TITLE_DETAILS) {
            if (mIsTheTitleContainerVisible) {
                startAlphaAnimation(mLinearlayoutTitle, ALPHA_ANIMATIONS_DURATION, View.INVISIBLE);
                startAlphaAnimation(mToolbar, ALPHA_ANIMATIONS_DURATION, View.VISIBLE);
                startAlphaAnimation(mBackArrow, ALPHA_ANIMATIONS_DURATION, View.GONE);
                mIsTheTitleContainerVisible = false;
            }

        } else {

            if (!mIsTheTitleContainerVisible) {
                startAlphaAnimation(mLinearlayoutTitle, ALPHA_ANIMATIONS_DURATION, View.VISIBLE);
                startAlphaAnimation(mToolbar, ALPHA_ANIMATIONS_DURATION, View.INVISIBLE);
                startAlphaAnimation(mBackArrow, ALPHA_ANIMATIONS_DURATION, View.VISIBLE);

                mIsTheTitleContainerVisible = true;
            }
        }
    }

     @OnClick(R.id.tv_DeleteAccount)
    public void onViewClicked() {
        callConfirmDeletionPopUp();
    }
     @OnClick(R.id.back_arrow)
    public void onBackClicked() {
        onBackPressed();
    }
}
