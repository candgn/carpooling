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

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class MeineAnzeigeListClass extends ArrayAdapter<String> {

    private final ArrayList<String> name,erklaerung,datum,ausgang,ziel,uid,auto,postid,image;
    private Activity context;
    MeineAnzeigen anzeigeClass;
    Button anwenden;
    String can;

    public MeineAnzeigeListClass(MeineAnzeigen anzeigeClass, ArrayList<String> erklaerung, ArrayList<String> datum,ArrayList<String> name, ArrayList<String> ausgang, ArrayList<String> ziel,ArrayList<String> image, ArrayList<String> uid,ArrayList<String>postid, ArrayList<String> auto,  Activity context){
        super( context,R.layout.post_view,erklaerung);
        this.erklaerung = erklaerung;
        this.datum = datum;
        this.image = image;
        this.name = name;
        this.ausgang = ausgang;
        this.ziel = ziel;
        this.uid = uid;
        this.postid = postid;
        this.auto = auto;
        this.anzeigeClass = anzeigeClass;
        this.context=context;
    }

    private View.OnClickListener mylistener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            View  pr = (View) v.getParent();
            ListView lw = (ListView) pr.getParent();
            final int position = lw.getPositionForView(pr);


            Intent intent = new Intent (anzeigeClass.getApplicationContext(), Anwenden.class);


            intent.putExtra("info",postid.get(position));
            intent.putExtra("uid",uid.get(position));


            context.startActivity(intent);
            System.out.println("---------------- " + position);

        }
    };

    private View.OnClickListener iwlistener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            View  pr = (View) v.getParent();
            ListView lw = (ListView) pr.getParent();
            final int position = lw.getPositionForView(pr);
            Intent intent = new Intent(anzeigeClass.getApplicationContext(),Profil.class);
            context.startActivity(intent);


        }
    };

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        LayoutInflater layoutInflater = context.getLayoutInflater();
        View postview = layoutInflater.inflate(R.layout.post_view, null, true);

        ImageView car = (ImageView) postview.findViewById(R.id.imageView4);
        ImageView profilPhoto = (ImageView) postview.findViewById(R.id.imageView2);
        TextView  Name = (TextView)postview.findViewById(R.id.Name);
        TextView  Datum = (TextView)postview.findViewById(R.id.Datum);
        TextView  Ausgang = (TextView)postview.findViewById(R.id.Ausgangspunkt);
        TextView  Ziel = (TextView)postview.findViewById(R.id.Ziel);

        String carControl;

        carControl = auto.get(position);

        if (carControl.equals("false")){
            car.setVisibility(View.INVISIBLE);
        }


        anwenden = (Button) postview.findViewById(R.id.Anwenden);
        anwenden.setId(position);
        can = image.get(position);

        Picasso.with(context).load(can).fit().centerCrop().into(profilPhoto);


        Name.setText(name.get(position));
        Ausgang.setText(ausgang.get(position));
        Ziel.setText(ziel.get(position));
        Datum.setText(datum.get(position));


        postview.setVerticalScrollbarPosition(anwenden.getId());
        int c = postview.getVerticalScrollbarPosition();


        profilPhoto.setOnClickListener(iwlistener);
        anwenden.setOnClickListener(mylistener);
        anwenden.setText("Detail ");







        return postview;
    }
}
