package com.igniva.qwer.ui.activities;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.igniva.qwer.R;
import com.igniva.qwer.ui.adapters.FragmentAdapterClass;
import com.igniva.qwer.ui.fragments.PostsListFragment;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by tanmey on 10/11/17.
 */

public class MyFavActivity extends BaseActivity {
     FragmentAdapterClass fragmentAdapter;
    @BindView(R.id.ivbackIcon)
    ImageView ivbackIcon;
    @BindView(R.id.tv_tap_to_rename)
    TextView tvTapToRename;
    @BindView(R.id.framelayout_main)
    FrameLayout frameLayout;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.my_fav_list);
        ButterKnife.bind(this);
        setUpLayout();
    }

    @Override
    protected void setUpLayout() {

        tvTapToRename.setText(getString(R.string.my_favourites));

replaceFragment(PostsListFragment.newInstance(R.string.my_favourites));

     }
    private void replaceFragment(Fragment fragment) {
         FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        ft.replace(R.id.framelayout_main, fragment);
        ft.commit();
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
