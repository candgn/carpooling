package com.baca.carpooling;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.View;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class BestatigungMap extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    String postid,startlat,startlon,ziellat,ziellon,coordLat,coordLong,puid,treffLat,treffLon;
    String anwendenid;
    LatLng startlocation,ziellocation,treffLocation;
    LatLng startLocation;//Yeni buluşma lokasyonu
    Double lx,ly;
    private FirebaseAuth mAuth;
    DatabaseReference dbref;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map_anwenden);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);


        dbref = FirebaseDatabase.getInstance().getReference();

        Intent intent=getIntent();
        postid = intent.getStringExtra("postid");
        puid = intent.getStringExtra("uid");
        treffLat = intent.getStringExtra("treffLat");
        treffLon = intent.getStringExtra("treffLon");
        startlat = intent.getStringExtra("startlat");
        startlon = intent.getStringExtra("startlon");
        ziellat = intent.getStringExtra("ziellat");
        ziellon = intent.getStringExtra("ziellon");










        //Databaseden Post Verilerini çekme


        Double l1 = Double.parseDouble(startlat);
        Double l2 = Double.parseDouble(startlon);
        startlocation =new LatLng(l1,l2);

        Double l3 = Double.parseDouble(ziellat);
        Double l4 = Double.parseDouble(ziellon);
        ziellocation = new LatLng(l3,l4);

        Double l5 = Double.parseDouble(treffLat);
        Double l6 = Double.parseDouble(treffLon);
        treffLocation = new LatLng(l5,l6);




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
        final Marker letzteTreffPunktMarker;

        // Add a marker in Sydney and move the camera

        mMap.addMarker(new MarkerOptions().position(startlocation).title("Ausgangspunkt").icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_ORANGE )));
        mMap.addMarker(new MarkerOptions().position(ziellocation).title("Ziel").icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_BLUE )));
        letzteTreffPunktMarker= mMap.addMarker(new MarkerOptions().position(treffLocation).title("TreffPunkt").icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_GREEN )));
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(startlocation,9));


        mMap.setOnMapLongClickListener(new GoogleMap.OnMapLongClickListener() {
            @Override
            public void onMapLongClick(LatLng latLng) {
                startLocation=latLng;

                letzteTreffPunktMarker.remove();;
                mMap.addMarker(new MarkerOptions().position(startLocation).icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_YELLOW )));
                lx = startLocation.latitude;
                ly = startLocation.longitude;
                coordLat = lx.toString();
                coordLong = ly.toString();



            }
        });


    }


    public void anwenden (View view){
        FirebaseAuth mAuth;
        mAuth = FirebaseAuth.getInstance();
        String buid;//başvuran id
        buid = mAuth.getCurrentUser().getUid();

        dbref.child("Anwenden").child(postid + buid).child("postid").setValue(postid);
        dbref.child("Anwenden").child(postid + buid).child("treffLat").setValue(coordLat);
        dbref.child("Anwenden").child(postid + buid).child("treffLong").setValue(coordLong);
        dbref.child("Anwenden").child(postid + buid).child("anwendenuid").setValue(buid);
        dbref.child("Anwenden").child(postid + buid).child("postuid").setValue(puid);
        dbref.child("Post").child(postid).child("treffLat").setValue(coordLat);
        dbref.child("Post").child(postid).child("treffLong").setValue(coordLong);

        Intent intent = new Intent(this,Fahrten.class);
        startActivity(intent);


    }
}
