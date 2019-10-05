package com.example.depansmwen;

public class Prepare_Rapport {
    String Categorie;
    String Prix;
    String Compte;
    String Note;
    String Date;
    String Prix_total;

    public String getPrix_total() {
        return Prix_total;
    }

    public void setPrix_total(String prix_total) {
        Prix_total = prix_total;
    }


    public String getNote() {
        return Note;
    }

    public void setNote(String note) {
        Note = note;
    }



    public String getCategorie() {
        return Categorie;
    }

    public void setCategorie(String categorie) {
        Categorie = categorie;
    }

    public String getPrix() {
        return Prix;
    }

    @Override
    public String toString() {
        return "Prepare_Rapport{" +
                "Categorie='" + Categorie + '\'' +
                ", Prix='" + Prix + '\'' +
                ", Compte='" + Compte + '\'' +
                ", Date='" + Date + '\'' +
                '}';
    }

    public void setPrix(String prix) {
        Prix = prix;
    }

    public String getCompte() {
        return Compte;
    }

    public void setCompte(String compte) {
        Compte = compte;
    }

    public String getDate() {
        return Date;
    }

    public void setDate(String date) {
        Date = date;
    }


}
