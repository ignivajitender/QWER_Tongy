package com.igniva.qwer.ui.adapters;

import android.content.Context;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.igniva.qwer.R;
import com.igniva.qwer.model.ConnectionPojo;
import com.igniva.qwer.utils.CircularImageView;

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
        holder.mtvCountryName.setText(pojo.getCountry());
        if(pojo.getUser_image()!=null && pojo.getUser_image().size()>0)
        Glide.with(mContext).load(pojo.getUser_image().get(0).getImage()).into(holder.mivImage);

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