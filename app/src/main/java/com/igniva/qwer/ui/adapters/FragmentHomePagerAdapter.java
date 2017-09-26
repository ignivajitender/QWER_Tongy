package com.igniva.qwer.ui.adapters;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.igniva.qwer.ui.fragments.SpeakFragment;

public class FragmentHomePagerAdapter extends FragmentPagerAdapter {

    //integer to count number of tabs
    private CharSequence titles[];
    private boolean misFilter = false;

    //Constructor to the class
    public FragmentHomePagerAdapter(FragmentManager fm) {
        super(fm);

    }
     //Overriding method getItem
    @Override
    public Fragment getItem(int position) {
        //Returning the current tabs
        switch (position) {
            case 0:
                return SpeakFragment.newInstance();
            case 1:
                return SpeakFragment.newInstance();
            case 2:
                return SpeakFragment.newInstance();
            default:
                return SpeakFragment.newInstance();
        }
    }

    @Override
    public CharSequence getPageTitle(int position) {

        switch (position) {
            case 0:
                return "SPEAKS";
            case 1:
                return "LEARN";
            case 2:
                return "ABOUT";
        }
        return null;
    }

    //Overriden method getCount to get the number of tabs
    @Override
    public int getCount() {
        return 3;
    }


}