package com.igniva.qwer.ui.activities.twilio_chat;

import android.content.Context;

import com.igniva.qwer.ui.activities.twilio_chat.accesstoken.AccessTokenFetcher;
import com.igniva.qwer.ui.activities.twilio_chat.listeners.TaskCompletionListener;
import com.igniva.qwer.utils.Global;
import com.igniva.qwer.utils.PreferenceHandler;
import com.twilio.accessmanager.AccessManager;
import com.twilio.chat.ChatClient;
import com.twilio.chat.ChatClientListener;

import javax.inject.Inject;

import retrofit2.Retrofit;


public class ChatClientManager implements AccessManager.Listener, AccessManager.TokenUpdateListener {
    private ChatClient chatClient;
    private Context context;
    private AccessManager accessManager;
    private AccessTokenFetcher accessTokenFetcher;
    private ChatClientBuilder chatClientBuilder;
    @Inject
    Retrofit retrofit;

    public ChatClientManager(Context context) {
        this.context = context;
        ((Global)context.getApplicationContext()).getNetComponent().inject(this);
        this.accessTokenFetcher = new AccessTokenFetcher(this.context);
        this.chatClientBuilder = new ChatClientBuilder(this.context);
    }

    public void setClientListener(ChatClientListener listener) {
        if (this.chatClient != null) {
            this.chatClient.setListener(listener);
        }
    }

    public ChatClient getChatClient() {
        return this.chatClient;
    }

    public void setChatClient(ChatClient client) {
        this.chatClient = client;
    }

    public void connectClient(final TaskCompletionListener<Void, String> listener) {
        ChatClient.setLogLevel(android.util.Log.DEBUG);

        String loginUserId = "" + PreferenceHandler.readString(context, PreferenceHandler.PREF_KEY_USER_ID, "");
        accessTokenFetcher.fetch(retrofit,loginUserId, new TaskCompletionListener<String, String>() {
            @Override
            public void onSuccess(String token) {
                createAccessManager(token);
                buildClient(token, listener);
            }

            @Override
            public void onError(String message) {
                if (listener != null) {
                    listener.onError(message);
                }
            }
        });
    }

    private void buildClient(String token, final TaskCompletionListener<Void, String> listener) {
        chatClientBuilder.build(token, new TaskCompletionListener<ChatClient, String>() {
            @Override
            public void onSuccess(ChatClient chatClient) {
                ChatClientManager.this.chatClient = chatClient;
                listener.onSuccess(null);
            }

            @Override
            public void onError(String message) {
                listener.onError(message);
            }
        });
    }

    private void createAccessManager(String token) {
        this.accessManager = new AccessManager(token, this);
        accessManager.addTokenUpdateListener(this);
    }

    @Override
    public void onTokenWillExpire(AccessManager accessManager) {

    }

    @Override
    public void onTokenExpired(AccessManager accessManager) {
        System.out.println("token expired.");
        String loginUserId = "" + PreferenceHandler.readString(context, PreferenceHandler.PREF_KEY_USER_ID, "");
        accessTokenFetcher.fetch(retrofit,loginUserId, new TaskCompletionListener<String, String>() {
            @Override
            public void onSuccess(String token) {
                ChatClientManager.this.accessManager.updateToken(token);
            }

            @Override
            public void onError(String message) {
                System.out.println("Error trying to fetch token: " + message);
            }
        });
    }

    @Override
    public void onError(AccessManager accessManager, String s) {
        System.out.println("token error: " + s);
    }

    @Override
    public void onTokenUpdated(String s) {
        System.out.println("token updated.");
    }

    public void shutdown() {
        if (chatClient != null) {
            chatClient.shutdown();
        }
    }
}
