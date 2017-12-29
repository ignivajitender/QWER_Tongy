package com.igniva.qwer.ui.activities;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.igniva.qwer.R;
import com.igniva.qwer.controller.ApiInterface;
import com.igniva.qwer.model.NotificationPojo;
import com.igniva.qwer.ui.adapters.NotificationRecyclerViewAdapter;
import com.igniva.qwer.ui.views.CallProgressWheel;
import com.igniva.qwer.utils.EndlessRecyclerViewScrollListener;
import com.igniva.qwer.utils.Global;
import com.igniva.qwer.utils.Utility;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class NotificationActivity extends BaseActivity {


    @BindView(R.id.tvNoData)
    public TextView mtvNoData;
    public int pageNo = 1;
    @BindView(R.id.tv_tap_to_rename)
    TextView mtvTitle;
    @Inject
    Retrofit retrofit;
    NotificationRecyclerViewAdapter adapter;
    @BindView(R.id.rvNotification)
    RecyclerView mrecyclerView;
    Boolean isLast = false;
    @BindView(R.id.swipe_refresh_layout)
    SwipeRefreshLayout swipeRefreshLayout;
    private Paint p = new Paint();

    // Store a member variable for the listener
    private EndlessRecyclerViewScrollListener scrollListener;

    @OnClick(R.id.ivbackIcon)
    public void back() {
        onBackPressed();
    }

    @Override
    protected void setUpLayout() {
        LinearLayoutManager mLayoutManager = new LinearLayoutManager(NotificationActivity.this);
        mrecyclerView.setLayoutManager(mLayoutManager);

        scrollListener = new EndlessRecyclerViewScrollListener(mLayoutManager) {
            @Override
            public void onLoadMore(int page, int totalItemsCount, RecyclerView view) {
                // Triggered only when new data needs to be appended to the list
                // Add whatever code is needed to append new items to the bottom of the list
                if (!isLast) {
                    getNotifications();
                }

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
                getNotifications();
            }
        });
    }

    @Override
    protected void setDataInViewObjects() {


    }

    @Override
    protected void setUpToolbar() {
        mtvTitle.setText(getResources().getString(R.string.notification_text));
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        ((Global) getApplication()).getNetComponent().inject(this);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notification);
        ButterKnife.bind(this);
        setUpToolbar();
        setUpLayout();
        //  setDataInViewObjects();
        getNotifications();
        initSwipe();
    }

    private void getNotifications() {
        getNotificationsApi(retrofit, NotificationActivity.this);
    }


    /**
     * fetch all notifications from server
     *
     * @param retrofit
     * @param context
     */
    public void getNotificationsApi(Retrofit retrofit, NotificationActivity context) {

        try {
            if (Utility.isInternetConnection(context)) {

                CallProgressWheel.showLoadingDialog(context, "Loading...");

                //Create a retrofit call object
                Call<NotificationPojo> posts = null;
                posts = retrofit.create(ApiInterface.class).getNotifications(pageNo);

                if (posts != null)
                    posts.enqueue(new retrofit2.Callback<NotificationPojo>() {
                        @Override
                        public void onResponse(Call<NotificationPojo> call, retrofit2.Response<NotificationPojo> response) {
                            if (response.body().getStatus() == 200) {
                                CallProgressWheel.dismissLoadingDialog();
                                //Log.e("data",response.body().getData()+"");
                                setDataInViewObjects(response.body());
                                //callSuccessPopUp((Activity)context, response.body().getDescription());
                                // Utility.showToastMessageShort(ChangePasswordActivity.this,responsePojo.getDescription());
                            } else {
                                setDataInViewObjects(null);
                                CallProgressWheel.dismissLoadingDialog();
                                //Utility.showToastMessageShort((Activity) context, response.body().getDescription());
                            }
                        }


                        @Override
                        public void onFailure(Call<NotificationPojo> call, Throwable t) {
                            setDataInViewObjects(null);

                            CallProgressWheel.dismissLoadingDialog();
                        }
                    });
            }


        } catch (Exception e) {
            e.printStackTrace();
        }


    }

    private void setDataInViewObjects(NotificationPojo data) {
        if (data != null && data.getData().size() > 0) {
            mrecyclerView.setVisibility(View.VISIBLE);
            mtvNoData.setVisibility(View.GONE);

            pageNo++;
            if (pageNo >= data.getLast_page())
                isLast = true;

            if (adapter == null) {
                adapter = new NotificationRecyclerViewAdapter(NotificationActivity.this, (ArrayList<NotificationPojo.NotificationDataPojo>) data.getData(), retrofit);
                mrecyclerView.setAdapter(adapter);
            } else {
                adapter.addAll((ArrayList<NotificationPojo.NotificationDataPojo>) data.getData());
            }

        } else {
            mrecyclerView.setVisibility(View.GONE);
            mtvNoData.setVisibility(View.VISIBLE);
            isLast = true;

        }
    }

    /**
     * Init swipe gesture on recycler view items
     */
    private void initSwipe() {

        ItemTouchHelper.SimpleCallback simpleItemTouchCallback = new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT) {

            @Override
            public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction) {
                int position = viewHolder.getAdapterPosition();

                if (direction == ItemTouchHelper.LEFT) {
                    adapter.removeItem(position);
                }
               /* else if (direction == ItemTouchHelper.RIGHT) {
                    adapter.removeItem(position);
                }*/
            }

            @Override
            public void onChildDraw(Canvas c, RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, float dX, float dY, int actionState, boolean isCurrentlyActive) {

                Bitmap icon;
                if (actionState == ItemTouchHelper.ACTION_STATE_SWIPE) {

                    View itemView = viewHolder.itemView;
                    float height = (float) itemView.getBottom() - (float) itemView.getTop();
                    float width = height / 3;

                    if (dX > 0) {
                        p.setColor(Color.parseColor("#D32F2F"));
                        RectF background = new RectF((float) itemView.getLeft(), (float) itemView.getTop(), dX, (float) itemView.getBottom());
                        c.drawRect(background, p);
                        icon = BitmapFactory.decodeResource(getResources(), R.drawable.delete);
                        RectF icon_dest = new RectF((float) itemView.getLeft() + width, (float) itemView.getTop() + width, (float) itemView.getLeft() + 2 * width, (float) itemView.getBottom() - width);
                        c.drawBitmap(icon, null, icon_dest, p);
                    } else {
                        p.setColor(Color.parseColor("#D32F2F"));
                        RectF background = new RectF((float) itemView.getRight() + dX, (float) itemView.getTop(), (float) itemView.getRight(), (float) itemView.getBottom());
                        c.drawRect(background, p);
                        icon = BitmapFactory.decodeResource(getResources(), R.drawable.delete);
                        RectF icon_dest = new RectF((float) itemView.getRight() - 2 * width, (float) itemView.getTop() + width, (float) itemView.getRight() - width, (float) itemView.getBottom() - width);
                        c.drawBitmap(icon, null, icon_dest, p);
                    }
                }
                super.onChildDraw(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive);
            }
        };
        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(simpleItemTouchCallback);
        itemTouchHelper.attachToRecyclerView(mrecyclerView);
    }

    /**
     * remove notification on left swipe
     *
     * @param position
     * @param post_id
     * @param listData
     */
    public void removeTheNotification(final int position, int post_id, final List<NotificationPojo.NotificationDataPojo> listData) {
        if (Utility.isInternetConnection(NotificationActivity.this)) {
            CallProgressWheel.showLoadingDialog(NotificationActivity.this, "Loading...");
            Log.e("tag", "-------notification id---" + post_id);
            Call<NotificationPojo> Call = retrofit.create(ApiInterface.class).removeNotifications(post_id);
            Call.enqueue(new Callback<NotificationPojo>() {
                @Override
                public void onResponse(Call<NotificationPojo> call, Response<NotificationPojo> response) {
                    CallProgressWheel.dismissLoadingDialog();
                    if (response.body().getStatus() == 200) {
                        listData.remove(position);
                        adapter.notifyDataSetChanged();
                        Utility.showToastMessageLong(NotificationActivity.this, response.body().getDescription());
                    } else {
                        Utility.showToastMessageLong(NotificationActivity.this, response.body().getDescription());
                    }
                }

                @Override
                public void onFailure(Call<NotificationPojo> call, Throwable t) {
                    CallProgressWheel.dismissLoadingDialog();
                }
            });

        }

    }
}

