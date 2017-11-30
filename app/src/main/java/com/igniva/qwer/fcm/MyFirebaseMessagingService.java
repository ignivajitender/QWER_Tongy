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

import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;
import com.igniva.qwer.R;
import com.igniva.qwer.ui.activities.SplashActivity;
import com.igniva.qwer.ui.activities.TwilioVideoActivity;
import com.igniva.qwer.ui.activities.VideoActivity;
import com.igniva.qwer.utils.Constants;


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
        Log.e(TAG, "Notification Message Body: " + remoteMessage.getData().get("message"));
        Log.e(TAG, "Notification token: " + remoteMessage.getData().get("token"));
        Log.e(TAG, "Notification room_name: " + remoteMessage.getData().get("room"));
        //Calling method to generate notification
        if (remoteMessage.getData().containsKey("token")) {
            Intent intent = new Intent(Constants.VIDEO_CALL_RECEAVER);
            intent.putExtra(Constants.TWILIO_TOKEN, remoteMessage.getData().get("token"));
            intent.putExtra(Constants.TWILIO_ROOM, remoteMessage.getData().get("room_name"));
            LocalBroadcastManager.getInstance(this).sendBroadcast(intent);
            //open video call activity
            Intent dialogIntent = new Intent(this, TwilioVideoActivity.class);
            dialogIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            dialogIntent.putExtra(Constants.TWILIO_TOKEN, remoteMessage.getData().get("token"));
            dialogIntent.putExtra(Constants.TWILIO_ROOM, remoteMessage.getData().get("room"));
            dialogIntent.putExtra(Constants.TWILIO_INCOMMING, 1);
            VideoActivity.TWILIO_ACCESS_TOKEN = remoteMessage.getData().get("token");
            VideoActivity.TWILIO_ROOM_ID = remoteMessage.getData().get("room");
             startActivity(dialogIntent);
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

