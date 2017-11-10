package com.igniva.qwer.ui.activities;

import android.os.Bundle;
import android.widget.TextView;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.igniva.qwer.R;
import com.igniva.qwer.utils.Global;
import com.igniva.qwer.utils.Utility;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class LocationActivity extends BaseActivity implements OnMapReadyCallback {




    // Google Map
    private GoogleMap googleMap;

    @OnClick(R.id.ivbackIcon)
    public void back(){
        onBackPressed();
    }

    @BindView(R.id.tv_tap_to_rename)
    TextView mtvTitle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        ((Global) getApplication()).getNetComponent().inject(this);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_location);
        ButterKnife.bind(this);
        setUpToolbar();
        initilizeMap();
    }

    /**
     * function to load map. If map is not created it will create it for you
     * */
    private void initilizeMap() {

            MapFragment mapFragment = (MapFragment) getFragmentManager()
                    .findFragmentById(R.id.map);
            mapFragment.getMapAsync(LocationActivity.this);

    }

    @Override
    protected void onResume() {
        super.onResume();
        initilizeMap();
    }

    @Override
    public void onMapReady(GoogleMap map) {

        googleMap = map;

        setUpMap();

    }

    public void setUpMap(){

        googleMap.setMapType(GoogleMap.MAP_TYPE_HYBRID);

        googleMap.setTrafficEnabled(true);
        googleMap.setIndoorEnabled(true);
        googleMap.setBuildingsEnabled(true);
        googleMap.getUiSettings().setZoomControlsEnabled(true);

        if(Utility.latitude!=0.0 && Utility.longitude!=0.0)
            drawMarker();
        else
            googleMap.setMyLocationEnabled(true);

    }

    private void drawMarker(){

        Marker marker = googleMap.addMarker(new MarkerOptions()
                .position(new LatLng(Utility.latitude, Utility.longitude))
                .title(Utility.address));

    }

    @Override
    protected void setUpLayout() {

    }

    @Override
    protected void setDataInViewObjects() {

    }

    @Override
    protected void setUpToolbar() {
        mtvTitle.setText(getResources().getString(R.string.location));
    }
}
