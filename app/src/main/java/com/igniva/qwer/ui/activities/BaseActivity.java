package com.igniva.qwer.ui.activities;

import android.Manifest;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;

import com.igniva.qwer.R;

import pub.devrel.easypermissions.AfterPermissionGranted;
import pub.devrel.easypermissions.EasyPermissions;

/**
 * Created by igniva-andriod-11 on 29/4/16.
 */
public abstract class BaseActivity extends AppCompatActivity{

    protected abstract void setUpLayout();
    protected abstract void setDataInViewObjects();
    protected abstract void setUpToolbar();

    private final int RC_CAMERA_PERM = 123;
    private int IMAGE_REQUEST_CODE = 500;
    @AfterPermissionGranted(RC_CAMERA_PERM)
    public void changeLocation() {
        if (hasLocationPermission()) {
            // Have permission, do the thing!
            // startActivityForResult(ImagePicker.getPickImageIntent(LocationActivity.this), IMAGE_REQUEST_CODE);
            try {

                startActivity(new Intent(BaseActivity.this,LocationActivity.class));
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            // Ask for one permission
            EasyPermissions.requestPermissions(
                    this,
                    getString(R.string.rationale_ask_again),
                    RC_CAMERA_PERM,
                    Manifest.permission.ACCESS_COARSE_LOCATION, Manifest.permission.ACCESS_FINE_LOCATION);
        }
    }
    private boolean hasLocationPermission() {
        return EasyPermissions.hasPermissions(this, Manifest.permission.ACCESS_COARSE_LOCATION,  Manifest.permission.ACCESS_FINE_LOCATION);
    }


}
