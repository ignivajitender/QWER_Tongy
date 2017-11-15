package com.igniva.qwer.ui.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.igniva.qwer.R;
import com.igniva.qwer.controller.ApiControllerClass;
import com.igniva.qwer.model.PostDetailPojo;
import com.igniva.qwer.utils.Global;
import com.igniva.qwer.utils.Utility;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit2.Retrofit;

public class PostDetailActivity extends BaseActivity {

    @BindView(R.id.tv_tap_to_rename)
    TextView mtvTitle;
    @BindView(R.id.ivbackIcon)
    ImageView ivbackIcon;
    @BindView(R.id.ll_change_title)
    LinearLayout mllChangeTitle;
    @BindView(R.id.toolbar_top)
    Toolbar mtoolbarTop;
    @BindView(R.id.iv_profile)
    ImageView mivProfile;
    @BindView(R.id.tv_name)
    TextView mtvName;
    @BindView(R.id.tv_date)
    TextView mtvDate;
    @BindView(R.id.ib_chat)
    TextView mibChat;
    @BindView(R.id.tv_fav)
    ImageView mtvFav;
    @BindView(R.id.iv_image)
    ImageView mivImage;
    @BindView(R.id.tv_title)
    TextView mtvPostTitle;
    @BindView(R.id.desc)
    TextView mtvDesc;
    @BindView(R.id.llInfoBottom)
    LinearLayout mllInfoBottom;
    @BindView(R.id.cardView2)
    CardView mcardView2;
    @BindView(R.id.tv_postType)
    TextView mtvPostType;
    @BindView(R.id.ivPostImage)
    ImageView mivPostImage;
    @BindView(R.id.tvTypeOfClassOrPresenter)
    TextView mtvTypeOfClassOrPresenter;
    @BindView(R.id.tvPrice)
    TextView mtvPrice;
    @BindView(R.id.tvTimeAndDate)
    TextView mtvTimeAndDate;
    @BindView(R.id.ivPrice)
    ImageView mivPrice;
    @BindView(R.id.ivDate)
    ImageView mivDate;
    @BindView(R.id.ivType)
    ImageView mivType;

    @BindView(R.id.view)
    View mView;

    @OnClick(R.id.ivbackIcon)
    public void back() {
        Utility.hideSoftKeyboard(PostDetailActivity.this);
        onBackPressed();
    }

    @Inject
    Retrofit retrofit;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        ((Global) getApplication()).getNetComponent().inject(this);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post_detail);
        ButterKnife.bind(this);
        setUpToolbar();
        setUpLayout();
        setDataInViewObjects();
    }

    @Override
    protected void setUpLayout() {
        if (getIntent() != null && getIntent().hasExtra("post_id")) {
            Log.e("post_id", String.valueOf(getIntent().getIntExtra("post_id", 0)));
            int post_id = getIntent().getIntExtra("post_id", 0);
            ApiControllerClass.getPostDetail(post_id, retrofit, PostDetailActivity.this);
        }

    }

    @Override
    public void setDataInViewObjects() {


    }

    @Override
    protected void setUpToolbar() {
        mtvTitle.setText(getResources().getString(R.string.post_details));
    }

    /**
     * set post detail data coming from server
     *
     * @param data
     */
    public void setData(final PostDetailPojo.DataPojo data) {
        if (data != null) {

            if (data.getPost_type().equalsIgnoreCase(getResources().getString(R.string.meeting))) {
                mtvPostType.setBackgroundColor(getResources().getColor(R.color.bg_blue));
                mtvPostType.setText(data.getPost_type());
                mivPostImage.setVisibility(View.GONE);
                mivPrice.setImageResource(R.drawable.calendar_details);
                mtvPrice.setText("Date and Time\n"+Utility.getDatePostDetail(data.getStart_date_time(),PostDetailActivity.this)+" To "+Utility.getDatePostDetail(data.getEnd_date_time(),PostDetailActivity.this)+"\n"
                        +Utility.getTimePostDetail(data.getStart_date_time(),PostDetailActivity.this)+" to "+Utility.getTimePostDetail(data.getEnd_date_time(),PostDetailActivity.this));
                mivDate.setImageResource(R.drawable.location__details);
                mtvTimeAndDate.setText("Location\n"+data.getClass_location());
                mtvTimeAndDate.setCompoundDrawablesWithIntrinsicBounds(0,0,R.drawable.location,0);
                mivType.setImageResource(R.drawable.people_details);
                StringBuilder builder=new StringBuilder();
                if(data.getPost_member()!=null && data.getPost_member().size()>0)
                {

                    for(int i=0;i<data.getPost_member().size();i++)
                        builder.append(data.getPost_member().get(i).getPresenter()+"\n");
                    }
                mtvTypeOfClassOrPresenter.setText("Presenter\n"+builder);
                mView.setVisibility(View.VISIBLE);
                mtvTimeAndDate.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        startActivity(new Intent(PostDetailActivity.this,LocationActivity.class).putExtra("dataPojo",data));
                    }
                });
            }
            if (data.getPost_type().equalsIgnoreCase(getResources().getString(R.string.teaching))) {
                mtvPostType.setBackgroundColor(getResources().getColor(R.color.yellow_color));
                mtvPostType.setText(data.getPost_type());
                mivPostImage.setVisibility(View.GONE);
                mtvPrice.setText("Price\n$"+data.getPrice());
                mtvTimeAndDate.setText("Date and Time\n"+Utility.getDatePostDetail(data.getStart_date_time(),PostDetailActivity.this)+" To "+Utility.getDatePostDetail(data.getEnd_date_time(),PostDetailActivity.this)+"\n"
                        +Utility.getTimePostDetail(data.getStart_date_time(),PostDetailActivity.this)+" to "+Utility.getTimePostDetail(data.getEnd_date_time(),PostDetailActivity.this));

                if(data.getClass_type().equalsIgnoreCase("physical")) {
                    mtvTypeOfClassOrPresenter.setText("Type of Class\n" + data.getClass_type() + "\nLocation\n" + data.getClass_location());
                    mtvTypeOfClassOrPresenter.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.location, 0);
                    mtvTypeOfClassOrPresenter.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            startActivity(new Intent(PostDetailActivity.this,LocationActivity.class).putExtra("dataPojo",data));
                        }
                    });
                }
                else
                    mtvTypeOfClassOrPresenter.setText("Type of Class\n" + data.getClass_type());

                mView.setVisibility(View.VISIBLE);

            }
            if (data.getPost_type().equalsIgnoreCase(getResources().getString(R.string.other))) {
                mtvPostType.setBackgroundColor(getResources().getColor(R.color.other_red_color));
                mtvPostType.setText(data.getPost_type());
                mivPostImage.setVisibility(View.VISIBLE);
                Glide.with(PostDetailActivity.this).load(data.getImage()).into(mivPostImage);
                mivPrice.setVisibility(View.GONE);
                mtvPrice.setVisibility(View.GONE);
                mivDate.setVisibility(View.GONE);
                mtvTimeAndDate.setVisibility(View.GONE);
                mivType.setVisibility(View.GONE);
                mtvTypeOfClassOrPresenter.setVisibility(View.GONE);
                mView.setVisibility(View.GONE);
            }

            if(data.getPost_user().getUser_image()!=null && data.getPost_user().getUser_image().size()>0)
            Glide.with(PostDetailActivity.this).load(data.getPost_user().getUser_image().get(0).getImage()).into(mivProfile);
            if(data.getPost_comment_count()!=null && data.getPost_comment_count().size()>0)
                mibChat.setText(data.getPost_comment_count().get(0).getCount());
            else
                mibChat.setText("0");

            if(data.getPost_fav()!=null && data.getPost_fav().size()>0)
                mtvFav.setImageResource(R.drawable.liked);
            else
                mtvFav.setImageResource(R.drawable.like);

            mtvName.setText(data.getPost_user().getName());
            mtvDate.setText(Utility.getTimeAgoPost(data.getCreated_at(),PostDetailActivity.this));
            mtvPostTitle.setText(data.getTitle());
            mtvDesc.setText(data.getDescription());
            mtvFav.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    ApiControllerClass.markFavoriteUnfavorite(retrofit, PostDetailActivity.this, data.getPost_fav(),mtvFav, data.getId());
                }
            });
            mibChat.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
//                    startActivity(new Intent(PostDetailActivity.this,CommentsActivity.class).putExtra("dataPojo",data));

                }
            });

        }
    }
}
