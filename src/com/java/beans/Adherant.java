package com.java.beans;

public class Adherant {
    private long id;
    private String nom;
    private String prenom;
    private String adresse;
    private String email;

    public Adherant() {
    }

    public Adherant(long id, String nom, String prenom, String adresse, String email) {
        this.id = id;
        this.nom = nom;
        this.prenom = prenom;
        this.adresse = adresse;
        this.email = email;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getPrenom() {
        return prenom;
    }

    public void setPrenom(String prenom) {
        this.prenom = prenom;
    }

    public String getAdresse() {
        return adresse;
    }

    public void setAdresse(String adresse) {
        this.adresse = adresse;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @Override
    public String toString() {
        return "Adherant: " +
                "id=" + id +
                ", nom=" + nom +
                ", prenom=" + prenom +
                ", adresse=" + adresse +
                ", email=" + email;
    }
}


