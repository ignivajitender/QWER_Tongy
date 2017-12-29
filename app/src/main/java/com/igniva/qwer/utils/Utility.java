package com.igniva.qwer.utils;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.UiModeManager;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.Signature;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.ColorDrawable;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Environment;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupMenu;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.android.gms.location.places.GeoDataClient;
import com.google.android.gms.location.places.Place;
import com.google.android.gms.location.places.PlaceBufferResponse;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.gson.Gson;
import com.igniva.qwer.R;
import com.igniva.qwer.controller.ApiControllerClass;
import com.igniva.qwer.controller.ApiInterface;
import com.igniva.qwer.model.GooglePlaceApiResponsePojo;
import com.igniva.qwer.model.ProfileResponsePojo;
import com.igniva.qwer.model.predictionsPojo;
import com.igniva.qwer.ui.activities.ConnectionAcceptedActivity;
import com.igniva.qwer.ui.activities.LocationActivity;
import com.igniva.qwer.ui.activities.LoginActivity;
import com.igniva.qwer.ui.activities.SearchActivity;
import com.igniva.qwer.ui.activities.twilio_chat.MainChatActivity;
import com.igniva.qwer.ui.views.CallProgressWheel;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.IOException;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.TimeZone;

import okhttp3.OkHttpClient;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class Utility {

    static final String[] BinaryPlaces = {"/data/bin/", "/system/bin/", "/system/xbin/", "/sbin/",
            "/data/local/xbin/", "/data/local/bin/", "/system/sd/xbin/", "/system/bin/failsafe/",
            "/data/local/"};
    private static final int SECOND_MILLIS = 1000;
    private static final int MINUTE_MILLIS = 60 * SECOND_MILLIS;
    private static final int HOUR_MILLIS = 60 * MINUTE_MILLIS;
    private static final int DAY_MILLIS = 24 * HOUR_MILLIS;
    public static ArrayList<String> languages = new ArrayList<>();
//    public static String aaTemp;
    public static String placeId = "";
    public static String place__Id = "";
    public static double latitude = 0.0;
    public static double longitude = 0.0;
    public static String address = "";

    /**
     * @param context
     * @return Returns true if there is network connectivity
     */


    public static boolean isInternetConnection(Context context) {
        boolean HaveConnectedWifi = false;
        boolean HaveConnectedMobile = false;
        try {
            ConnectivityManager cm = (ConnectivityManager) context
                    .getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo ni = cm.getActiveNetworkInfo();
            if (ni != null) {
                if (ni.getType() == ConnectivityManager.TYPE_WIFI)
                    if (ni.isConnectedOrConnecting())
                        HaveConnectedWifi = true;
                if (ni.getType() == ConnectivityManager.TYPE_MOBILE)
                    if (ni.isConnectedOrConnecting())
                        HaveConnectedMobile = true;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        if (HaveConnectedWifi || HaveConnectedMobile) {
            return true;
        } else {
            Toast.makeText(context, context.getString(R.string.please_check_internet_connection), Toast.LENGTH_SHORT).show();
            return false;
        }

    }

    /**
     * Display Toast Message
     **/
    public static void showToastMessageShort(Context context, String message) {
        Toast.makeText(context.getApplicationContext(), message,
                Toast.LENGTH_SHORT).show();
    }

    /**
     * Display Toast Message
     **/
    public static void showToastMessageLong(Context context, String message) {
        Toast.makeText(context.getApplicationContext(), message,
                Toast.LENGTH_LONG).show();
    }

    /**
     * Get IP address from first non-localhost interface
     *
     * @param useIPv4 true=return ipv4, false=return ipv6
     * @return address or empty string
     */
    public static String getIPAddress(boolean useIPv4) {
        try {
            List<NetworkInterface> interfaces = Collections
                    .list(NetworkInterface.getNetworkInterfaces());
            for (NetworkInterface intf : interfaces) {
                List<InetAddress> addrs = Collections.list(intf
                        .getInetAddresses());
                for (InetAddress addr : addrs) {
                    if (!addr.isLoopbackAddress()) {
                        String sAddr = addr.getHostAddress();
                        // boolean isIPv4 =
                        // InetAddressUtils.isIPv4Address(sAddr);
                        boolean isIPv4 = sAddr.indexOf(':') < 0;

                        if (useIPv4) {
                            if (isIPv4)
                                return sAddr;
                        } else {
                            if (!isIPv4) {
                                int delim = sAddr.indexOf('%'); // drop ip6 zone
                                // suffix
                                return delim < 0 ? sAddr.toUpperCase() : sAddr
                                        .substring(0, delim).toUpperCase();
                            }
                        }
                    }
                }
            }
        } catch (Exception ex) {
        } // for now eat exceptions
        return "";
    }

    public static boolean isLocationEnabled(Context context) {
        int locationMode = 0;
        String locationProviders;

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            try {
                locationMode = Settings.Secure.getInt(context.getContentResolver(), Settings.Secure.LOCATION_MODE);
            } catch (Exception e) {
                e.printStackTrace();
                return false;
            }
            return locationMode != Settings.Secure.LOCATION_MODE_OFF;

        } else {
            locationProviders = Settings.Secure.getString(context.getContentResolver(), Settings.Secure.LOCATION_PROVIDERS_ALLOWED);
            return !TextUtils.isEmpty(locationProviders);
        }


    }

    public static void showAlertWithSingleButton(Context context, String title, String message, final OnAlertOkClickListener onAlertOkClickListener) {
        AlertDialog.Builder builder =
                new AlertDialog.Builder(context, R.style.AppCompatAlertDialogStyle);
        builder.setTitle(title);
        builder.setMessage(message);
        builder.setCancelable(false);
        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                onAlertOkClickListener.onOkButtonClicked();
                dialog.dismiss();
            }
        });
        builder.show();
    }

    public static void showAlertWithCancelButton(Context context, String title, String message, final OnAlertOkClickListener onAlertOkClickListener) {
        AlertDialog.Builder builder =
                new AlertDialog.Builder(context, R.style.AppCompatAlertDialogStyle);
        builder.setTitle(title);
        builder.setMessage(message);
        builder.setCancelable(false);
        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                onAlertOkClickListener.onOkButtonClicked();
                dialog.dismiss();
            }
        });
        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        builder.show();
    }

    public static void callSuccessPopUp(final Context context, String message) {
        // Create custom dialog object
        final Dialog dialog = new Dialog(context);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.setCancelable(false);
        dialog.setCanceledOnTouchOutside(false);
        dialog.setContentView(R.layout.succuess_pop_up);
        TextView text_message = (TextView) dialog.findViewById(R.id.tv_success_message);
        text_message.setText(message);
//        dialog.getWindow().getAttributes().windowAnimations = R.style.DialogAnimation;
        TextView mBtnOk = (TextView) dialog.findViewById(R.id.btn_ok);
        mBtnOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
                ((Activity) context).finish();
            }
        });
        dialog.setTitle("Custom Dialog");
        dialog.show();
    }

    /**
     * Returns true when running Android TV
     *
     * @param c Context to detect UI Mode.
     * @return true when device is running in tv mode, false otherwise.
     */
    public static String getDeviceType(Context c) {
        UiModeManager uiModeManager = (UiModeManager) c.getSystemService(Context.UI_MODE_SERVICE);
        int modeType = uiModeManager.getCurrentModeType();
        switch (modeType) {
            case Configuration.UI_MODE_TYPE_TELEVISION:
                return "TELEVISION";
            case Configuration.UI_MODE_TYPE_WATCH:
                return "WATCH";
            case Configuration.UI_MODE_TYPE_NORMAL:
                String type = isTablet(c) ? "TABLET" : "PHONE";
                return type;
            case Configuration.UI_MODE_TYPE_UNDEFINED:
                return "UNKOWN";
            default:
                return "";
        }
    }

    public static boolean isRooted() {
        for (String p : Utility.BinaryPlaces) {
            File su = new File(p + "su");
            if (su.exists()) {
                return true;
            }
        }
        return false;//RootTools.isRootAvailable();
    }

    public static boolean isTablet(Context context) {
        return context.getResources().getConfiguration().smallestScreenWidthDp >= 600;
    }

    // Hide keyboard
    public static void hideSoftKeyboard(Activity activity) {
        try {
            InputMethodManager imm = (InputMethodManager) activity.getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(activity.getCurrentFocus().getWindowToken(), 0);
        } catch (Exception exp) {
        }
    }

    //print Hash Key for facebook
    public static String printKeyHash(Context context) {
        PackageInfo packageInfo;
        String key = null;
        try {
            //getting application package name, as defined in manifest
            String packageName = context.getApplicationContext().getPackageName();

            //Retriving package info
            packageInfo = context.getPackageManager().getPackageInfo(packageName,
                    PackageManager.GET_SIGNATURES);

            Log.e("Package Name=", context.getApplicationContext().getPackageName());

            for (Signature signature : packageInfo.signatures) {
                MessageDigest md = MessageDigest.getInstance("SHA");
                md.update(signature.toByteArray());
                key = new String(Base64.encode(md.digest(), 0));

                // String key = new String(Base64.encodeBytes(md.digest()));
                Log.e("Key_Hash=", key);
            }
        } catch (PackageManager.NameNotFoundException e1) {
            Log.e("Name not found", e1.toString());
        } catch (NoSuchAlgorithmException e) {
            Log.e("No such an algorithm", e.toString());
        } catch (Exception e) {
            Log.e("Exception", e.toString());
        }
        return key;
    }

    public static void getLatLngFromPDI(final Context context) {
        new AsyncTask<String, Void, String[]>() {
            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                CallProgressWheel.showLoadingDialog(context,"");
            }

            @Override
            protected String[] doInBackground(String... params) {
                String response;
                try {
                    response = LocationAddress.getLatLongByURL("http://maps.google.com/maps/api/geocode/json?address=" + address + "&sensor=false");
                    Log.d("response", "" + response);
                    return new String[]{response};
                } catch (Exception e) {
                    return new String[]{"error"};
                }
            }

            @Override
            protected void onPostExecute(String... result) {
                try {
                    JSONObject jsonObject = new JSONObject(result[0]);

                    double lng = ((JSONArray) jsonObject.get("results")).getJSONObject(0)
                            .getJSONObject("geometry").getJSONObject("location")
                            .getDouble("lng");

                    double lat = ((JSONArray) jsonObject.get("results")).getJSONObject(0)
                            .getJSONObject("geometry").getJSONObject("location")
                            .getDouble("lat");

                    Log.e("latitude", "" + lat);
                    Log.e("longitude", "" + lng);
                    Utility.latitude = lat;
                    Utility.longitude = lng;
                    if (context instanceof LocationActivity)
                        ((LocationActivity) context).setUpLayout();


                } catch (JSONException e) {
                    getLatLngFromPDI(context);
                    e.printStackTrace();
                }finally {
                    CallProgressWheel.dismissLoadingDialog();
                 }
            }
        }.execute();
    }

    static void getLatLngFromPlaceID(final Context context, GeoDataClient mGeoDataClient) {
        mGeoDataClient.getPlaceById(place__Id).addOnCompleteListener(new OnCompleteListener<PlaceBufferResponse>() {
            @Override
            public void onComplete(@NonNull Task<PlaceBufferResponse> task) {
                if (task.isSuccessful()) {
                    PlaceBufferResponse places = task.getResult();
                    Place myPlace = places.get(0);
                    Log.i("getLatLngFromPlaceID", "Place found: " + myPlace.getName());
                    latitude = myPlace.getLatLng().latitude;
                    longitude = myPlace.getLatLng().longitude;
                    ((LocationActivity) context).setUpLayout();
                    places.release();
                } else {
                    Log.e("getLatLngFromPlaceID", "Place not found.");
                }
            }
        });
    }
    public static ApiInterface googleService;
    public static void callGoogleApi(final Activity context, final AutoCompleteTextView mautocomTextviewDeliveryAddress, String address, OkHttpClient okHttpClient, Gson gson) {
        if(googleService==null)
        try {
            placeId = null;
//			CallProgressWheel.showLoadingDialog(context, "Loading...");
            Retrofit retrofit1 = new Retrofit.Builder()
                    .baseUrl(ApiInterface.BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create(gson))
                    .client(okHttpClient)
                    .build();
            googleService = retrofit1.create(ApiInterface.class);

            String types = "", input = mautocomTextviewDeliveryAddress.getText().toString().trim(), location = "", key = "AIzaSyALGUNJ_BwsxSHkXtokTVZsFv9QYDRnuhY";
            int radius = 0;
            googleService.getCityResults(types, input, location, radius, key).enqueue(new Callback<GooglePlaceApiResponsePojo>() {
                @Override
                public void onResponse(Call<GooglePlaceApiResponsePojo> call, Response<GooglePlaceApiResponsePojo> response) {
                    GooglePlaceApiResponsePojo places = response.body();
                    Log.e("places", response.body().getPredictions().size() + "===");
                    //getEmaildata(response.body().getPredictions(), mautocomTextviewDeliveryAddress, context);
//					CallProgressWheel.dismissLoadingDialog();
                    showAddress(response.body().getPredictions(), mautocomTextviewDeliveryAddress, context);
                    googleService=null;
                }

                @Override
                public void onFailure(Call<GooglePlaceApiResponsePojo> call, Throwable t) {
                    googleService=null;
                     t.printStackTrace();
//					CallProgressWheel.dismissLoadingDialog();
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
            googleService=null;

        }
    }

    private static void showAddress(final ArrayList<predictionsPojo> predictionsPojo, final AutoCompleteTextView mautocomTextviewDeliveryAddress, final Activity context) {
        if (languages == null)
            languages = new ArrayList<>();
        else
            languages.clear();
//		android.util.Log.e("predictionsPojo", predictionsPojo.size() + "");
        for (int i = 0; i < predictionsPojo.size(); i++) {
            languages.add(predictionsPojo.get(i).getDescription());
            Log.e("description", predictionsPojo.get(i).getDescription());
        }
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>
                (context, R.layout.spinner_dropdown_item, languages);

        // dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        mautocomTextviewDeliveryAddress.setThreshold(0);
        mautocomTextviewDeliveryAddress.setAdapter(dataAdapter);
        dataAdapter.notifyDataSetChanged();
        mautocomTextviewDeliveryAddress.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
                address = mautocomTextviewDeliveryAddress.getText().toString().trim();
                placeId = predictionsPojo.get(arg2).getPlace_id();
//                aaTemp = predictionsPojo.get(arg2).getDescription();
//                place__Id = predictionsPojo.get(arg2).getId();
                latitude = 0.0;
                longitude = 0.0;
                //type = 1;
                getLatLngFromPDI(context);
//                mautocomTextviewDeliveryAddress.setSelection(mautocomTextviewDeliveryAddress.getText().length());
//                if (context instanceof LocationActivity) {
//                    getLatLngFromPlaceID(context, ((LocationActivity) context).mGeoDataClient);
//                }
                Log.e("placeid", placeId);
                /*if (context instanceof HomeActivity)
                    searchCategories(context, null, null, placeId, aaTemp);
                    */
                //	Utility.hideKeyboard(context, mautocomTextviewDeliveryAddress);
            }
        });
    }

    public static File getFilePath(String fileName, String fileSuffix) {
        File storageDir = new File(Environment.getExternalStorageDirectory(), "Tongy");
        if (!storageDir.exists())
            storageDir.mkdir();
        File myPath = null;
        try {
            myPath = File.createTempFile(
                    fileName,  /* prefix */
                    fileSuffix,         /* suffix */
                    storageDir      /* directory */
            );
        } catch (IOException e) {
            e.printStackTrace();
        }
        return myPath;
    }

    /**
     * convert date in format
     *
     * @param date
     * @param activity
     * @return
     */

    public static String getTimeAgoPost(String date, Activity activity) {
        long time = Long.valueOf(date);
        if (time < 1000000000000L) {
            // if timestamp given in seconds, convert to millis
            time *= 1000;
        }

        long now = System.currentTimeMillis();
        if (time > now || time <= 0) {
            return "just now";
        }

        final long diff = now - time;
        Date date1 = new Date(time);
        // TODO: localize

        TimeZone utc = TimeZone.getTimeZone("etc/UTC");

        Resources r = activity.getResources();

        String prefix = r.getString(R.string.time_ago_prefix);

        double seconds = Math.abs(diff) / 1000;
        double minutes = seconds / 60;
        double hours = minutes / 60;
        double days = hours / 24;
        double years = days / 365;

        String words;
        if (seconds < 45) {
            words = r.getString(R.string.timeAgoSeconds, Math.round(seconds));
        } else if (seconds < 90) {
            words = r.getString(R.string.timeAgoMinute, 1);
        } else if (minutes < 45) {
            words = r.getString(R.string.timeAgoMinutes, Math.round(minutes));
        } else if (minutes < 90) {
            words = r.getString(R.string.timeAgoHour, 1);
        } else if (hours < 24) {

            words = r.getString(R.string.timeAgoHours, Math.round(hours));
            ;
        } else if (hours < 42) {
            words = "Yesterday ";
        } else {
            Date date12 = new Date(time);
            return new SimpleDateFormat("MMM dd,yyyy ").format(date12);
            // return diff / DAY_MILLIS + " days ago.";
        }

        StringBuilder sb = new StringBuilder();

        if (prefix != null && prefix.length() > 0) {
            sb.append(prefix).append(" ");
        }

        sb.append(words);


        return sb.toString().trim();
    }

    public static void showInvalidSessionDialog(final Context mContext) {
        try {

            AlertDialog.Builder builder = new AlertDialog.Builder(mContext,
                    R.style.AppTheme);
            builder.setTitle(mContext.getResources().getString(R.string.invalid_session));
            builder.setCancelable(false);
            builder.setMessage(mContext.getResources().getString(R.string.logout_device));
            builder.setPositiveButton("OK", new OnClickListener() {

                @Override
                public void onClick(DialogInterface dialog, int which) {

                    dialog.dismiss();
                    // TODO redirect to login screen
                    Global.sAppContext.startActivity(new Intent(mContext, LoginActivity.class).setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK));

                }
            });

            builder.show();
        } catch (Exception e) {
            Log.e(e);
        }
    }

    public static void onChangeClearButtonVisible(final Context context, final EditText editText, final View linearLayout) {
        try {
            editText.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                }

                @Override
                public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                    if (editText.getText().toString().trim().length() > 0) {
                        // textInputLayout.setErrorEnabled(false);
                        editText.setError(null);
                        linearLayout.setVisibility(View.VISIBLE);
                    } else {
                        linearLayout.setVisibility(View.GONE);
                    }
                }

                @Override
                public void afterTextChanged(Editable editable) {


                }
            });
            linearLayout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    if (context instanceof SearchActivity) {
                        ((SearchActivity) context).mtvNoData.setVisibility(View.GONE);
                    }
                    editText.setText("");

                }
            });


        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static String getDatePostDetail(String date, Activity activity) {

        long time = Long.valueOf(date);
        if (time < 1000000000000L) {
            // if timestamp given in seconds, convert to millis
            time *= 1000;
        }


        Date date12 = new Date(time);

        return new SimpleDateFormat("MMM dd, yyyy").format(date12);
    }

    public static String getTimePostDetail(String date, Activity activity) {
        long time = Long.valueOf(date);
        if (time < 1000000000000L) {
            // if timestamp given in seconds, convert to millis
            time *= 1000;
        }
        Date date12 = new Date(time);
        return new SimpleDateFormat("hh:mm a").format(date12);
    }

    public static String getColoredSpanned(String text, String color) {
        String input = "<font color=" + color + ">" + text + "</font>";
        return input;
 		/*
		* Use of the colored spanned
		* String name = getColoredSpanned("Hiren", "#800000");
		* String surName = getColoredSpanned("Patel","#000080");
		* txtView.setText(Html.fromHtml(name+" "+surName));
		* */
    }

    public static void goToChatActivity(Context context, int toUserId, String userName, String userImage) {

        String fromUserID = PreferenceHandler.readString(context, PreferenceHandler.PREF_KEY_USER_ID, "");
        String channelName = toUserId + "_" + fromUserID;
        if (Integer.valueOf(toUserId) > Integer.valueOf(fromUserID))
            channelName = fromUserID + "_" + toUserId;
        Intent intent2 = new Intent(context, MainChatActivity.class);
        intent2.putExtra(Constants.CHANNEL_NAME, channelName);
        intent2.putExtra(Constants.ROOM_USER_NAME, userName);
        intent2.putExtra(Constants.TWILIO_RECEAVER_IMAGE, userImage);
        context.startActivity(intent2);
        if(context instanceof ConnectionAcceptedActivity){
            ((ConnectionAcceptedActivity)context).finish();
            ((ConnectionAcceptedActivity)context).overridePendingTransition(R.anim.right_in, R.anim.right_out);
        }
    }

    public static void openBlockMenu(final Activity context, final Retrofit retrofit, String blockUnblock, View mivDotIcon, final int userId) {
        final PopupWindow popup = new PopupWindow(context);
        View layout = LayoutInflater.from(context).inflate(R.layout.layout_options_popup, null);
        //popup.showAtLocation(layout, Gravity.RIGHT, 0, 0);
        popup.setContentView(layout);
        // Set content width and height
        popup.setHeight(WindowManager.LayoutParams.WRAP_CONTENT);
        popup.setWidth(WindowManager.LayoutParams.WRAP_CONTENT);
        // Closes the popup window when touch outside of it - when looses focus
        popup.setOutsideTouchable(true);
        popup.setFocusable(true);
        popup.setBackgroundDrawable(new BitmapDrawable());

        LinearLayout layout_block = (LinearLayout) layout.findViewById(R.id.layout_block);
        LinearLayout layout_report = (LinearLayout) layout.findViewById(R.id.layout_report);
        LinearLayout layout_remove = (LinearLayout) layout.findViewById(R.id.layout_remove);
        final TextView mtvBlockUnblock = (TextView) layout.findViewById(R.id.tv_block);
        mtvBlockUnblock.setText(blockUnblock);

        layout_block.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //popup.dismiss();
                ApiControllerClass.callblockApi(context, retrofit, userId, popup, mtvBlockUnblock);
            }
        });
        layout_remove.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Create custom dialog object
                final Dialog dialog = new Dialog(context);
                dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                dialog.setCancelable(true);
                dialog.setCanceledOnTouchOutside(true);

                dialog.setContentView(R.layout.logout_account_pop_up);

                Button mBtnConfirm = (Button) dialog.findViewById(R.id.btn_confirm);
                Button mBtnCancel = (Button) dialog.findViewById(R.id.btn_cancel);
                TextView mtitle = (TextView) dialog.findViewById(R.id.title);
                mtitle.setText(context.getResources().getString(R.string.remove_user));

                TextView mMessage = (TextView) dialog.findViewById(R.id.message);
                mMessage.setText(context.getResources().getString(R.string.remove_user_message));

                mBtnConfirm.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialog.dismiss();
                        popup.dismiss();
                        ApiControllerClass.callUserAction(context, retrofit, context.getResources().getString(R.string.delete_action), popup, userId, null);


                    }
                });
                mBtnCancel.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialog.dismiss();
                    }
                });
                dialog.setTitle("Custom Dialog");
                dialog.show();

            }
        });
        layout_report.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openReportUserPopup(context, retrofit, userId, popup);

            }
        });

        popup.showAsDropDown(mivDotIcon);
    }

    /**
     * report user pop up
     *
     * @param userId
     */
    private static void openReportUserPopup(final Context context, final Retrofit retrofit, final int userId, final PopupWindow popupMenu) {
        // Create custom dialog object
        final Dialog dialog = new Dialog(context);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.setCancelable(false);
        dialog.setCanceledOnTouchOutside(false);

        dialog.setContentView(R.layout.layout_report_abuse);
        final TextView mtvDialogTitle = (TextView) dialog.findViewById(R.id.tvDialogTitle);
        mtvDialogTitle.setText(context.getResources().getString(R.string.report_user));
        final EditText metReason = (EditText) dialog.findViewById(R.id.etReason);
        final EditText metComment = (EditText) dialog.findViewById(R.id.et_comment);
        metReason.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                PopupMenu dropDownMenu = new PopupMenu(context, metReason);
                dropDownMenu.getMenuInflater().inflate(R.menu.drop_down_menu, dropDownMenu.getMenu());
                //showMenu.setText("DropDown Menu");
                dropDownMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {

                    @Override
                    public boolean onMenuItemClick(MenuItem menuItem) {
                        //Toast.makeText(mContext, "You have clicked " + menuItem.getTitle(), Toast.LENGTH_LONG).show();
                        metReason.setText(menuItem.getTitle());
                        metReason.setError(null);
                        return true;
                    }
                });
                dropDownMenu.show();
            }
        });
        TextView mBtnOk = (TextView) dialog.findViewById(R.id.btn_ok);
        mBtnOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                metReason.setError(null);
                metComment.setError(null);
                if (FieldValidators.isNullOrEmpty(metReason)) {
                    metReason.setFocusable(true);
                    metReason.requestFocus();
                    metReason.setError("Please select reason for report");
                    return;
                } else if (FieldValidators.isNullOrEmpty(metComment)) {
                    metComment.setFocusable(true);
                    metComment.requestFocus();
                    metComment.setError("Please enter your comment");
                    return;
                } else {
                    dialog.dismiss();
                    popupMenu.dismiss();
                    ApiControllerClass.callReportUserApi(context, retrofit, metReason, metComment, userId, dialog);
                }
                //((Activity)mContext).finish();
            }
        });

        TextView mbtnCancel = (TextView) dialog.findViewById(R.id.btn_cancel);
        mbtnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
                //((Activity)mContext).finish();
            }
        });
//         dialog.setTitle("Custom Dialog");
        dialog.show();
    }

    public void showNoInternetDialog(final Activity mContext) {
        try {

            AlertDialog.Builder builder = new AlertDialog.Builder(mContext,
                    R.style.AppTheme);
            builder.setTitle(mContext.getResources().getString(R.string.no_internet_title));
            builder.setMessage(mContext.getResources().getString(R.string.no_internet));
            builder.setPositiveButton("OK", new OnClickListener() {

                @Override
                public void onClick(DialogInterface dialog, int which) {

                    dialog.dismiss();
                    //((Activity) mContext).finish();

                }
            });

            builder.show();
        } catch (Exception e) {
            showToastMessageLong(mContext,
                    mContext.getResources().getString(R.string.no_internet));
        }
    }

    public interface OnAlertOkClickListener {
        void onOkButtonClicked();
    }

    public static void setUserImage(Context context,ImageView view,List<ProfileResponsePojo.UserImageData> pojo) {
        if (pojo != null && pojo.size() > 0) {
             String coverImage = null;
            for (ProfileResponsePojo.UserImageData userImageData : pojo
                    ) {
                if (userImageData.getIs_cover_image().equals("1")) {
                    coverImage = userImageData.getImage();
                    break;
                }
            }
            if(coverImage==null)
                coverImage= pojo.get(0).getImage();

            Glide.with(context).load(coverImage).into(view);

        }
    }

}
