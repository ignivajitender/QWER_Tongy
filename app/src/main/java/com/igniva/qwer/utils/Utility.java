package com.igniva.qwer.utils;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.UiModeManager;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.content.res.Configuration;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.widget.Toast;

import com.igniva.qwer.R;

import java.io.File;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.util.Collections;
import java.util.List;

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

		return HaveConnectedWifi || HaveConnectedMobile;
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

}
