package com.example.projets4test;

public class ModelVoyagePrix {
    String id,nom_agence,destination,periode,description,prix,nombre_places;
    //Integer prix,nombre_places;
    //public ModelVoyagePrix(String id, String nom_agence, String prix, String destination, String periode, String nombre_places, String description){};

    public ModelVoyagePrix(String id, String nom_agence, String prix, String destination, String periode, String nombre_places, String description){
        this.id=id;
        this.nom_agence=nom_agence;
        this.prix=prix;
        this.destination=destination;
        this.periode=periode;
        this.nombre_places=nombre_places;
        this.description=description;
    }


    public String getId() {
        return id;
    }

    public String getNom_agence() {
        return nom_agence;
    }

    public String getDestination() {
        return destination;
    }

    public String getPeriode() {
        return periode;
    }

    public String getDescription() {
        return description;
    }

    public String getPrix() {
        return prix;
    }

    public String getNombre_places() {
        return nombre_places;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setNom_agence(String nom_agence) {
        this.nom_agence = nom_agence;
    }

    public void setDestination(String destination) {
        this.destination = destination;
    }

    public void setPeriode(String periode) {
        this.periode = periode;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setPrix(String prix) {
        this.prix = prix;
    }

    public void setNombre_places(String nombre_places) {
        this.nombre_places = nombre_places;
    }

}
