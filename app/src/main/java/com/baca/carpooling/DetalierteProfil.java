package com.baca.carpooling;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
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

public class DetalierteProfil extends AppCompatActivity {

    private StorageReference mStorageRef;
    private String uid;
    DatabaseReference myRef;
    FirebaseDatabase database ;
    ImageView profilFoto;
    TextView userName, GSM, mail;

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
        setContentView(R.layout.activity_detalierte_profil);
        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);

        Intent intent =getIntent();
        uid = intent.getStringExtra("uid");


        mStorageRef = FirebaseStorage.getInstance().getReference();
        database = FirebaseDatabase.getInstance();
        myRef = database.getReference();


        profilFoto = (ImageView)findViewById(R.id.imageView2);
        userName = (TextView)findViewById(R.id.userName);
        GSM = (TextView)findViewById(R.id.gsm);
        mail = (TextView)findViewById(R.id.mail);

        //Databaseden verileri çekip profil sayfasında gösterme
        myRef.child("users").child(uid).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {


                UserPf user = dataSnapshot.getValue(UserPf.class);
                userName.setText(user.getName()+ " " + user.getNachname());
                GSM.setText(user.getGSM());
                mail.setText(user.getMail());




            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        // Database Storagedan resmi indirme
        mStorageRef.child("images").child(uid).getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
            @Override
            public void onSuccess(Uri uri) {
                // Got the download URL for 'users/me/profile.png'
                Picasso.with(DetalierteProfil.this).load(uri).fit().centerCrop().into(profilFoto);


            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception exception) {
                // Handle any errors
            }
        });



    }

}
