package com.baca.carpooling;

public class PostClass {


    private String erklaerung, datum, ausgang, ziel, uid,auto,startLat,startlong,zielLat,zielLong;




    public PostClass (String erklaerung, String datum, String ausgang, String ziel, String auto, String StartLat, String Startlong, String ZielLat, String ZielLong, String uid){
        this.erklaerung = erklaerung;
        this.datum = datum;
        this.ausgang = ausgang;
        this.ziel = ziel;
        this.auto = auto;
        this.startLat = StartLat;
        this.startlong = Startlong;
        this.zielLat = ZielLat;
        this.zielLong = ZielLong;
        this.uid = uid;

    }

    public String getStartLat() {
        return startLat;
    }

    public String getStartlong() {
        return startlong;
    }

    public String getZielLat() {
        return zielLat;
    }

    public String getZielLong() {
        return zielLong;
    }

    public String getErklaerung() {
        return erklaerung;
    }

    public String getDatum() {
        return datum;
    }

    public String getAusgang() {
        return ausgang;
    }

    public String getZiel() {
        return ziel;
    }

    public String getAuto() {
        return auto;
    }

    public String getUid() {
        return uid;
    }

    public void setErklaerung(String erklaerung) {
        this.erklaerung = erklaerung;
    }

    public void setDatum(String datum) {
        this.datum = datum;
    }

    public void setAusgang(String ausgang) {
        this.ausgang = ausgang;
    }

    public void setZiel(String ziel) {
        this.ziel = ziel;
    }

    public void setAuto(String auto) {
        this.auto = auto;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }
}
