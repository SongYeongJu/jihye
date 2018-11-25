package com.example.administrator.jihye.Activity;

import android.content.Intent;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;

import com.example.administrator.jihye.DataStructure.Item;
import com.example.administrator.jihye.R;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

public class MapsActivity2 extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    private SupportMapFragment mapFragment;
    private Item item;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps2);
        mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
    }

    @Override
    protected void onStart() {
        super.onStart();
        try {
            Intent intent=getIntent();
            item=(Item)intent.getExtras().get("info");
        }catch (Exception e) {}
        mapFragment.getMapAsync(this);
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        LatLng cur = new LatLng(Double.parseDouble(item.getLat()),Double.parseDouble(item.getLon()));
        mMap.addMarker(new MarkerOptions().position(cur).title(item.getItemName()));
        mMap.moveCamera(CameraUpdateFactory.newLatLng(cur));
        mMap.setMinZoomPreference(12);
/*
        CameraUpdate zoom = CameraUpdateFactory.zoomTo(15);
        googleMap.animateCamera(zoom);
*/

    }
}
