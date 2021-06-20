package com.example.projets4test;

public class ModelVoyageurs {
    String id,nom,cin,email,specialite,telephone;
    //Integer prix,nombre_places;
    //public ModelVoyagePrix(String id, String nom_agence, String prix, String destination, String periode, String nombre_places, String description){};

    public ModelVoyageurs(String id, String nom, String cin, String email, String specialite, String telephone){
        this.id=id;
        this.nom=nom;
        this.cin=cin;
        this.email=email;
        this.telephone=telephone;
        this.specialite=specialite;
    }

    /*
    public ModelVoyageurs(String id, String nom, String cin, String specialite, String telephone) {
        this.id=id;
        this.nom=nom;
        this.cin=cin;
        //this.email=email;
        this.telephone=telephone;
        this.specialite=specialite;

    }

     */

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

    public String getCin() {
        return cin;
    }

    public void setCin(String cin) {
        this.cin = cin;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getSpecialite() {
        return specialite;
    }

    public void setSpecialite(String specialite) {
        this.specialite = specialite;
    }

    public String getTelephone() {
        return telephone;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }






}
