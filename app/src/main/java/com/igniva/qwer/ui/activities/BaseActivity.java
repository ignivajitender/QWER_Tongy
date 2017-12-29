package com.igniva.qwer.ui.activities;

import android.Manifest;
import android.content.Intent;
import android.location.Location;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.igniva.qwer.R;
import com.igniva.qwer.utils.Global;
import com.igniva.qwer.utils.ImagePicker;
import com.igniva.qwer.utils.Log;
import com.igniva.qwer.utils.Utility;

import pub.devrel.easypermissions.AfterPermissionGranted;
import pub.devrel.easypermissions.EasyPermissions;

/**
 * Created by igniva-andriod-11 on 29/4/16.
 */
public abstract class BaseActivity extends AppCompatActivity {
    String TAG="BaseActivity";
    private final int RC_CAMERA_PERM = 123;
    public final int RC_LOCATION_PERM = 124;
    /**
     * Represents a geographical location.
     */
    private int IMAGE_REQUEST_CODE = 500;
    /**
     * Provides the entry point to the Fused Location Provider API.
     */
    private FusedLocationProviderClient mFusedLocationClient;

    protected abstract void setUpLayout();

    protected abstract void setDataInViewObjects();

    protected abstract void setUpToolbar();

    @AfterPermissionGranted(RC_LOCATION_PERM)
    public void changeLocation() {
        if (hasLocationPermission()) {
            // Have permission, do the thing!
            // startActivityForResult(ImagePicker.getPickImageIntent(LocationActivity.this), IMAGE_REQUEST_CODE);
            try {
                 startActivity(new Intent(BaseActivity.this, LocationActivity.class));
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            // Ask for one permission
            EasyPermissions.requestPermissions(
                    this,
                    getString(R.string.rationale_ask_again),
                    RC_LOCATION_PERM,
                    Manifest.permission.ACCESS_COARSE_LOCATION, Manifest.permission.ACCESS_FINE_LOCATION);
        }
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mFusedLocationClient = LocationServices.getFusedLocationProviderClient(Global.sAppContext);

    }

     public void getLocation() {
          if (!hasLocationPermission())
            EasyPermissions.requestPermissions(
                    this,
                    getString(R.string.rationale_ask_again),
                    RC_LOCATION_PERM,
                    Manifest.permission.ACCESS_COARSE_LOCATION, Manifest.permission.ACCESS_FINE_LOCATION);
         else {
            getLastLocation();
        }
    }


    private boolean hasLocationPermission() {
        return EasyPermissions.hasPermissions(this, Manifest.permission.ACCESS_COARSE_LOCATION, Manifest.permission.ACCESS_FINE_LOCATION);
    }
    /**
     * Provides a simple way of getting a device's location and is well suited for
     * applications that do not require a fine-grained location and that do not need location
     * updates. Gets the best and most recent location currently available, which may be null
     * in rare cases when a location is not available.
     * <p>
     * Note: this method should be called after location permission has been granted.
     */
    @AfterPermissionGranted(RC_LOCATION_PERM)
    @SuppressWarnings("MissingPermission")
    public void getLastLocation() {

        if(hasLocationPermission())
        mFusedLocationClient.getLastLocation()
                 .addOnCompleteListener(this, new OnCompleteListener<Location>() {
                    @Override
                    public void onComplete(@NonNull Task<Location> task) {

                        if (task.isSuccessful() && task.getResult() != null) {
                            Global.mLastLocation = task.getResult();
                            Utility.latitude=task.getResult().getLatitude();
                            Utility.longitude=task.getResult().getLongitude();
                            Log.e("","mLastLocation===="+Global.mLastLocation);
//                            if(Global.mLastLocation==null)
//                                getLastLocation();
                         } else  {
                            if(!Utility.isLocationEnabled(Global.sAppContext))
                                Utility.showAlertWithSingleButton(BaseActivity.this, getResources().getString(R.string.location), getResources().getString(R.string.enable_location_setting), new Utility.OnAlertOkClickListener() {
                                    @Override
                                    public void onOkButtonClicked() {
                                        mFusedLocationClient = LocationServices.getFusedLocationProviderClient(Global.sAppContext);
                                         getLastLocation();
                                    }
                                });
//                            else
//                                getLastLocation();

                            Log.e( "getLastLocation:exception","task.getException===="+ task.getException());
                         }
                    }
                });
    }

    /**
     * check run time camera permissions
     */
    @AfterPermissionGranted(RC_CAMERA_PERM)
    public void changeImage() {
        if (hasCameraPermission()) {
            // Have permission, do the thing!
            startActivityForResult(ImagePicker.getPickImageIntent(BaseActivity.this), IMAGE_REQUEST_CODE);
        } else {
            // Ask for one permission
            EasyPermissions.requestPermissions(
                    this,
                    getString(R.string.rationale_ask_again),
                    RC_CAMERA_PERM,
                    Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE);
        }
    }

    private boolean hasCameraPermission() {
        return EasyPermissions.hasPermissions(this, Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE);
    }

}
