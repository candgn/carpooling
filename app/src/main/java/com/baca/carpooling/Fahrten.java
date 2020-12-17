package com.baca.carpooling;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;

public class Fahrten extends AppCompatActivity {





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
        setContentView(R.layout.activity_fahrten);


        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
    }

    public void suchanzeige (View view){

        Intent intent = new Intent(this,Anzeige.class);
        startActivity(intent);

    }

    public void addanzeige (View view){

        Intent intent = new Intent(this,AddAnzeige.class);
        startActivity(intent);

    }

}
