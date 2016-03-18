package fr.pkshetlie.carnetdepeche.bdd;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;

import fr.pkshetlie.carnetdepeche.objects.Peche;
import fr.pkshetlie.carnetdepeche.sqlite.BasePecheSQlite;

/**
 * Created by pierrick on 24/08/2014.
 */
public class PecheBDD {
    private static final int VERSION_BDD = 1;
    private static final String TABLE = "peches";
    private static final String NOM_BDD = TABLE + ".bdd";

    private static final String COL_ID = "ID";
    private static final int NUM_COL_ID = 0;
    private static final String COL_TITRE = "TITRE";
    private static final int NUM_COL_TITRE = 1;
    private static final String COL_DATE_DEBUT = "DATE_DEBUT";
    private static final int NUM_COL_DATE_DEBUT = 2;
    private static final String COL_DATE_FIN = "DATE_FIN";
    private static final int NUM_COL_DATE_FIN = 3;
    private static final String COL_TYPE_PECHE = "TYPE_PECHE";
    private static final int NUM_COL_TYPE_PECHE = 4;
    private static final String COL_TYPE_EAU = "TYPE_EAU";
    private static final int NUM_COL_TYPE_EAU = 5;
    private static final String COL_COMMENTAIRE = "COMMENTAIRE";
    private static final String[] ALL_COLS = new String[]{COL_ID, COL_TITRE, COL_DATE_DEBUT, COL_DATE_FIN, COL_TYPE_PECHE, COL_TYPE_EAU, COL_COMMENTAIRE};
    private static final int NUM_COL_COMMENTAIRE = 6;
    private SQLiteDatabase bdd;
    private BasePecheSQlite baseSQlite;
    private Context tContext;
    public PecheBDD(Context context) {
        tContext = context;
        baseSQlite = new BasePecheSQlite(context, NOM_BDD, null, VERSION_BDD);
    }

    public void open() {
        bdd = baseSQlite.getWritableDatabase();
    }

    public void close() {
        bdd.close();
    }

    public SQLiteDatabase getBdd() {
        return bdd;
    }

    public long insert(Peche peche) {

        ContentValues values = new ContentValues();
        values.put(COL_TITRE, peche.getTitre());
        values.put(COL_DATE_DEBUT, peche.getDateDebut());
        values.put(COL_DATE_FIN, peche.getDateFin());
        values.put(COL_TYPE_PECHE, peche.getTypePeche());
        values.put(COL_TYPE_EAU, peche.getTypeEau());
        values.put(COL_COMMENTAIRE, peche.getCommentaire());
        return bdd.insert(TABLE, null, values);
    }

    public long update(Peche peche) {
        ContentValues values = new ContentValues();
        values.put(COL_TITRE, peche.getTitre());
        values.put(COL_DATE_DEBUT, peche.getDateDebut());
        values.put(COL_DATE_FIN, peche.getDateFin());
        values.put(COL_TYPE_PECHE, peche.getTypePeche());
        values.put(COL_TYPE_EAU, peche.getTypeEau());
        values.put(COL_COMMENTAIRE, peche.getCommentaire());
        return bdd.update(TABLE, values, COL_ID + " = " + peche.getId(), null);
    }

    public int delete(int id) {
        this.open();
        PriseBDD prises = new PriseBDD(tContext);
        prises.deleteAllByPecheId(id);
        int res = bdd.delete(TABLE, COL_ID + "=" + id, null);
        this.close();
        return res;
    }

    public Peche getOneId(int id) {

        Cursor c = bdd.query(TABLE, new String[]{COL_ID, COL_TITRE, COL_DATE_DEBUT, COL_DATE_FIN, COL_TYPE_PECHE, COL_TYPE_EAU, COL_COMMENTAIRE}, COL_ID + " = " + id, null, null, null, null);
        if (c.getCount() == 0) {
            return null;
        }
        c.moveToFirst();
        return cursorToObject(c);
    }

    public ArrayList<Peche> getAll() {
        Cursor c = bdd.query(TABLE, ALL_COLS, null, null, null, null, COL_DATE_DEBUT + " DESC");
        if (c.getCount() == 0) {
            return null;
        }
        ArrayList<Peche> peches = new ArrayList<Peche>();
        if (c.moveToFirst()) {
            do {
                peches.add(cursorToObject(c));
            } while (c.moveToNext());
        }
        return peches;
    }

    private Peche cursorToObject(Cursor c) {
        Peche peche = new Peche();
        peche.setId(c.getInt(NUM_COL_ID));
        peche.setTitre(c.getString(NUM_COL_TITRE));
        peche.setDateDebut(c.getString(NUM_COL_DATE_DEBUT));
        peche.setDateFin(c.getString(NUM_COL_DATE_FIN));
        peche.setTypePeche(c.getString(NUM_COL_TYPE_PECHE));
        peche.setTypeEau(c.getString(NUM_COL_TYPE_EAU));
        peche.setCommentaire(c.getString(NUM_COL_COMMENTAIRE));
        return peche;
    }

}
