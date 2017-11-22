package com.igniva.qwer.ui.activities;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.Window;
import android.view.WindowManager;

import com.google.firebase.iid.FirebaseInstanceId;
import com.igniva.qwer.R;
import com.igniva.qwer.utils.Constants;
import com.igniva.qwer.utils.Global;
import com.igniva.qwer.utils.PreferenceHandler;


/**
 * Created by igniva-andriod-02 on 27/12/16.
 */

public class SplashActivity extends BaseActivity {

    //private PREFERENCES prefs;
    // SplashActivity screen timer
    private static int SPLASH_TIME_OUT = 3000;

    // Shared Preferences
    private String token;
    Handler handler = new Handler();
    Runnable runnable = new Runnable() {
        @Override
        public void run() {
            boolean alreadyLogin = PreferenceHandler.readBoolean(SplashActivity.this, PreferenceHandler.IS_ALREADY_LOGIN, false);
            if (alreadyLogin) {
                launchHomeScreen();
            } else {
                launchWalkThroughScreen();
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // Checking for first time launch - before calling setContentView()

        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        ((Global) getApplicationContext()).getNetComponent().inject(SplashActivity.this);
        try {
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    //Do something after 2sec
                    try {
                        token = FirebaseInstanceId.getInstance().getToken();
                        if (token != null) {
                            PreferenceHandler.writeFCM_KEY(SplashActivity.this, Constants.FCM_TOKEN, token);
                            com.igniva.qwer.utils.Log.d("TOKEN1", token);
                        } else {
                            token = FirebaseInstanceId.getInstance().getToken();
                            if (token != null) {
                                PreferenceHandler.writeFCM_KEY(SplashActivity.this, Constants.FCM_TOKEN, token);
                                com.igniva.qwer.utils.Log.d("TOKEN2", token);
                            } else {
                                PreferenceHandler.writeFCM_KEY(SplashActivity.this, Constants.FCM_TOKEN, PreferenceHandler.readFCM_KEY(SplashActivity.this, Constants.FCM_TOKEN, ""));
                                com.igniva.qwer.utils.Log.d("TOKEN3", token);
                            }
                        }
                    } catch (Exception e) {
                        e.printStackTrace();

                    }
                }
            }, 1000);
        } catch (Exception e) {
            e.printStackTrace();
        }


        handler.postDelayed(runnable, SPLASH_TIME_OUT);
    }

    private void launchHomeScreen() {
        if (!PreferenceHandler.readBoolean(SplashActivity.this, PreferenceHandler.IS_PROFILE_SET, false))
            startActivity(new Intent(SplashActivity.this, MyProfileActivity.class));
        else if (!PreferenceHandler.readBoolean(SplashActivity.this, PreferenceHandler.IS_PREF_SET, false))
            startActivity(new Intent(SplashActivity.this, SetPreferrencesActivity.class));
        else
            startActivity(new Intent(SplashActivity.this, MainActivity.class));
        finish();
    }

    private void launchWalkThroughScreen() {

        startActivity(new Intent(SplashActivity.this, WalkThroughActivity.class));
        finish();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        try {
            handler.removeCallbacks(runnable);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void setUpLayout() {

    }


    @Override
    public void setDataInViewObjects() {

    }

    @Override
    protected void setUpToolbar() {

    }


}

