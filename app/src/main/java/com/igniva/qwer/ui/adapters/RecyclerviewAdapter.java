package com.igniva.qwer.ui.adapters;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupMenu;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.igniva.qwer.R;
import com.igniva.qwer.controller.ApiControllerClass;
import com.igniva.qwer.model.PostPojo;
import com.igniva.qwer.ui.activities.PostDetailActivity;
import com.igniva.qwer.utils.FieldValidators;
import com.igniva.qwer.utils.Utility;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Retrofit;

public class RecyclerviewAdapter extends RecyclerView.Adapter<RecyclerviewAdapter.MyViewHolder> {


    List<PostPojo.PostDataPojo.DataBean> postsList;
    private Context mContext;
    private int listType;
    Retrofit retrofit;


    public RecyclerviewAdapter(Context mContext, int listType, ArrayList<PostPojo.PostDataPojo.DataBean> data, Retrofit retrofit) {
        this.mContext = mContext;
        this.listType = listType;
        this.postsList = data;
        this.retrofit=retrofit;
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
            holder.mTvTitle.setText(pojo.getTitle());

            if (pojo.getPost_type().equalsIgnoreCase(mContext.getResources().getString(R.string.teaching))) {
                holder.mTvPostType.setBackgroundColor(mContext.getResources().getColor(R.color.yellow_color));
                holder.mTvPostType.setText(pojo.getPost_type());
                holder.mIvImage.setVisibility(View.GONE);

            }
            if (pojo.getPost_type().equalsIgnoreCase(mContext.getResources().getString(R.string.meeting))) {
                holder.mTvPostType.setBackgroundColor(mContext.getResources().getColor(R.color.bg_blue));
                holder.mTvPostType.setText(pojo.getPost_type());
                holder.mIvImage.setVisibility(View.GONE);

            }
            if (pojo.getPost_type().equalsIgnoreCase(mContext.getResources().getString(R.string.other))) {
                holder.mTvPostType.setBackgroundColor(mContext.getResources().getColor(R.color.other_red_color));
                holder.mTvPostType.setText(pojo.getPost_type());
                holder.mIvImage.setVisibility(View.VISIBLE);
                Glide.with(mContext).load(pojo.getImage()).into(holder.mIvImage);
            }

            holder.mTvTitle.setText(pojo.getDescription());
            holder.mTvPostType.setText(pojo.getPost_type());
            if(pojo.getPost_user()!=null){
                 if (pojo.getPost_user().getUser_image() != null && pojo.getPost_user().getUser_image().size() > 0) {
                    Glide.with(mContext).load(pojo.getPost_user().getUser_image().get(0).getImage()).into(holder.mIvProfile);
                }
                holder.mTvName.setText(pojo.getPost_user().getName());
            }

            if(pojo.getPost_fav()!=null && pojo.getPost_fav().size()>0)
                holder.mIvFav.setImageResource(R.drawable.liked);
            else
                holder.mIvFav.setImageResource(R.drawable.like);


            holder.mIvFav.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    ApiControllerClass.markFavoriteUnfavorite(retrofit, mContext, pojo.getPost_fav(), holder.mIvFav, pojo.getId());

                }
            });
            holder.mibReport.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    /**
                     * // open report popup
                     *
                     * @param post_id
                     *
                     */
                    openReportPopup(pojo.getId());
                }
            });
            holder.mCardView2.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    mContext.startActivity(new Intent(mContext, PostDetailActivity.class));
                }
            });

            holder.mTvDate.setText(Utility.getTimeAgoPost(pojo.getCreated_at(),(Activity) mContext));
        }

    }

    private void openReportPopup(final int post_id) {

        // Create custom dialog object
        final Dialog dialog = new Dialog(mContext);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.setCancelable(false);
        dialog.setCanceledOnTouchOutside(false);

        dialog.setContentView(R.layout.layout_report_abuse);

        final EditText metReason=(EditText)dialog.findViewById(R.id.etReason);
        final EditText metComment=(EditText)dialog.findViewById(R.id.et_comment);


        metReason.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                PopupMenu dropDownMenu = new PopupMenu(mContext, metReason);
                dropDownMenu.getMenuInflater().inflate(R.menu.drop_down_menu, dropDownMenu.getMenu());
                //showMenu.setText("DropDown Menu");
                dropDownMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {

                    @Override
                    public boolean onMenuItemClick(MenuItem menuItem) {
                        //Toast.makeText(mContext, "You have clicked " + menuItem.getTitle(), Toast.LENGTH_LONG).show();
                        metReason.setText(menuItem.getTitle());
                        return true;
                    }
                });
                dropDownMenu.show();
            }
        });



        TextView mBtnOk=(TextView)dialog.findViewById(R.id.btn_ok);
        mBtnOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                metReason.setError(null);
                metComment.setError(null);
                if(FieldValidators.isNullOrEmpty(metReason))
                {
                    metReason.setFocusable(true);
                    metReason.setError("Please select reason for report");
                    return;
                }
                else if(FieldValidators.isNullOrEmpty(metComment))
                {
                    metComment.setFocusable(true);
                    metComment.setError("Please enter your comment");
                    return;
                }
                else
                {
                    dialog.dismiss();
                    ApiControllerClass.callReportAbuseApi(mContext,retrofit,metReason,metComment,post_id);
                }


                //((Activity)mContext).finish();
            }
        });

        TextView mbtnCancel=(TextView)dialog.findViewById(R.id.btn_cancel);
        mbtnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();


                //((Activity)mContext).finish();
            }
        });


        dialog.setTitle("Custom Dialog");


        dialog.show();


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
        @BindView(R.id.cardView2)
        CardView mCardView2;
        @BindView(R.id.tv_postType)
        TextView mTvPostType;
        @BindView(R.id.ib_report)
        TextView mibReport;
        @BindView(R.id.tv_fav)
        ImageView mIvFav;

        public MyViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }
}