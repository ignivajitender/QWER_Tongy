package com.igniva.qwer.ui.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.igniva.qwer.R;
import com.igniva.qwer.model.CommentPojo;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Retrofit;

public class CommentsAdapter extends RecyclerView.Adapter<CommentsAdapter.MyViewHolder> {


    List<CommentPojo> postsList;
    private Context mContext;
    private int listType;
    Retrofit retrofit;
 
    public CommentsAdapter(Context mContext, ArrayList<CommentPojo> data, Retrofit retrofit) {
        this.mContext = mContext;
        this.listType = listType;
        this.postsList = data;
        this.retrofit = retrofit;
    }


    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.comment_item, parent, false);

        return new MyViewHolder(itemView);
    }

    public void add(CommentPojo item) {
        if (!postsList.contains(item)) {
            postsList.add(item);
            notifyItemInserted(postsList.size() - 1);
        }
    }

    public void addAll(List<CommentPojo> mcList) {
        for (CommentPojo mc : mcList) {
            add(mc);
        }
    }

    public void remove(CommentPojo item) {
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

    public CommentPojo getItem(int position) {
        return postsList.get(position);
    }

    public boolean isEmpty() {
        return getItemCount() == 0;
    }


    @Override
    public void onBindViewHolder(final MyViewHolder holder, int position) {
//        ImageView ivUserImage;
//        @BindView(R.id.tv_name)
//        TextView tvName;
//        @BindView(R.id.tv_date)
//        TextView tvDate;
//        @BindView(R.id.tv_comment)
//        TextView tvComment;

        final CommentPojo pojo = postsList.get(position);
        if (pojo != null) {
            holder.tvComment.setText(pojo.getComment());
            if(pojo.getComment_by_user()!=null){
                if (pojo.getComment_by_user().getUser_image() != null && pojo.getComment_by_user().getUser_image().size() > 0) {
                    Glide.with(mContext).load(pojo.getComment_by_user().getUser_image().get(0).getImage()).into(holder.ivUserImage);
                }
                holder.tvName.setText(pojo.getComment_by_user().getName());
            }
            holder.tvDate.setText(pojo.getCreated_at());
         }

    }


    @Override
    public int getItemCount() {
        if (postsList != null && postsList.size() > 0) {
            return postsList.size();
        } else return 0;
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.iv_user_image)
        ImageView ivUserImage;
        @BindView(R.id.tv_name)
        TextView tvName;
        @BindView(R.id.tv_date)
        TextView tvDate;
        @BindView(R.id.tv_comment)
        TextView tvComment;


        public MyViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }
}