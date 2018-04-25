package com.example.student.p688;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.view.View;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
    }

    public void clickBtn(View v){
        if(v.getId()==R.id.button1){
            requestMyLocation(1);
        }
        else if(v.getId()==R.id.button2){
            //35.1570773,129.0579114 부산
            requestMyLocation(2);
        }
        else if(v.getId()==R.id.button3){
            //35.1653428,126.9070116 광주
            requestMyLocation(3);
        }
    }

    private void requestMyLocation(final int placeNum) {
        LocationManager manager =
                (LocationManager) getSystemService(Context.LOCATION_SERVICE);

        try {
            long minTime = 10000;
            float minDistance = 0;
            manager.requestLocationUpdates(
                    LocationManager.GPS_PROVIDER,
                    minTime,
                    minDistance,
                    new LocationListener() {
                        @Override
                        public void onLocationChanged(Location location) {
                            if(placeNum==1){
                                showCurrentLocation(location, "current");
                            }
                            else if(placeNum==2){
                                showCurrentLocation(location, "busan");
                            }
                            else if(placeNum==3){
                                showCurrentLocation(location, "gwangju");
                            }

                        }

                        @Override
                        public void onStatusChanged(String provider, int status, Bundle extras) {

                        }

                        @Override
                        public void onProviderEnabled(String provider) {

                        }

                        @Override
                        public void onProviderDisabled(String provider) {

                        }
                    }
            );

            Location lastLocation = manager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
            if (lastLocation != null) {
                if(placeNum==1){
                    showCurrentLocation(lastLocation, "current");
                }
                else if(placeNum==2){
                    showCurrentLocation(lastLocation, "busan");
                }
                else if(placeNum==3){
                    showCurrentLocation(lastLocation, "gwangju");
                }
            }

            manager.requestLocationUpdates(
                    LocationManager.NETWORK_PROVIDER,
                    minTime,
                    minDistance,
                    new LocationListener() {
                        @Override
                        public void onLocationChanged(Location location) {
                            if(placeNum==1){
                                showCurrentLocation(location, "current");
                            }
                            else if(placeNum==2){
                                showCurrentLocation(location, "busan");
                            }
                            else if(placeNum==3){
                                showCurrentLocation(location, "gwangju");
                            }
                        }

                        @Override
                        public void onStatusChanged(String provider, int status, Bundle extras) {

                        }

                        @Override
                        public void onProviderEnabled(String provider) {

                        }

                        @Override
                        public void onProviderDisabled(String provider) {

                        }
                    }
            );


        } catch (SecurityException e) {
            e.printStackTrace();
        }
    }

    private void showCurrentLocation(Location location, String status) {
        LatLng curPoint= null;
        LatLng m=null;
        if(status.equals("current")){
            curPoint = new LatLng(location.getLatitude(), location.getLongitude());
            mMap.addMarker(new MarkerOptions().position(curPoint).title("현재"));
        }
        else if(status.equals("busan")){
            //35.1570773,129.0579114 부산
            curPoint = new LatLng(35.1570773, 129.0579114);
            //m = new LatLng(35.1570773, 129.0579114);
            mMap.addMarker(new MarkerOptions().position(curPoint).title("부산"));
        }

        else if(status.equals("gwangju")){
            //35.1653428,126.9070116 광주
            curPoint = new LatLng(35.1653428, 126.9070116);
            //m = new LatLng(35.1766798, 126.7737603);
            mMap.addMarker(new MarkerOptions().position(curPoint).title("광주"));
        }

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {

            return;
        }
        mMap.setMyLocationEnabled(true);
        mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(curPoint, 10));
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        // Add a marker in Sydney and move the camera
        LatLng sydney = new LatLng(-34, 151);
        mMap.addMarker(new MarkerOptions().position(sydney).title("Marker in Sydney"));
        mMap.moveCamera(CameraUpdateFactory.newLatLng(sydney));
    }
}
