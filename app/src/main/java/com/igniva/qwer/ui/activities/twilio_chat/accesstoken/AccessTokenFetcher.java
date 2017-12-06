package com.igniva.qwer.ui.activities.twilio_chat.accesstoken;

import android.content.Context;
import android.provider.Settings;

import com.igniva.qwer.controller.ApiInterface;
import com.igniva.qwer.model.TokenPojo;
import com.igniva.qwer.ui.activities.twilio_chat.listeners.TaskCompletionListener;
import com.igniva.qwer.ui.views.CallProgressWheel;

import java.util.HashMap;

import retrofit2.Call;
import retrofit2.Retrofit;

public class AccessTokenFetcher {

    private static final String TAG = "AccessTokenFetcher";
    private Context mContext;

    public AccessTokenFetcher(Context mContext) {
        this.mContext = mContext;
    }

    public void fetch(Retrofit retrofit, String userName, final TaskCompletionListener<String, String> listener) {
        try {
//          if (!Utility.isInternetConnection(mContext)) {
//              Utility.showAlertWithSingleButton(mContext, mContext.getString(R.string.no_internet), new Utility.OnAlertOkClickListener() {
//                  @Override
//                  public void onOkButtonClicked() {
//
//                  }
//              });
//              return;
//          }
            CallProgressWheel.showLoadingDialog(mContext, "Loading...");

            String androidId =
                    Settings.Secure.getString(mContext.getContentResolver(), Settings.Secure.ANDROID_ID);

            HashMap<String, String> params = new HashMap<>();
            params.put("device_id", androidId);
            params.put("identity", userName);
            Call<TokenPojo> posts = null;
            posts = retrofit.create(ApiInterface.class).getMessageToken(params);
            if (posts != null)
                posts.enqueue(new retrofit2.Callback<TokenPojo>() {
                    @Override
                    public void onResponse(Call<TokenPojo> call, retrofit2.Response<TokenPojo> response) {
                        if (response.body().getStatus() == 200) {
                            listener.onSuccess(response.body().getToken());

                        } else {

                            //CallProgressWheel.dismissLoadingDialog();
                            //Utility.showToastMessageShort(activity,response.body().getDescription());

                        }
                    }

                    @Override
                    public void onFailure(Call<TokenPojo> call, Throwable t) {


                        //CallProgressWheel.dismissLoadingDialog();
                    }
                });


        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}
