package com.example.gestion_indemnite.payement;

public class payementModel {
    public int getId_payement() {
        return id_payement;
    }

    public void setId_payement(int id_payement) {
        this.id_payement = id_payement;
    }

    public String getPayement() {
        return payement;
    }

    public void setPayement(String payement) {
        this.payement = payement;
    }

    public int getEndamnite() {
        return endamnite;
    }

    public void setEndamnite(int endamnite) {
        this.endamnite = endamnite;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    int id_payement;
    String payement;
    int endamnite;
    String nom,description,date;
}
