package com.igniva.qwer.ui.activities;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.facebook.drawee.backends.pipeline.Fresco;
import com.facebook.drawee.view.SimpleDraweeView;
import com.igniva.qwer.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static com.igniva.qwer.utils.Constants.ALPHA_ANIMATIONS_DURATION;
import static com.igniva.qwer.utils.Constants.PERCENTAGE_TO_HIDE_TITLE_DETAILS;
import static com.igniva.qwer.utils.Constants.PERCENTAGE_TO_SHOW_TITLE_AT_TOOLBAR;


/**
 * Created by karanveer on 1/9/17.
 */

public class PrivacyPolicyActivity extends BaseActivity implements AppBarLayout.OnOffsetChangedListener {

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
    @BindView(R.id.tv_heading)
    TextView mTvHeading;
    @BindView(R.id.back_arrow)
    ImageView mBackArrow;

    private boolean mIsTheTitleVisible = false;
    private boolean mIsTheTitleContainerVisible = true;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Fresco.initialize(this);
        setContentView(R.layout.privacy_policy);
        ButterKnife.bind(this);
        setUpLayout();

    }

    @Override
    public void setUpLayout() {
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

    @OnClick(R.id.back_arrow)
    public void onViewClicked() {
        finish();
    }
}
