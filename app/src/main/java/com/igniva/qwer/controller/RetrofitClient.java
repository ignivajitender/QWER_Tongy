package com.igniva.qwer.controller;

import android.content.Context;

import com.igniva.qwer.utils.PreferenceHandler;
import com.jakewharton.retrofit.Ok3Client;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import retrofit.RequestInterceptor;
import retrofit.RestAdapter;


/**
 * Created by cl-macmini-38 on 18/05/16.
 */
public class RetrofitClient {

    private static final int CONNECT_TIMEOUT_MILLIS = 60 * 1000; // 60s
    private static final int READ_TIMEOUT_MILLIS = 60 * 1000; // 60s
    private static RestAdapter adapter;
    private static OkHttpClient.Builder okHttpClient = new OkHttpClient.Builder();
    static RestAdapter.Builder builder;

    private static RequestInterceptor newInterceptor(final Context context) {
        RequestInterceptor requestInterceptor = new RequestInterceptor() {
            @Override
            public void intercept(RequestFacade request) {
                String token = PreferenceHandler.readString(context,"accessToken", "");
                if (token != null) {
                    request.addHeader("authorization", "bearer " + token);
                }
            }
        };
        return requestInterceptor;
    }

    static RestAdapter.Builder getBuilder(Context context) {
        if (builder == null) {
            builder = new RestAdapter.Builder()
                    .setEndpoint(Config.getBaseURL())
                    .setLogLevel(RestAdapter.LogLevel.FULL)
                    .setRequestInterceptor(newInterceptor(context));
        }
        return builder;
    }

    public static <S> S createService(Class<S> serviceClass, Context context) {
        getBuilder(context);
        okHttpClient.readTimeout(READ_TIMEOUT_MILLIS, TimeUnit.SECONDS);
        okHttpClient.connectTimeout(CONNECT_TIMEOUT_MILLIS, TimeUnit.SECONDS);
        builder.setClient(new Ok3Client(okHttpClient.build()));
        if (adapter == null)
            adapter = builder.build();
        return adapter.create(serviceClass);
    }

}

