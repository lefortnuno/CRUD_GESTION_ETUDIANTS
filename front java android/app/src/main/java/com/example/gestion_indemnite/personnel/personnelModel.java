package com.example.gestion_indemnite.personnel;

public class personnelModel {
    int id_perso;

    public int getId_service() {
        return id_service;
    }

    public void setId_service(int id_service) {
        this.id_service = id_service;
    }

    int id_service;

    public int getId_perso() {
        return id_perso;
    }

    public void setId_perso(int id_perso) {
        this.id_perso = id_perso;
    }


    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getAdresse() {
        return adresse;
    }

    public void setAdresse(String adresse) {
        this.adresse = adresse;
    }

    String nom;
    String adresse;

    public String getService() {
        return service;
    }

    public void setService(String service) {
        this.service = service;
    }

    String service;

}
