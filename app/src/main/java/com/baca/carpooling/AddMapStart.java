package com.baca.carpooling;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.View;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

public class AddMapStart extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    LatLng istanbul,startLocation;
    Double l1,l2;
    String coordLat, coordLong, postid;


    //Coordinat of Istanbul
    Double li1=41.015137;
    Double li2=28.979530;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_map_start);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);


        Intent intent = getIntent();
        postid =intent.getStringExtra("postid");






    }


    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        // Zoom Istanbul
        istanbul = new LatLng(li1,li2);
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(istanbul,9));

        mMap.setOnMapLongClickListener(new GoogleMap.OnMapLongClickListener() {
            @Override
            public void onMapLongClick(LatLng latLng) {
                startLocation=latLng;
                mMap.clear();
                mMap.addMarker(new MarkerOptions().position(startLocation));
                l1 = startLocation.latitude;
                l2 = startLocation.longitude;
                coordLat = l1.toString();
                coordLong = l2.toString();



            }
        });
    }

    public void bestimmeZiel (View view){

        Intent intent = new Intent(getApplicationContext(),AddMapZiel.class);
        intent.putExtra("coordlat",coordLat);
        intent.putExtra("coordlong",coordLong);
        intent.putExtra("postid",postid);
        startActivity(intent);
    }





}
