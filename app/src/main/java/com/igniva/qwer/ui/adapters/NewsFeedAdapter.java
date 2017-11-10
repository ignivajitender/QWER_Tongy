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
import com.igniva.qwer.controller.ApiControllerClass;
import com.igniva.qwer.model.PostPojo;

import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Retrofit;

/**
 * Created by igniva-android13 on 7/11/17.
 */

public class NewsFeedAdapter extends RecyclerView.Adapter<NewsFeedAdapter.MyViewHolder> {


    List<PostPojo.PostDataPojo.DataBean> rantList;

    private Context mContext;
    private String fragmentName;
    private String formattedDate;
    @Inject
    Retrofit retrofit;


    /*public NewsFeedAdapter(Context mContext, String fragmentName, ArrayList<PostPojo> data) {
        this.mContext = mContext;
        this.fragmentName = fragmentName;
        this.rantList = data;
    }*/

    public NewsFeedAdapter(Context mContext, List<PostPojo.PostDataPojo.DataBean> postList, Retrofit retrofit) {
        this.mContext=mContext;
        this.rantList=postList;
        this.retrofit=retrofit;

    }


    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.post_news_item, parent, false);

        return new MyViewHolder(itemView);
    }

//    public void add(PostPojo item) {
//        if(!rantList.contains(item))
//        {
//            rantList.add(item);
//            notifyItemInserted(rantList.size() - 1);
//        }
//    }

//    public void addAll(List<RantsPojo> mcList) {
//        for (RantsPojo mc : mcList) {
//            add(mc);
//        }
//    }
//
//    public void remove(RantsPojo item) {
//        int position = rantList.indexOf(item);
//        if (position > -1) {
//            rantList.remove(position);
//            notifyItemRemoved(position);
//        }
//    }
//
//    public void clear() {
//        while (getItemCount() > 0) {
//            remove(getItem(0));
//        }
//    }

    public PostPojo.PostDataPojo.DataBean getItem(int position) {
        return rantList.get(position);
    }

    public boolean isEmpty() {
        return getItemCount() == 0;
    }


    @Override
    public void onBindViewHolder(final MyViewHolder holder, int position) {

        final PostPojo.PostDataPojo.DataBean pojo=rantList.get(position);
        holder.mDesc.setText(pojo.getDescription());
        holder.mTvTitle.setText(pojo.getDescription());
        holder.mTvPostType.setText(pojo.getPost_type());
        if(pojo.getPost_user().getUser_image()!=null && pojo.getPost_user().getUser_image().size()>0)
        Glide.with(mContext).load( pojo.getPost_user().getUser_image().get(0).getImage()).into(holder.mIvProfile);
        holder.mTvName.setText(pojo.getPost_user().getName());
        if(pojo.getPost_comment_count()==null && pojo.getPost_comment_count().size()==0)
            holder.mIbChat.setText("0");
        else
            holder.mIbChat.setText(pojo.getPost_comment_count().size()+"");

        if(pojo.getPost_fav()==null && pojo.getPost_fav().size()==0)
            holder.mIvFav.setImageResource(R.drawable.like);
        else
            holder.mIvFav.setImageResource(R.drawable.liked);

        holder.mIvFav.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ApiControllerClass.markFavoriteUnfavorite(retrofit, mContext, pojo.getPost_fav(), holder.mIvFav);

            }
        });


    }


    @Override
    public int getItemCount() {
        if (rantList != null && rantList.size() > 0) {
            return rantList.size();
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
        ImageView mIvFav;
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
