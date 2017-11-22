package com.igniva.qwer.ui.fragments;

import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.github.clans.fab.FloatingActionMenu;
import com.igniva.qwer.R;
import com.igniva.qwer.controller.ApiControllerClass;
import com.igniva.qwer.model.ConnectionPojo;
import com.igniva.qwer.model.PostDetailPojo;
import com.igniva.qwer.ui.adapters.ConnectionRecyclerviewAdapter;
import com.igniva.qwer.utils.EndlessRecyclerViewScrollListener;
import com.igniva.qwer.utils.Global;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Retrofit;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * <p>
 * create an instance of this fragment.
 */
public class ConnectionsFragment extends BaseFragment {

    public int pageNo = 1;
    View mView;
    @Inject
    public
    Retrofit retrofit;
    ConnectionRecyclerviewAdapter adapter;
    List<PostDetailPojo.DataPojo> postList = null;
    @BindView(R.id.recyclerView)
    RecyclerView mrecyclerView;
    int mListType;


    @BindView(R.id.swipe_refresh_layout)
    SwipeRefreshLayout swipeRefreshLayout;
    Boolean isLast = false;
    @BindView(R.id.tvNoData)
    TextView mtvNoData;
    @BindView(R.id.menu)
    FloatingActionMenu menuFloating;



    // Store a member variable for the listener
    private EndlessRecyclerViewScrollListener scrollListener;

    public static ConnectionsFragment newInstance() {
        ConnectionsFragment fragment = new ConnectionsFragment();

        return fragment;
    }


    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        mView = inflater.inflate(R.layout.fragment_news_feed, container, false);
        ButterKnife.bind(this, mView);
        setUpLayout();

        return mView;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        ((Global) getActivity().getApplicationContext()).getNetComponent().inject(this);
    }

    @Override
    public void setUpLayout() {
        menuFloating.setVisibility(View.GONE);
        LinearLayoutManager mLayoutManager = new LinearLayoutManager(getActivity());
        mrecyclerView.setLayoutManager(mLayoutManager);
        mrecyclerView.setItemAnimator(new DefaultItemAnimator());
        scrollListener = new EndlessRecyclerViewScrollListener(mLayoutManager) {
            @Override
            public void onLoadMore(int page, int totalItemsCount, RecyclerView view) {
                // Triggered only when new data needs to be appended to the list
                // Add whatever code is needed to append new items to the bottom of the list
                if (!isLast)
                    getConnections();
            }
        };
        // Adds the scroll listener to RecyclerView
        mrecyclerView.addOnScrollListener(scrollListener);

        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                // Refresh items
                swipeRefreshLayout.setRefreshing(false);
                pageNo = 1;
                isLast = false;
                scrollListener.resetState();
                adapter = null;
                getConnections();
            }
        });

        getConnections();
    }

    private void getConnections() {
        ApiControllerClass.getMyConnectionsApi(retrofit, getActivity(), ConnectionsFragment.this);
    }

    @Override
    public void setDataInViewObjects() {


    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();

    }

    public void setDataInViewObjects(final ConnectionPojo responsePojo) {


        if (responsePojo != null && responsePojo.getData() != null && responsePojo.getData().getData().size() > 0) {

            pageNo++;

            if (pageNo >= responsePojo.getData().getLast_page())
                isLast = true;

            if (adapter == null) {
                adapter = new ConnectionRecyclerviewAdapter(getActivity(), (ArrayList<ConnectionPojo.ConnectionDataPojo.ContactDataPojo>) responsePojo.getData().getData(), retrofit);
                mrecyclerView.setAdapter(adapter);
            } else
                adapter.addAll((List<ConnectionPojo.ConnectionDataPojo.ContactDataPojo>) responsePojo.getData());
            mrecyclerView.setVisibility(View.VISIBLE);
            mtvNoData.setVisibility(View.GONE);
        } else {
            mrecyclerView.setVisibility(View.GONE);
            mtvNoData.setVisibility(View.VISIBLE);
            isLast = true;
            //Utility.showToastMessageLong(getActivity(), "No data");
        }

    }

    @Override
    public void onResume() {
        super.onResume();
       // menuFloating.close(true);
    }
}