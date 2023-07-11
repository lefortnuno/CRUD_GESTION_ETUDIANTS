package com.example.gestion_indemnite.indemnite;

public class indemniteModel {
    public int getId_endamnite() {
        return id_endamnite;
    }

    public void setId_endamnite(int id_endamnite) {
        this.id_endamnite = id_endamnite;
    }

    public int getId_perso() {
        return id_perso;
    }

    public void setId_perso(int id_perso) {
        this.id_perso = id_perso;
    }

    public int getHeure_travail() {
        return heure_travail;
    }

    public void setHeure_travail(int heure_travail) {
        this.heure_travail = heure_travail;
    }

    public int getSalaire_heure() {
        return salaire_heure;
    }

    public void setSalaire_heure(int salaire_heure) {
        this.salaire_heure = salaire_heure;
    }

    public int getMontant() {
        return montant;
    }

    public void setMontant(int montant) {
        this.montant = montant;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getService() {
        return service;
    }

    public void setService(String service) {
        this.service = service;
    }

    int id_endamnite,id_perso,heure_travail,salaire_heure,montant;
    String nom,service;
}
