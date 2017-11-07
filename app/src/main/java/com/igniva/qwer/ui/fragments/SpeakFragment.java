package com.igniva.qwer.ui.fragments;

import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.igniva.qwer.R;
import com.igniva.qwer.ui.views.TextViewRegular;

import butterknife.BindView;
import butterknife.ButterKnife;


/**
 * Created by igniva-php-08 on 18/5/16.
 */
public class SpeakFragment extends BaseFragment {

    View mView;
    @BindView(R.id.tv_language_beginner)
    TextViewRegular mTvLanguageBeginner;
    @BindView(R.id.ll_beginner)
    LinearLayout mLlBeginner;
    @BindView(R.id.tv_language_Intermediate)
    TextViewRegular mTvLanguageIntermediate;
    @BindView(R.id.ll_intermediate)
    LinearLayout mLlIntermediate;
    @BindView(R.id.tv_language_Professional)
    TextViewRegular mTvLanguageProfessional;
    @BindView(R.id.ll_professional)
    LinearLayout mLlProfessional;
    @BindView(R.id.ll_Speaks)
    LinearLayout mLlSpeaks;


    public static SpeakFragment newInstance() {
        SpeakFragment fragment = new SpeakFragment();
        return fragment;
    }
    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        mView = inflater.inflate(R.layout.fragment_main_below, container, false);
        ButterKnife.bind(this, mView);
//        setUpLayout();
//        setDataInViewObjects();
         return mView;
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    public void setUpLayout() {

    }

    @Override
    public void setDataInViewObjects() {
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }


}