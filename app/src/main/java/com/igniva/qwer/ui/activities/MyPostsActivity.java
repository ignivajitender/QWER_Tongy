package com.igniva.qwer.ui.activities;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.widget.ImageView;
import android.widget.TextView;

import com.igniva.qwer.R;
import com.igniva.qwer.ui.adapters.FragmentAdapterClass;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by tanmey on 10/11/17.
 */

public class MyPostsActivity extends BaseActivity {
    TabLayout tabLayout;
    ViewPager viewPager;
    FragmentAdapterClass fragmentAdapter;
    @BindView(R.id.ivbackIcon)
    ImageView ivbackIcon;
    @BindView(R.id.tv_tap_to_rename)
    TextView tvTapToRename;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.my_post_list);
        ButterKnife.bind(this);
        setUpLayout();
    }

    @Override
    protected void setUpLayout() {
        tabLayout = (TabLayout) findViewById(R.id.tab_layout1);
        viewPager = (ViewPager) findViewById(R.id.pager1);

        tvTapToRename.setText(getString(R.string.my_uploads));
        tabLayout.addTab(tabLayout.newTab().setText(getString(R.string.upcomming)));
        tabLayout.addTab(tabLayout.newTab().setText(getString(R.string.ongoing)));
        tabLayout.addTab(tabLayout.newTab().setText(getString(R.string.archives)));

        tabLayout.setTabGravity(TabLayout.GRAVITY_FILL);

        fragmentAdapter = new FragmentAdapterClass(getSupportFragmentManager());
        viewPager.setOffscreenPageLimit(3);
        viewPager.setAdapter(fragmentAdapter);

        viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));

        tabLayout.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab LayoutTab) {
                viewPager.setCurrentItem(LayoutTab.getPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab LayoutTab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab LayoutTab) {

            }
        });
    }


    @Override
    protected void setDataInViewObjects() {

    }

    @Override
    protected void setUpToolbar() {

    }

    @OnClick(R.id.ivbackIcon)
    public void onViewClicked() {
        onBackPressed();
    }
}
