package com.igniva.qwer.ui.fragments;

import android.annotation.SuppressLint;
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
import com.igniva.qwer.controller.ApiControllerClass;
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
import retrofit2.Response;
import retrofit2.Retrofit;


/**
 * Created by igniva-php-08 on 18/5/16.
 */
public class HomeFragment extends BaseFragment {

    View mView;
    private CardStack mCardStack;
    private CardsDataAdapter mCardAdapter;
    ViewPager viewPager;
    @Inject
    Retrofit retrofit;
    int pageNo = 1;
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
        getUsersApi(pageNo);
        return mView;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        ((Global) getActivity().getApplicationContext()).getNetComponent().inject(this);
    }

    private void getUsersApi(int pageNo) {

        try {
            if (Utility.isInternetConnection(getActivity())) {

                CallProgressWheel.showLoadingDialog(getActivity(), "Loading...");

                Call<UsersResponsePojo> posts = null;
                posts = retrofit.create(ApiInterface.class).getUsers(Utility.latitude, Utility.longitude, pageNo);

                if (posts != null)
                    posts.enqueue(new retrofit2.Callback<UsersResponsePojo>() {
                        @Override
                        public void onResponse(Call<UsersResponsePojo> call, retrofit2.Response<UsersResponsePojo> response) {
                            if (response.body().getStatus() == 200) {
                                CallProgressWheel.dismissLoadingDialog();
                                setDatainCards(response);
                                // setDataInViewObjects(response.body().getData());
                                //callSuccessPopUp((Activity)context, response.body().getDescription());
                                // Utility.showToastMessageShort(ChangePasswordActivity.this,responsePojo.getDescription());
                            } else {
                               /* if (fragment != null)
                                    fragment.setDataInViewObjects(null);
*/
                                mtvNoData.setVisibility(View.VISIBLE);
                                //  mCardStack.setVisibility(View.GONE);
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

    @SuppressLint("ClickableViewAccessibility")
    private void setDatainCards(final Response<UsersResponsePojo> responsePojo) {

        mCardStack = (CardStack) mView.findViewById(R.id.container);
        mCardStack.setContentResource(R.layout.card_content);
        mCardStack.setStackMargin(20);
        mCardAdapter = new CardsDataAdapter(getActivity(), getFragmentManager(), responsePojo.body().getUsers().getData(), retrofit,HomeFragment.this);
        mCardStack.setAdapter(mCardAdapter);


        mCardStack.setListener(new CardStack.CardEventListener() {
            @Override
            public boolean swipeEnd(int section, float distance) {
//                if (section == 0)
//
//                    return true;
//                else
//                    return false;
                return true;
            }

            @Override
            public boolean swipeStart(int section, float distance) {
                return true;
            }

            @Override
            public boolean swipeContinue(int section, float distanceX, float distanceY) {
                return true;
            }

            @Override
            public void discarded(int mIndex, int direction) {
                int swiped_card_postion = mIndex - 1;

                //getting the string attached with the card

                String swiped_card_text = responsePojo.body().getUsers().getData().get(swiped_card_postion).getName();

                if (direction == 1) {

                   // Toast.makeText(getApplicationContext(), swiped_card_text + " Swipped to Right", Toast.LENGTH_SHORT).show();

                } else if (direction == 0) {

                   // Toast.makeText(getApplicationContext(), swiped_card_text + " Swipped to Left", Toast.LENGTH_SHORT).show();
                    ApiControllerClass.callUserAction(getContext(), retrofit, "reject", null, responsePojo.body().getUsers().getData().get(swiped_card_postion).id);
                } else {

                    //Toast.makeText(getApplicationContext(), swiped_card_text + " Swipped to Bottom", Toast.LENGTH_SHORT).show();

                }
                if (mIndex == 0 && pageNo < responsePojo.body().getUsers().getLast_page()) {
                    pageNo++;
                    getUsersApi(pageNo);

                }
            }

            @Override
            public void topCardTapped() {

            }
        });
    }

    public void swipeRight(){
        mCardStack.discardTop(1);
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
