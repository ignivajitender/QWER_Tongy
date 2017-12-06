package com.igniva.qwer.ui.activities.twilio_chat.messages;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.igniva.qwer.R;
import com.igniva.qwer.utils.PreferenceHandler;
import com.twilio.chat.Message;

import org.apache.commons.lang.StringEscapeUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.TreeSet;

public class MessageAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    String TAG = "MessageAdapter";
    private List<ChatMessage> mList;
    private final int LEFT_CHAT = 1;
    private final int RIGHT_CHAT = 2;
    private final int CENTER_CHAT = 3;
    private Context mContext;


    private TreeSet statusMessageSet = new TreeSet();

    public MessageAdapter(Context activity) {
        mList = new ArrayList<>();
        mContext = activity;
    }

    public void setMessages(List<Message> messages) {
        this.mList = convertTwilioMessages(messages);
        this.statusMessageSet.clear();
        notifyDataSetChanged();
    }

    public void addMessage(Message message) {
        mList.add(new UserMessage(message));
        notifyDataSetChanged();
    }

    public void addStatusMessage(StatusMessage message) {
        mList.add(message);
        statusMessageSet.add(mList.size() - 1);
        notifyDataSetChanged();
    }

    public void removeMessage(Message message) {
        mList.remove(mList.indexOf(message));
        notifyDataSetChanged();
    }

    private List<ChatMessage> convertTwilioMessages(List<Message> messages) {
        List<ChatMessage> chatMessages = new ArrayList<>();
        for (Message message : messages) {
            chatMessages.add(new UserMessage(message));
        }
        return chatMessages;
    }
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view;
        switch (viewType) {
            case LEFT_CHAT:
                view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_left_chat, parent, false);
                return new LeftViewHolder(view);
            case RIGHT_CHAT:
                view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_right_chat, parent, false);
                return new RightViewHolder(view);
            case CENTER_CHAT:
                view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_center_chat, parent, false);
                return new CenterViewHolder(view);
        }
        return null;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        ChatMessage object = mList.get(position);
        String createdAt = "";
        try {
            createdAt = getDate(mList.get(position - 1).getTimeStamp());
        } catch (Exception e) {
            e.printStackTrace();
        }
        String createdAtCurrent = "";
        if (object != null) {
            try {
                createdAtCurrent = getDate(object.getTimeStamp());
            } catch (Exception e) {
                e.printStackTrace();
            }
            try {
                if (object.getAuthor().equalsIgnoreCase
                        ("" + PreferenceHandler.readInteger(mContext, PreferenceHandler.PREF_KEY_USER_ID, 0))) {
                    ((RightViewHolder) holder).mMessage.setText(StringEscapeUtils.unescapeJava(object.getMessageBody()));
                    ((RightViewHolder) holder).mTime.setText(getTime(object.getTimeStamp()));
                    if (!createdAtCurrent.equalsIgnoreCase(createdAt)) {
                        Log.e(TAG, createdAtCurrent + "===================");
                        ((RightViewHolder) holder).mMessageDate.setVisibility(View.VISIBLE);
                        ((RightViewHolder) holder).mMessageDate.setText(createdAtCurrent);
                    } else {
                        ((RightViewHolder) holder).mMessageDate.setVisibility(View.GONE);
                    }
                } else {
                    ((LeftViewHolder) holder).mMessage.setText(StringEscapeUtils.unescapeJava(object.getMessageBody()));
                    ((LeftViewHolder) holder).mTime.setText(getTime(object.getTimeStamp()));
                    if (!createdAtCurrent.equalsIgnoreCase(createdAt)) {
                        ((LeftViewHolder) holder).mMessageDate.setVisibility(View.VISIBLE);
                        Log.e(TAG, createdAtCurrent + "===================");
                        ((LeftViewHolder) holder).mMessageDate.setText(createdAtCurrent);
                    } else {
                        ((LeftViewHolder) holder).mMessageDate.setVisibility(View.GONE);
                    }

                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public int getItemCount() {
        if (mList == null)
            return 0;
        return mList.size();
    }

    @Override
    public int getItemViewType(int position) {
        if (mList != null) {
            ChatMessage object = mList.get(position);
            if (object != null) {
                if (object.getAuthor().equalsIgnoreCase(""+PreferenceHandler.readInteger(mContext, PreferenceHandler.PREF_KEY_USER_ID, 0))) {
                    return RIGHT_CHAT;
                } else {
                    return LEFT_CHAT;
                }
            }
        }
        return -1;
    }

    public static class LeftViewHolder extends RecyclerView.ViewHolder {
        private TextView mMessage;
        private TextView mMessageDate;
        private TextView mTime;
        private TextView mTvSenderName;

        public LeftViewHolder(View itemView) {
            super(itemView);
            mMessageDate = (TextView) itemView.findViewById(R.id.tv_message_center);
            mMessage = (TextView) itemView.findViewById(R.id.tv_message);
            mTime = (TextView) itemView.findViewById(R.id.tv_time);
            mTvSenderName = (TextView) itemView.findViewById(R.id.tv_sender_name);
        }
    }

    public static class RightViewHolder extends RecyclerView.ViewHolder {
        private TextView mMessage;
        private TextView mMessageDate;
        private TextView mTime;

        public RightViewHolder(View itemView) {
            super(itemView);
            mMessageDate = (TextView) itemView.findViewById(R.id.tv_message_center);
            mMessage = (TextView) itemView.findViewById(R.id.tv_message);
            mTime = (TextView) itemView.findViewById(R.id.tv_time);
        }
    }

    public static class CenterViewHolder extends RecyclerView.ViewHolder {
        private TextView mMessage;
        public CenterViewHolder(View itemView) {
            super(itemView);
            mMessage = (TextView) itemView.findViewById(R.id.tv_message_center);
        }
    }

    public String getDate(String date){
        String currentDate = DateFormatter.getFormattedDateFromISOString(date);
        String date1[] = currentDate.split("-");
        return date1[0];
    }

    public String getTime(String date){
        String currentDate = DateFormatter.getFormattedDateFromISOString(date);
        String date1[] = currentDate.split("-");
        return date1[1];
    }
}