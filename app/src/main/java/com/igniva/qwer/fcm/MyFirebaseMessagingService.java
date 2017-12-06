package com.igniva.qwer.fcm;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.media.RingtoneManager;
import android.net.Uri;
import android.support.v4.app.NotificationCompat;
import android.support.v4.content.LocalBroadcastManager;
import android.util.Log;
import android.view.WindowManager;

import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;
import com.igniva.qwer.R;
import com.igniva.qwer.ui.activities.SplashActivity;
import com.igniva.qwer.ui.activities.TwilioVideoActivity;
import com.igniva.qwer.ui.activities.twilio_chat.MainChatActivity;
import com.igniva.qwer.ui.activities.voice.TwilioVoiceClientActivity;
import com.igniva.qwer.utils.Constants;
import com.igniva.qwer.utils.Global;

import java.util.Random;


public class MyFirebaseMessagingService extends FirebaseMessagingService {

    private static final String TAG = "MyFirebaseMsgService";

    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {
        //Displaying data in log
        //It is optional
        //Log.d(TAG, "From: " + remoteMessage.toString());
        Log.e(TAG, "From: " + remoteMessage.getFrom());
        // Notification Message Body:
        // {receiver_id=59, post_id=156, type=comment on post, sender_id=98, sound=1, vibrate=1, notification=142, message=Jack commented on your post.}
        Log.e(TAG, "Notification Message getData: " + remoteMessage.getData());
        //Calling method to generate notification
        if (remoteMessage.getData().containsKey("token") && remoteMessage.getData().get("notification").equalsIgnoreCase("20")) {
//            if (!PreferenceHandler.readString(this, PreferenceHandler.PREF_KEY_USER_ID, "").equals(remoteMessage.getData().get("room_name"))) {
                Intent intent = new Intent(Constants.VIDEO_CALL_RECEAVER);
                 intent.putExtra(Constants.TWILIO_TOKEN, remoteMessage.getData().get("token"));
                intent.putExtra(Constants.TWILIO_ROOM, remoteMessage.getData().get("room_name"));
                LocalBroadcastManager.getInstance(this).sendBroadcast(intent);
                //open video call activity
                Intent dialogIntent = new Intent(this, TwilioVideoActivity.class);
                dialogIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                dialogIntent.putExtra(Constants.TWILIO_TOKEN, remoteMessage.getData().get("token"));
                dialogIntent.putExtra(Constants.TWILIO_ROOM, remoteMessage.getData().get("room_name"));
                dialogIntent.putExtra(Constants.TWILIO_INCOMMING, 1);
                 startActivity(dialogIntent);
//            }

        } else if (remoteMessage.getData().containsKey("token") && remoteMessage.getData().get("notification").equalsIgnoreCase("21")) {

            Intent intent = new Intent(Constants.VOICE_CALL_RECEAVER);
            intent.putExtra(Constants.TWILIO_TOKEN, remoteMessage.getData().get("token"));
            intent.putExtra(Constants.TWILIO_SENDER_NAME, remoteMessage.getData().get("reciver_name"));

            LocalBroadcastManager.getInstance(this).sendBroadcast(intent);
            //open video call activity

            Intent dialogIntent = new Intent(this, TwilioVoiceClientActivity.class);
            dialogIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            dialogIntent.putExtra(Constants.TWILIO_TOKEN, remoteMessage.getData().get("token"));
            dialogIntent.putExtra(Constants.TWILIO_SENDER_NAME, remoteMessage.getData().get("sender_name"));
            dialogIntent.putExtra(Constants.TWILIO_RECEAVER_NAME, remoteMessage.getData().get("sender_name"));
            dialogIntent.putExtra(Constants.TWILIO_CALLER_ID, remoteMessage.getData().get("caller_id"));
            dialogIntent.putExtra(Constants.TWILIO_SENDER_ID, remoteMessage.getData().get("sender_id"));

            try {
                dialogIntent.putExtra(Constants.ROOM_TITLE, remoteMessage.getData().get("sender_name"));
            } catch (Exception e) {
                e.printStackTrace();
            }
            dialogIntent.putExtra(Constants.TWILIO_INCOMMING, 1);
            dialogIntent.addFlags(WindowManager.LayoutParams.FLAG_SHOW_WHEN_LOCKED +
                    WindowManager.LayoutParams.FLAG_DISMISS_KEYGUARD +
                    WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON +
                    WindowManager.LayoutParams.FLAG_TURN_SCREEN_ON);
            startActivity(dialogIntent);

            Log.d(TAG, "TWILIO_TOKEN: " + remoteMessage.getData().get("token"));
            Log.d(TAG, "TWILIO_SENDER_NAME: " + remoteMessage.getData().get("reciver_name"));
        }else if (remoteMessage.getData().get("notification").equals("24")) //request
        { //invite

            if (remoteMessage.getData().get("channel_name").equalsIgnoreCase(Global.activeChannelName)) {
                return;
            } else {
                Intent intent6 = new Intent(this, MainChatActivity.class);
                intent6.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                intent6.putExtra(Constants.CHANNEL_NAME, remoteMessage.getData().get("channel_name"));
                intent6.putExtra(Constants.ROOM_USER_ID, remoteMessage.getData().get("sender_id"));
                intent6.putExtra(Constants.ROOM_USER_NAME, remoteMessage.getData().get("sender_name"));
                PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, intent6,
                        PendingIntent.FLAG_ONE_SHOT);
                NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(this)
                        .setSmallIcon(R.mipmap.ic_launcher)
                        .setContentTitle("SkipRoom")
                        .setContentText(remoteMessage.getData().get("message"))
                        .setStyle(new NotificationCompat.BigTextStyle()
                                .bigText(remoteMessage.getData().get("message")))
                        .setAutoCancel(true)
                         .setContentIntent(pendingIntent);

                NotificationManager notificationManager =
                        (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);

                Random random = new Random();
                int not_id = random.nextInt(10000);
                notificationManager.notify(not_id, notificationBuilder.build());

            }
        } else
            sendNotification(remoteMessage.getData().get("message"));
    }

    //This method is only generating push notification
    //It is same as we did in earlier posts
    private void sendNotification(String messageBody) {
        Intent intent = new Intent(this, SplashActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, intent,
                PendingIntent.FLAG_ONE_SHOT);

        Uri defaultSoundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
        NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(this)
                .setSmallIcon(R.drawable.tongy_logo)
                .setContentTitle("Tongy")
                .setContentText(messageBody)
                .setAutoCancel(true)
                .setSound(defaultSoundUri)
                .setContentIntent(pendingIntent);

        NotificationManager notificationManager =
                (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);

        notificationManager.notify(0, notificationBuilder.build());
    }
}

