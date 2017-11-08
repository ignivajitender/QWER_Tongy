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

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by igniva-android13 on 7/11/17.
 */

public class NewsFeedAdapter extends RecyclerView.Adapter<NewsFeedAdapter.MyViewHolder> {


    List<PostPojo.PostDataPojo.DataBean> rantList;

    private Context mContext;
    private String fragmentName;
    private String formattedDate;


    /*public NewsFeedAdapter(Context mContext, String fragmentName, ArrayList<PostPojo> data) {
        this.mContext = mContext;
        this.fragmentName = fragmentName;
        this.rantList = data;
    }*/

    public NewsFeedAdapter(Context mContext,  List<PostPojo.PostDataPojo.DataBean> postList) {
        this.mContext=mContext;
        this.rantList=postList;

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
        holder.mTvPostType.setText(pojo.getClass_type());
        Glide.with(mContext).load( pojo.getPost_user().getUser_image().get(0).getImage()).into(holder.mIvProfile);
        holder.mTvName.setText(pojo.getPost_user().getName());
        //holder.mIbChat.setText(pojo.getPost_comment_count().size());
        //holder.mTvDate.setText(Utility.getTimeAgo(pojo.getCreated_at().toString(),mContext);
       /* final RantsPojo modeldata = rantList.get(position);
        holder.mib_fav.setVisibility(View.VISIBLE);
        holder.mtv_postBy.setVisibility(View.VISIBLE);
        holder.mtv_postBy.setText(modeldata.getUser_id().getFirst_name() + " " + modeldata.getUser_id().getLast_name());
        Glide.with(mContext).load("http://rantogasm.ignivastaging.com:8042/v1/Files/" + modeldata.getRantFile() + "?thumbnail=true").into(holder.mivPost);


        if (modeldata.is_favourite() || fragmentName.equalsIgnoreCase(mContext.getResources().getString(R.string.favourite_rants)))
            holder.mib_fav.setImageResource(R.drawable.like_red_large);
        else
            holder.mib_fav.setImageResource(R.drawable.like_grey_large);

        holder.tvRantTitle.setText(modeldata.getTitle());

        holder.tvViews.setText(modeldata.getViews() + " views | " + modeldata.getLikes() + " Likes");
        holder.tvRating.setText(modeldata.getRating() + "");
        holder.tvDuration.setText(modeldata.getRant_duration());
//        Log.e("date", Utility.getLognDate(modeldata.getCreated_at()) + " ");
        holder.tvPostTime.setText(Utility.getTimeAgo(Utility.getLognDate(modeldata.getCreated_at()), mContext));
//        Log.e("image url", "http://rantogasm.ignivastaging.com:8042/v1/Files/" + modeldata.getRantFile() + "?thumbnail=true");
        //Glide.with(mContext).load("http://rantogasm.ignivastaging.com:8042/v1/Files/" + modeldata.getRantFile() + "?thumbnail=true").into(holder.mivPost);
        holder.mcvViewRant.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //  Utility.showToastMessageLong(mContext,"click");
                mContext.startActivity(new Intent(mContext, VideoActivity.class).putExtra("uri", modeldata.getRantFile()).putExtra("dataPojo", modeldata).putExtra("comingFrom", "rants list").setFlags(Intent.FLAG_ACTIVITY_NEW_TASK));
            }
        });

        holder.mib_fav.setTag(modeldata.is_favourite());
        holder.mib_fav.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.e("mContext--modeldata.is_favourite()---", modeldata.is_favourite() + " ");
                if (mContext instanceof RantsListActivity) {
                    ApiControllerClass.markFavoriteUnfavorite(((RantsListActivity) mContext).retrofit, mContext, modeldata.get_id(), holder.mib_fav);
                } else if (mContext instanceof NavigationActivity && fragmentName.equalsIgnoreCase(mContext.getResources().getString(R.string.favourite_rants))) {
                    view.setTag(true);
                    ApiControllerClass.markFavoriteUnfavorite(((NavigationActivity) mContext).retrofit, mContext, modeldata.get_id(), holder.mib_fav);
                }

            }
        });

        if (fragmentName.equalsIgnoreCase(mContext.getResources().getString(R.string.my_rants))) {
            holder.mib_fav.setVisibility(View.GONE);
            holder.mtv_postBy.setVisibility(View.GONE);

        }*/
    }


    @Override
    public int getItemCount() {
        if (rantList != null && rantList.size() > 0) {
            return rantList.size();
        } else return 5;
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
