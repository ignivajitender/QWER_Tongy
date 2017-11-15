package com.igniva.qwer.ui.activities;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.igniva.qwer.R;
import com.igniva.qwer.controller.ApiControllerClass;
import com.igniva.qwer.model.CommentPojo;
import com.igniva.qwer.model.PostDetailPojo;
import com.igniva.qwer.ui.adapters.CommentsAdapter;
import com.igniva.qwer.utils.Global;
import com.igniva.qwer.utils.Utility;

import java.util.ArrayList;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit2.Retrofit;

/**
 * Created by tanmey on 14/11/17.
 */

public class CommentsActivity extends AppCompatActivity {

    @Inject
    public Retrofit retrofit;
    CommentsAdapter adapter;

    ArrayList<CommentPojo> commentList;
    @BindView(R.id.ivbackIcon)
    ImageView ivbackIcon;
    @BindView(R.id.tv_tap_to_rename)
    TextView tvTapToRename;
    @BindView(R.id.rv_list)
    RecyclerView mrecyclerView;
    @BindView(R.id.et_comment)
    EditText etComment;
    PostDetailPojo.DataPojo pojo;
    @BindView(R.id.iv_done)
    ImageView ivDone;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_comment);
        ((Global) getApplicationContext()).getNetComponent().inject(this);
        ButterKnife.bind(this);
        if (getIntent().hasExtra("dataPojo")) {
            pojo = (PostDetailPojo.DataPojo) getIntent().getSerializableExtra("dataPojo");
            if (pojo != null) {
                commentList = pojo.getPost_comment();
                tvTapToRename.setText(pojo.getTitle());
                 setUpLayout();
                setData(commentList);
            } else
                finish();

//        } else if(getIntent().hasExtra("pojoItem")){
//            PostPojo.PostDataPojo.DataBean dataPojo = (PostPojo.PostDataPojo.DataBean) getIntent().getSerializableExtra("dataPojo");
//            if (dataPojo != null) {
//                tvTapToRename.setText(dataPojo.getTitle());
//                  setUpLayout();
//                 ApiControllerClass.getPostDetail(dataPojo.getId(), retrofit,   this);
//
//            } else
//                finish();
//
        }else
            finish();
    }

    public void setUpLayout() {
        LinearLayoutManager mLayoutManager = new LinearLayoutManager(this);
        mrecyclerView.setLayoutManager(mLayoutManager);
        mrecyclerView.setItemAnimator(new DefaultItemAnimator());

//        tvTapToRename.setText(getString(R.string.));
//        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
//            @Override
//            public void onRefresh() {
//                // Refresh items
//                swipeRefreshLayout.setRefreshing(false);
//                 adapter = null;
//             }
//        });

    }


    public void setData( ArrayList<CommentPojo>  commentList) {
        if (commentList != null && commentList.size() > 0) {
//            if (adapter == null) {
            adapter = new CommentsAdapter(this, commentList, retrofit);
            mrecyclerView.setAdapter(adapter);
//            } else
//                adapter.addAll(responsePojo.getData());
            mrecyclerView.setVisibility(View.VISIBLE);
            mrecyclerView.scrollToPosition(commentList.size()-1);
             etComment.setText("");
        }

    }

    @Override
    public void onBackPressed() {
        Utility.hideSoftKeyboard(this);
        super.onBackPressed();
    }

    @OnClick(R.id.ivbackIcon)
    public void onViewClicked() {
        onBackPressed();
    }

    @OnClick(R.id.iv_done)
    public void onDoneClicked() {
        if(etComment.getText().toString().trim().length()>0)
            ApiControllerClass.sendComment(retrofit,CommentsActivity.this,etComment.getText().toString().trim(),pojo.getId());
     }
}
