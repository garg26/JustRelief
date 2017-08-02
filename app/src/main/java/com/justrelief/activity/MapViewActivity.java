package com.justrelief.activity;

import android.content.Intent;
import android.location.Address;
import android.location.Location;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.justrelief.R;

import simplifii.framework.activity.BaseActivity;
import simplifii.framework.utility.AppConstants;


public class MapViewActivity extends AppBaseActivity implements OnMapReadyCallback{
    private String latitude;
    private String longitude;
    private Intent intent = new Intent();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mapview);
        initToolBar("Pick Your Position");
        Bundle bundle = getIntent().getExtras();
        if (bundle!=null) {
            latitude = bundle.getString(AppConstants.BUNDLE_KEYS.LOCATION_LATITUDE);
            longitude = bundle.getString(AppConstants.BUNDLE_KEYS.LOCATION_LONGITUDE);
        }
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu, menu);
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.place:
                setResult(AppConstants.RESULT_CODE.LOCATION,intent);
                finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public void onMapReady(final GoogleMap googleMap) {
        double lat = Double.valueOf(latitude);
        double longi = Double.valueOf(longitude);
        CameraPosition cameraPosition = new CameraPosition.Builder().target(
                new LatLng(lat,longi)).zoom(17).build();
        googleMap.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));


        googleMap.setOnCameraIdleListener(new GoogleMap.OnCameraIdleListener() {
            @Override
            public void onCameraIdle() {
                LatLng target = googleMap.getCameraPosition().target;
                double latitude = target.latitude;
                double longitude = target.longitude;
                intent.putExtra(AppConstants.BUNDLE_KEYS.LATITUDE,String.valueOf(latitude));
                intent.putExtra(AppConstants.BUNDLE_KEYS.LONGITUDE,String.valueOf(longitude));
            }
        });


    }


}
