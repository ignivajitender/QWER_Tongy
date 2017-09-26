package com.igniva.qwer.utils;



public class MyApplication extends android.app.Application {

    private PreferenceHandler prefs;
    private static MyApplication app;


    @Override
    public void onCreate() {
        super.onCreate();
        app = this;
        prefs = new PreferenceHandler(this);
    }




    public static MyApplication getApp() {
        return app;
    }

    public PreferenceHandler getPrefs() {
        return prefs;
    }

    public void setPrefs(PreferenceHandler prefs) {
        this.prefs = prefs;
    }


}
