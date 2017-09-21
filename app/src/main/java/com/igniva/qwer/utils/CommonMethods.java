package com.igniva.qwer.utils;

import android.Manifest;
import android.app.Activity;
import android.content.ComponentName;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationManager;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.location.FusedLocationProviderApi;

/**
 * Created by sharma on 10/11/2016.
 */

public class CommonMethods {





    public static android.app.AlertDialog locationAlertDialog;
    /**
     * Function to show settings alert dialog
     * On pressing Settings button will lauch Settings Options
     * */
    public static void showLocationSettingsAlert(final Context mContext){
        try{

            if(!((LocationManager) mContext.getSystemService(Context.LOCATION_SERVICE)).isProviderEnabled(LocationManager.NETWORK_PROVIDER)
                    &&
                    !((LocationManager) mContext.getSystemService(Context.LOCATION_SERVICE)).isProviderEnabled(LocationManager.GPS_PROVIDER)){
                if(locationAlertDialog != null && locationAlertDialog.isShowing()){
                    locationAlertDialog.dismiss();
                }
                android.app.AlertDialog.Builder alertDialogPrepare = new android.app.AlertDialog.Builder(mContext);

                // Setting Dialog Title
                alertDialogPrepare.setTitle("Location Settings");
                alertDialogPrepare.setCancelable(false);

                // Setting Dialog Message
                alertDialogPrepare.setMessage("Location is not enabled. Do you want to enable it from settings menu?");

                // On pressing Settings button
                alertDialogPrepare.setPositiveButton("Settings", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        Intent intent = new Intent(android.provider.Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                        mContext.startActivity(intent);
                        dialog.dismiss();
                    }
                });

                locationAlertDialog = alertDialogPrepare.create();

                // Showing Alert Message
                locationAlertDialog.show();
            }
        } catch(Exception e){
            e.printStackTrace();
        }
    }

    public static android.app.AlertDialog googlePlayAlertDialog;
    /**
     * Function to show settings alert dialog
     * On pressing Settings button will lauch Settings Options
     * */
    public static void showGooglePlayErrorAlert(final Activity mContext){
        try{
            if(googlePlayAlertDialog != null && googlePlayAlertDialog.isShowing()){
                googlePlayAlertDialog.dismiss();
            }
            android.app.AlertDialog.Builder alertDialogPrepare = new android.app.AlertDialog.Builder(mContext);

            // Setting Dialog Title
            alertDialogPrepare.setTitle("Google Play Services Error");
            alertDialogPrepare.setCancelable(false);

            // Setting Dialog Message
            alertDialogPrepare.setMessage("Google Play services not found or outdated. Please install Google Play Services?");

            // On pressing Settings button
            alertDialogPrepare.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int which) {
                    dialog.dismiss();
                    try {
                        Intent intent = new Intent(Intent.ACTION_VIEW);
                        intent.setData(Uri.parse("https://play.google.com/store/apps/details?id=com.google.android.gms"));
                        ComponentName componentName = intent.resolveActivity(mContext.getPackageManager());
                        if(componentName != null){
                            mContext.startActivity(intent);
                        }
                    } catch(Exception e){
                        e.printStackTrace();
                    }
                }
            });

            // on pressing cancel button
            alertDialogPrepare.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int which) {
                    dialog.dismiss();
                    mContext.finish();
                }
            });

            googlePlayAlertDialog = alertDialogPrepare.create();

            // Showing Alert Message
            googlePlayAlertDialog.show();
        } catch(Exception e){
            e.printStackTrace();
        }
    }
    public static boolean mockLocationEnabled(Location location) {
        try {
            int i=6;
            if (i==6) {
                boolean isMockLocation = false;
                if(location != null){
                    Bundle extras = location.getExtras();
                    isMockLocation = extras != null && extras.getBoolean(FusedLocationProviderApi.KEY_MOCK_LOCATION, false);
                }
                return isMockLocation;
            } else {
                return false;
            }
        } catch (Exception e) {
            return false;
        }
    }

}
