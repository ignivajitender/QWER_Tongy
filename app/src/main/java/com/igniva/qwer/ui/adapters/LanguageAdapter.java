package com.igniva.qwer.ui.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.igniva.qwer.R;
import com.igniva.qwer.model.OtherUserProfilePojo;
import com.igniva.qwer.ui.views.TextViewRegular;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by igniva-android13 on 4/1/17.
 */

public class LanguageAdapter extends RecyclerView.Adapter<LanguageAdapter.ViewHolder> {

    List<OtherUserProfilePojo.UsersPojo.UserSpeakPojo> items = new ArrayList<>();
    Context context;

    //public OnItemClickListener mItemClickListener;
//    private static RecyclerViewClickListener itemListener;

    public LanguageAdapter(Context context, List<OtherUserProfilePojo.UsersPojo.UserSpeakPojo> items) {
        this.items = items;
        this.context = context;


    }


    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.
                from(parent.getContext()).
                inflate(R.layout.language_speak_item, parent, false);

        return new ViewHolder(itemView);

    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {

        OtherUserProfilePojo.UsersPojo.UserSpeakPojo pojo = items.get(position);
        holder.mtvNameLanguage.setText(pojo.getLanguages().getName());
        holder.mtvProficiency.setText(pojo.getProficiency());
        if(pojo.getProficiency().equalsIgnoreCase("beginner"))
            holder.mivProficiency.setImageResource(R.drawable.beginner);
        else if (pojo.getProficiency().equalsIgnoreCase("intermediate"))
            holder.mivProficiency.setImageResource(R.drawable.intermediate);
        else if (pojo.getProficiency().equalsIgnoreCase("professional"))
            holder.mivProficiency.setImageResource(R.drawable.professional);



    }


    @Override
    public int getItemCount() {
        return items.size();
    }


    public class ViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.tvNameLanguage)
        TextView mtvNameLanguage;
        @BindView(R.id.tvProficiency)
        TextViewRegular mtvProficiency;
        @BindView(R.id.ivProficiency)
        ImageView mivProficiency;
        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this,itemView);

        }


    }


}
