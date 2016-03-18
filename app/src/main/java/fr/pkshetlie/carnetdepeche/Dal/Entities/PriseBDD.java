package fr.pkshetlie.carnetdepeche.Dal.Entities;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;

import fr.pkshetlie.carnetdepeche.Models.Prise;
import fr.pkshetlie.carnetdepeche.objects.Prise;
import fr.pkshetlie.carnetdepeche.sqlite.BasePriseSQlite;


/**
 * Created by pierrick on 26/08/2014.
 */
public class PriseBDD {
    private static final int VERSION_BDD = 2;
    private static final String TABLE = "prises";
    private static final String NOM_BDD = TABLE + ".bdd";


    private static final String COL_ID = "ID";
    private static final int NUM_COL_ID = 0;
    private static final String COL_TYPE = "TYPE";
    private static final int NUM_COL_TYPE = 1;
    private static final String COL_TAILLE = "TAILLE";
    private static final int NUM_COL_TAILLE = 2;
    private static final String COL_POIDS = "POIDS";
    private static final int NUM_COL_POIDS = 3;
    private static final String COL_PHOTO = "PHOTO";
    private static final int NUM_COL_PHOTO = 4;
    private static final String COL_COMMENTAIRE = "COMMENTAIRE";
    private static final int NUM_COL_COMMENTAIRE = 5;
    private static final String COL_ID_PECHE = "ID_PECHE";
    private static final int NUM_COL_ID_PECHE = 6;
    private static final String[] ALL_COLS = new String[]{COL_ID, COL_TYPE, COL_TAILLE,COL_POIDS, COL_PHOTO, COL_COMMENTAIRE,COL_ID_PECHE};
    private SQLiteDatabase bdd;
    private BasePriseSQlite baseSQlite;

    public PriseBDD(Context context) {
        baseSQlite = new BasePriseSQlite(context, NOM_BDD, null, VERSION_BDD);
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

    public long insert(Prise prise) {
        ContentValues values = new ContentValues();
        values.put(COL_TYPE, prise.getType());
        values.put(COL_TAILLE, prise.getTaille());
        values.put(COL_POIDS, prise.getPoids());
        values.put(COL_PHOTO, prise.getPhoto());
        values.put(COL_COMMENTAIRE, prise.getCommentaire());
        values.put(COL_ID_PECHE, prise.getIdPeche());
        return bdd.insert(TABLE, null, values);
    }

    public long update(Prise prise) {
        ContentValues values = new ContentValues();
        values.put(COL_TYPE, prise.getType());
        values.put(COL_TAILLE, prise.getTaille());
        values.put(COL_POIDS, prise.getPoids());
        values.put(COL_PHOTO, prise.getPhoto());
        values.put(COL_COMMENTAIRE, prise.getCommentaire());
        values.put(COL_ID_PECHE, prise.getIdPeche());

        return bdd.update(TABLE, values, COL_ID + " = " + prise.getId(), null);
    }

    public int delete(int id) {
        this.open();
        int res = bdd.delete(TABLE, COL_ID + "=" + id, null);
        this.close();
        return res;
    }
    public int deleteAllByPecheId(int id) {
        this.open();
        Cursor c = bdd.query(TABLE, ALL_COLS, COL_ID_PECHE +" = "+id , null, null, null, COL_ID + " DESC");
        if (c.getCount() == 0) {
            return 0;
        }

        if (c.moveToFirst()) {
            do {
                this.delete(cursorToObject(c).getId());
            } while (c.moveToNext());
        }
        this.close();
        return 1;
    }

    public Prise getOneId(int id) {
        Cursor c = bdd.query(TABLE, ALL_COLS, COL_ID + " = " + id, null, null, null, null);
        if (c.getCount() == 0) {
            return null;
        }
        c.moveToFirst();
        return cursorToObject(c);
    }
    public ArrayList<Prise> loadFromPecheId(int idPeche) {
        Cursor c = bdd.query(TABLE, ALL_COLS, COL_ID_PECHE +" = "+idPeche , null, null, null, COL_ID + " DESC");
        if (c.getCount() == 0) {
            return null;
        }
        ArrayList<Prise> prises = new ArrayList<Prise>();
        if (c.moveToFirst()) {
            do {
                prises.add(cursorToObject(c));
            } while (c.moveToNext());
        }
        return prises;
    }
    public ArrayList<Prise> getAll() {
        Cursor c = bdd.query(TABLE, ALL_COLS, null, null, null, null, COL_ID + " DESC");
        if (c.getCount() == 0) {
            return null;
        }
        ArrayList<Prise> prises = new ArrayList<Prise>();
        if (c.moveToFirst()) {
            do {
                prises.add(cursorToObject(c));
            } while (c.moveToNext());
        }
        return prises;
    }

    private Prise cursorToObject(Cursor c) {
        Prise prise = new Prise();
        prise.setId(c.getInt(NUM_COL_ID));
        prise.setType(c.getString(NUM_COL_TYPE));
        prise.setTaille(c.getDouble(NUM_COL_TAILLE));
        prise.setPoids(c.getDouble(NUM_COL_POIDS));
        prise.setPhoto(c.getString(NUM_COL_PHOTO));
        prise.setCommentaire(c.getString(NUM_COL_COMMENTAIRE));
        prise.setIdPeche(c.getInt(NUM_COL_ID_PECHE));
        return prise;
    }
}
