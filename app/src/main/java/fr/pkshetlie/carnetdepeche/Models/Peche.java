package fr.pkshetlie.carnetdepeche.Models;

/**
 * Application Carnet de Peche
 * Pour et par les membres de traditionspeche.fr
 * Created by pierrick on 26/02/14.
 */
public final class Peche {
    private int id;
    private String titre;
    private String dateDebut;
    private String dateFin;
    private String typePeche;
    private String typeEau;
    private String commentaire;
    // private JSONArray prises = new JSONArray();

    /*
    public JSONObject getJson() throws JSONException {
        final JSONObject json = new JSONObject();
        json.put("titre", titre);
        json.put("date", date);
        json.put("heure_debut", heureDebut);
        json.put("heure_fin", heureFin);
        json.put("type_peche", typePeche);
        json.put("type_water", typeWater);
        json.put("commentaire", commentaire);
        json.put("prises", prises);
        return json;
    }

    public Peche getFromJson(JSONObject json) throws JSONException {
        this.titre = json.getString("titre");
        this.date = json.getString("date");
        this.heureDebut = json.getString("heure_debut");
        this.heureFin = json.getString("heure_fin");
        this.typePeche = json.getString("type_peche");
        this.typeWater = json.getString("type_water");
        this.commentaire = json.getString("commentaire");
        this.prises = json.getJSONArray("prises");
        Log.v("carnetdepeche", this.toString());
        return this;
    }

    public void addPrise(Prise prise) throws JSONException {
        prises.put(prise.getJson());
    }

    public void setPrise(int id, Prise prise) throws JSONException {
        prises.put(id, prise.getJson());
    }

    public JSONArray getPrises() {
        return prises;
    }
    */

    @Override
    public String toString() {
        return "Peche{" +
                "id='" + id + '\'' +
                ", titre='" + titre + '\'' +
                ", date de debut='" + dateDebut + '\'' +
                ", date de fin='" + dateFin + '\'' +
                ", typePeche='" + typePeche + '\'' +
                ", typeWater='" + typeEau + '\'' +
                ", commentaire='" + commentaire + '\'' +
                '}';
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
    public String getTitre() {
        return titre;
    }

    public void setTitre(String titre) {
        this.titre = titre;
    }

    public String getDateDebut() {
        return dateDebut;
    }

    public void setDateDebut(String date) {
        this.dateDebut = date;
    }

    public String getDateFin() {
        return dateFin;
    }

    public void setDateFin(String date) {
        this.dateFin = date;
    }

    public String getTypePeche() {
        return typePeche;
    }

    public void setTypePeche(String typePeche) {
        this.typePeche = typePeche;
    }

    public String getTypeEau() {
        return typeEau;
    }

    public void setTypeEau(String eau) {
        this.typeEau = eau;
    }

    public String getCommentaire() {
        return commentaire;
    }

    public void setCommentaire(String commentaire) {
        this.commentaire = commentaire;
    }


}
