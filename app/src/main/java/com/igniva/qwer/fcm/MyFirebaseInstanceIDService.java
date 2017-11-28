package com.igniva.qwer.fcm;

import android.util.Log;

import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.FirebaseInstanceIdService;
import com.igniva.qwer.utils.Constants;
import com.igniva.qwer.utils.PreferenceHandler;


//Class extending FirebaseInstanceIdService
public class MyFirebaseInstanceIDService extends FirebaseInstanceIdService {

    private static final String TAG = "MyFirebaseIIDService";



    @Override
    public void onTokenRefresh() {

        //Getting registration token
        String refreshedToken = FirebaseInstanceId.getInstance().getToken();

        //Displaying token on logcat
        Log.d(TAG, "Refreshed token: " + refreshedToken);
        try {
            // add refreshed token in preference
            if (PreferenceHandler.readFCM_KEY(MyFirebaseInstanceIDService.this, Constants.FCM_TOKEN, "").equalsIgnoreCase("")) {
                PreferenceHandler.writeFCM_KEY(MyFirebaseInstanceIDService.this, Constants.FCM_TOKEN, refreshedToken);
                 if (!refreshedToken.equalsIgnoreCase(PreferenceHandler.readFCM_KEY(MyFirebaseInstanceIDService.this, Constants.FCM_TOKEN, ""))) {
                    if (PreferenceHandler.readString(MyFirebaseInstanceIDService.this, PreferenceHandler.PREF_KEY_LOGIN_USER_TOKEN, "").isEmpty())
                        //ApiControllerClass.updateToken(MyFirebaseInstanceIDService.this, refreshedToken, retrofit);
                    Log.e("in token refresh", refreshedToken);
                }
             } else {
                //ApiControllerClass.updateToken(MyFirebaseInstanceIDService.this, refreshedToken, retrofit);
                PreferenceHandler.writeFCM_KEY(MyFirebaseInstanceIDService.this, Constants.FCM_TOKEN, refreshedToken);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void sendRegistrationToServer(String token) {
        //You can implement this method to store the token on your server
        //Not required for current project
    }
}