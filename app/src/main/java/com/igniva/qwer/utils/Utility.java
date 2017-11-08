package com.igniva.qwer.utils;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.UiModeManager;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.Signature;
import android.content.res.Configuration;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Environment;
import android.util.Base64;
import android.view.View;
import android.view.Window;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.igniva.qwer.R;
import com.igniva.qwer.controller.ApiInterface;
import com.igniva.qwer.model.GooglePlaceApiResponsePojo;
import com.igniva.qwer.model.predictionsPojo;
import com.igniva.qwer.ui.views.CallProgressWheel;

import java.io.File;
import java.io.IOException;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import okhttp3.OkHttpClient;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class Utility {
	static final String[] BinaryPlaces = { "/data/bin/", "/system/bin/", "/system/xbin/", "/sbin/",
			"/data/local/xbin/", "/data/local/bin/", "/system/sd/xbin/", "/system/bin/failsafe/",
			"/data/local/" };

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

		if(HaveConnectedWifi || HaveConnectedMobile){
			return true;
		}else{
			Toast.makeText(context,context.getString(R.string.please_check_internet_connection), Toast.LENGTH_SHORT).show();
			return false;
		}

	}

	/**
	 * Display Toast Message
	 **/
	public static void showToastMessageShort(Activity context, String message) {
		Toast.makeText(context.getApplicationContext(), message,
				Toast.LENGTH_SHORT).show();
	}

	/**
	 * Display Toast Message
	 **/
	public static void showToastMessageLong(Activity context, String message) {
		Toast.makeText(context.getApplicationContext(), message,
				Toast.LENGTH_LONG).show();
	}



	/**
	 * Get IP address from first non-localhost interface
	 * 
	 * @param useIPv4
	 *            true=return ipv4, false=return ipv6
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



    public void showInvalidSessionDialog(final Activity mContext) {
        try {

            AlertDialog.Builder builder = new AlertDialog.Builder(mContext,
                    R.style.AppTheme);
            builder.setTitle(mContext.getResources().getString(R.string.invalid_session));
            builder.setMessage(mContext.getResources().getString(R.string.logout_device));
            builder.setPositiveButton("OK", new OnClickListener() {

                @Override
                public void onClick(DialogInterface dialog, int which) {

                    dialog.dismiss();
                    // TODO redirect to login screen
                    ((Activity) mContext).finish();

                }
            });

            builder.show();
        } catch (Exception e) {
            showToastMessageLong(mContext,
                    mContext.getResources().getString(R.string.no_internet));
        }
    }

	public static void showAlertWithSingleButton(Context context, String message, final OnAlertOkClickListener onAlertOkClickListener){
		AlertDialog.Builder builder =
				new AlertDialog.Builder(context, R.style.AppCompatAlertDialogStyle);
		builder.setTitle("Error");
		builder.setMessage(message);
		builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
			@Override
			public void onClick(DialogInterface dialog, int which) {
				onAlertOkClickListener.onOkButtonClicked();
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
		TextView mBtnOk=(TextView)dialog.findViewById(R.id.btn_ok);
		mBtnOk.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				dialog.dismiss();
				((Activity)context).finish();
			}
		});

		dialog.setTitle("Custom Dialog");


		dialog.show();


	}

	public interface OnAlertOkClickListener {
		 void onOkButtonClicked();
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
		switch (modeType){
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

	public static boolean isRooted(){
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

	public static ArrayList<String> languages = new ArrayList<>();
	public static String aaTemp;
	public static String placeId = "";
	public static double latitude = 0.0;
	public static double longitude = 0.0;
	public static String address = "";
	public static void callGoogleApi(final Activity context, final AutoCompleteTextView mautocomTextviewDeliveryAddress, String address, OkHttpClient okHttpClient, Gson gson) {

		try {
			placeId=null;
			CallProgressWheel.showLoadingDialog(context, "Loading...");
			Retrofit retrofit1 = new Retrofit.Builder()
					.baseUrl(ApiInterface.BASE_URL)
					.addConverterFactory(GsonConverterFactory.create(gson))
					.client(okHttpClient)
					.build();

			ApiInterface service = retrofit1.create(ApiInterface.class);

			String types = "", input = mautocomTextviewDeliveryAddress.getText().toString().trim(), location = "", key = "AIzaSyALGUNJ_BwsxSHkXtokTVZsFv9QYDRnuhY";
			int radius = 0;
			service.getCityResults(types, input, location, radius, key).enqueue(new Callback<GooglePlaceApiResponsePojo>() {
				@Override
				public void onResponse(Call<GooglePlaceApiResponsePojo> call, Response<GooglePlaceApiResponsePojo> response) {
					GooglePlaceApiResponsePojo places = response.body();
					Log.e("places", response.body().getPredictions().size() + "===");
					//getEmaildata(response.body().getPredictions(), mautocomTextviewDeliveryAddress, context);
					showAddress(response.body().getPredictions(), mautocomTextviewDeliveryAddress, context);
					CallProgressWheel.dismissLoadingDialog();
				}

				@Override
				public void onFailure(Call<GooglePlaceApiResponsePojo> call, Throwable t) {
					t.printStackTrace();
					CallProgressWheel.dismissLoadingDialog();
				}
			});


		} catch (Exception e) {
			e.printStackTrace();
		}


	}

	private static void showAddress(final ArrayList<predictionsPojo> predictionsPojo, final AutoCompleteTextView mautocomTextviewDeliveryAddress, final Activity context) {


		if (languages == null)
			languages = new ArrayList<>();
		else
			languages.clear();
		android.util.Log.e("predictionsPojo", predictionsPojo.size() + "");
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
				aaTemp = predictionsPojo.get(arg2).getDescription();
				latitude = 0.0;
				longitude = 0.0;
				//type = 1;
				mautocomTextviewDeliveryAddress.setSelection(mautocomTextviewDeliveryAddress.getText().length());
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
}
