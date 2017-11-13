package com.igniva.qwer.utils;

import android.content.Context;
import android.location.Location;
import android.os.StrictMode;
import android.support.multidex.MultiDexApplication;

import com.facebook.FacebookSdk;
import com.google.firebase.analytics.FirebaseAnalytics;
import com.igniva.qwer.controller.AppModule;
import com.igniva.qwer.controller.Config;
import com.igniva.qwer.controller.DaggerNetComponent;
import com.igniva.qwer.controller.NetComponent;
import com.igniva.qwer.controller.NetModule;


/**
 * Created by tanmey on 14/9/17.
 */

public class Global extends MultiDexApplication {
    public static FirebaseAnalytics mFirebaseAnalytics;
    public static Context sAppContext=null;
    private NetComponent mNetComponent;
    private PreferenceHandler prefs;
    public static Location mLastLocation;





    @Override
    public void onCreate() {
        super.onCreate();
        StrictMode.VmPolicy.Builder builder = new StrictMode.VmPolicy.Builder();
        StrictMode.setVmPolicy(builder.build());
        sAppContext=this;
        intializeFirebase(this);
        mNetComponent = DaggerNetComponent.builder()
                .appModule(new AppModule(this))
                .netModule(new NetModule(Config.getBaseURL()))
                .build();

        prefs = new PreferenceHandler(this);
        FacebookSdk.sdkInitialize(getApplicationContext());
    }
//    void setComponets(Activity context){
//        getNetComponent().inject((Activity) context);
//        ButterKnife.bind(context);
//     }

    public NetComponent getNetComponent() {
        return mNetComponent;
    }

    private static boolean canSend() {
        return sAppContext != null && mFirebaseAnalytics != null;
    }

    public static synchronized void intializeFirebase(Context context) {
        sAppContext = context;
        mFirebaseAnalytics = FirebaseAnalytics.getInstance(context);
        setProperty("DeviceType", Utility.getDeviceType(context));
        setProperty("Rooted", Boolean.toString(Utility.isRooted()));
    }

    public static void setProperty(String propertyName, String propertyValue){
        if (!canSend()) {
            return;
        }
        mFirebaseAnalytics.setUserProperty(propertyName, propertyValue);
    }

    @Override
    public void onTerminate() {
        super.onTerminate();
        Log.e("Global","onTerminate");
    }

//    private static void handleActivity(Activity activity) {
//        if (activity instanceof HasSupportFragmentInjector) {
//            AndroidInjection.inject(activity);
//        }
//        if (activity instanceof FragmentActivity) {
//            ((FragmentActivity) activity).getSupportFragmentManager()
//                    .registerFragmentLifecycleCallbacks(
//                            new FragmentManager.FragmentLifecycleCallbacks() {
//                                @Override
//                                public void onFragmentCreated(FragmentManager fm, Fragment f,
//                                                              Bundle savedInstanceState) {
//                                    if (f instanceof Injectable) {
//                                        AndroidSupportInjection.inject(f);
//                                    }
//                                }
//                            }, true);
//        }
//    }
 }
