package com.igniva.qwer.ui.activities;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.design.widget.FloatingActionButton;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.AutoCompleteTextView;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.gms.location.places.GeoDataClient;
import com.google.android.gms.location.places.Places;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.gson.Gson;
import com.igniva.qwer.R;
import com.igniva.qwer.model.PostDetailPojo;
import com.igniva.qwer.utils.Global;
import com.igniva.qwer.utils.LocationAddress;
import com.igniva.qwer.utils.Log;
import com.igniva.qwer.utils.Utility;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import okhttp3.OkHttpClient;

public class LocationActivity extends BaseActivity implements OnMapReadyCallback {

    @BindView(R.id.tv_tap_to_rename)
    TextView mtvTitle;
    @BindView(R.id.ivbackIcon)
    ImageView ivbackIcon;
    @BindView(R.id.tv_location)
    TextView tvLocation;
    @BindView(R.id.autocomTextViewAddress)
    AutoCompleteTextView mautocomTextViewAddress;
    @Inject
    OkHttpClient okHttpClient;
    @Inject
    Gson gson;
    @BindView(R.id.fab_tick)
    FloatingActionButton fabTick;
    // Google Map
    private GoogleMap googleMap;
    public GeoDataClient mGeoDataClient;
    PostDetailPojo.DataPojo pojo;
    @OnClick(R.id.ivbackIcon)
    public void back() {
        onBackPressed();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        ((Global) getApplication()).getNetComponent().inject(this);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_location);
        ButterKnife.bind(this);
        initilizeMap();
        Utility.address=null;
        mtvTitle.setText(getResources().getString(R.string.location));
        if(getIntent().hasExtra("dataPojo")){
              pojo= (PostDetailPojo.DataPojo) getIntent().getSerializableExtra("dataPojo");
              tvLocation.setText(pojo.getClass_location());
        }
        else {
            getLastLocation();
            setUpToolbar();
        }

    }

    /**
     * function to load map. If map is not created it will create it for you
     */
    private void initilizeMap() {
        MapFragment mapFragment = (MapFragment) getFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(LocationActivity.this);
    }

    @Override
    protected void onResume() {
        super.onResume();
//        initilizeMap();
//        getLastLocation();

    }

    @Override
    public void onMapReady(GoogleMap map) {

        googleMap = map;

        setUpMap();

    }

    @SuppressLint("MissingPermission")
    public void setUpMap() {
        googleMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);
//         googleMap.setTrafficEnabled(true);
//        googleMap.setIndoorEnabled(true);
        googleMap.setBuildingsEnabled(true);
//        googleMap.getUiSettings().setZoomControlsEnabled(true);

        if(getIntent().hasExtra("dataPojo")){
            mautocomTextViewAddress.setVisibility(View.GONE);
            PostDetailPojo.DataPojo pojo= (PostDetailPojo.DataPojo) getIntent().getSerializableExtra("dataPojo");
            drawMarker(Double.parseDouble(pojo.getLat()),Double.parseDouble(pojo.getLng()));
        }
         else if (Global.mLastLocation != null) {
            drawMarker();
            Utility.latitude = Global.mLastLocation.getLatitude();
            Utility.longitude = Global.mLastLocation.getLongitude();
            LocationAddress locationAddress = new LocationAddress();
            locationAddress.getAddressFromLocation(Utility.latitude, Utility.longitude,
                    getApplicationContext(), new GeocoderHandler());

        }
        googleMap.setMyLocationEnabled(true);
    }

    private void drawMarker() {
        Log.e("drawMarker", "lat------" + Utility.latitude);
        Log.e("drawMarker", "lat------" + Utility.longitude);
        googleMap.clear();
        Marker marker = googleMap.addMarker(new MarkerOptions()
                .position(new LatLng(Utility.latitude, Utility.longitude))
        );

        moveToCurrentLocation(new LatLng(Utility.latitude, Utility.longitude));

    }

    private void moveToCurrentLocation(LatLng currentLocation) {
        googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(currentLocation, 15));
        // Zoom in, animating the camera.
        googleMap.animateCamera(CameraUpdateFactory.zoomIn());
        // Zoom out to zoom level 10, animating with a duration of 2 seconds.
        googleMap.animateCamera(CameraUpdateFactory.zoomTo(15), 2000, null);
    }

    @Override
    public void setUpLayout() {
        tvLocation.setText(mautocomTextViewAddress.getText().toString());
        drawMarker();
    }

    @Override
    protected void setDataInViewObjects() {
    }


    @Override
    protected void setUpToolbar() {
        mGeoDataClient = Places.getGeoDataClient(this, null);

         mautocomTextViewAddress.addTextChangedListener(new TextWatcher() {
            @Override
            public void afterTextChanged(Editable s) {

            }

            @Override
            public void beforeTextChanged(CharSequence s, int start,
                                          int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start,
                                      int before, int count) {
                if (s.length() > 2) {
                    // call google place api to fetch addresses
                    Utility.callGoogleApi(LocationActivity.this, mautocomTextViewAddress, "", okHttpClient, gson);
                }

               /* if (!mautocomTextviewAddress.getText().toString().equals("")) { //if edittext include text
                    mbtnClearAddress.setVisibility(View.VISIBLE);
                } else { //not include text
                    mbtnClearAddress.setVisibility(View.GONE);
                }*/
            }
        });

    }

    @OnClick(R.id.fab_tick)
    public void onViewClicked() {
        onBackPressed();
    }

    private class GeocoderHandler extends Handler {
        @Override
        public void handleMessage(Message message) {
            String locationAddress;
            switch (message.what) {
                case 1:
                    Bundle bundle = message.getData();
                    locationAddress = bundle.getString("address");
                    break;
                default:
                    locationAddress = null;
            }
            Utility.address = locationAddress;
            tvLocation.setText(locationAddress);

        }
    }
    private void drawMarker(Double lat,Double lng) {
        Log.e("drawMarker","lat------"+Utility.latitude);
        Log.e("drawMarker","lat------"+Utility.longitude);
        googleMap.clear();
        Marker marker = googleMap.addMarker(new MarkerOptions()
                .position(new LatLng(lat, lng))
        );

        moveToCurrentLocation(new LatLng(lat, lng));

    }

}
