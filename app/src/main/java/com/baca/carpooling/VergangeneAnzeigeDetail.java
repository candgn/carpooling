package com.baca.carpooling;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

public class VergangeneAnzeigeDetail extends AppCompatActivity {

    String postid,uid,treffLat,treffLon,statu;
    DatabaseReference dbref;
    TextView name,ausgang,datum,erklaerung,ziel;
    ImageView image;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vergangene_anzeige_detail);

        Intent intent=getIntent();
        postid = intent.getStringExtra("postid");
        uid = intent.getStringExtra("uid");
        treffLat = intent.getStringExtra("trefflat");
        treffLon = intent.getStringExtra("trefflon");
        statu = intent.getStringExtra("statu");

         name = (TextView)findViewById(R.id.textView12);
         ausgang = (TextView)findViewById(R.id.textView4);
         datum = (TextView)findViewById(R.id.textView11);
         erklaerung = (TextView)findViewById(R.id.textView13);
        ziel = (TextView)findViewById(R.id.textView5);
        image =(ImageView) findViewById(R.id.imageView2);

        dbref = FirebaseDatabase.getInstance().getReference();

        dbref.child("Post").child(postid).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                String uid;



                PostClassD postClassD = dataSnapshot.getValue(PostClassD.class);

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

    public void treffpunkt(View view ) {

    }
}
