package com.baca.carpooling;

public class UserPf {

    private String Name;
    private String Nachname;
    private String GSM;
    private String Mail;
    private String Password;
    private String Photo;

    public UserPf(){
        this.Name = Name;
        this.Nachname = Nachname;
        this.GSM = GSM;
        this.Mail = Mail;
        this.Password = Password;
        this.Photo = Photo;



    }

    public String getGSM() {
        return GSM;
    }

    public String getNachname() {
        return Nachname;
    }

    public String getName() {
        return Name;
    }

    public String getMail() {
        return Mail;
    }

    public String getPassword() {
        return Password;
    }

    public String getPhoto() {
        return Photo;
    }

    public void setName(String name) {
        Name = name;
    }

    public void setNachname(String nachname) {
        Nachname = nachname;
    }

    public void setGSM(String GSM) {
        this.GSM = GSM;
    }

    public void setMail(String mail) {
        Mail = mail;
    }

    public void setPassword(String password) {
        Password = password;
    }

    public void setPhoto(String photo) {
        Photo = photo;
    }


}
