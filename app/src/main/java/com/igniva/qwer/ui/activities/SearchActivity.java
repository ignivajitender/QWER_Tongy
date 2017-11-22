package com.igniva.qwer.ui.activities;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.AutoCompleteTextView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.igniva.qwer.R;
import com.igniva.qwer.controller.ApiInterface;
import com.igniva.qwer.model.PostDetailPojo;
import com.igniva.qwer.model.PostPojo;
import com.igniva.qwer.ui.adapters.RecyclerviewAdapter;
import com.igniva.qwer.utils.EndlessRecyclerViewScrollListener;
import com.igniva.qwer.utils.Global;
import com.igniva.qwer.utils.Utility;

import java.util.ArrayList;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class SearchActivity extends AppCompatActivity {

    private static final String LOG_TAG = "SearchActivity";

    @BindView(R.id.tv_tap_to_rename)
    TextView mtvTitle;

    @OnClick(R.id.ivbackIcon)
    public void back(){
        Utility.hideSoftKeyboard(SearchActivity.this);
        onBackPressed();
    }

    @Inject
    Retrofit retrofit;
    @BindView(R.id.autoCompleteSearch)
    AutoCompleteTextView mautoCompleteSearch;

    RecyclerviewAdapter adapter;
    @BindView(R.id.recyclerRants)
    RecyclerView mrecyclerView;
    @BindView(R.id.tvNoData)
   public TextView mtvNoData;

     Boolean isLast = false;
    public int pageNo = 1;
    int mListType;
    @BindView(R.id.llClear)
    LinearLayout mllClear;


    // Store a member variable for the listener
    private EndlessRecyclerViewScrollListener scrollListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        ((Global) getApplication()).getNetComponent().inject(this);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
         ButterKnife.bind(this);
        setUpToolbar();
        setUpLayout();
        setDataInViewObjects();
    }

     protected void setDataInViewObjects() {
        //mautoCompleteSearch.performClick();
        mautoCompleteSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                try {
                    if (mautoCompleteSearch.getText().toString().trim().length() > 0) {
                        callSearchApi(mautoCompleteSearch.getText().toString().trim());
                    }
                    else if(mautoCompleteSearch.getText().toString().trim().length() == 0){
                        setData(null);
                    }



                } catch (Exception e) {
                    Log.e(LOG_TAG, e.getMessage());
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }


        });
        mListType=5;
        Utility.onChangeClearButtonVisible(SearchActivity.this,mautoCompleteSearch , mllClear);
    }

     protected void setUpToolbar() {
        mtvTitle.setText(getResources().getString(R.string.search));
    }

    private void callSearchApi(String searchString) {

        // Show loading progress dialog
        //CallProgressWheel.showLoadingDialog(SearchActivity.this, "Loading...");
        //Create a retrofit call object
        Call<PostPojo> posts = retrofit.create(ApiInterface.class).getSearchResults(searchString);
        //Enque the call
        posts.enqueue(new Callback<PostPojo>() {
            @SuppressLint("LongLogTag")
            @Override
            public void onResponse(Call<PostPojo> call, Response<PostPojo> response) {
                try {

                    if (response.body().getStatus() == 200) {
                        //CallProgressWheel.dismissLoadingDialog();
                        //Utility.showToastMessageShort(SearchActivity.this, response.body().getDescription());
                        setData(response.body().getData());
                    }
                    else
                    {
                       setData(null);
                    }


                } catch (Exception e) {
                    e.printStackTrace();
                    //CallProgressWheel.dismissLoadingDialog();
                }
            }

            @Override
            public void onFailure(Call<PostPojo> call, Throwable t) {

                try {
                    Log.d("error", t.toString());
                    //Utility.showToastMessageShort(SearchActivity.this, getResources().getString(R.string.unknown_error));
                } catch (Exception e) {
                    e.printStackTrace();
                }
                // CallProgressWheel.dismissLoadingDialog();
            }
        });


    }

     protected void setUpLayout() {

        LinearLayoutManager mLayoutManager = new LinearLayoutManager(SearchActivity.this);
        mrecyclerView.setLayoutManager(mLayoutManager);
//        mrecyclerView.setItemAnimator(new DefaultItemAnimator());
        scrollListener = new EndlessRecyclerViewScrollListener(mLayoutManager) {
            @Override
            public void onLoadMore(int page, int totalItemsCount, RecyclerView view) {
                // Triggered only when new data needs to be appended to the list
                // Add whatever code is needed to append new items to the bottom of the list
                if (!isLast)
                    callSearchApi(mautoCompleteSearch.getText().toString().trim());
            }
        };
        // Adds the scroll listener to RecyclerView
        mrecyclerView.addOnScrollListener(scrollListener);



        //callSearchApi(mautoCompleteSearch.getText().toString().trim());

    }

    public void setData(PostPojo.PostDataPojo data){
        if (data != null && data.getData() != null && data.getData().size() > 0 && mautoCompleteSearch!=null && mautoCompleteSearch.getText().toString().trim().length()>0) {
             pageNo++;
             if (pageNo >= data.getLast_page())
                isLast = true;
//             if (adapter == null)
//             {
                adapter = new RecyclerviewAdapter(SearchActivity.this, mListType, (ArrayList<PostDetailPojo.DataPojo>)data.getData(),retrofit);
                mrecyclerView.setAdapter(adapter);
//            } else
//                adapter.addAll(data.getData());
            mrecyclerView.setVisibility(View.VISIBLE);
            mtvNoData.setVisibility(View.GONE);
        } else {
            mrecyclerView.setVisibility(View.GONE);
            if(mautoCompleteSearch!=null && mautoCompleteSearch.getText().toString().trim().length()>0)
            mtvNoData.setVisibility(View.VISIBLE);
            isLast = true;
            //Utility.showToastMessageLong(getActivity(), "No data");
        }
    }
}
