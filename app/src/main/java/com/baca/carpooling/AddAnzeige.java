package com.baca.carpooling;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.Switch;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.UUID;

public class AddAnzeige extends AppCompatActivity {


    Switch switchButton;
    Boolean autoAnzeige;
    EditText erklaerung, datum, ausgang, ziel;
    private FirebaseAuth mAuth;
    FirebaseUser user;
    FirebaseDatabase database;
    DatabaseReference dbref;
    String uid;




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
        setContentView(R.layout.activity_add_anzeige);
        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);

        switchButton = (Switch) findViewById(R.id.switch1);
        erklaerung = (EditText)findViewById(R.id.editErklaerung);
        datum = (EditText)findViewById(R.id.editDatum);
        ausgang = (EditText)findViewById(R.id.editAusgang);
        ziel = (EditText)findViewById(R.id.editZiel);


        //Uid von Current User
        mAuth = FirebaseAuth.getInstance();
        user = mAuth.getCurrentUser();
        uid = user.getUid();

        //Databaseref
        dbref = database.getInstance().getReference();





    }
    public void switch_buton (View view ) {

        if(switchButton.isChecked()){
            autoAnzeige = true;
        }
        else if(switchButton.isChecked()== false){
            autoAnzeige = false;
        }


    }


    public void weiter(View view){

        UUID postid =UUID.randomUUID();
        writeNewPost(dbref,postid.toString());
        Intent intent = new Intent(this,AddMapStart.class);
        intent.putExtra("postid",postid.toString());
        startActivity(intent);


    }

    public void writeNewPost(DatabaseReference databaseReference, String id){


        //
        if (autoAnzeige == null){
            autoAnzeige = false;
        }

        PostClass postClass = new PostClass(erklaerung.getText().toString(),datum.getText().toString(),ausgang.getText().toString(),ziel.getText().toString(),autoAnzeige.toString(),"","","","",uid);
        databaseReference.child("Post").child(id).setValue(postClass);
    }

}
