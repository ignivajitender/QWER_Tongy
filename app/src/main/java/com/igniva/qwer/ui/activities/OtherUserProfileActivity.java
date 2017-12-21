package com.igniva.qwer.ui.activities;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.igniva.qwer.R;
import com.igniva.qwer.controller.ApiControllerClass;
import com.igniva.qwer.model.OtherUserProfilePojo;
import com.igniva.qwer.ui.adapters.LanguageAdapter;
import com.igniva.qwer.ui.adapters.LanguageSpeakAdapter;
import com.igniva.qwer.ui.adapters.MultiImages;
import com.igniva.qwer.ui.views.TextViewLight;
import com.igniva.qwer.utils.CircularImageView;
import com.igniva.qwer.utils.DepthPageTransformer;
import com.igniva.qwer.utils.Global;
import com.igniva.qwer.utils.PreferenceHandler;
import com.igniva.qwer.utils.Utility;

import java.util.ArrayList;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit2.Response;
import retrofit2.Retrofit;

public class OtherUserProfileActivity extends BaseActivity {


    public int NUM_PAGES = 4;
    public String type = "Block";
    @BindView(R.id.ivbackIcon)
    ImageView mivbackIcon;
    @BindView(R.id.ivDotIcon)
    ImageView mivDotIcon;
    @BindView(R.id.toolbar_top)
    Toolbar mtoolbarTop;
    @BindView(R.id.iv_user_image)
    ViewPager mivUserImage;
    @BindView(R.id.dotsLayout)
    LinearLayout mdotsLayout;
    @Inject
    Retrofit retrofit;
    MultiImages multiImages;
    ArrayList mImagesSlidingArray;
    boolean isOpaque = true;
    @BindView(R.id.iv_videoCall)
    ImageButton ivVideoCall;
    @BindView(R.id.iv_voiceCall)
    ImageButton ivVoiceCall;
    @BindView(R.id.iv_message)
    ImageButton ivMessage;
    @BindView(R.id.tvName)
    TextView mtvName;
    @BindView(R.id.tv_age_heading)
    TextViewLight tvAgeHeading;
    @BindView(R.id.tv_age)
    TextViewLight tvAge;
    @BindView(R.id.tv_radius)
    TextViewLight tvRadius;
    @BindView(R.id.languageSpeak)
    TextView mtvlanguageSpeak;
    @BindView(R.id.rvLanguageSpeaks)
    RecyclerView mrvLanguageSpeaks;
    @BindView(R.id.tvInterestedIn)
    TextView mtvInterestedIn;
    @BindView(R.id.rvInterestedIn)
    RecyclerView mrvInterestedIn;
    @BindView(R.id.tvAbout)
    TextView mtvAbout;
    @BindView(R.id.tvAboutDetails)
    TextView mtvAboutDetails;
    @BindView(R.id.tvFrom)
    TextView mtvFrom;
    @BindView(R.id.tvFromDetails)
    TextView mtvFromDetails;
    @BindView(R.id.iv_countryImage)
    CircularImageView mivImageCountry;
    OtherUserProfilePojo otherUserProfilePojo;
    private int userId;
    private int size;

    @OnClick(R.id.ivbackIcon)
    public void back() {
        Utility.hideSoftKeyboard(OtherUserProfileActivity.this);
        onBackPressed();
    }

    @OnClick(R.id.ivDotIcon)
    public void openOptionsMenu() {
        Utility.openBlockMenu(OtherUserProfileActivity.this, retrofit, type, mivDotIcon, userId);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        ((Global) getApplication()).getNetComponent().inject(this);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_other_user_profile);
        ButterKnife.bind(this);
        if (PreferenceHandler.readString(OtherUserProfileActivity.this, PreferenceHandler.PREF_KEY_USER_ID, "").equals(getIntent().getIntExtra("userId", 0) + ""))
            finish();
        else {
            setUpToolbar();
            setUpLayout();
            setDataInViewObjects();
            ApiControllerClass.getOtherUserProfile(retrofit, OtherUserProfileActivity.this, userId, false);
        }
    }

    @Override
    protected void setUpLayout() {
        if (getIntent() != null && getIntent().hasExtra("userId"))
            userId = getIntent().getIntExtra("userId", 0);
        Log.e("userId", userId + "");

        mivUserImage.setAdapter(multiImages);
        mivUserImage.setPageTransformer(true, new DepthPageTransformer());
        int color = Color.parseColor("#FF0000"); //The color u want
        mivbackIcon.setColorFilter(color);
        mivDotIcon.setColorFilter(color);
    }

    @Override
    protected void setDataInViewObjects() {
        mivUserImage.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                if (position == NUM_PAGES - 1 && positionOffset > 0) {
                    if (isOpaque) {
                        mivUserImage.setBackgroundColor(Color.TRANSPARENT);
                        isOpaque = false;
                    }
                } else {
                    if (!isOpaque) {
                        mivUserImage.setBackgroundColor(getResources().getColor(R.color.primary_material_light));
                        isOpaque = true;
                    }
                }
            }

            @Override
            public void onPageSelected(int position) {
                setIndicator(position);

            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });


    }

    @Override
    protected void setUpToolbar() {

    }

    public void buildCircles(int size) {
        try {
            float scale = getResources().getDisplayMetrics().density;
            int padding = (int) (5 * scale + 0.5f);


            for (int i = 0; i < size; i++) {
                ImageView circle = new ImageView(this);
                circle.setImageResource(R.drawable.login_checkbox);
                circle.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT));
                circle.setAdjustViewBounds(true);
                circle.setPadding(padding, 0, padding, 0);
                mdotsLayout.addView(circle);
            }

            setIndicator(0);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void setIndicator(int index) {
        if (index < size) {
            for (int i = 0; i < size; i++) {
                ImageView circle = (ImageView) mdotsLayout.getChildAt(i);
                if (i == index) {
                    circle.setColorFilter(getResources().getColor(android.R.color.white));
                } else {
                    circle.setColorFilter(getResources().getColor(R.color.text_medium_grey));
                }
            }
        }
    }

    public void setData(Response<OtherUserProfilePojo> response) {
        otherUserProfilePojo = response.body();
        mtvAboutDetails.setText(response.body().getUsers().getAbout());
        if (response.body().getUsers().getAge() != null)
            tvAge.setText(response.body().getUsers().getAge() + " Years");
        mtvName.setText(response.body().getUsers().getName());
        String beforeFirstDot = response.body().getUsers().getDistance().split("\\.")[0];
        tvRadius.setText(beforeFirstDot + " km away");

        if (response.body().getUsers().getIs_videocall() == 1)
            ivVideoCall.setVisibility(View.VISIBLE);
        else
            ivVideoCall.setVisibility(View.GONE);
        if (response.body().getUsers().getIs_voicecall() == 1)
            ivVoiceCall.setVisibility(View.VISIBLE);
        else
            ivVoiceCall.setVisibility(View.GONE);


        GridLayoutManager linearLayoutManager = new GridLayoutManager(OtherUserProfileActivity.this, 3);
        mrvInterestedIn.setLayoutManager(linearLayoutManager);
        // mrvInterestedIn.addItemDecoration(new GridSpacingItemDecoration(3, 20, false));

        GridLayoutManager linearLayoutManager1 = new GridLayoutManager(OtherUserProfileActivity.this, 3);
        mrvLanguageSpeaks.setLayoutManager(linearLayoutManager1);
        // mrvLanguageSpeaks.addItemDecoration(new GridSpacingItemDecoration(3, 20, false));

        mrvInterestedIn.setVisibility(View.VISIBLE);
        mrvLanguageSpeaks.setVisibility(View.VISIBLE);
        LanguageSpeakAdapter adapter = new LanguageSpeakAdapter(OtherUserProfileActivity.this, response.body().getUsers().getUser_learn(), "");
        mrvInterestedIn.setAdapter(adapter);

        LanguageAdapter adapter1 = new LanguageAdapter(OtherUserProfileActivity.this, response.body().getUsers().getUser_speak());
        mrvLanguageSpeaks.setAdapter(adapter1);

        if (response.body().getUsers().getUser_image() != null && response.body().getUsers().getUser_image().size() > 0) {
            multiImages = new MultiImages(OtherUserProfileActivity.this, response.body().getUsers().getUser_image());
            mivUserImage.setAdapter(multiImages);
            buildCircles(response.body().getUsers().getUser_image().size());
            size = response.body().getUsers().getUser_image().size();
        } else {
            mivUserImage.setBackgroundResource(R.drawable.imgpsh_dummy);
        }
        if (response.body().getUsers().getUser_country() != null && response.body().getUsers().getUser_country().getCountry_flag() != null) {
            mtvFromDetails.setText(response.body().getUsers().getUser_country().getCountry());
            Glide.with(OtherUserProfileActivity.this).load(response.body().getUsers().getUser_country().getCountry_flag()).into(mivImageCountry);
        }


        if (response.body().getUsers().getUser_block().size() > 0) {
            //openBlockMenu("Unblock");
            Log.e("size", response.body().getUsers().getUser_block().size() + "jfjg");
            type = "Unblock";
        }
    }


    @OnClick(R.id.iv_videoCall)
    public void onIvVideoCallClicked() {
        try {
            ApiControllerClass.getVideoToken(OtherUserProfileActivity.this, retrofit, PreferenceHandler.readString(OtherUserProfileActivity.this, PreferenceHandler.PREF_KEY_USER_ID, ""), otherUserProfilePojo.getUsers().getId() + "", otherUserProfilePojo.getUsers().getName() + "", otherUserProfilePojo.getUsers().getUser_image().get(0).getImage() + "");
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    @OnClick(R.id.iv_voiceCall)
    public void onIvVoiceCallClicked() {
        try {
            ApiControllerClass.sendTwilioVoiceNotification(OtherUserProfileActivity.this, retrofit, PreferenceHandler.readString(OtherUserProfileActivity.this, PreferenceHandler.PREF_KEY_USER_ID, ""), otherUserProfilePojo.getUsers().getId() + "", otherUserProfilePojo.getUsers().getName(), otherUserProfilePojo.getUsers().getUser_image().get(0).getImage());
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    @OnClick(R.id.iv_message)
    public void onIvMessageClicked() {
        try {
            Utility.goToChatActivity(OtherUserProfileActivity.this, otherUserProfilePojo.getUsers().getId(), otherUserProfilePojo.getUsers().getName(), otherUserProfilePojo.getUsers().getUser_image().get(0).getImage());
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}
