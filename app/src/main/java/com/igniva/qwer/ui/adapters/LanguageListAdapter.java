//package com.igniva.qwer.ui.adapters;
//
//import android.graphics.Movie;
//import android.support.v7.widget.RecyclerView;
//import android.view.LayoutInflater;
//import android.view.View;
//import android.view.ViewGroup;
//import android.widget.TextView;
//
//import com.igniva.qwer.R;
//
//import java.util.ArrayList;
//import java.util.List;
//
//import butterknife.BindView;
//
///**
// * Created by karanveer on 21/9/17.
// */
//
//public class LanguageListAdapter extends RecyclerView.Adapter<LanguageListAdapter.MyViewHolder> {
//
//    ArrayList<String>
//
//
//    public class MyViewHolder extends RecyclerView.ViewHolder {
//
//        @BindView(R.id.tv_language_name)
//        TextView mTvLanguageName;
//        @BindView(R.id.tv_language_proficiency)
//        TextView mTvLanguageProficiency;
//        public MyViewHolder(View view) {
//            super(view);
//
//        }
//    }
//
//
//    public LanguageListAdapter(List<Movie> moviesList) {
//        this.moviesList = moviesList;
//    }
//
//    @Override
//    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
//        View itemView = LayoutInflater.from(parent.getContext())
//                .inflate(R.layout.language_list_item, parent, false);
//
//        return new MyViewHolder(itemView);
//    }
//
//    @Override
//    public void onBindViewHolder(MyViewHolder holder, int position) {
//
//    }
//
//    @Override
//    public int getItemCount() {
//        return moviesList.size();
//    }
//}