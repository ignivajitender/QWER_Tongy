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
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.facebook.drawee.backends.pipeline.Fresco;
import com.facebook.drawee.view.SimpleDraweeView;
import com.igniva.qwer.R;
import com.igniva.qwer.controller.ApiInterface;
import com.igniva.qwer.controller.RetrofitClient;
import com.igniva.qwer.model.ProfileResponsePojo;
import com.igniva.qwer.model.ResponsePojo;
import com.igniva.qwer.ui.views.CallProgressWheel;
import com.igniva.qwer.utils.Utility;
import com.igniva.qwer.utils.Validation;
import com.igniva.qwer.utils.fcm.Constants;

import java.util.HashMap;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

import static com.igniva.qwer.R.id.textview_title;
import static com.igniva.qwer.utils.fcm.Constants.ALPHA_ANIMATIONS_DURATION;
import static com.igniva.qwer.utils.fcm.Constants.PERCENTAGE_TO_HIDE_TITLE_DETAILS;
import static com.igniva.qwer.utils.fcm.Constants.PERCENTAGE_TO_SHOW_TITLE_AT_TOOLBAR;

/**
 * Created by karanveer on 21/9/17.
 */

public class MyProfileActivity extends BaseActivity implements AppBarLayout.OnOffsetChangedListener {


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
    @BindView(R.id.et_country)
    EditText mEtCountry;
    @BindView(R.id.et_city)
    EditText mEtCity;
    @BindView(R.id.et_pincode)
    EditText mEtPincode;
    @BindView(R.id.et_age)
    EditText mEtAge;
    @BindView(R.id.et_gender)
    EditText mEtGender;
    @BindView(R.id.et_about)
    EditText mEtAbout;
    @BindView(textview_title)
    TextView mTextviewTitle;
    @BindView(R.id.toolbar)
    Toolbar mToolbar;
    @BindView(R.id.avatar)
    SimpleDraweeView mAvatar1;
    @BindView(R.id.iv_edit_profile)
    ImageView mIvEditProfile;
    @BindView(R.id.iv_add_pic)
    ImageView mIvAddPic;
    @BindView(R.id.iv_proile_pic1)
    ImageView mIvProilePic1;
    @BindView(R.id.iv_proile_pic2)
    ImageView mIvProilePic2;
    @BindView(R.id.iv_proile_pic3)
    ImageView mIvProilePic3;
    @BindView(R.id.tv_lets_start)
    TextView mTvLetsStart;
    @BindView(R.id.ll_lets_start)
    LinearLayout mLlLetsStart;
    @BindView(R.id.cross_icon1)
    ImageView mCrossIcon1;
    @BindView(R.id.cross_icon2)
    ImageView mCrossIcon2;
    @BindView(R.id.cross_icon3)
    ImageView mCrossIcon3;
    @BindView(R.id.tv_save)
    TextView mTvSave;
    @BindView(R.id.tvName)
    TextView mtvName;

    private boolean mIsTheTitleVisible = false;
    private boolean mIsTheTitleContainerVisible = true;
    private boolean alreadyLogin;
    private String TAG = getClass().getName();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Fresco.initialize(this);
        setContentView(R.layout.activity_my_profile);

        ButterKnife.bind(this);
        setUpLayout();
        getProfileApi();
    }

    private void getProfileApi() {


        try {

                if (Utility.isInternetConnection(this)) {
                    ApiInterface mWebApi = RetrofitClient.createService(ApiInterface.class, this);
                    CallProgressWheel.showLoadingDialog(this, "Loading...");
                    mWebApi.getProfile(new Callback<ProfileResponsePojo>() {
                        @Override
                        public void success(ProfileResponsePojo responsePojo, Response response) {

                            if (responsePojo.getStatus() == 200) {
                                CallProgressWheel.dismissLoadingDialog();
                                //callSuccessPopUp(MyProfileActivity.this, responsePojo.getDescription());
                                Utility.showToastMessageShort(MyProfileActivity.this,responsePojo.getDescription());
                                setDataInView(responsePojo);

                            } else if (responsePojo.getStatus() == 400) {
                                CallProgressWheel.dismissLoadingDialog();
                                Toast.makeText(MyProfileActivity.this, responsePojo.getDescription(), Toast.LENGTH_SHORT).show();
                            }
                            else
                            {
                                CallProgressWheel.dismissLoadingDialog();
                                Toast.makeText(MyProfileActivity.this, responsePojo.getDescription(), Toast.LENGTH_SHORT).show();
                            }

                        }

                        @Override
                        public void failure(RetrofitError error) {
                            CallProgressWheel.dismissLoadingDialog();
                            Toast.makeText(MyProfileActivity.this, getString(R.string.something_went_wrong), Toast.LENGTH_SHORT).show();
                        }
                    });
                }

        } catch (Exception e) {
            CallProgressWheel.dismissLoadingDialog();
            Toast.makeText(MyProfileActivity.this, getString(R.string.something_went_wrong), Toast.LENGTH_SHORT).show();
            e.printStackTrace();
        }

    }

    private void setDataInView(ProfileResponsePojo responsePojo) {
        mEtAbout.setText(responsePojo.getData().about);
        mEtAge.setText(responsePojo.getData().age);
        mEtCity.setText(responsePojo.getData().city);
        mEtCountry.setText(responsePojo.getData().country);
        mEtGender.setText(responsePojo.getData().gender);
        mEtPincode.setText(responsePojo.getData().pincode);
        mtvName.setText(responsePojo.getData().getName());
        mTextviewTitle.setText(responsePojo.getData().getName());
    }


    @Override
    protected void setUpLayout() {


        if (getIntent().hasExtra(Constants.MYPROFILEEDITABLE)) {
            mIvEditProfile.setVisibility(View.VISIBLE);
            Glide.with(this).load(R.drawable.pp4).into(mIvProilePic1);
            Glide.with(this).load(R.drawable.pp1).into(mIvProilePic2);
            Glide.with(this).load(R.drawable.pp2).into(mIvProilePic3);
//            mIvProilePic1.setImageDrawable(getResources().getDrawable(R.drawable.pp4));
//            mIvProilePic2.setImageDrawable(getResources().getDrawable(R.drawable.pp1));
//            mIvProilePic3.setImageDrawable(getResources().getDrawable(R.drawable.pp2));
            mTvLetsStart.setVisibility(View.GONE);
            Glide.with(this).load(R.drawable.pp4).into(mImageviewPlaceholder);


        } else {
            mIvEditProfile.setVisibility(View.GONE);

            mIvProilePic1.setImageDrawable(getResources().getDrawable(R.drawable.image_placeholder));
            mIvProilePic2.setImageDrawable(getResources().getDrawable(R.drawable.image_placeholder));
            mIvProilePic3.setImageDrawable(getResources().getDrawable(R.drawable.image_placeholder));
            mLlLetsStart.setVisibility(View.VISIBLE);
            mImageviewPlaceholder.setImageDrawable(getResources().getDrawable(R.drawable.blue_skyline));

        }
        mToolbar.setTitle("");
        mAppbar.addOnOffsetChangedListener(this);

        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(true);


        startAlphaAnimation(mTextviewTitle, 0, View.INVISIBLE);
        //set avatar and cover
        mAvatar1.setImageDrawable(getResources().getDrawable(R.drawable.circular2));
//        avatar.setImageURI(imageUri);


        mToolbar.setNavigationIcon(android.support.v7.appcompat.R.drawable.abc_ic_ab_back_material);
        mToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                onBackPressed();
            }
        });
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


//                startAlphaAnimation(mToolbarRating, ALPHA_ANIMATIONS_DURATION, View.VISIBLE);
//                startAlphaAnimation(mTvRating, ALPHA_ANIMATIONS_DURATION, View.VISIBLE);
//                startAlphaAnimation(toolbar1, ALPHA_ANIMATIONS_DURATION, View.VISIBLE);
                mIsTheTitleVisible = true;
            }

        } else {

            if (mIsTheTitleVisible) {
                startAlphaAnimation(mTextviewTitle, ALPHA_ANIMATIONS_DURATION, View.INVISIBLE);


//                startAlphaAnimation(mToolbarRating, ALPHA_ANIMATIONS_DURATION, View.INVISIBLE);
//                startAlphaAnimation(mTvRating, ALPHA_ANIMATIONS_DURATION, View.INVISIBLE);
//                startAlphaAnimation(toolbar1, ALPHA_ANIMATIONS_DURATION, View.INVISIBLE);
                mIsTheTitleVisible = false;
            }
        }
    }

    private void handleAlphaOnTitle(float percentage) {
        if (percentage >= PERCENTAGE_TO_HIDE_TITLE_DETAILS) {
            if (mIsTheTitleContainerVisible) {
                startAlphaAnimation(mLinearlayoutTitle, ALPHA_ANIMATIONS_DURATION, View.INVISIBLE);
                startAlphaAnimation(mToolbar, ALPHA_ANIMATIONS_DURATION, View.VISIBLE);
                mIsTheTitleContainerVisible = false;
            }

        } else {

            if (!mIsTheTitleContainerVisible) {
                startAlphaAnimation(mLinearlayoutTitle, ALPHA_ANIMATIONS_DURATION, View.VISIBLE);
                startAlphaAnimation(mToolbar, ALPHA_ANIMATIONS_DURATION, View.INVISIBLE);
                mIsTheTitleContainerVisible = true;
            }
        }
    }

    @Override
    protected void setDataInViewObjects() {




    }
    public void callSuccessPopUp(MyProfileActivity myProfileActivity, String description) {

        // Create custom dialog object
        final Dialog dialog = new Dialog(this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.setCancelable(true);
        dialog.setCanceledOnTouchOutside(true);

        dialog.setContentView(R.layout.succuess_pop_up);

        TextView mBtnOk = (TextView) dialog.findViewById(R.id.btn_ok);
        TextView mTvMessage = (TextView) dialog.findViewById(R.id.tv_success_message);
        mTvMessage.setText(getResources().getString(R.string.profile_updated_successfully));

//        dialog.getWindow().getAttributes().windowAnimations = R.style.DialogAnimation;

        mBtnOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
                mCrossIcon1.setVisibility(View.INVISIBLE);
                mCrossIcon2.setVisibility(View.INVISIBLE);
                mCrossIcon3.setVisibility(View.INVISIBLE);
                mTvSave.setVisibility(View.INVISIBLE);


            }
        });
        dialog.setTitle("Custom Dialog");


        dialog.show();


    }
    @Override
    protected void setUpToolbar() {

    }

    @OnClick({R.id.cross_icon1, R.id.cross_icon2, R.id.cross_icon3, R.id.iv_edit_profile, R.id.tv_lets_start,R.id.tv_save})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.cross_icon1:
                mIvProilePic1.setImageDrawable(getResources().getDrawable(R.drawable.image_placeholder));
                mCrossIcon1.setVisibility(View.GONE);
                break;
            case R.id.cross_icon2:
                mIvProilePic2.setImageDrawable(getResources().getDrawable(R.drawable.image_placeholder));
                mCrossIcon2.setVisibility(View.GONE);
                break;
            case R.id.cross_icon3:
                mIvProilePic3.setImageDrawable(getResources().getDrawable(R.drawable.image_placeholder));
                mCrossIcon3.setVisibility(View.GONE);
                break;
            case R.id.iv_edit_profile:
                mCrossIcon1.setVisibility(View.VISIBLE);
                mCrossIcon2.setVisibility(View.VISIBLE);
                mCrossIcon3.setVisibility(View.VISIBLE);
                mTvSave.setVisibility(View.VISIBLE);
                break;
            case R.id.tv_save:
             // callSuccessPopUp();
                callSaveUpdateProfile();

                break;
            case R.id.tv_lets_start:
                Intent intent = new Intent(this, MainActivity.class);
                startActivity(intent);
                finish();

                break;
        }
    }
// method for Save Profile
    private void callSaveUpdateProfile() {
        try {
            // check validations for save profile input
            if (Validation.validateUpdateProfile(this, mEtCountry,mEtPincode,mEtAbout,mEtCity,mEtGender,mEtAge)) {
                if (Utility.isInternetConnection(this)) {
                    ApiInterface mWebApi = RetrofitClient.createService(ApiInterface.class, this);
                    CallProgressWheel.showLoadingDialog(this, "Loading...");
                           /*
                            payload
                            {“country”:”india”
                              "city/state":"chandigarh",
                              "gender":"male",”age”:”20”,”pincode”:”148002”,”about”:”tongy app”
                            }
                            */



                    HashMap<String, String> updateProfilePayload = new HashMap<>();
                    updateProfilePayload.put("country", mEtCountry.getText().toString().trim());
                    updateProfilePayload.put("city", mEtCity.getText().toString().trim());
                    updateProfilePayload.put("gender", mEtGender.getText().toString().trim());
                    updateProfilePayload.put("pincode", mEtPincode.getText().toString().trim());
                    updateProfilePayload.put("about", mEtAbout.getText().toString().trim());
                    updateProfilePayload.put("age", mEtAge.getText().toString().trim());



                    mWebApi.updateProfile(updateProfilePayload, new Callback<ResponsePojo>() {
                        @Override
                        public void success(ResponsePojo responsePojo, Response response) {

                            if (responsePojo.getStatus() == 200) {
                                CallProgressWheel.dismissLoadingDialog();
                                callSuccessPopUp(MyProfileActivity.this, responsePojo.getDescription());
                                 //Utility.showToastMessageShort(MyProfileActivity.this,responsePojo.getDescription());


                            }
                            else
                            {
                                CallProgressWheel.dismissLoadingDialog();
                                Toast.makeText(MyProfileActivity.this, responsePojo.getDescription(), Toast.LENGTH_SHORT).show();
                            }

                        }

                        @Override
                        public void failure(RetrofitError error) {
                            CallProgressWheel.dismissLoadingDialog();
                            Toast.makeText(MyProfileActivity.this, getString(R.string.something_went_wrong), Toast.LENGTH_SHORT).show();
                        }
                    });
                }
            }
        } catch (Exception e) {
            CallProgressWheel.dismissLoadingDialog();
            Toast.makeText(MyProfileActivity.this, getString(R.string.something_went_wrong), Toast.LENGTH_SHORT).show();
            e.printStackTrace();
        }

    }

}








