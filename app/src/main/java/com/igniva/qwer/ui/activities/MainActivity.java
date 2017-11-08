package com.igniva.qwer.ui.activities;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.igniva.qwer.R;
import com.igniva.qwer.ui.adapters.FragmentViewPagerAdapter;
import com.igniva.qwer.ui.fragments.HomeFragment;
import com.igniva.qwer.utils.fcm.Constants;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;


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
    @BindView(R.id.tabHost)
    LinearLayout mTabHost;
    @BindView(R.id.framelayout_main)
    FrameLayout framelayoutMain;
    private TabLayout tab_layout;
    FragmentViewPagerAdapter pagerAdapter;
    private Fragment currentFrag;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        setUpLayout();
    }


    @Override
    protected void setUpLayout() {
        replaceFragment(new HomeFragment());
        tab_layout = (TabLayout) findViewById(R.id.tab_layout);
        tab_layout.addTab(tab_layout.newTab().setIcon(R.drawable.news_feeds));
        tab_layout.addTab(tab_layout.newTab().setIcon(R.drawable.home));
        tab_layout.addTab(tab_layout.newTab().setIcon(R.drawable.connections));
        tab_layout.setTabGravity(TabLayout.GRAVITY_FILL);
        //tab_layout.setupWithViewPager(mViewPager1);

        tab_layout.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                tab.select();
             }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
        tab_layout.setScrollPosition(1,0f,true);
//        mViewPager1.setCurrentItem(1);

    }

    @Override
    protected void setDataInViewObjects() {

    }

    @Override
    protected void setUpToolbar() {

    }

    @OnClick({R.id.cross_icon, R.id.edit_pref_icon})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.cross_icon:
                Intent intentAbout = new Intent(MainActivity.this, SettingsActivity.class);
                startActivity(intentAbout);
                break;
            case R.id.edit_pref_icon:
                Intent intent = new Intent(MainActivity.this, SetPreferrencesActivity.class);
                intent.putExtra(Constants.TO_EDIT_PREFERENCES, "Yes");
                startActivity(intent);

            default:
                break;
        }
    }

    private boolean doubleBackToExitPressedOnce;
    private Handler mHandler = new Handler();

    private final Runnable mRunnable = new Runnable() {
        @Override
        public void run() {
            doubleBackToExitPressedOnce = false;
        }
    };


    @Override
    public void onBackPressed() {
        if (doubleBackToExitPressedOnce) {
            super.onBackPressed();
            return;
        }

        this.doubleBackToExitPressedOnce = true;
        Toast.makeText(this, "Press back again to exit", Toast.LENGTH_SHORT).show();

        mHandler.postDelayed(mRunnable, 2000);
    }

    private void replaceFragment(Fragment fragment) {
        currentFrag = fragment;
          FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        ft.replace(R.id.framelayout_main, fragment);
        ft.commit();


    }

}



