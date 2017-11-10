package com.igniva.qwer.ui.fragments;

import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.igniva.qwer.R;
import com.igniva.qwer.controller.ApiControllerClass;
import com.igniva.qwer.model.PostPojo;
import com.igniva.qwer.ui.activities.CreateNewPostActivity;
import com.igniva.qwer.ui.adapters.NewsFeedAdapter;
import com.igniva.qwer.utils.Global;
import com.igniva.qwer.utils.Utility;

import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit2.Retrofit;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the

 * create an instance of this fragment.
 */
public class NewsFeedFragment extends BaseFragment {

    View mView;
    @Inject
    public
    Retrofit retrofit;
    @OnClick({R.id.fabCreatePost, R.id.fabMyUploads,R.id.fabMyFavorites})
    public void onViewClicked(View view) {
    switch (view.getId()){
        case R.id.fabCreatePost:
            //Utility.showToastMessageLong(getActivity(),"clicked create post");
            startActivity(new Intent(getActivity(), CreateNewPostActivity.class));
            break;
        case R.id.fabMyUploads:
            Utility.showToastMessageLong(getActivity(),"clicked my uploads");
            break;
        case R.id.fabMyFavorites:
            Utility.showToastMessageLong(getActivity(),"clicked my favorites");
            break;
    }



    }

    NewsFeedAdapter adapter;
    List<PostPojo.PostDataPojo.DataBean> postList = null;
    @BindView(R.id.recyclerView)
    RecyclerView mrecyclerView;

    public static NewsFeedFragment newInstance() {
        NewsFeedFragment fragment = new NewsFeedFragment();
        return fragment;
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        mView = inflater.inflate(R.layout.fragment_news_feed, container, false);
        ButterKnife.bind(this, mView);
        setUpLayout();
        ApiControllerClass.getAllFeedsApi(retrofit,getActivity(),NewsFeedFragment.this);
        return mView;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        ((Global) getActivity().getApplicationContext()).getNetComponent().inject(this);
    }

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

    public void setDataInViewObjects(final PostPojo.PostDataPojo responsePojo) {
        postList=responsePojo.getData();
        LinearLayoutManager mLayoutManager = new LinearLayoutManager(getActivity());
        mrecyclerView.setLayoutManager(mLayoutManager);
        mrecyclerView.setItemAnimator(new DefaultItemAnimator());
        Log.e("","postList----"+postList.size());
        adapter = new NewsFeedAdapter(getActivity(),postList,retrofit);
        mrecyclerView.setAdapter(adapter);
    }

}
