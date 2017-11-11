package com.igniva.qwer.ui.adapters;

import android.content.Context;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.igniva.qwer.R;
import com.igniva.qwer.model.PostPojo;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class RecyclerviewAdapter extends RecyclerView.Adapter<RecyclerviewAdapter.MyViewHolder> {


    List<PostPojo.PostDataPojo.DataBean> postsList;
    private Context mContext;
    private int listType;


    public RecyclerviewAdapter(Context mContext, int listType, ArrayList<PostPojo.PostDataPojo.DataBean> data) {
        this.mContext = mContext;
        this.listType = listType;
        this.postsList = data;
    }


    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.post_news_item, parent, false);

        return new MyViewHolder(itemView);
    }

    public void add(PostPojo.PostDataPojo.DataBean item) {
        if (!postsList.contains(item)) {
            postsList.add(item);
            notifyItemInserted(postsList.size() - 1);
        }
    }

    public void addAll(List<PostPojo.PostDataPojo.DataBean> mcList) {
        for (PostPojo.PostDataPojo.DataBean mc : mcList) {
            add(mc);
        }
    }

    public void remove(PostPojo.PostDataPojo.DataBean item) {
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

    public PostPojo.PostDataPojo.DataBean getItem(int position) {
        return postsList.get(position);
    }

    public boolean isEmpty() {
        return getItemCount() == 0;
    }


    @Override
    public void onBindViewHolder(final MyViewHolder holder, int position) {

        final PostPojo.PostDataPojo.DataBean pojo = postsList.get(position);
        if (pojo != null) {

            holder.mDesc.setText(pojo.getDescription());
            holder.mTvTitle.setText(pojo.getDescription());
            holder.mTvPostType.setText(pojo.getPost_type());
            if(pojo.getPost_user()!=null){
                 if (pojo.getPost_user().getUser_image() != null && pojo.getPost_user().getUser_image().size() > 0) {
                    Glide.with(mContext).load(pojo.getPost_user().getUser_image().get(0).getImage()).into(holder.mIvProfile);
                }
                holder.mTvName.setText(pojo.getPost_user().getName());
            }
        }
    }


    @Override
    public int getItemCount() {
        if (postsList != null && postsList.size() > 0) {
            return postsList.size();
        } else return 0;
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.iv_image)
        ImageView mIvImage;
        @BindView(R.id.iv_profile)
        ImageView mIvProfile;
        @BindView(R.id.tv_title)
        TextView mTvTitle;
        @BindView(R.id.desc)
        TextView mDesc;
        @BindView(R.id.linearLayout)
        LinearLayout mLinearLayout;
        @BindView(R.id.tv_name)
        TextView mTvName;
        @BindView(R.id.tv_date)
        TextView mTvDate;
        @BindView(R.id.ib_chat)
        TextView mIbChat;
        @BindView(R.id.tv_fav)
        TextView mTvFav;
        @BindView(R.id.cardView2)
        CardView mCardView2;
        @BindView(R.id.tv_postType)
        TextView mTvPostType;

        public MyViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }
}