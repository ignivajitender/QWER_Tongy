package com.igniva.qwer.controller;

import android.app.Activity;
import android.app.Application;
import android.content.Intent;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.igniva.qwer.ui.activities.LoginActivity;
import com.igniva.qwer.utils.Global;
import com.igniva.qwer.utils.PreferenceHandler;
import com.igniva.qwer.utils.Utility;

import java.io.IOException;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import okhttp3.Cache;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

@Module
public class NetModule {

    // Nexus

//    eyJhbGciOiJIUzUxMiIsInR5cCI6IkpXVCJ9.eyJfaWQiOiI1OWNhMDcyN2JhODhkNzY5Y2Q3NWFhNTAiLCJlbWFpbCI6InRlc3Rlci5pZ25pdmE0QGdtYWlsLmNvbSIsImlhdCI6MTUwNzkwMDE3NX0.EOZl2KilFt_-Dbs3ZA2teaDQo9Hz8wCVCEKjYC5SvGW0W45d1TNQ1oNEFpOBwaO4LaCCedwq8hew1sFJsIA70A
     private static final HttpLoggingInterceptor loggingInterceptor =
            new HttpLoggingInterceptor(new HttpLoggingInterceptor.Logger() {
                Handler mHandler;

                @Override
                public void log(String message) {
//                    {"status":"warning","statusCode":324,"message":"Your session is expired. Please login again to continue"}
                    Log.e("NetModule-----", "--" + message);
                    if (message != null && message.contains("Your session is expired. Please login again to continue")) {
                        Global.sAppContext.startActivity(new Intent(Global.sAppContext, LoginActivity.class).setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK));
                        PreferenceHandler.writeString(Global.sAppContext, PreferenceHandler.PREF_KEY_IS_USER_LOGIN, "false");
                        mHandler = new Handler(Looper.getMainLooper()) {
                            @Override
                            public void handleMessage(Message message) {
                                Utility.showToastMessageLong((Activity) Global.sAppContext, "Your session is expired. Please login again to continue");
                                // This is where you do your work in the UI thread.
                                // Your worker tells you in the message what to do.
                            }
                        };

                    }
                }
            });

    static {
        loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
    }

    String mBaseUrl;

    public NetModule(String mBaseUrl) {
        this.mBaseUrl = mBaseUrl;
    }

    @Provides
    @Singleton
    Cache provideHttpCache(Application application) {
        int cacheSize = 10 * 1024 * 1024;
        Cache cache = new Cache(application.getCacheDir(), cacheSize);
        return cache;
    }

    @Provides
    @Singleton
    Gson provideGson() {
        GsonBuilder gsonBuilder = new GsonBuilder();
        gsonBuilder.setPrettyPrinting();
//        gsonBuilder.setFieldNamingPolicy(FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES);
        return gsonBuilder.create();
    }

    @Provides
    @Singleton
    OkHttpClient provideOkhttpClient(Cache cache) {
        OkHttpClient.Builder client = new OkHttpClient.Builder();
//        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
//        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        client.cache(cache);
        client.addInterceptor(loggingInterceptor);
        client.addInterceptor(new Interceptor() {
            @Override
            public okhttp3.Response intercept(Interceptor.Chain chain) throws IOException {
                Request original = chain.request();
                String token = PreferenceHandler.readString(Global.sAppContext, PreferenceHandler.PREF_KEY_LOGIN_USER_TOKEN, null);
                 if (token != null && token.trim().length() > 0) {
                    Log.e("NetModule--login token-", "--" + token);
                    Request request = original.newBuilder()
                            .header("x-logintoken", token)
                            .method(original.method(), original.body())
                            .build();
                    return chain.proceed(request);
                }
                return chain.proceed(original);
            }
        });
        return client.build();
    }

    @Provides
    @Singleton
    Retrofit provideRetrofit(Gson gson, OkHttpClient okHttpClient) {
        return new Retrofit.Builder()
                .addConverterFactory(GsonConverterFactory.create(gson))
                .baseUrl(mBaseUrl)
                .client(okHttpClient)
                .build();
    }


}