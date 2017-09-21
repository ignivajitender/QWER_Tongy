package com.igniva.qwer.ui.activities;

import android.Manifest;
import android.app.Dialog;
import android.app.DialogFragment;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.igniva.qwer.R;
import com.igniva.qwer.utils.DataNames;
import com.igniva.qwer.utils.LocationFetcher;
import com.igniva.qwer.utils.LocationInit;
import com.igniva.qwer.utils.LocationUpdate;
import com.jaredrummler.materialspinner.MaterialSpinner;

import java.util.Calendar;

/**
 * Created by jitender-android on 1/3/17.
 */

public class TestActivity extends BaseActivity implements LocationUpdate {

    public static TextView mTvStartTime, mTvEndTime, mTvDone;
    Calendar calendar;
    String time;
    String selectedItem = "";
    int mPosition = 0;
    public static String type;
    private static final int REQUEST_PERMISSION_REQ_CODE = 243;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);
        setUpLayout();
        setDataInViewObjects();

        setGender();

        getLocation();

    }

    private void getLocation() {

        if ((ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED)
                && (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED)) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION}, REQUEST_PERMISSION_REQ_CODE);
            return;
        }

        if (DataNames.locationFetcher == null) {
            DataNames.locationFetcher = new LocationFetcher(TestActivity.this, 1000);
        } else {
            DataNames.locationFetcher.connect();
        }
    }

    private void setGender() {

        String[] option1 = new String[]{"Select Gender", "Male", "Female"};

        final MaterialSpinner spinner = (MaterialSpinner) findViewById(R.id.spinner);
        findViewById(R.id.spinner).setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                spinner.setSelectedIndex(0);

                return false;
            }
        });
        spinner.setItems(option1);
        spinner.setOnItemSelectedListener(new MaterialSpinner.OnItemSelectedListener<String>() {
            @Override
            public void onItemSelected(MaterialSpinner view, int position, long id, String item) {
                //Snackbar.make(view, "Clicked " + item, Snackbar.LENGTH_LONG).show();
                selectedItem = item;
                mPosition = position;
            }
        });
        spinner.setOnNothingSelectedListener(new MaterialSpinner.OnNothingSelectedListener() {
            @Override
            public void onNothingSelected(MaterialSpinner spinner) {
                spinner.setSelectedIndex(mPosition);
            }
        });

    }

    @Override
    protected void setUpLayout() {
        mTvStartTime = (TextView) findViewById(R.id.tv_start_time);
        mTvEndTime = (TextView) findViewById(R.id.tv_end_time);
        mTvDone = (TextView) findViewById(R.id.tv_done);
    }

    @Override
    protected void setDataInViewObjects() {
        mTvStartTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                type = "START";
                DialogFragment newFragment = new TimePickerFragment();
                newFragment.show(getFragmentManager(), "timePicker");

            }
        });

        mTvEndTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                type = "END";
                DialogFragment newFragment = new TimePickerFragment();
                newFragment.show(getFragmentManager(), "timePicker");

            }
        });


    }

    @Override
    protected void setUpToolbar() {

    }

    @Override
    public void onLocationChanged(Location location) {

    }


    public static class TimePickerFragment extends DialogFragment
            implements TimePickerDialog.OnTimeSetListener {


        @Override
        public Dialog onCreateDialog(Bundle savedInstanceState) {
            // Use the current time as the default values for the picker
            final Calendar c = Calendar.getInstance();
            int hour = c.get(Calendar.HOUR_OF_DAY);
            int minute = c.get(Calendar.MINUTE);
            // Create a new instance of TimePickerDialog and return it
            return new TimePickerDialog(getActivity(), this, hour, minute,
                    true
            );
        }

        public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
            // Do something with the time chosen by the user
            String  hour=hourOfDay<9?"0"+hourOfDay:""+hourOfDay;
            String  min=minute<9?"0"+minute:""+minute;
            switch (type) {
                case "START":
                    mTvStartTime.setText(hour + ":" + min);
                    break;
                case "END":
                    mTvEndTime.setText(hour + ":" + min);
                    break;
                default:
                    break;
            }

        }
    }

    @Override
    public void onRequestPermissionsResult(final int requestCode, final @NonNull String[] permissions, final @NonNull int[] grantResults) {
        switch (requestCode) {
            case REQUEST_PERMISSION_REQ_CODE: {
                if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    Toast.makeText(TestActivity.this, "Permission granted.", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(TestActivity.this, "Permission denied.", Toast.LENGTH_SHORT).show();
                }
                break;
            }
        }
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (LocationInit.LOCATION_REQUEST_CODE == requestCode) {
            if (0 == resultCode) {
                DataNames.locationSettingsNoPressed = true;
                DataNames.locationAddressSettingsNoPressed = true;
            }
            runAfterLocation();
        }
    }

    private void runAfterLocation() {

        if (DataNames.locationFetcher != null) {
            DataNames.loginLatitude = DataNames.locationFetcher.getLatitude();
            DataNames.loginLongitude = DataNames.locationFetcher.getLongitude();
            Log.v("lat", "---> " + DataNames.loginLatitude);
            Log.v("longi", "---> " + DataNames.loginLongitude);
        }
    }


}
