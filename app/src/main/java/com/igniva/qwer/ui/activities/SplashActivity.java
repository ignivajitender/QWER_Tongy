package com.igniva.qwer.ui.activities;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.Window;
import android.view.WindowManager;

import com.igniva.qwer.R;
import com.igniva.qwer.utils.Constants;
import com.igniva.qwer.utils.PreferenceHandler;


/**
 * Created by igniva-andriod-02 on 27/12/16.
 */

public class SplashActivity extends BaseActivity {

    //private PREFERENCES prefs;
    // SplashActivity screen timer
    private static int SPLASH_TIME_OUT = 3000         ;

    // Shared Preferences

    Handler handler = new Handler();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // Checking for first time launch - before calling setContentView()

        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        handler.postDelayed(runnable, SPLASH_TIME_OUT);
    }


    private void launchHomeScreen() {
        startActivity(new Intent(SplashActivity.this, MainActivity.class));
        finish();
    }


    private void launchWalkThroughScreen() {

        startActivity(new Intent(SplashActivity.this, WalkThroughActivity.class));
        finish();
    }

    Runnable runnable = new Runnable() {
        @Override
        public void run() {
            boolean alreadyLogin = PreferenceHandler.readBoolean(SplashActivity.this, Constants.IS_ALREADY_LOGIN, false);
            if (alreadyLogin) {
                launchHomeScreen();
            }else{
                launchWalkThroughScreen();
            }
        }
    };

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

