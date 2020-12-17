package com.baca.carpooling;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ListView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;

public class FragAktuell extends Fragment {

    static ListView listview;
    static ArrayList<String> Name, Datum, Ausgangpunkt, Ziel, Erklaerung,pfuid,postid,Auto,image;
    static AnwendenPostListClass adapter;
    DatabaseReference dbref;
    String profilidx;
    Button button;
    int control = 0;
    int i = 0;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view= inflater.inflate(R.layout.frag_aktuell, container, false);

        listview = (ListView)view.findViewById(R.id.listView);
        button = (Button) view.findViewById(R.id.button8);









        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(control==0){
                    control = 1;
                    button.setText("Mein Fahrten");
                    getDataFromFirebaseMeinFahrten();
                }
                else if(control == 1){
                    control = 0 ;
                    button.setText("Mein Anwenden");
                    getDataFromFirebaseMeinAnwenden();

                }
            }
        });

        Name = new ArrayList<>();
        Datum = new ArrayList<>();
        Ausgangpunkt = new ArrayList<>();
        Ziel = new ArrayList<>();
        Erklaerung = new ArrayList<>();
        pfuid = new ArrayList<>();
        postid = new ArrayList<>();
        image= new ArrayList<>();

        adapter = new AnwendenPostListClass(this,pfuid,postid, this.getActivity());
        getDataFromFirebaseMeinAnwenden();



        return view;
    }

    public void getDataFromFirebaseMeinAnwenden () {

        FirebaseAuth mAuth;
        mAuth = FirebaseAuth.getInstance();
        final String buid;//başvuran id
        buid = mAuth.getCurrentUser().getUid();


            dbref = FirebaseDatabase.getInstance().getReference().child("Anwenden");
            dbref.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                    postid.clear();
                    pfuid.clear();

                    for(DataSnapshot ds : dataSnapshot.getChildren()) {


                        HashMap<String, String> hashMap = (HashMap<String, String>) ds.getValue();



                        System.out.println("anwendenid"+hashMap.get("anwendenuid"));


                        if (hashMap.get("anwendenuid") != null) {
                            if (hashMap.get("anwendenuid").equals(buid)) {


                                postid.add(hashMap.get("postid"));
                                pfuid.add(hashMap.get("postuid"));
                                System.out.println("postidfragaktuel" + hashMap.get("postid"));
                                adapter.notifyDataSetChanged();


                            }
                        }
                            System.out.println("ifin dışı");


                        }


                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            });

            listview.setAdapter(adapter);




    }

    public void  getDataFromFirebaseMeinFahrten(){

        FirebaseAuth mAuth;
        mAuth = FirebaseAuth.getInstance();
        final String buid;//başvuran id
        buid = mAuth.getCurrentUser().getUid();


            dbref = FirebaseDatabase.getInstance().getReference().child("Anwenden");
            dbref.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    postid.clear();
                    pfuid.clear();

                    for(DataSnapshot ds : dataSnapshot.getChildren()) {


                        HashMap<String, String> hashMap = (HashMap<String, String>) ds.getValue();





                        System.out.println("xxxxxxxx"+hashMap.get("postuid")+"yyyyyyyy"+buid);

                        if (hashMap.get("postuid") != null) {
                        if (hashMap.get("postuid").equals(buid)) {


                            postid.add(hashMap.get("postid"));
                            pfuid.add(hashMap.get("anwendenuid"));
                            System.out.println("postidfragaktuel"+hashMap.get("postid"));
                            adapter.notifyDataSetChanged();


                        }
                        }
                        System.out.println("ifin dışı");


                    }


                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            });

            listview.setAdapter(adapter);
        }


}
