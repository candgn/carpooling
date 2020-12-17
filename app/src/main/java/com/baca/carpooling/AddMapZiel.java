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
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class AddMapZiel extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    LatLng istanbul, startLocation;
    Double l1,l2,lat1,long1;
    String cl,clng,coordLat, coordLong, postid;
    DatabaseReference myRef;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_map_ziel);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        Intent intent = getIntent();
        postid =intent.getStringExtra("postid");
        cl = intent.getStringExtra("coordlat");
        clng = intent.getStringExtra("coordlong");

        lat1 = Double.parseDouble(cl);
        long1 = Double.parseDouble(clng);

        FirebaseDatabase database = FirebaseDatabase.getInstance();
        myRef = database.getReference();



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
        istanbul = new LatLng(lat1, long1);
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(istanbul, 9));

        mMap.setOnMapLongClickListener(new GoogleMap.OnMapLongClickListener() {
            @Override
            public void onMapLongClick(LatLng latLng) {
                startLocation = latLng;
                mMap.clear();
                mMap.addMarker(new MarkerOptions().position(startLocation));
                l1 = startLocation.latitude;
                l2 = startLocation.longitude;
                coordLat = l1.toString();
                coordLong = l2.toString();


            }
        });
    }

    public void addAnzeige (View view) {

        myRef.child("Post").child(postid).child("startLat").setValue(cl);
        myRef.child("Post").child(postid).child("startLong").setValue(clng);
        myRef.child("Post").child(postid).child("zielLat").setValue(coordLat);
        myRef.child("Post").child(postid).child("zielLong").setValue(coordLong);





        Intent intent = new Intent(getApplicationContext(), Fahrten.class);
        startActivity(intent);
    }
}

