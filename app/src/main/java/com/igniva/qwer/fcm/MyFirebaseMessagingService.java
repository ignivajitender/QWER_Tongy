package com.igniva.qwer.fcm;

import android.annotation.SuppressLint;
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
import com.google.gson.Gson;
import com.igniva.qwer.R;
import com.igniva.qwer.ui.activities.NotificationActivity;
import com.igniva.qwer.ui.activities.TwilioVideoActivity;
import com.igniva.qwer.ui.activities.twilio_chat.MainChatActivity;
import com.igniva.qwer.ui.activities.voice.TwilioVoiceClientActivity;
import com.igniva.qwer.utils.Constants;
import com.igniva.qwer.utils.Global;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Random;

import javax.inject.Inject;


public class MyFirebaseMessagingService extends FirebaseMessagingService {

    private static final String TAG = "MyFirebaseMsgService";
    @Inject
    Gson gson;

    @SuppressLint("WrongConstant")
    @Override
    public synchronized void onMessageReceived(RemoteMessage remoteMessage) {
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
            dialogIntent.putExtra(Constants.TWILIO_RECEAVER_NAME, remoteMessage.getData().get("sender_name"));
            dialogIntent.putExtra(Constants.TWILIO_RECEAVER_IMAGE, remoteMessage.getData().get("sender_image"));
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
            dialogIntent.putExtra(Constants.TWILIO_RECEAVER_NAME, remoteMessage.getData().get("sender_name"));
            dialogIntent.putExtra(Constants.TWILIO_RECEAVER_IMAGE, remoteMessage.getData().get("sender_image"));
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
        } else if (remoteMessage.getData().get("notification").equals("24")) //request
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
                String msg = remoteMessage.getData().get("sender_name") + remoteMessage.getData().get("message");

                NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(this)
                        .setSmallIcon(R.drawable.tongy_logo)
                        .setContentTitle("Tongy")
                        .setContentText(msg)
                        .setStyle(new NotificationCompat.BigTextStyle()
                                .bigText(msg))
                        .setAutoCancel(true)
                        .setContentIntent(pendingIntent);
                NotificationManager notificationManager =
                        (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
                Random random = new Random();
                int not_id = random.nextInt(10000);
                notificationManager.notify(not_id, notificationBuilder.build());
            }
        } else if (remoteMessage.getData().get("notification").equals("view")) //request
        { //view user MainActivity
             //            {receiver_id=115, post_id=0, type=view user, sender_id={"view":"1","data":{"country":"India","gender":"Male","city":"chandigarh","about":"dev","created_at":"1509630015","device_type":"android","secondary_email":null,"platform":"app","user_role":{"name":"user","id":2},"password":"$2y$10$QTin\/lQV.mMW9cMgF5WYEeJOGXYLe0qyxAkYtj5\/0.PWROntHkDpq","updated_at":"1513945115","role_id":"2","is_push_notification":"1","is_videocall":"1","is_voicecall":"1","id":103,"email":"tanmey.singh@ignivasolutions.com","lat":"30.711427","pincode":"160047","user_email":"1","lng":"76.686177","device_id":"fLuGflbPp-Q:APA91bFZRB4gAoMmMQBfS2KPZzvDquPSYtvarrbvPQzFZvibMTZ_uhyULddM5qvPsMEdLMXuseKb6uHiwgKH5GlulhP28oC3UP516Cyvb7ti919uyjEqUTDQguxeZCISo2ppuyJofXLg","user_image":[{"image":"http:\/\/tongy.ignivastaging.com\/images\/profile_image\/1512656524-96B57670.jpg","user_id":"103","is_cover_image":"1","id":86}],"view_id":"0","is_verified":"1","social_id":null,"pref_status":"1","name":"Tanmey","forgot_password_token":"","auth_token":"eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJzdWIiOjEwMywiaXNzIjoiaHR0cDovL3Rvbmd5Lmlnbml2YXN0YWdpbmcuY29tL2FwaS9sb2dpbiIsImlhdCI6MTUxMzk0NTA5MiwiZXhwIjoxNTE0MDMxNDkyLCJuYmYiOjE1MTM5NDUwOTIsImp0aSI6IldzeTdDMjFUcldMZWhSdkUifQ.vD73dJ67Onig_7zY8VU6zZFUgwu6wxDxWZby5mHN8pM","verification_code":"","age":"26","country_id":"101","status":"1"},"user_id":"115"}, sound=1, vibrate=1, notification=view, message=viewing you}
             try {
                Log.e("", "notification-view---" + remoteMessage.getData().get("sender_id"));
                JSONObject jsonObject = new JSONObject(remoteMessage.getData().get("sender_id").toString());
//                JSONObject jsonDAta = new JSONObject(jsonObject.get("data").toString());
                Log.e("", "notification-view---" + jsonObject.optString("view_id"));
//                if (jsonObject != null && jsonObject.optString("view_id").equals(PreferenceHandler.readString(this, PreferenceHandler.PREF_KEY_USER_ID, ""))) {
//                     Log.e("", "Can you see me????");
                    Log.e("", System.currentTimeMillis()/1000+"----tym----"+jsonObject.optInt("updated_at"));
                    if(System.currentTimeMillis()/1000-jsonObject.optInt("updated_at")<200){
                        Log.e("", "! see you");
                        if(Global.homeFrag!=null)
                           Global.homeFrag.updateCard(true,jsonObject.optString("id"));
//                    }
                }
                jsonObject=null ;
             } catch (JSONException e) {
                e.printStackTrace();
            } catch (Exception e) {
                e.printStackTrace();
            }
         }else if (remoteMessage.getData().get("notification").equals("viewChnaged")) //request
        { //view user MainActivity
            //            {receiver_id=115, post_id=0, type=view user, sender_id={"view":"1","data":{"country":"India","gender":"Male","city":"chandigarh","about":"dev","created_at":"1509630015","device_type":"android","secondary_email":null,"platform":"app","user_role":{"name":"user","id":2},"password":"$2y$10$QTin\/lQV.mMW9cMgF5WYEeJOGXYLe0qyxAkYtj5\/0.PWROntHkDpq","updated_at":"1513945115","role_id":"2","is_push_notification":"1","is_videocall":"1","is_voicecall":"1","id":103,"email":"tanmey.singh@ignivasolutions.com","lat":"30.711427","pincode":"160047","user_email":"1","lng":"76.686177","device_id":"fLuGflbPp-Q:APA91bFZRB4gAoMmMQBfS2KPZzvDquPSYtvarrbvPQzFZvibMTZ_uhyULddM5qvPsMEdLMXuseKb6uHiwgKH5GlulhP28oC3UP516Cyvb7ti919uyjEqUTDQguxeZCISo2ppuyJofXLg","user_image":[{"image":"http:\/\/tongy.ignivastaging.com\/images\/profile_image\/1512656524-96B57670.jpg","user_id":"103","is_cover_image":"1","id":86}],"view_id":"0","is_verified":"1","social_id":null,"pref_status":"1","name":"Tanmey","forgot_password_token":"","auth_token":"eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJzdWIiOjEwMywiaXNzIjoiaHR0cDovL3Rvbmd5Lmlnbml2YXN0YWdpbmcuY29tL2FwaS9sb2dpbiIsImlhdCI6MTUxMzk0NTA5MiwiZXhwIjoxNTE0MDMxNDkyLCJuYmYiOjE1MTM5NDUwOTIsImp0aSI6IldzeTdDMjFUcldMZWhSdkUifQ.vD73dJ67Onig_7zY8VU6zZFUgwu6wxDxWZby5mHN8pM","verification_code":"","age":"26","country_id":"101","status":"1"},"user_id":"115"}, sound=1, vibrate=1, notification=view, message=viewing you}
            try {
                Log.e("", "notification-viewChnaged---" + remoteMessage.getData().get("sender_id"));
                JSONObject jsonObject = new JSONObject(remoteMessage.getData().get("sender_id").toString());
//                JSONObject jsonDAta = new JSONObject(jsonObject.get("data").toString());
//                Log.e("", "notification-viewChnaged---" + jsonObject.optString("view_id"));
//                if (jsonObject != null && jsonObject.optString("view_id").equals(PreferenceHandler.readString(this, PreferenceHandler.PREF_KEY_USER_ID, ""))) {
                     Log.e("", "You cant see me!!!!! ");
                         if(Global.homeFrag!=null)
                            Global.homeFrag.updateCard(false,jsonObject.optString("id"));
//                 }
                jsonObject=null ;
            } catch (JSONException e) {
                e.printStackTrace();
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else
            sendNotification(remoteMessage.getData().get("message"));
    }

    //This method is only generating push notification
    //It is same as we did in earlier p+++osts
    private void sendNotification(String messageBody) {
        Intent baseIntent = new Intent(this, NotificationActivity.class);
        baseIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        Uri defaultSoundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
        NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(this)
                .setSmallIcon(R.drawable.tongy_logo)
                .setContentTitle("Tongy")
                .setContentText(messageBody)
                .setAutoCancel(true)
                .setSound(defaultSoundUri);

        NotificationManager notificationManager =
                (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);

//        TaskStackBuilder stackBuilder = TaskStackBuilder.create(this);
////                stackBuilder.addParentStack(NotificationActivity.class);
//        stackBuilder.addNextIntent(new Intent(this, NotificationActivity.class));
//        stackBuilder.addNextIntent(baseIntent);
//        PendingIntent resultPendingIntent = stackBuilder.getPendingIntent(0, PendingIntent.FLAG_ONE_SHOT);


        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, baseIntent,
                PendingIntent.FLAG_CANCEL_CURRENT);
//        notificationBuilder.setContentIntent(resultPendingIntent);
        notificationBuilder.setContentIntent(pendingIntent);
        notificationManager.notify(0, notificationBuilder.build());
    }
}

