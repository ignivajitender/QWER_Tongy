package com.igniva.qwer.ui.activities.twilio_chat.channels;

import android.os.Handler;
import android.os.Looper;
import android.util.Log;

import com.igniva.qwer.ui.activities.twilio_chat.ChatClientManager;
import com.igniva.qwer.ui.activities.twilio_chat.listeners.ChannelFindOrCreateListener;
import com.igniva.qwer.utils.Global;
import com.twilio.chat.CallbackListener;
import com.twilio.chat.Channel;
import com.twilio.chat.Channel.ChannelType;
import com.twilio.chat.ChatClient;
import com.twilio.chat.ChatClientListener;
import com.twilio.chat.ErrorInfo;
import com.twilio.chat.StatusListener;
import com.twilio.chat.UserInfo;

public class ChannelManager implements ChatClientListener {

    private static final String TAG = "ChannelManager";

    private static ChannelManager sharedManager = new ChannelManager();
    private ChatClientManager chatClientManager;
    private ChatClientListener listener;
    private ChannelFindOrCreateListener channelFindOrCreateListener;
    private Handler handler;
    public Channel mChannel;
    public String channelNameToCreate;

    private ChannelManager() {
        this.chatClientManager = Global.sAppContext.getChatClientManager();
        this.listener = this;
        handler = setupListenerHandler();
    }

    public static ChannelManager getInstance() {
        return sharedManager;
    }

    public void setChannelListener(ChatClientListener listener) {
        this.listener = listener;
    }

    public void setChannelFindOrCreateListener(ChannelFindOrCreateListener channelFindOrCreateListener) {
        this.channelFindOrCreateListener = channelFindOrCreateListener;
    }

    public void createOrGetChannel(final String channelNameToCreate) {
        if (this.chatClientManager == null) {
            return;
        }
        this.channelNameToCreate = channelNameToCreate;

        handler.post(new Runnable() {
            @Override
            public void run() {
                chatClientManager.getChatClient().getChannels().getChannel(channelNameToCreate, new CallbackListener<Channel>() {
                    @Override
                    public void onSuccess(Channel channel) {
                        if (channel != null) {
                            mChannel = channel;
                            if (channelFindOrCreateListener != null ){
                                channelFindOrCreateListener.channelCreated(mChannel);
                            }
                        }
                    }

                    @Override
                    public void onError(ErrorInfo errorInfo) {
                        super.onError(errorInfo);
                        createChannelWithName(channelNameToCreate, new StatusListener() {
                            @Override
                            public void onSuccess() {
                                if (mChannel != null) {
                                    Log.e(TAG, "onSuccess: Channel recieved");
                                    if (channelFindOrCreateListener != null ){
                                        channelFindOrCreateListener.channelFind(mChannel);
                                    }
                                }
                            }

                            @Override
                            public void onError(ErrorInfo errorInfo) {
                                super.onError(errorInfo);
                                try {
                                    Log.e(TAG, "onError: " + errorInfo.toString());
                                } catch (Exception e) {
                                    e.printStackTrace();
                                }
                            }
                        });
                    }
                });

            }
        });
    }

    public void createChannelWithName(String name, final StatusListener handler) {
        chatClientManager.getChatClient().getChannels()
                .channelBuilder()
                .withUniqueName(name)
                .withType(ChannelType.PUBLIC)
                .build(new CallbackListener<Channel>() {
                    @Override
                    public void onSuccess(Channel newChannel) {
                        mChannel = newChannel;
                        handler.onSuccess();
                    }

                    @Override
                    public void onError(ErrorInfo errorInfo) {
                        handler.onError(errorInfo);
                        try {
                            Log.e(TAG, "onError: " + errorInfo.toString());
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                });
    }

    @Override
    public void onChannelAdd(Channel channel) {
        if (listener != null) {
            listener.onChannelAdd(channel);
        }
    }

    @Override
    public void onChannelInvite(Channel channel) {

    }

    @Override
    public void onChannelChange(Channel channel) {
        if (listener != null) {
            listener.onChannelChange(channel);
        }
    }

    @Override
    public void onChannelDelete(Channel channel) {
        if (listener != null) {
            listener.onChannelDelete(channel);
        }
    }

    @Override
    public void onChannelSynchronizationChange(Channel channel) {
        if (listener != null) {
            listener.onChannelSynchronizationChange(channel);
        }
    }

    @Override
    public void onError(ErrorInfo errorInfo) {
        if (listener != null) {
            listener.onError(errorInfo);
        }
    }

    @Override
    public void onUserInfoChange(UserInfo userInfo, UserInfo.UpdateReason updateReason) {
        if (listener != null) {
            listener.onUserInfoChange(userInfo, updateReason);
        }
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
        Log.e("ChannelManager","onConnectionStateChange"+connectionState.getValue());
    }

    private Handler setupListenerHandler() {
        Looper looper;
        Handler handler;
        if ((looper = Looper.myLooper()) != null) {
            handler = new Handler(looper);
        } else if ((looper = Looper.getMainLooper()) != null) {
            handler = new Handler(looper);
        } else {
            throw new IllegalArgumentException("Channel Listener must have a Looper.");
        }
        return handler;
    }
}
