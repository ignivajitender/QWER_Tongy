package com.igniva.qwer.ui.activities;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import com.igniva.qwer.R;


/**
 * Created by igniva-andriod-02 on 27/12/16.
 */

public class NoResultsActivity extends BaseActivity {

    private static int TIME_OUT = 3000;
    Handler handler = new Handler();

    @Override protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        handler.postDelayed(runnable, TIME_OUT);
    }

    private void launchSetPreferencesScreen() {

        startActivity(new Intent(NoResultsActivity.this, SetPreferrencesActivity.class));
        finish();
    }

    Runnable runnable = new Runnable() {
        @Override
        public void run() {
            launchSetPreferencesScreen();

        }
    };

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

