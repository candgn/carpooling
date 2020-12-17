package com.baca.carpooling;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.squareup.picasso.Picasso;

public class Anwenden extends AppCompatActivity {

    private TextView mTextMessage;
    private String postid, uid, photo,startlat,startlon,ziellat,ziellon;
    TextView name,ausgang, ziel, datum, erklaerung;
    ImageView auto,profilPhoto;
    DatabaseReference myRef;
    private StorageReference mStorageRef;

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
        setContentView(R.layout.activity_anwenden);


        Intent intent = getIntent();
        postid = intent.getStringExtra("info");
        uid = intent.getStringExtra("uid");


        FirebaseDatabase database = FirebaseDatabase.getInstance();
        myRef = database.getReference();

        name = (TextView) findViewById(R.id.textView12);
        ausgang = (TextView) findViewById(R.id.textView4);
        ziel = (TextView) findViewById(R.id.textView5);
        datum = (TextView) findViewById(R.id.textView11);
        erklaerung = (TextView) findViewById(R.id.textView13);
        auto = (ImageView) findViewById(R.id.imageView);
        profilPhoto = (ImageView) findViewById(R.id.imageView2);

            //Databaseden Post Verilerini çekme
        myRef.child("Post").child(postid).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                PostClassD post = dataSnapshot.getValue(PostClassD.class);
                ausgang.setText(post.getAusgang());
                ziel.setText(post.getZiel());
                datum.setText(post.getDatum());
                erklaerung.setText(post.getErklaerung());
                startlat = post.getStartLat();
                startlon = post.getStartlong();
                ziellat = post.getZielLat();
                ziellon = post.getZielLong();


            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });



        //Databaseden Profil verilerini çekme(İsim)
        myRef.child("users").child(uid).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                UserPf user = dataSnapshot.getValue(UserPf.class);
                name.setText(user.getName()+ " " + user.getNachname());
                photo = user.getPhoto();

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });



        // Database Storagedan resmi indirme
        mStorageRef = FirebaseStorage.getInstance().getReference();
        mStorageRef.child("images").child(uid).getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
            @Override
            public void onSuccess(Uri uri) {
                // Got the download URL for 'users/me/profile.png'
                Picasso.with(Anwenden.this).load(uri).fit().centerCrop().into(profilPhoto);


            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception exception) {
                // Handle any errors
            }
        });







        mTextMessage = (TextView) findViewById(R.id.message);
        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);



    }


    public void anwenden(View view){

        Intent intent = new Intent(getApplicationContext(),MapAnwenden.class);
        intent.putExtra("postid",postid);
        intent.putExtra("startlat",startlat);
        intent.putExtra("startlon",startlon);
        intent.putExtra("ziellat",ziellat);
        intent.putExtra("ziellon",ziellon);
        intent.putExtra("uid",uid);
        startActivity(intent);


    }

}
