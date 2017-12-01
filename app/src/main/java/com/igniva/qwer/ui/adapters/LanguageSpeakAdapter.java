package com.igniva.qwer.ui.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.igniva.qwer.R;
import com.igniva.qwer.model.OtherUserProfilePojo;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by igniva-android13 on 4/1/17.
 */

public class LanguageSpeakAdapter extends RecyclerView.Adapter<LanguageSpeakAdapter.ViewHolder> {

    List<OtherUserProfilePojo.UsersPojo.UserSpeakPojo> items = new ArrayList<>();
    Context context;
    String type;
    //public OnItemClickListener mItemClickListener;
//    private static RecyclerViewClickListener itemListener;

    public LanguageSpeakAdapter(Context context, List<OtherUserProfilePojo.UsersPojo.UserSpeakPojo> items, String learn) {
        this.items = items;
        this.context = context;
        this.type = learn;

    }


    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.
                from(parent.getContext()).
                inflate(R.layout.language_item, parent, false);

        return new ViewHolder(itemView);

    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {

        OtherUserProfilePojo.UsersPojo.UserSpeakPojo pojo = items.get(position);
        holder.dayName.setText(pojo.getLanguages().getName());
        if (type.equalsIgnoreCase("learn"))
            holder.mllLanguage.setBackground(context.getResources().getDrawable(R.drawable.edit_text_rounded_corners_grey));
        else
            holder.mllLanguage.setBackground(context.getResources().getDrawable(R.drawable.edit_text_grey_rounded_corners));

    }


    @Override
    public int getItemCount() {
        return items.size();
    }


    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView dayName;
        LinearLayout mllLanguage;

        public ViewHolder(View itemView) {
            super(itemView);

            dayName = (TextView) itemView.findViewById(R.id.tvdayName);
            mllLanguage = (LinearLayout) itemView.findViewById(R.id.llLanguage);
        }


    }

   /* public void setOnItemClickListener(final OnItemClickListener mItemClickListener) {
        this.mItemClickListener = mItemClickListener;
    }

    public interface OnItemClickListener {
        void onItemClick(View view, int position);
    }
*/

}
