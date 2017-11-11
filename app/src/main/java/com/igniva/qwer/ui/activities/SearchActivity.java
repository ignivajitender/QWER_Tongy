package com.igniva.qwer.ui.activities;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.AutoCompleteTextView;
import android.widget.TextView;

import com.igniva.qwer.R;
import com.igniva.qwer.controller.ApiInterface;
import com.igniva.qwer.model.PostPojo;
import com.igniva.qwer.ui.adapters.NewsFeedAdapter;
import com.igniva.qwer.ui.views.CallProgressWheel;
import com.igniva.qwer.utils.Global;
import com.igniva.qwer.utils.Utility;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class SearchActivity extends BaseActivity {

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

    NewsFeedAdapter adapter;
    @BindView(R.id.recyclerRants)
    RecyclerView mrecyclerView;
    @BindView(R.id.tvNoData)
    TextView mtvNoData;



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

    @Override
    protected void setDataInViewObjects() {
        mautoCompleteSearch.performClick();
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


                } catch (Exception e) {
                    Log.e(LOG_TAG, e.getMessage());
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }


        });


    }

    @Override
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
                       // Utility.showToastMessageLong(SearchActivity.this, response.body().getDescription());
                        if(response.body()!=null && response.body().getData()!=null && response.body().getData().getData().size()>0)
                        {
                            mrecyclerView.setVisibility(View.VISIBLE);
                            adapter = new NewsFeedAdapter(SearchActivity.this, "search",response.body().getData().getData(),retrofit);
                            mrecyclerView.setAdapter(adapter);

                        }
                        else
                        {
                            mtvNoData.setVisibility(View.VISIBLE);
                            mrecyclerView.setVisibility(View.GONE);
                        }

                    } else {
                        mtvNoData.setVisibility(View.VISIBLE);
                        mtvNoData.setText(response.body().getDescription());
                        //Utility.showToastMessageLong(SearchActivity.this, response.body().getMessage());
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
                CallProgressWheel.dismissLoadingDialog();
            }
        });


    }

    @Override
    protected void setUpLayout() {
        LinearLayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
        mrecyclerView.setLayoutManager(mLayoutManager);

    }
}
