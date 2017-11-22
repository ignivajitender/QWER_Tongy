package com.igniva.qwer.ui.activities;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.Toolbar;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.igniva.qwer.R;
import com.igniva.qwer.ui.adapters.MultiImages;
import com.igniva.qwer.utils.DepthPageTransformer;
import com.igniva.qwer.utils.Global;

import java.util.ArrayList;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Retrofit;

public class OtherUserProfileActivity extends BaseActivity {


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
    public int NUM_PAGES=4;
    boolean isOpaque = true;
   @Override
    protected void onCreate(Bundle savedInstanceState) {
        ((Global) getApplication()).getNetComponent().inject(this);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_other_user_profile);
        ButterKnife.bind(this);
        setUpToolbar();
        setUpLayout();
        setDataInViewObjects();
    }

    @Override
    protected void setUpLayout() {
        mivUserImage.setAdapter(multiImages);
        mivUserImage.setPageTransformer(true, new DepthPageTransformer());
        //if(imagesList.size()>0) {
            multiImages = new MultiImages(OtherUserProfileActivity.this,mImagesSlidingArray,"");
            mivUserImage.setAdapter(multiImages);
        //}

       buildCircles();

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

    private void buildCircles() {
        try {
            float scale = getResources().getDisplayMetrics().density;
            int padding = (int) (5 * scale + 0.5f);



            for (int i = 0; i < NUM_PAGES; i++) {
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
    private void setIndicator(int index) {
        if (index < NUM_PAGES) {
            for (int i = 0; i < NUM_PAGES; i++) {
                ImageView circle = (ImageView) mdotsLayout.getChildAt(i);
                if (i == index) {
                    circle.setColorFilter(getResources().getColor(R.color.colorPrimaryDark));
                } else {
                    circle.setColorFilter(getResources().getColor(R.color.text_medium_grey));
                }
            }
        }
    }
}
