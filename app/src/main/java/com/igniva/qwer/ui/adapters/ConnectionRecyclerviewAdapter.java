package com.igniva.qwer.ui.adapters;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.igniva.qwer.R;
import com.igniva.qwer.controller.ApiControllerClass;
import com.igniva.qwer.model.ConnectionPojo;
import com.igniva.qwer.ui.activities.OtherUserProfileActivity;
import com.igniva.qwer.utils.CircularImageView;
import com.igniva.qwer.utils.Utility;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Retrofit;

public class ConnectionRecyclerviewAdapter extends RecyclerView.Adapter<ConnectionRecyclerviewAdapter.MyViewHolder> {


    List<ConnectionPojo.ConnectionDataPojo.ContactDataPojo> postsList;
    Retrofit retrofit;

    private Context mContext;
    private int listType;


    public ConnectionRecyclerviewAdapter(Context mContext, ArrayList<ConnectionPojo.ConnectionDataPojo.ContactDataPojo> data, Retrofit retrofit) {
        this.mContext = mContext;

        this.postsList = data;
        this.retrofit = retrofit;
    }


    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.layout_connection_item, parent, false);

        return new MyViewHolder(itemView);
    }

    public void add(ConnectionPojo.ConnectionDataPojo.ContactDataPojo item) {
        if (!postsList.contains(item)) {
            postsList.add(item);
            notifyItemInserted(postsList.size() - 1);
        }
    }

    public void addAll(List<ConnectionPojo.ConnectionDataPojo.ContactDataPojo> mcList) {
        for (ConnectionPojo.ConnectionDataPojo.ContactDataPojo mc : mcList) {
            add(mc);
        }
    }

    public void remove(ConnectionPojo.ConnectionDataPojo.ContactDataPojo item) {
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

    public ConnectionPojo.ConnectionDataPojo.ContactDataPojo getItem(int position) {
        return postsList.get(position);
    }

    public boolean isEmpty() {
        return getItemCount() == 0;
    }


    @Override
    public void onBindViewHolder(final MyViewHolder holder, int position) {

        final ConnectionPojo.ConnectionDataPojo.ContactDataPojo pojo = postsList.get(position);
        holder.mtvName.setText(pojo.getName());
        if (pojo.getUser_country() != null && pojo.getUser_country().getCountry() != null)
            holder.mtvCountryName.setText(pojo.getUser_country().getCountry());
        if (pojo.getUser_image() != null && pojo.getUser_image().size() > 0)
            Utility.setUserImage(mContext,holder.mivImage,pojo.getUser_image());

//            Glide.with(mContext).load(pojo.getUser_image().get(0).getImage()).into(holder.mivImage);

        holder.mivImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mContext.startActivity(new Intent(mContext, OtherUserProfileActivity.class).putExtra("userId", pojo.getId()));
                Log.e("adapter", pojo.getId() + "");
            }
        });
        holder.mcvConnection.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String userIMage="";
                if(pojo.getUser_image()!=null && pojo.getUser_image().size()>0 && pojo.getUser_image().get(0)!=null)
                    userIMage=pojo.getUser_image().get(0).getImage();
                ApiControllerClass.getOtherUserProfile(retrofit,(Activity) mContext, pojo.getId(),true);
 //                Utility.goToChatActivity((Activity) mContext,pojo.getId() ,pojo.getName(),userIMage);
//                ApiControllerClass.createChannel(retrofit, mContext, pojo.getId(), PreferenceHandler.readString(mContext, PreferenceHandler.PREF_KEY_USER_ID, ""));
            }
        });

        if (pojo.getUser_country() != null && pojo.getUser_country().getCountry_flag() != null)
            Glide.with(mContext).load(pojo.getUser_country().getCountry_flag()).into(holder.mivImageCountry);
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
        @BindView(R.id.ivImageCountry)
        CircularImageView mivImageCountry;
        @BindView(R.id.tvName)
        TextView mtvName;
        @BindView(R.id.tvCountryName)
        TextView mtvCountryName;
        @BindView(R.id.ivChat)
        ImageView mivChat;
        @BindView(R.id.cvConnection)
        CardView mcvConnection;

        public MyViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }
}