package com.igniva.qwer.ui.activities;

import android.os.Bundle;
import android.widget.Button;

import com.igniva.qwer.R;
import com.igniva.qwer.controller.ApiControllerClass;
import com.igniva.qwer.model.OtherUserProfilePojo;
import com.igniva.qwer.ui.views.TextViewBold;
import com.igniva.qwer.utils.CircularImageView;
import com.igniva.qwer.utils.Global;
import com.igniva.qwer.utils.Utility;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit2.Response;
import retrofit2.Retrofit;

public class ConnectionAcceptedActivity extends BaseActivity {

    @Inject
    Retrofit retrofit;

    @BindView(R.id.ivImage)
    CircularImageView mivImage;
    @BindView(R.id.tvName)
    TextViewBold mtvName;
    @BindView(R.id.btn_signUp)
    Button mbtnSignUp;
    @BindView(R.id.btnHome)
    Button mbtnHome;

    @OnClick(R.id.btn_signUp)
    public void openChat(){
        ApiControllerClass.getOtherUserProfile(retrofit, ConnectionAcceptedActivity.this, userId,true);

//        Utility.showToastMessageLong(ConnectionAcceptedActivity.this,getResources().getString(R.string.coming_soon));
    }
    @OnClick(R.id.btnHome)
    public void close(){
       finish();
    }
    @Override
    protected void setUpLayout() {

    }

    @Override
    protected void setDataInViewObjects() {

    }

    @Override
    protected void setUpToolbar() {

    }
    int userId;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        ((Global) getApplication()).getNetComponent().inject(this);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_connection_accepted);
        ButterKnife.bind(this);

        setUpLayout();
        setDataInViewObjects();

        if (getIntent() != null && getIntent().hasExtra("userId")) {
              userId = getIntent().getIntExtra("userId", 0);
            ApiControllerClass.getConnectionUserProfile(retrofit, ConnectionAcceptedActivity.this, userId);
        }

    }


    public void setData(Response<OtherUserProfilePojo> response) {


        mtvName.setText(response.body().getUsers().getName());
        if(response.body().getUsers().getUser_image()!=null && response.body().getUsers().getUser_image().size()>0)
            Utility.setUserImage( ConnectionAcceptedActivity.this, mivImage,response.body().getUsers().getUser_image() );

//        Glide.with(ConnectionAcceptedActivity.this).load(response.body().getUsers().getUser_image().get(0).getImage()).into(mivImage);


    }


}
