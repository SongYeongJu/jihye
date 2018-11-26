package com.example.administrator.jihye.Activity;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Build;
import android.os.IBinder;
import android.provider.Settings;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.view.View;
import android.widget.Button;

import com.example.administrator.jihye.R;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback,LocationListener {

    private GoogleMap mMap;
    private Context mContext;
    private SupportMapFragment mapFragment;

    boolean isGPSEnabled = false;    // 현재 GPS 사용유무
    boolean isNetworkEnabled = false;    // 네트워크 사용유무
    boolean isGetLocation = false;    // GPS 상태값

    Location location;
    double lat; // 위도
    double lon; // 경도

    private static final long MIN_DISTANCE_CHANGE_FOR_UPDATES = 10;    // 최소 GPS 정보 업데이트 거리 10미터
    private static final long MIN_TIME_BW_UPDATES = 1000 * 60 * 1;    // 최소 GPS 정보 업데이트 시간 밀리세컨이므로 1분

    protected LocationManager locationManager;

    private Marker curmarker;

    @TargetApi(23)
    public Location getLocation() {
        if ( Build.VERSION.SDK_INT >= 23 && ContextCompat.checkSelfPermission( mContext, android.Manifest.permission.ACCESS_FINE_LOCATION )
                        != PackageManager.PERMISSION_GRANTED && ContextCompat.checkSelfPermission( mContext, android.Manifest.permission.ACCESS_COARSE_LOCATION)!= PackageManager.PERMISSION_GRANTED) {
            return null;
        }
        try {
            locationManager = (LocationManager) mContext
                    .getSystemService(LOCATION_SERVICE);

            isGPSEnabled = locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER);    // GPS 정보 가져오기
            isNetworkEnabled = locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER);            // 현재 네트워크 상태 값 알아오기

            if (!isGPSEnabled && !isNetworkEnabled) {                 // GPS 와 네트워크사용이 가능하지 않을때 소스 구현
            } else {
                this.isGetLocation = true;   // 네트워크 정보로 부터 위치값 가져오기
                if (isNetworkEnabled) {
                    locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, MIN_TIME_BW_UPDATES, MIN_DISTANCE_CHANGE_FOR_UPDATES, this);
                    if (locationManager != null) {
                        location = locationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
                        if (location != null) { // 위도 경도 저장
                            lat = location.getLatitude();
                            lon = location.getLongitude();
                        }
                    }
                }
                if (isGPSEnabled) {
                    if (location == null) {
                        locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, MIN_TIME_BW_UPDATES, MIN_DISTANCE_CHANGE_FOR_UPDATES, this);
                        if (locationManager != null) {
                            location = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
                            if (location != null) {
                                lat = location.getLatitude();
                                lon = location.getLongitude();
                            }
                        }
                    }
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return location;
    }

    //  GPS 종료
    public void stopUsingGPS(){
        if(locationManager != null){
            locationManager.removeUpdates(MapsActivity.this);
        }
    }

//위도값을 가져옵니다.
    public double getLatitude(){
        if(location != null){
            lat = location.getLatitude();
        }
        return lat;
    }

 //    * 경도값을 가져옵니다.
    public double getLongitude(){
        if(location != null){
            lon = location.getLongitude();
        }
        return lon;
    }

 //    * GPS 나 wife 정보가 켜져있는지 확인합니다.
    public boolean isGetLocation() {
        return this.isGetLocation;
    }

    public void onLocationChanged(Location location) {
        // TODO Auto-generated method stub

    }

    public void onStatusChanged(String provider, int status, Bundle extras) {
        // TODO Auto-generated method stub

    }

    public void onProviderEnabled(String provider) {
        // TODO Auto-generated method stub
    }

    public void onProviderDisabled(String provider) {
        // TODO Auto-generated method stub
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
        Button confirm=(Button)findViewById(R.id.MapConfirmButton);
        confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=getIntent();
                LatLng latLng=curmarker.getPosition();
                intent.putExtra("location",latLng);
                setResult(1234,intent);
                finish();
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
        mContext = getApplicationContext();
        getLocation();
        mapFragment.getMapAsync(this);

    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        mMap.setOnMapClickListener(new GoogleMap.OnMapClickListener() {
            @Override
            public void onMapClick(LatLng latLng) {
                curmarker.setPosition(latLng); // 지도 클릭한곳으로 마커 이동
            }
        });

        LatLng curr = new LatLng(lat, lon);        // 현재 위치 설정
        MarkerOptions makerOptions = new MarkerOptions();        // 구글 맵에 표시할 마커에 대한 옵션 설정
        makerOptions.position(curr);
        curmarker=mMap.addMarker(makerOptions);        // 마커를 생성한다.
        mMap.moveCamera(CameraUpdateFactory.newLatLng(curr));        //카메라를 위치로 옮긴다.
        mMap.setMinZoomPreference(12);
    }
}
