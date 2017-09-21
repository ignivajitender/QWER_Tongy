package com.igniva.qwer.ui.activities;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.Toolbar;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.igniva.qwer.R;
import com.igniva.qwer.ui.adapters.FragmentViewPagerAdapter;

import butterknife.BindView;
import butterknife.ButterKnife;


public class MainActivity extends BaseActivity {


    @BindView(R.id.tab_layout)
    TabLayout mTabLayout;
    @BindView(R.id.cross_icon)
    ImageView mCrossIcon;
    @BindView(R.id.tv_toolbar)
    TextView mTvToolbar;
    @BindView(R.id.tv_tap_to_rename)
    TextView mTvTapToRename;
    @BindView(R.id.ll_change_title)
    LinearLayout mLlChangeTitle;
    @BindView(R.id.notification_icon)
    ImageView mNotificationIcon;
    @BindView(R.id.edit_pref_icon)
    ImageView mEditPrefIcon;
    @BindView(R.id.toolbar_top)
    Toolbar mToolbarTop;
    @BindView(R.id.view_pager1)
    ViewPager mViewPager1;
    @BindView(R.id.tabHost)
    LinearLayout mTabHost;
    private TabLayout tab_layout;
    FragmentViewPagerAdapter pagerAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        setUpLayout();
    }


    @Override
    protected void setUpLayout() {
        pagerAdapter = new FragmentViewPagerAdapter(getSupportFragmentManager());
        mViewPager1.setAdapter(pagerAdapter);
        tab_layout = (TabLayout) findViewById(R.id.tab_layout);
        tab_layout.addTab(tab_layout.newTab().setIcon(R.drawable.news_feeds));
        tab_layout.addTab(tab_layout.newTab().setIcon(R.drawable.home));
        tab_layout.addTab(tab_layout.newTab().setIcon(R.drawable.connections));
        tab_layout.setTabGravity(TabLayout.GRAVITY_FILL);
//        tab_layout.setupWithViewPager(mViewPager);

        tab_layout.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
//                mViewPager.setCurrentItem(tab.getPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });

    }

    @Override
    protected void setDataInViewObjects() {

    }

    @Override
    protected void setUpToolbar() {

    }
}



