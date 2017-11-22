package com.igniva.qwer.ui.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.igniva.qwer.R;
import com.igniva.qwer.model.PrefInputPojo;
import com.igniva.qwer.ui.callbacks.MyCallBack;
import com.igniva.qwer.utils.Constants;
import com.igniva.qwer.utils.Log;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by karanveer on 21/9/17.
 */

public class LanguageListAdapter extends RecyclerView.Adapter<LanguageListAdapter.ViewHolder> {


    ArrayList<PrefInputPojo.LanguagesProficiency> mLangList;
    Context mContext;

    String type;
    MyCallBack callBack;

    public LanguageListAdapter(Context mContext, ArrayList<PrefInputPojo.LanguagesProficiency> mHashmapLangList, String type, MyCallBack callBack) {
        this.mLangList = mHashmapLangList;
        this.mContext = mContext;
        this.type = type;
        this.callBack = callBack;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.language_list_item, parent, false);

        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
        if (mLangList.size() == 0) {
            holder.mLlOuterLayout.setVisibility(View.GONE);
        } else {
            if (position == (mLangList.size() - 1)) {
                holder.mViewItemDivider.setVisibility(View.GONE);

            } else {
                holder.mViewItemDivider.setVisibility(View.VISIBLE);
            }
            holder.mLlOuterLayout.setVisibility(View.VISIBLE);

             holder.mTvLanguageName.setText(mLangList.get(position).getName());
            holder.mTvLanguageProficiency.setText(mLangList.get(position).getProficiency());
            switch (mLangList.get(position).getProficiency()) {
                case Constants.BEGINNER:
                    holder.mIvBanner.setImageDrawable(mContext.getResources().getDrawable(R.drawable.beginner));
                    break;
                case Constants.INTERMEDIATE:
                    holder.mIvBanner.setImageDrawable(mContext.getResources().getDrawable(R.drawable.intermediate));
                    break;
                case Constants.PROFESSIONAL:
                    holder.mIvBanner.setImageDrawable(mContext.getResources().getDrawable(R.drawable.professional));

                    break;
            }
            holder.mIvRemove.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    switch (type) {
                        case Constants.LANGUAGE_SPEAK:
                            Log.e("pos", position + "");
                            ;
                            callBack.removeItem(Constants.LANGUAGE_SPEAK , position);
                            break;
                        case Constants.LANGUAGE_LEARN:
                            callBack.removeItem(Constants.LANGUAGE_LEARN , position);
                            break;
                    }

                }
            });
        }

    }

    /* @Override
     public int getItemCount() {
         return 3;
     }
 */

    @Override
    public int getItemCount() {
        if (mLangList.size() == 0) {
            return 1;
        } else
            return mLangList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.iv_banner)
        ImageView mIvBanner;
        @BindView(R.id.tv_language_name)
        TextView mTvLanguageName;
        @BindView(R.id.tv_language_proficiency)
        TextView mTvLanguageProficiency;
        @BindView(R.id.iv_remove)
        ImageView mIvRemove;
        @BindView(R.id.ll_outer_layout)
        LinearLayout mLlOuterLayout;
        @BindView(R.id.view_item_divider)
        View mViewItemDivider;

//        @OnClick(R.id.iv_remove)
//        public void onViewClicked() {
//
//        }
          public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
         }
     }
 }