package com.projeto.patyalves.projeto;

import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.util.Log;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

public class PlaceDetailMapActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    private Double latitude, longitude;
    private String name;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_place_detail_map);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
       // this.getActionBar().setDisplayHomeAsUpEnabled(true);
       // this.getSupportActionBar().setDisplayHomeAsUpEnabled(true);


        Bundle bundle=getIntent().getExtras();
        latitude = bundle.getDouble("latitude",0);
        longitude = bundle.getDouble("longitude",0);
        name=bundle.getString("nome","");

        Log.i("mark", "latitude "+ latitude);
        Log.i("mark", "longitude "+ longitude);

    }


    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        LatLng mark = new LatLng(latitude, longitude);
        mMap.addMarker(new MarkerOptions().position(mark).title(name));
        mMap.moveCamera(CameraUpdateFactory.newLatLng(mark));
        mMap.animateCamera(CameraUpdateFactory.zoomTo(15));
    }
}
