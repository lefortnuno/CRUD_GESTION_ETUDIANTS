package com.example.gestion_indemnite.service;

public class serviceModel {
    public int getId_service() {
        return id_service;
    }

    public void setId_service(int id_service) {
        this.id_service = id_service;
    }

    public int getNb_jour() {
        return nb_jour;
    }

    public int getNb_Redoubl() {
        return redoublant;
    }

    public void setNb_jour(int nb_jour) {
        this.nb_jour = nb_jour;
    }

    public void setNb_Redoubl(int redoublant) {
        this.redoublant = redoublant;
    }

    public int getSalaire_heure() {
        return salaire_heure;
    }

    public void setSalaire_heure(int salaire_heure) {
        this.salaire_heure = salaire_heure;
    }

    public String getLibelle() {
        return libelle;
    }

    public void setLibelle(String libelle) {
        this.libelle = libelle;
    }

    int id_service, redoublant, nb_jour, salaire_heure;
    String libelle;
}
