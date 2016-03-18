package fr.pkshetlie.carnetdepeche.sqlite;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by pierrick on 26/08/2014.
 */
public class BasePriseSQlite extends SQLiteOpenHelper {
    private static final String TABLE = "prises";
    private static final String COL_ID = "ID";
    private static final String COL_TYPE = "TYPE";
    private static final String COL_TAILLE = "TAILLE";
    private static final String COL_POIDS = "POIDS";
    private static final String COL_PHOTO = "PHOTO";
    private static final String COL_COMMENTAIRE = "COMMENTAIRE";
    private static final String COL_ID_PECHE = "ID_PECHE";

    private static final String CREATE_BDD = "CREATE TABLE " + TABLE + " (" +
            COL_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
            COL_TYPE + " TEXT, " +
            COL_TAILLE + " DOUBLE, " +
            COL_POIDS + " DOUBLE, " +
            COL_PHOTO + " TEXT, " +
            COL_COMMENTAIRE + " TEXT, " +
            COL_ID_PECHE + " INTEGER);";

    public BasePriseSQlite(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_BDD);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE " + TABLE + ";");
        onCreate(db);
    }
}
