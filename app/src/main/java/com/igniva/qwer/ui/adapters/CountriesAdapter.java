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
import com.igniva.qwer.model.predictionsCountriesPojo;
import com.igniva.qwer.ui.callbacks.MyCallBack;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by igniva-android13 on 21/11/17.
 */

public class CountriesAdapter extends RecyclerView.Adapter<CountriesAdapter.ViewHolder> {
    ArrayList<predictionsCountriesPojo> mLangList;
    Context mContext;

    String type;
    MyCallBack callBack;


    public CountriesAdapter(Context mContext, ArrayList<predictionsCountriesPojo> mHashmapLangList) {
        this.mLangList = mHashmapLangList;
        this.mContext = mContext;

    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.layout_country_item, parent, false);

        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
        predictionsCountriesPojo pojo=mLangList.get(position);
        holder.tvName.setText(pojo.getCountry());
        Glide.with(mContext).load(pojo.getCountry_flag()).into(holder.ivImage);


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

        @BindView(R.id.ivImage)
        ImageView ivImage;
        @BindView(R.id.tvName)
        TextView tvName;
        @BindView(R.id.cvCountry)
        CardView cvCountry;

        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}