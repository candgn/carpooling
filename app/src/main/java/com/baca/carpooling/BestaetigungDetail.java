package com.baca.carpooling;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

public class BestaetigungDetail extends AppCompatActivity {


    String postid,uid,startlat,startlon,ziellat,ziellon,treffLat,treffLon;
    String treffLat1,treffLon2;
    DatabaseReference dbref;
    TextView ausgang,ziel,datum,name,erklaerung;
    ImageView image;




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
        setContentView(R.layout.activity_bestaetigung_detail);
        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);

        ausgang = (TextView) findViewById(R.id.textView4);
        ziel = (TextView) findViewById(R.id.textView5);
        datum = (TextView) findViewById(R.id.textView11);
        name = (TextView) findViewById(R.id.textView12);
        erklaerung = (TextView) findViewById(R.id.textView13);
        image =(ImageView) findViewById(R.id.imageView2);

        Intent intent = getIntent();
        postid = intent.getStringExtra("info");
        uid = intent.getStringExtra("uid");


        dbref = FirebaseDatabase.getInstance().getReference();







        dbref.child("Post").child(postid).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                String uid;



                PostClassD postClassD = dataSnapshot.getValue(PostClassD.class);
                startlat=postClassD.getStartLat();
                startlon=postClassD.getStartlong();
                ziellat=postClassD.getZielLat();
                ziellon=postClassD.getZielLong();
                treffLat=postClassD.getTreffLat();
                treffLon=postClassD.getTreffLong();
                ausgang.setText(postClassD.getAusgang());
                ziel.setText(postClassD.getZiel());
                datum.setText(postClassD.getDatum());
                erklaerung.setText(postClassD.getErklaerung());
                uid = postClassD.getUid();

                getProfilData(uid);





            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });




    }

    public void getProfilData(String uid){

        dbref.child("users").child(uid).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                String imagelink;

                UserPf userPf = dataSnapshot.getValue(UserPf.class);
                name.setText(userPf.getName()+userPf.getNachname());
                imagelink = userPf.getPhoto();


                Picasso.with(getApplicationContext()).load(imagelink).fit().centerCrop().into(image);


            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });


    }
    public void ablehnen (View view){

        dbref = FirebaseDatabase.getInstance().getReference();
        String statu = "0";

        VergangenPostClass vergangenPostClass = new VergangenPostClass(postid,uid,treffLat,treffLon, statu);

        dbref.child("VergangenPost").child(postid + uid).setValue(vergangenPostClass);
        dbref.child("Anwenden").child(postid+uid).removeValue();

        Intent intent = new Intent(this,Bestaetigung.class);
        startActivity(intent);


    }
    public void bestatigen (View view){



        //postid ve trefflat trefflon u database de vergangen diye bir child açıp kaydetmem lazım.

        dbref = FirebaseDatabase.getInstance().getReference();
        String statu = "1";

        VergangenPostClass vergangenPostClass = new VergangenPostClass(postid,uid,treffLat,treffLon, statu);
        dbref.child("VergangenPost").child(postid + uid).setValue(vergangenPostClass);

        dbref.child("Anwenden").child(postid+uid).removeValue();
        Intent intent = new Intent(this,Bestaetigung.class);
        startActivity(intent);





    }
    public void treffpunkt (View view){

        final Intent intent = new Intent(this,BestatigungMap.class);
        intent.putExtra("postid",postid);
        intent.putExtra("uid",uid);
        intent.putExtra("treffLat",treffLat);
        intent.putExtra("treffLon",treffLon);
        intent.putExtra("startlat",startlat);
        intent.putExtra("startlon",startlon);
        intent.putExtra("ziellat",ziellat);
        intent.putExtra("ziellon",ziellon);


        startActivity(intent);



    }

}
