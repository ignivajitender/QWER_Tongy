package com.igniva.qwer.ui.adapters;

import android.app.Activity;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.igniva.qwer.R;
import com.igniva.qwer.model.NotificationPojo;
import com.igniva.qwer.ui.activities.NotificationActivity;
import com.igniva.qwer.utils.CircularImageView;
import com.igniva.qwer.utils.Utility;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Retrofit;

/**
 * Created by igniva-android13 on 17/11/17.
 */

public class NotificationRecyclerViewAdapter extends RecyclerView.Adapter<NotificationRecyclerViewAdapter.MyViewHolder> {


    List<NotificationPojo.NotificationDataPojo> postsList;
    Retrofit retrofit;

    private NotificationActivity mContext;



    public NotificationRecyclerViewAdapter(NotificationActivity mContext, ArrayList<NotificationPojo.NotificationDataPojo> data, Retrofit retrofit) {
        this.mContext = mContext;
        this.postsList = data;
        this.retrofit = retrofit;
    }


    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.layout_notification_item, parent, false);

        return new MyViewHolder(itemView);
    }

    public void add(NotificationPojo.NotificationDataPojo item) {
        if (!postsList.contains(item)) {
            postsList.add(item);
            notifyItemInserted(postsList.size() - 1);
        }
    }

    public void addAll(List<NotificationPojo.NotificationDataPojo> mcList) {
        for (NotificationPojo.NotificationDataPojo mc : mcList) {
            add(mc);
        }
    }

    public void remove(NotificationPojo.NotificationDataPojo item) {
        int position = postsList.indexOf(item);
        if (position > -1) {
            postsList.remove(position);
            notifyItemRemoved(position);
        }
    }

    public void clear() {
        while (getItemCount() > 0) {
            remove(getItem(0));
        }
    }

    public NotificationPojo.NotificationDataPojo getItem(int position) {
        return postsList.get(position);
    }

    public boolean isEmpty() {
        return getItemCount() == 0;
    }

    public void removeItem(int position) {
       mContext.removeTheNotification(position,postsList.get(position).getId(),postsList);
        Log.e("position","item removed at"+position);
    }
    @Override
    public void onBindViewHolder(final MyViewHolder holder, int position) {

        final NotificationPojo.NotificationDataPojo dataPojo=postsList.get(position);
        holder.mtvName.setText(dataPojo.getSender_name());
        holder.mtvMessage.setText(Html.fromHtml(Utility.getColoredSpanned(dataPojo.getSender_name()+" ","#000000")+Utility.getColoredSpanned(dataPojo.getMessage(),"#c0c0c0")));
        //Glide.with(mContext).load(dataPojo.)
        holder.mtvTimeAndDate.setText(Utility.getTimeAgoPost(dataPojo.getCreated_at(),(Activity) mContext));

        holder.mcvNotification.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                   // mContext.startActivity(new Intent(mContext, OtherUserProfileActivity.class).putExtra("userId",dataPojo.getId()));
                }
            });


        }






    @Override
    public int getItemCount() {
        if (postsList != null && postsList.size() > 0) {
            return postsList.size();
        } else return 0;
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.ivImage)
        CircularImageView mivImage;
        @BindView(R.id.tvName)
        TextView mtvName;
        @BindView(R.id.tvTimeAndDate)
        TextView mtvTimeAndDate;
        @BindView(R.id.tvMessage)
        TextView mtvMessage;
        @BindView(R.id.cvNotification)
        CardView mcvNotification;

        public MyViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }
}