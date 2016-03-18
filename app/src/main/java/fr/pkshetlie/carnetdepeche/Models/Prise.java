package fr.pkshetlie.carnetdepeche.Models;

import android.net.Uri;

import java.net.URL;

/**
 * Application Carnet de Peche
 * Pour et par les membres de traditionspeche.fr
 * Created by pierrick on 07/03/14.
 */
public final class Prise {
    private int id = 0;
    private String type = "";
    private double taille = 0;
    private double poids = 0;
    private String photo = "";
    private String commentaire = "";
    private int idPeche = 0;

    public Prise() {
    }

    public Prise(String type, double taille, double poids, String commentaire, String mCurrentPhotoPath) {
        this.type = type;
        this.taille = taille;
        this.poids = poids;
        this.commentaire = commentaire;
        this.photo = mCurrentPhotoPath.equals("") ? "" : mCurrentPhotoPath;
    }


    @Override
    public String toString() {
        return "Prise{" +
                "type='" + type + '\'' +
                ", taille=" + taille +
                ", poids=" + poids +
                ", photo='" + photo + '\'' +
                ", commentaire='" + commentaire + '\'' +
                '}';
    }
    public int getId(){ return id; }

    public void setId(int id){ this.id = id;}

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public double getTaille() {
        return taille;
    }

    public void setTaille(double taille) {
        this.taille = taille;
    }

    public double getPoids() {
        return poids;
    }

    public void setPoids(double poids) {
        this.poids = poids;
    }

    public String getPhoto() {
        return (photo);
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }

    public String getCommentaire() {
        return commentaire;
    }

    public void setCommentaire(String commentaire) {
        this.commentaire = commentaire;
    }

    public int getIdPeche() {
        return idPeche;
    }
    public void setIdPeche(int id_peche) {
        idPeche = id_peche;
    }
}
