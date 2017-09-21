package com.igniva.qwer.ui.activities;

import android.animation.LayoutTransition;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.igniva.qwer.R;
import com.igniva.qwer.controller.ApiInterface;
import com.igniva.qwer.controller.RetrofitClient;
import com.igniva.qwer.model.ResponsePojo;
import com.igniva.qwer.ui.views.CallProgressWheel;

import org.json.JSONObject;

import java.util.HashMap;

import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;
import retrofit.mime.TypedByteArray;

public class SplashActivity extends BaseActivity {

    private ImageView ivlogo;
    private LinearLayout linearLayoutOffering;
    private LinearLayout llMain;
    String LOG_TAG = "SplashActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        setUpLayout();
        //loginApiCall();
        //dynamicApi();
        //
        articleApi();
//        if (!PreferenceHandler.readString(SplashActivity.this, PreferenceHandler.PREF_KEY_USER_ID, "").equalsIgnoreCase("")) {
//            loginApiCall();
//        } else {
//            new Handler().postDelayed(new Runnable() {
//                @Override
//                public void run() {
//                    linearLayoutOffering.setVisibility(View.VISIBLE);
//                }
//            }, 3000);
//        }
//
//        linearLayoutOffering.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                startActivity(new Intent(SplashActivity.this, LoginActivity.class));
//                overridePendingTransition(R.anim.right_in, R.anim.right_out);
//            }
//        });
    }


    void loginApiCall() {
        ApiInterface mWebApi = RetrofitClient.createService(ApiInterface.class, this);
        CallProgressWheel.showLoadingDialog(SplashActivity.this, "Loading...");
        HashMap<String, String> params = new HashMap<>();
        params.put("email", "as@as.co");
        params.put("password", "123456");
        params.put("deviceType", "ANDROID");
        params.put("deviceToken", "12345");
        params.put("flushPreviousSessions", "true");
        params.put("appVersion", "101");


        mWebApi.login(params, new Callback<ResponsePojo>() {
            @Override
            public void success(ResponsePojo commonResponse, retrofit.client.Response response) {
                CallProgressWheel.dismissLoadingDialog();
                Log.d(LOG_TAG, "Success " + commonResponse.toString());
                try {
//                    Prefs.with(LoginActivity.this).save(DataNames.ACCESS_TOKEN, commonResponse.getData().getAccessToken());
//                    if (commonResponse.getData().getUserDetails().getFirstTimeLogin()) {
//                        Prefs.with(LoginActivity.this).save(DataNames.ACCESS_TOKEN, commonResponse.getData().getAccessToken());
//                        startActivity(new Intent(LoginActivity.this, MainActivityManager.class));
//                        overridePendingTransition(R.anim.right_in, R.anim.right_out);
//                    } else {
//                        startActivity(new Intent(LoginActivity.this, SetPasswordActivity.class));
//                        overridePendingTransition(R.anim.right_in, R.anim.right_out);
//                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void failure(RetrofitError error) {
                Log.e("failure", error.getMessage());
                CallProgressWheel.dismissLoadingDialog();
                try {
                    String json = new String(((TypedByteArray) error.getResponse().getBody()).getBytes());
                    Log.v("failure", json.toString());
                    JSONObject jsonObject = new JSONObject(json);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }


    void dynamicApi() {
        try {
            String mPageNo="2";
            HashMap<String, String> params = new HashMap<>();
            params.put("post_id", "79");
            params.put("data_type", "get_comment");
            params.put("user_id", "470");
            params.put("auth_token", "U18494256856713");

            ApiInterface mWebApi = RetrofitClient.createService(ApiInterface.class, this);
            CallProgressWheel.showLoadingDialog(SplashActivity.this, "Loading...");
            mWebApi.getComment(mPageNo, params, new Callback<ResponsePojo>() {
                @Override
                public void success(ResponsePojo responsePojo, Response response) {

                }

                @Override
                public void failure(RetrofitError error) {

                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    @Override
    protected void setUpLayout() {
        ivlogo = (ImageView) findViewById(R.id.ivlogo);
        linearLayoutOffering = (LinearLayout) findViewById(R.id.linearLayoutOffering);
        llMain = (LinearLayout) findViewById(R.id.llMain);
        LayoutTransition layoutTransition = new LayoutTransition();
        layoutTransition.setDuration(500);
        llMain.setLayoutTransition(layoutTransition);
    }

    @Override
    protected void setDataInViewObjects() {

    }

    @Override
    protected void setUpToolbar() {

    }


    private HashMap createPayload() {
        HashMap<String, String> params = new HashMap<>();
        try {
            params.put("email", "neha.singla@ignivasolutions.com");
            params.put("password", "neha@123");
            params.put("device_id", "12345");
            params.put("gcm_id", "d48221-LnwA:APA91bG_hJhr9z0l8jrgxsE1BnnYxuvpcCFrMnKbUhCjdmYKLxIkpGxBYaFb930qVHi1ARBCHtdHIbgdtRze1NNtmOQNVz0KoaX98GQMXUNW6FCWHrOFS1JLHx3O-48ZKoqDor7Jgqfo");
        } catch (Exception e) {
            Log.d(LOG_TAG, "Error creating payload");
            e.printStackTrace();
            params = new HashMap<>();
        }
        Log.d(LOG_TAG, "Login payload is " + params.toString());
        return params;
    }

    void articleApi() {
        try {
            String type="lifestyle";
            HashMap<String, String> params = new HashMap<>();
            params.put("article_type", "[\n" +
                    "    \""+type+"\"\n" +
                    "  ]");

            ApiInterface mWebApi = RetrofitClient.createService(ApiInterface.class, this);
            CallProgressWheel.showLoadingDialog(SplashActivity.this, "Loading...");
            mWebApi.article( params, new Callback<ResponsePojo>() {
                @Override
                public void success(ResponsePojo responsePojo, Response response) {

                }

                @Override
                public void failure(RetrofitError error) {

                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


}
