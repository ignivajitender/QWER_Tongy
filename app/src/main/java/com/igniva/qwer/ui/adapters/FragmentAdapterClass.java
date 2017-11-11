package com.igniva.qwer.ui.adapters;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.igniva.qwer.R;
import com.igniva.qwer.ui.fragments.PostsListFragment;

import java.util.ArrayList;

public class FragmentAdapterClass extends FragmentStatePagerAdapter {

    int NumbOfTabs = 3; // Store the number of tabs, this will also be passed when the FragmentAdapterClass is created
    ArrayList<Fragment> fragArray = null;

    // Build a Constructor and assign the passed Values to appropriate values in the class
    public FragmentAdapterClass(FragmentManager fm) {
        super(fm);
        if(fragArray==null)
        fragArray = new ArrayList<>();

    }

    //This method return the fragment for the every position in the View Pager
    @Override
    public Fragment getItem(int position) {
//
        if(position==0 && fragArray.size()==0){
            fragArray.add(PostsListFragment.newInstance(R.string.upcomming));
        }else if(position==1 &&  fragArray.size()==1)
            fragArray.add(PostsListFragment.newInstance(R.string.ongoing));
        else if(position==2 && fragArray.size()==2)
            fragArray.add(PostsListFragment.newInstance(R.string.archives));


        return fragArray.get(position);


//        switch (position) {
//            case 0:
//                return PostsListFragment.newInstance(R.string.upcomming);
//
//            case 1:
//                return PostsListFragment.newInstance(R.string.ongoing);
//
//            case 2:
//                return PostsListFragment.newInstance(R.string.archives);
//
//            default:
//                return null;
//        }

    }


    // This method return the Number of tabs for the tabs Strip

    @Override
    public int getCount() {
        return NumbOfTabs;
    }
}