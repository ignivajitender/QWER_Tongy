package com.igniva.qwer.ui.fragments;

import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.igniva.qwer.R;
import com.igniva.qwer.controller.ApiInterface;
import com.igniva.qwer.model.UsersResponsePojo;
import com.igniva.qwer.ui.adapters.CardsDataAdapter;
import com.igniva.qwer.ui.views.CallProgressWheel;
import com.igniva.qwer.utils.Global;
import com.igniva.qwer.utils.Utility;
import com.wenchao.cardstack.CardStack;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Retrofit;


/**
 * Created by igniva-php-08 on 18/5/16.
 */
public class HomeFragment extends BaseFragment  {

    View mView;
    private CardStack mCardStack;
    private CardsDataAdapter mCardAdapter;
    ViewPager viewPager;
    @Inject
    Retrofit retrofit;
    int pageNo=1;
    @BindView(R.id.tvNoData)
    TextView mtvNoData;

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
        getUsersApi();
        return mView;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        ((Global) getActivity().getApplicationContext()).getNetComponent().inject(this);
    }
    private void getUsersApi() {

        try {
            if (Utility.isInternetConnection(getActivity())) {

                CallProgressWheel.showLoadingDialog(getActivity(), "Loading...");

                Call<UsersResponsePojo> posts = null;
                posts = retrofit.create(ApiInterface.class).getUsers(40.120749,75.860596,pageNo);

                if (posts != null)
                    posts.enqueue(new retrofit2.Callback<UsersResponsePojo>() {
                        @Override
                        public void onResponse(Call<UsersResponsePojo> call, retrofit2.Response<UsersResponsePojo> response) {
                            if (response.body().getStatus() == 200) {
                                CallProgressWheel.dismissLoadingDialog();
                                setDatainCards(response.body().getUsers());
                                  // setDataInViewObjects(response.body().getData());
                                //callSuccessPopUp((Activity)context, response.body().getDescription());
                                // Utility.showToastMessageShort(ChangePasswordActivity.this,responsePojo.getDescription());
                            } else {
                               /* if (fragment != null)
                                    fragment.setDataInViewObjects(null);
*/                              mtvNoData.setVisibility(View.VISIBLE);
                                mCardStack.setVisibility(View.GONE);
                                CallProgressWheel.dismissLoadingDialog();

                                //Utility.showToastMessageShort((Activity) context, response.body().getDescription());
                            }
                        }


                        @Override
                        public void onFailure(Call<UsersResponsePojo> call, Throwable t) {
                           /* if (fragment != null)
                                fragment.setDataInViewObjects(null);
*/
                            CallProgressWheel.dismissLoadingDialog();
                        }
                    });
            }


        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    private void setDatainCards(final UsersResponsePojo.UsersPojo users) {

        mCardStack = (CardStack) mView.findViewById(R.id.container);
        mCardStack.setContentResource(R.layout.card_content);
        mCardStack.setStackMargin(20);
        mCardAdapter = new CardsDataAdapter(getActivity(),getFragmentManager(),users.getData(),retrofit);
        mCardStack.setAdapter(mCardAdapter);

    }


    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    public void setUpLayout() {


        /* mCardStack = (CardStack) mView.findViewById(R.id.container);
        mCardStack.setContentResource(R.layout.card_content);
        mCardStack.setStackMargin(20);


        //   mCardAdapter = new CardsDataAdapter(getActivity(),getFragmentManager(),);
      *//*  mCardAdapter.add("test1");
        mCardAdapter.add("test2");
        mCardAdapter.add("test3");
        mCardAdapter.add("test4");
        mCardAdapter.add("test5");
        mCardAdapter.add("test6");
        mCardAdapter.add("test7");*//*

        //mCardStack.setAdapter(mCardAdapter);


        if (mCardStack.getAdapter() != null) {
            Log.i("MyActivity", "Card Stack size: " + mCardStack.getAdapter().getCount());
        }*/
    }

    @Override
    public void setDataInViewObjects() {
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }


}