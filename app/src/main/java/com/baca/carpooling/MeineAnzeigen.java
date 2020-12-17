package com.baca.carpooling;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.widget.ListView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.StorageReference;

import java.util.ArrayList;
import java.util.HashMap;

public class MeineAnzeigen extends AppCompatActivity {

    static ArrayList<String> Name, Datum, Ausgangpunkt, Ziel, Erklaerung,pfuid,postid,Auto,image;
    static ListView listview;
    static MeineAnzeigeListClass adapter;
    private StorageReference mStorageRef;
    private FirebaseAuth mAuth;
    DatabaseReference dbref;
    String profilidx;
    int i=0;


    //Menü
    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.navigation_fahrten:
                    Intent intent = new Intent(getApplicationContext(),Fahrten.class);
                    startActivity(intent);
                    return true;
                case R.id.navigation_bestaetigung:
                    Intent intent2 = new Intent(getApplicationContext(),Bestaetigung.class);
                    startActivity(intent2);
                    return true;
                case R.id.navigation_profil:
                    Intent intent3 = new Intent(getApplicationContext(),Profil.class);
                    startActivity(intent3);
                    return true;
            }
            return false;
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_meine_anzeigen);
        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);


        listview = (ListView)findViewById(R.id.listView);

        Name = new ArrayList<>();
        Datum = new ArrayList<>();
        Ausgangpunkt = new ArrayList<>();
        Ziel = new ArrayList<>();
        Erklaerung = new ArrayList<>();
        pfuid = new ArrayList<>();
        postid = new ArrayList<>();
        image= new ArrayList<>();
        Auto= new ArrayList<>();


        adapter = new MeineAnzeigeListClass(this,Erklaerung,Datum,Name,Ausgangpunkt,Ziel,image,pfuid,postid,Auto, this);

        getDataFromFirebase();




    }

    public void getDataFromFirebase () {

        FirebaseAuth mAuth;
        mAuth = FirebaseAuth.getInstance();
        final String buid;//başvuran id
        buid = mAuth.getCurrentUser().getUid();

        dbref = FirebaseDatabase.getInstance().getReference().child("Post");
        dbref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                adapter.clear();

                for(DataSnapshot ds : dataSnapshot.getChildren()){

                    HashMap<String,String> hashMap = (HashMap<String, String>) ds.getValue();

                    if(buid.equals(hashMap.get("uid"))){
                        Erklaerung.add(hashMap.get("erklaerung"));
                        Datum.add(hashMap.get("datum"));
                        Ausgangpunkt.add(hashMap.get("ausgang"));
                        Ziel.add(hashMap.get("ziel"));
                        pfuid.add(hashMap.get("uid"));
                        Auto.add(hashMap.get("auto"));
                        postid.add(ds.getKey());
                        adapter.notifyDataSetChanged();
                        i++;

                        System.out.println("aaaaaaaaaaaaaaaaaaaa");
                    }


                }
                for (int a=0;a<i;a++){

                    System.out.println("dddddddddddddddddddddddd" + a);
                    profilidx = pfuid.get(a);
                    getProfilDataFromFirebase(profilidx);

                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });


    }

    public void getProfilDataFromFirebase(final String profilid){

        dbref = FirebaseDatabase.getInstance().getReference().child("users");
        dbref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                for(DataSnapshot ds : dataSnapshot.getChildren()){
                    if (ds.getKey().equals(profilid)){

                        HashMap<String,String> hashMap = (HashMap<String, String>) ds.getValue();
                        image.add(hashMap.get("photo"));
                        Name.add(hashMap.get("name") );


                        System.out.println("ccccccccccccccccccc");

                    }
                    adapter.notifyDataSetChanged();
                    listview.setAdapter(adapter);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });



    }

}
