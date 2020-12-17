package com.baca.carpooling;

import android.app.Activity;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class AnwendenPostListClass extends ArrayAdapter<String> {
    private final ArrayList<String> uid,postid;
    private Activity context;
    FragAktuell fragAktuell;
    Button anwenden;
    String can,ausgang,ziel,carControl;

    public AnwendenPostListClass(FragAktuell fragAktuell,  ArrayList<String> uid,ArrayList<String>postid,  Activity context){
        super( context,R.layout.anwenden_view,uid);


        this.uid = uid;
        this.postid = postid;
        this.fragAktuell = fragAktuell;
        this.context=context;
    }

    private View.OnClickListener mylistener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            View  pr = (View) v.getParent();
            ListView lw = (ListView) pr.getParent();
            final int position = lw.getPositionForView(pr);
            Intent intent = new Intent (fragAktuell.getActivity(), BestaetigungDetail.class);


            intent.putExtra("info",postid.get(position));
            intent.putExtra("uid",uid.get(position));


            context.startActivity(intent);



        }
    };

    private View.OnClickListener iwlistener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            View  pr = (View) v.getParent();
            ListView lw = (ListView) pr.getParent();
            final int position = lw.getPositionForView(pr);
            Intent intent = new Intent(fragAktuell.getActivity(),DetalierteProfil.class);
            intent.putExtra("uid",uid.get(position));
            context.startActivity(intent);



        }
    };

    @NonNull
    @Override
    public View getView(final int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        LayoutInflater layoutInflater = context.getLayoutInflater();
        View postview = layoutInflater.inflate(R.layout.anwenden_view, null, true);

        final ImageView profilPhoto = (ImageView) postview.findViewById(R.id.imageView2);
        final TextView  Ausgang = (TextView)postview.findViewById(R.id.Ausgangspunkt);
        final TextView  Ziel = (TextView)postview.findViewById(R.id.Ziel);
        final ImageView car = (ImageView) postview.findViewById(R.id.imageView5);

        DatabaseReference dbref;
        dbref = FirebaseDatabase.getInstance().getReference();
        System.out.println("anwendenlist_uid"+uid.get(position));

        dbref.child("users").child(uid.get(position)).child("photo").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                // This method is called once with the initial value and again
                // whenever data at this location is updated.
                String value = dataSnapshot.getValue(String.class);
                can = value;
                Picasso.with(context).load(can).fit().centerCrop().into(profilPhoto);

            }

            @Override
            public void onCancelled(DatabaseError error) {
                // Failed to read value

            }
        });

        dbref.child("Post").child(postid.get(position)).child("ausgang").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                // This method is called once with the initial value and again
                // whenever data at this location is updated.
                String value = dataSnapshot.getValue(String.class);
                ausgang = value;
                Ausgang.setText(ausgang);

            }

            @Override
            public void onCancelled(DatabaseError error) {
                // Failed to read value

            }
        });
        dbref.child("Post").child(postid.get(position)).child("ziel").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                // This method is called once with the initial value and again
                // whenever data at this location is updated.
                String value = dataSnapshot.getValue(String.class);
                ziel = value;
                Ziel.setText(ziel);


            }

            @Override
            public void onCancelled(DatabaseError error) {
                // Failed to read value

            }
        });

        dbref.child("Post").child(postid.get(position)).child("auto").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                // This method is called once with the initial value and again
                // whenever data at this location is updated.
                String value = dataSnapshot.getValue(String.class);
                carControl = value;


                if (carControl.equals("false")){
                    car.setVisibility(View.INVISIBLE);
                }



            }

            @Override
            public void onCancelled(DatabaseError error) {
                // Failed to read value

            }
        });

        System.out.println("anwendenlist_postid"+postid.get(position));




        anwenden = (Button) postview.findViewById(R.id.Anwenden);
        anwenden.setId(position);











        postview.setVerticalScrollbarPosition(anwenden.getId());
        int c = postview.getVerticalScrollbarPosition();


        profilPhoto.setOnClickListener(iwlistener);
        anwenden.setOnClickListener(mylistener);







        return postview;
    }

}
