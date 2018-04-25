package com.example.student.map;

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
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    WebView webView;
    LinearLayout ll_map;
    ImageView imageView;
    LatLng curPoint= new LatLng(37.5511542, 126.9613611);


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        webView=findViewById(R.id.webview);
        webView.setWebViewClient(new WebViewClient());
        webView.getSettings().setJavaScriptEnabled(true);
        ll_map=findViewById(R.id.ll_map);
        imageView=findViewById(R.id.imageview);
        //초기화면 설정
        ll_map.setVisibility(View.INVISIBLE);
        imageView.setVisibility(View.VISIBLE);
        webView.setVisibility(View.INVISIBLE);
    }
    public void clickMainBtn(View v){
        if(v.getId()==R.id.btn_map){
            ll_map.setVisibility(View.VISIBLE);
            imageView.setVisibility(View.INVISIBLE);
            webView.setVisibility(View.INVISIBLE);
        }
        else if(v.getId()==R.id.btn_image){
            ll_map.setVisibility(View.INVISIBLE);
            imageView.setVisibility(View.VISIBLE);
            webView.setVisibility(View.INVISIBLE);
            imageView.setImageResource(R.drawable.yunaaa);
        }
        else if(v.getId()==R.id.btn_chart){
            webView.loadUrl("http://www.naver.com");
            ll_map.setVisibility(View.INVISIBLE);
            imageView.setVisibility(View.INVISIBLE);
            webView.setVisibility(View.VISIBLE);
        }
    }
    public void clickMapBtn(View v){
        if(v.getId()==R.id.btn_bread){
            //requestMyLocation(1);
            mMap.clear();
            curPoint = new LatLng(37.5511542, 126.9613611);
            requestMyLocation();
        }
        else if(v.getId()==R.id.btn_drink){
            //requestMyLocation(2);
            mMap.clear();
            curPoint = new LatLng(37.5581542, 126.9664611);
            requestMyLocation();
        }
        else if(v.getId()==R.id.btn_coffee){
            //requestMyLocation(3);
            mMap.clear();
            curPoint = new LatLng(37.5521542, 126.9751611);
            requestMyLocation();

        }
    }
    private void requestMyLocation() {
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
                            showCurrentLocation(location);

                            /*if(placeNum==1){
                                showCurrentLocation(location, "bread");
                            }
                            else if(placeNum==2){
                                showCurrentLocation(location, "drink");
                            }
                            else if(placeNum==3){
                                showCurrentLocation(location, "coffee");
                            }*/

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
            /*if (lastLocation != null) {
                if(placeNum==1){
                    showCurrentLocation(lastLocation, "bread");
                }
                else if(placeNum==2){
                    showCurrentLocation(lastLocation, "drink");
                }
                else if(placeNum==3){
                    showCurrentLocation(lastLocation, "coffee");
                }
            }*/
            showCurrentLocation(lastLocation);

            manager.requestLocationUpdates(
                    LocationManager.NETWORK_PROVIDER,
                    minTime,
                    minDistance,
                    new LocationListener() {
                        @Override
                        public void onLocationChanged(Location location) {
                            /*if(placeNum==1){
                                showCurrentLocation(location, "bread");
                            }
                            else if(placeNum==2){
                                showCurrentLocation(location, "drink");
                            }
                            else if(placeNum==3){
                                showCurrentLocation(location, "coffee");
                            }*/
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

    private void showCurrentLocation(Location location) {
        /*LatLng curPoint= null;
        String str="";
        if(status.equals("bread")){
            curPoint = new LatLng(37.5511542, 126.9613611);
            str="빵집";
        }
        else if(status.equals("drink")){
            curPoint = new LatLng(37.5581542, 126.9664611);
            str="술집";
        }
        else if(status.equals("coffee")){
            curPoint = new LatLng(37.5521542, 126.9751611);
            str="카페";
        }*/
        //mMap.addMarker(new MarkerOptions().position(curPoint).title(str));
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            return;
        }
        mMap.setMyLocationEnabled(true);
        mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(curPoint, 15));
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        LatLng seoul = new LatLng(37.5541542, 126.9691611);
        //mMap.addMarker(new MarkerOptions().position(seoul).title("SEOUL"));
        mMap.moveCamera(CameraUpdateFactory.newLatLng(seoul));
        requestMyLocation();
    }
}
