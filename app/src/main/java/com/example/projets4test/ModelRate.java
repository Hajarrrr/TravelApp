package com.example.projets4test;

public class ModelRate {
    String id,nom,rate;

    //Integer prix,nombre_places;
    public ModelRate(String id, String nom, String rate){
        this.id=id;
        this.nom=nom;
        this.rate=rate;
    }


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getRate() {
        return rate;
    }

    public void setRate(String rate) {
        this.rate = rate;
    }


}
