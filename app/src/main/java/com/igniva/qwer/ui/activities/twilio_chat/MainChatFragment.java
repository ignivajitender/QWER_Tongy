package com.igniva.qwer.ui.activities.twilio_chat;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.igniva.qwer.R;
import com.igniva.qwer.controller.ApiControllerClass;
import com.igniva.qwer.ui.activities.twilio_chat.messages.JoinedStatusMessage;
import com.igniva.qwer.ui.activities.twilio_chat.messages.LeftStatusMessage;
import com.igniva.qwer.ui.activities.twilio_chat.messages.MessageAdapter;
import com.igniva.qwer.ui.activities.twilio_chat.messages.StatusMessage;
import com.igniva.qwer.utils.Global;
import com.igniva.qwer.utils.Utility;
import com.twilio.chat.CallbackListener;
import com.twilio.chat.Channel;
import com.twilio.chat.ChannelListener;
import com.twilio.chat.ErrorInfo;
import com.twilio.chat.Member;
import com.twilio.chat.Message;
import com.twilio.chat.Messages;
import com.twilio.chat.StatusListener;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import retrofit2.Retrofit;

public class MainChatFragment extends Fragment implements ChannelListener {
    private static final String TAG = "MainChatFragment";
    Context context;
    Activity mainActivity;
    ImageView sendButton;
    ListView messagesListView;
    EditText messageTextEdit;

    MessageAdapter mAdapter;
//    MessageAdapterOld messageAdapter;
    Channel currentChannel;
    Messages messagesObject;

    RecyclerView recyclerView;
    RecyclerView.LayoutManager mLayoutManager;
    List<Message> arrMessageList = new ArrayList<>();
    @Inject
    Retrofit retrofit;

    TextView tvTyping;
    public MainChatFragment() {
    }

    public static MainChatFragment newInstance() {
        MainChatFragment fragment = new MainChatFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        context = this.getActivity();
        ((Global)getActivity().getApplicationContext()).getNetComponent().inject(this);
        mainActivity = this.getActivity();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_main_chat, container, false);
        tvTyping = (TextView) view.findViewById(R.id.tv_typing);
        recyclerView = (RecyclerView) view.findViewById(R.id.recycler_view);
        sendButton = (ImageView) view.findViewById(R.id.buttonSend);
        messagesListView = (ListView) view.findViewById(R.id.listViewMessages);
        messageTextEdit = (EditText) view.findViewById(R.id.editTextMessage);

//        messageAdapter = new MessageAdapterOld(mainActivity);
//        messagesListView.setAdapter(messageAdapter);
        setUpListeners();
        setMessageInputEnabled(false);


        setupRecycleView();
        return view;
    }

    private void setupRecycleView() {
        mAdapter = new MessageAdapter(mainActivity);
        mLayoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(mAdapter);
    }


    public Channel getCurrentChannel() {
        return currentChannel;
    }

    public void setCurrentChannel(Channel currentChannel, final StatusListener handler) {
        if (currentChannel == null) {
            this.currentChannel = null;
            return;
        }
        if (!currentChannel.equals(this.currentChannel)) {
            setMessageInputEnabled(false);
            this.currentChannel = currentChannel;
            this.currentChannel.addListener(this);
            if (this.currentChannel.getStatus() == Channel.ChannelStatus.JOINED) {
                synchronizeMessages(handler);
            } else {
                this.currentChannel.join(new StatusListener() {
                    @Override
                    public void onSuccess() {
                        synchronizeMessages(handler);
                    }

                    @Override
                    public void onError(ErrorInfo errorInfo) {
                        handler.onError(errorInfo);
                    }
                });
            }
        }
    }

    private void synchronizeMessages(final StatusListener handler) {
        currentChannel.synchronize(new CallbackListener<Channel>() {
            @Override
            public void onError(ErrorInfo errorInfo) {
                handler.onError(errorInfo);
                Log.e(TAG, "onError: Channel sync failed");
            }

            @Override
            public void onSuccess(Channel result) {
                Log.e(TAG, "Channel sync success for " + result.getFriendlyName() + " now can get messages and members objects");
                currentChannel = result;
                loadMessages(handler);
                // should be non-null now
            }
        });
    }

    private void loadMessages(final StatusListener handler) {
        this.messagesObject = this.currentChannel.getMessages();

        if (messagesObject != null) {
            messagesObject.getLastMessages(100, new CallbackListener<List<Message>>() {
                @Override
                public void onSuccess(List<Message> messageList) {
//                    messageAdapter.setMessages(messageList);
                    mAdapter.setMessages(messageList);
                    setMessageInputEnabled(true);
//                    messageTextEdit.requestFocus();
                    handler.onSuccess();
                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            scrollToBottom();
                        }
                    },200);

                }

                @Override
                public void onError(ErrorInfo errorInfo) {
                    super.onError(errorInfo);
                    handler.onError(errorInfo);
                    try {
                        Log.e(TAG, "onError: " + errorInfo.toString());
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            });
        }
    }

    private void setUpListeners() {

        sendButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!((MainChatActivity)getActivity()).type.equalsIgnoreCase("block")){
                    Utility.showToastMessageLong(getActivity(),getActivity().getResources().getString(R.string.user_is_blocked));
                    return;
                 }else if(TextUtils.isEmpty(messageTextEdit.getText().toString().trim())){
//                    Utility.showToastMessageShort(mainActivity,"Please write some message.");
                    return;
                }else {
                    sendMessage();
                }
            }
        });
        messageTextEdit.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                return false;
            }
        });

        messageTextEdit.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                Log.d(TAG, "beforeTextChanged: "+charSequence);
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                Log.d(TAG, "onTextChanged: "+charSequence);
                if(TextUtils.isEmpty(charSequence)){
                    Log.d(TAG, "onTextChanged: DISABLE BUTTON");
                    setDesableSendButton();
                }else {
                    Log.d(TAG, "onTextChanged: ENABLE BUTTON");
                    setEnableSendButton();
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        setDesableSendButton();
    }

    private void sendMessage() {
        String messageText = getTextInput();
        if (messageText.length() == 0) {
            return;
        }
        Message newMessage = this.messagesObject.createMessage(messageText);
        this.messagesObject.sendMessage(newMessage, null);
        clearTextInput();
        //send notification to other user
        JSONObject jsonObject = new JSONObject();
        JSONArray array = new JSONArray();
        try {
            array.put(new JSONObject().put("id", ((MainChatActivity) getActivity()).userId));
            jsonObject.put("identity", array);
            jsonObject.put("channel_name", currentChannel.getUniqueName());
        } catch (JSONException e) {
            e.printStackTrace();
        }
        ApiControllerClass.sendTwilioChatNotification(retrofit,mainActivity,jsonObject.toString());
    }

/*
    private void sendNotification(String messageText) {

        JSONObject jsonObject = new JSONObject();

        try {
            JSONObject dataObject = new JSONObject();
            String userName = PreferenceHandler.readString(getActivity(), PreferenceHandler.PREF_KEY_USER_NAME, "User");
            dataObject.put("roomId", ((MainChatActivity) getActivity()).getIntent().getStringExtra(Constants.ROOM_ID));
            dataObject.put("notification", 25);
            dataObject.put("title", userName + " sent you a message");
            dataObject.put("sender_name", userName);
            dataObject.put("message", messageText);
             jsonObject.put("to", ((MainChatActivity) getActivity()).gcmId);
            jsonObject.put("data", dataObject);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        Log.e(TAG, "callClick: " + jsonObject.toString());


        ApiControllerClass.sendFcmNotification(getActivity(), jsonObject.toString());
    }
*/

    private void setMessageInputEnabled(final boolean enabled) {
        mainActivity.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                MainChatFragment.this.sendButton.setEnabled(enabled);
                MainChatFragment.this.messageTextEdit.setEnabled(enabled);
            }
        });
    }

    private String getTextInput() {
        return messageTextEdit.getText().toString();
    }

    private void clearTextInput() {
        messageTextEdit.setText("");
    }

    @Override
    public void onMessageAdd(Message message) {
        mAdapter.notifyDataSetChanged();
//        messageAdapter.addMessage(message);
        mAdapter.addMessage(message);
        scrollToBottom();
    }

    @Override
    public void onMemberJoin(Member member) {
        StatusMessage statusMessage = new JoinedStatusMessage(member.getUserInfo().getIdentity());
//        this.messageAdapter.addStatusMessage(statusMessage);
        this.mAdapter.addStatusMessage(statusMessage);
    }

    @Override
    public void onMemberDelete(Member member) {
        StatusMessage statusMessage = new LeftStatusMessage(member.getUserInfo().getIdentity());
//        this.messageAdapter.addStatusMessage(statusMessage);
        this.mAdapter.addStatusMessage(statusMessage);
    }

    @Override
    public void onMessageChange(Message message) {
    }

    @Override
    public void onMessageDelete(Message message) {
    }

    @Override
    public void onMemberChange(Member member) {
    }

    @Override
    public void onTypingStarted(Member member) {
        Log.d(TAG, "onTypingStarted: ");
        tvTyping.setVisibility(View.VISIBLE);
    }

    @Override
    public void onTypingEnded(Member member) {
        Log.d(TAG, "onTypingEnded: ");
        tvTyping.setVisibility(View.GONE);
    }

    @Override
    public void onSynchronizationChange(Channel channel) {
    }

    void scrollToBottom(){
        mLayoutManager.scrollToPosition(mAdapter.getItemCount() - 1);
    }

    public void setEnableSendButton(){
        sendButton.setEnabled(true);
        sendButton.setAlpha(1f);
    }
    public void setDesableSendButton(){
        sendButton.setEnabled(false);
        sendButton.setAlpha(0.5f);
    }
}
