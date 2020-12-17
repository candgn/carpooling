package com.baca.carpooling;

public class PostClassD {
    private String erklaerung, datum, ausgang, ziel, uid,auto,startLat,startLong,zielLat,zielLong,treffLat,treffLong;




    public PostClassD (){
        this.erklaerung = erklaerung;
        this.datum = datum;
        this.ausgang = ausgang;
        this.ziel = ziel;
        this.auto = auto;
        this.startLat = startLat;
        this.startLong = startLong;
        this.zielLat = zielLat;
        this.zielLong = zielLong;
        this.treffLat = treffLat;
        this.treffLong = treffLong;
        this.uid = uid;

    }

    public String getTreffLat() {
        return treffLat;
    }

    public void setTreffLat(String treffLat) {
        this.treffLat = treffLat;
    }

    public String getTreffLong() {
        return treffLong;
    }

    public void setTreffLong(String treffLong) {
        this.treffLong = treffLong;
    }

    public String getStartLat() {
        return startLat;
    }

    public String getStartlong() {
        return startLong;
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


