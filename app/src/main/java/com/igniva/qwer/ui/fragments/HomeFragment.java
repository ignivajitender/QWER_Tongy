package com.igniva.qwer.ui.fragments;

import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.igniva.qwer.R;
import com.igniva.qwer.ui.adapters.CardsDataAdapter;
import com.wenchao.cardstack.CardStack;

import butterknife.ButterKnife;


/**
 * Created by igniva-php-08 on 18/5/16.
 */
public class HomeFragment extends BaseFragment  {

    View mView;
    private CardStack mCardStack;
    private CardsDataAdapter mCardAdapter;
    ViewPager viewPager;


    public static HomeFragment newInstance() {
        HomeFragment fragment = new HomeFragment();
        return fragment;
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        mView = inflater.inflate(R.layout.activity_test, container, false);
        ButterKnife.bind(this, mView);
        setUpLayout();
        setDataInViewObjects();
        return mView;
    }


    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    public void setUpLayout() {
        mCardStack = (CardStack) mView.findViewById(R.id.container);


        mCardStack.setContentResource(R.layout.card_content);
        mCardStack.setStackMargin(20);

        mCardAdapter = new CardsDataAdapter(getActivity());
        mCardAdapter.add("test1");
        mCardAdapter.add("test2");
        mCardAdapter.add("test3");
        mCardAdapter.add("test4");
        mCardAdapter.add("test5");
        mCardAdapter.add("test6");
        mCardAdapter.add("test7");

        mCardStack.setAdapter(mCardAdapter);


        if (mCardStack.getAdapter() != null) {
            Log.i("MyActivity", "Card Stack size: " + mCardStack.getAdapter().getCount());
        }
    }

    @Override
    public void setDataInViewObjects() {
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }


}