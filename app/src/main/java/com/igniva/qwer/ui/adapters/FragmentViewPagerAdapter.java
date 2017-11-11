package com.igniva.qwer.ui.adapters;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.igniva.qwer.R;
import com.igniva.qwer.ui.fragments.HomeFragment;
import com.igniva.qwer.ui.fragments.PostsListFragment;


public class FragmentViewPagerAdapter extends FragmentPagerAdapter {
    public FragmentViewPagerAdapter(FragmentManager fm) {
        super(fm);
    }

    public Fragment getItem(int fragment_number) {
        switch (fragment_number) {
            case 0:
                return PostsListFragment.newInstance(R.string.news_feed);
            case 1:
                return new HomeFragment();
            case 2:
                return new Fragment();
            default:
                return null;
        }

    }

    @Override
    public int getCount() {
        return 3;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        switch (position) {
            case 0:
                return "Upcoming Bookings";
            case 1:
                return "Past Bookings";
            default:
                return null;
        }
    }

}
    /*
    * It doesn't matter the color of the icons, but they must have solid colors
    */
//    private Drawable getIcon(int position) {
//        switch(position) {
//            case 0:
//                return res.getDrawable(R.drawable.ic_person_black_24dp);
//            case 1:
//                return res.getDrawable(R.drawable.ic_group_black_24dp);
//            case 2:
//                return res.getDrawable(R.drawable.ic_notifications_off_white_24dp);
//        }
//        return null;
//    }