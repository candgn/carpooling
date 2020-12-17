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

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;

public class FragVergangen extends Fragment {

    static ListView listview;
    static ArrayList<String>postid,treffLat,treffLon,statu,uid;
    static AnwendenPostlistClassVergangen adapter;
    DatabaseReference dtref;
    String profilidx;
    Button button;
    int control = 0;
    int i = 0;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view= inflater.inflate(R.layout.frag_vergangen, container, false);

        listview = (ListView)view.findViewById(R.id.listView);










        statu = new ArrayList<>();
        uid = new ArrayList<>();
        treffLon = new ArrayList<>();
        postid = new ArrayList<>();
        treffLat= new ArrayList<>();

        adapter = new AnwendenPostlistClassVergangen(this,postid,uid,treffLat,treffLon,statu, this.getActivity());
        getDataFromFirebaseMeinAnwenden();



        return view;
    }

    public void getDataFromFirebaseMeinAnwenden () {

        dtref = FirebaseDatabase.getInstance().getReference();
        dtref.child("VergangenPost").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                adapter.clear();

                for (DataSnapshot ds : dataSnapshot.getChildren()){
                    HashMap<String,String> hashMap = (HashMap<String, String>) ds.getValue();
                    postid.add(hashMap.get("postid"));
                    uid.add(hashMap.get("uid"));
                    treffLat.add(hashMap.get("trefflat"));
                    treffLon.add(hashMap.get("trefflon"));
                    statu.add(hashMap.get("statu"));
                    adapter.notifyDataSetChanged();

                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
        listview.setAdapter(adapter);

    }

}
