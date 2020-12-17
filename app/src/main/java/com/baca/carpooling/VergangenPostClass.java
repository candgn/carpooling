package com.baca.carpooling;

public class VergangenPostClass {

    private String postid,trefflat,trefflon,statu,uid;

    public VergangenPostClass(String postid,String uid,String trefflat,String trefflon,String statu){

        this.postid=postid;
        this.uid=uid;
        this.trefflat=trefflat;
        this.trefflon=trefflon;
        this.statu=statu;
    }

    public String getStatu() {
        return statu;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public void setStatu(String statu) {
        this.statu = statu;
    }

    public String getPostid() {
        return postid;
    }

    public void setPostid(String postid) {
        this.postid = postid;
    }

    public String getTrefflat() {
        return trefflat;
    }

    public void setTrefflat(String trefflat) {
        this.trefflat = trefflat;
    }

    public String getTrefflon() {
        return trefflon;
    }

    public void setTrefflon(String trefflon) {
        this.trefflon = trefflon;
    }
}
