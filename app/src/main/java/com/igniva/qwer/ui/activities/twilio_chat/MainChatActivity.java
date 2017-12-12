package com.igniva.qwer.ui.activities.twilio_chat;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.igniva.qwer.R;
import com.igniva.qwer.controller.ApiControllerClass;
import com.igniva.qwer.ui.activities.MainActivity;
import com.igniva.qwer.ui.activities.twilio_chat.accesstoken.AlertDialogHandler;
import com.igniva.qwer.ui.activities.twilio_chat.channels.ChannelManager;
import com.igniva.qwer.ui.activities.twilio_chat.listeners.ChannelFindOrCreateListener;
import com.igniva.qwer.ui.activities.twilio_chat.listeners.TaskCompletionListener;
import com.igniva.qwer.utils.CircularImageView;
import com.igniva.qwer.utils.Constants;
import com.igniva.qwer.utils.Global;
import com.igniva.qwer.utils.PreferenceHandler;
import com.igniva.qwer.utils.Utility;
import com.twilio.chat.Channel;
import com.twilio.chat.ChatClient;
import com.twilio.chat.ChatClientListener;
import com.twilio.chat.ErrorInfo;
import com.twilio.chat.StatusListener;
import com.twilio.chat.UserInfo;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit2.Retrofit;

public class MainChatActivity extends AppCompatActivity implements ChatClientListener, ChannelFindOrCreateListener {

    private static final String TAG = "MainChatActivity";
    public String type = "Block";
    @BindView(R.id.ivbackIcon)
    ImageView mIvbackIcon;
    @BindView(R.id.iv_user_image)
    CircularImageView mIvUserImage;
    @BindView(R.id.tv_user_name)
    TextView mTvUserName;
    @BindView(R.id.tv_online_status)
    TextView mTvOnlineStatus;
    @BindView(R.id.iv_videoCall)
    ImageView mIvVideoCall;
    @BindView(R.id.iv_voiceCall)
    ImageView mIvVoiceCall;
    @BindView(R.id.iv_option)
    ImageView mIvOption;
    @BindView(R.id.ll_contact)
    LinearLayout mLlContact;
    String userId,userName,userImage;
    @Inject
    Retrofit retrofit;
    private Context context;
    private Activity mainActivity;
    private ChatClientManager chatClientManager;
    private ChannelManager channelManager;
    private MainChatFragment chatFragment;
    private ProgressDialog progressDialog;
    private String channelName;

    @Override
    protected void onDestroy() {
        super.onDestroy();
        new Handler().post(new Runnable() {
            @Override
            public void run() {
                chatClientManager.shutdown();
                ((Global) getApplicationContext()).getChatClientManager().setChatClient(null);
            }
        });
        Global.activeChannelName = "";
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_chat);
        ((Global) getApplicationContext()).getNetComponent().inject(this);
        ButterKnife.bind(this);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
//        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        //        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                onBackPressed();
//            }
//        });
//        if(getIntent().hasExtra(Constants.ROOM_TITLE)) {
//            getSupportActionBar().setTitle(getIntent().getStringExtra(Constants.ROOM_TITLE));
//        }
        if (getIntent().hasExtra(Constants.ROOM_USER_NAME)) {
            userName=getIntent().getStringExtra(Constants.ROOM_USER_NAME);
//            getSupportActionBar().setTitle(getIntent().getStringExtra(Constants.ROOM_USER_NAME));
            mTvUserName.setText(userName);

        }
        if (getIntent().hasExtra(Constants.TWILIO_RECEAVER_IMAGE)) {
            userImage=getIntent().getStringExtra(Constants.TWILIO_RECEAVER_IMAGE);
            Glide.with(this)
                    .load(userImage)
                    .into(mIvUserImage);
            Log.d(TAG, "onCreate: " + getIntent().getStringExtra(Constants.TWILIO_RECEAVER_IMAGE));
        }

        if (getIntent().hasExtra(Constants.CHANNEL_NAME)) {
            channelName = getIntent().getStringExtra(Constants.CHANNEL_NAME);
            userId = channelName.replace(PreferenceHandler.readString(MainChatActivity.this,PreferenceHandler.PREF_KEY_USER_ID,""),"").replace("_","");

        }
        if(Global.otherUserProfilePojo!=null)
        try {

            if (   Global.otherUserProfilePojo.getUsers().getUser_block().size() > 0) {
                //openBlockMenu("Unblock");
                Log.e("size", Global.otherUserProfilePojo.getUsers().getUser_block().size() + "jfjg");
                type = "Unblock";
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

//        if (getIntent().hasExtra(Constants.CHAT_GCM_ID)) {
//            gcmId = getIntent().getStringExtra(Constants.CHAT_GCM_ID);
//        }
//        if (getIntent().hasExtra(Constants.ROOM_ID)) {
//            ROOMId = getIntent().getStringExtra(Constants.ROOM_ID);
//        }
//        if (getIntent().hasExtra(Constants.ROOM_USER_ID)) {
//        }

        chatFragment = new MainChatFragment();
        getSupportFragmentManager().beginTransaction().add(R.id.fragment_container, chatFragment).commit();

        context = this;
        mainActivity = this;

        channelManager = ChannelManager.getInstance();

        Global.activeChannelName = channelName;
        checkTwilioClient();
        showActivityIndicator("Please wait...");
    }

    @Override
    protected void onStart() {
        super.onStart();
//        new CountDownTimer(5000, 1000) {
//
//            public void onTick(long millisUntilFinished) {
//                //here you can have your logic to set text to edittext
//            }
//
//            public void onFinish() {
//
//            }
//
//        }.start();
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    public void onBackPressed() {
        Utility.hideSoftKeyboard(this);
         if (isTaskRoot()) {
            Intent intent = new Intent(this, MainActivity.class);
            startActivity(intent);
        } else {
            super.onBackPressed();
        }
    }

  /*@Override
  public boolean onCreateOptionsMenu(Menu menu) {
    getMenuInflater().inflate(R.menu.main_chat, menu);
    this.leaveChannelMenuItem = menu.findItem(R.id.action_leave_channel);
    this.leaveChannelMenuItem.setVisible(false);
    this.deleteChannelMenuItem = menu.findItem(R.id.action_delete_channel);
    this.deleteChannelMenuItem.setVisible(false);
    return true;
  }

  @Override
  public boolean onOptionsItemSelected(MenuItem item) {
    int id = item.getItemId();

    if (id == R.id.action_leave_channel) {
      leaveCurrentChannel();
      return true;
    }
    if (id == R.id.action_delete_channel) {
      promptChannelDeletion();
    }

    return super.onOptionsItemSelected(item);
  }*/

    private String getStringResource(int id) {
        Resources resources = getResources();
        return resources.getString(id);
    }

    private void populateChannel() {
        channelManager.setChannelListener(this);
        channelManager.setChannelFindOrCreateListener(this);

        channelManager.createOrGetChannel(channelName);
    }


    private void checkTwilioClient() {

        chatClientManager = ((Global) getApplicationContext()).getChatClientManager();
        if (chatClientManager.getChatClient() == null) {
            initializeClient();
        } else {
            populateChannel();
        }
    }

    private void initializeClient() {
        chatClientManager.connectClient(new TaskCompletionListener<Void, String>() {
            @Override
            public void onSuccess(Void aVoid) {
                populateChannel();
            }

            @Override
            public void onError(String errorMessage) {
                stopActivityIndicator();
                showAlertWithMessage("Client connection error");
            }
        });
    }

    private void promptLogout() {
    /*final String message = getStringResource(R.string.logout_prompt_message);
    runOnUiThread(new Runnable() {
      @Override
      public void run() {
        AlertDialogHandler.displayCancellableAlertWithHandler(message, context,
            new DialogInterface.OnClickListener() {
              @Override
              public void onClick(DialogInterface dialog, int which) {
                SessionManager.getInstance().logoutUser();
                showLoginActivity();
              }
            });
      }
    });*/

    }

    private void stopActivityIndicator() {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                try {
                    if (progressDialog.isShowing()) {
                        progressDialog.dismiss();
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    private void showActivityIndicator(final String message) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                progressDialog = new ProgressDialog(MainChatActivity.this.mainActivity);
                progressDialog.setMessage(message);
                progressDialog.show();
                progressDialog.setCanceledOnTouchOutside(false);
                progressDialog.setCancelable(false);
            }
        });
    }

    private void showAlertWithMessage(final String message) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                AlertDialogHandler.displayAlertWithMessage(message, context);
            }
        });
    }

    @Override
    public void onChannelAdd(Channel channel) {
        System.out.println("Channel Added");
    }

    @Override
    public void onChannelDelete(final Channel channel) {
        System.out.println("Channel Deleted");
        Channel currentChannel = chatFragment.getCurrentChannel();
        if (channel.getSid().contentEquals(currentChannel.getSid())) {
            chatFragment.setCurrentChannel(null, null);
        }
    }

    @Override
    public void onChannelInvite(Channel channel) {

    }

    @Override
    public void onChannelSynchronizationChange(Channel channel) {

    }

    @Override
    public void onError(ErrorInfo errorInfo) {
        System.out.println("ErrorInfo " + errorInfo.toString());
    }

    @Override
    public void onUserInfoChange(UserInfo userInfo, UserInfo.UpdateReason updateReason) {

    }

    @Override
    public void onClientSynchronization(ChatClient.SynchronizationStatus synchronizationStatus) {

    }

    @Override
    public void onToastNotification(String s, String s1) {

    }

    @Override
    public void onToastSubscribed() {

    }

    @Override
    public void onToastFailed(ErrorInfo errorInfo) {

    }

    @Override
    public void onConnectionStateChange(ChatClient.ConnectionState connectionState) {

    }

    @Override
    public void onChannelChange(Channel channel) {
    }

    @Override
    public void channelCreated(Channel channel) {
        setChannelInFragment(channel);
    }

    @Override
    public void channelFind(Channel channel) {
        setChannelInFragment(channel);
    }

    private void setChannelInFragment(Channel channel) {
        chatFragment.setCurrentChannel(channel, new StatusListener() {
            @Override
            public void onSuccess() {
                Log.e(TAG, "onSuccess: Channel set in fragment");
                stopActivityIndicator();
            }

            @Override
            public void onError(ErrorInfo errorInfo) {
                super.onError(errorInfo);
                stopActivityIndicator();
            }
        });
    }

    @OnClick(R.id.ivbackIcon)
    public void onMIvbackIconClicked() {
        onBackPressed();
    }

//    @OnClick(R.id.iv_user_image)
//    public void onMIvUserImageClicked() {
//         startActivity(new Intent(MainChatActivity.this, OtherUserProfileActivity.class).putExtra("userId", userId));
//
//    }

    @OnClick(R.id.iv_videoCall)
    public void onMIvVideoCallClicked() {
        try {
            ApiControllerClass.getVideoToken(MainChatActivity.this, retrofit, PreferenceHandler.readString(MainChatActivity.this, PreferenceHandler.PREF_KEY_USER_ID, ""),userId+"",userName+"",userImage+"");
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    @OnClick(R.id.iv_voiceCall)
    public void onMIvVoiceCallClicked() {
        try {
            ApiControllerClass.sendTwilioVoiceNotification(MainChatActivity.this, retrofit, PreferenceHandler.readString(MainChatActivity.this, PreferenceHandler.PREF_KEY_USER_ID, ""),userId+"",userName,userImage);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    @OnClick(R.id.iv_option)
    public void onMIvOptionClicked() {
//        if (userId != null && userId.length() > 0)
            Utility.openBlockMenu(MainChatActivity.this, retrofit, type, mIvOption,  Integer.valueOf(userId.trim()));
    }

}
