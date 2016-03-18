package fr.pkshetlie.carnetdepeche.Dal.Tables;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by pierrick on 24/08/2014.
 */
public class BasePecheSQlite extends SQLiteOpenHelper {
    private static final String TABLE = "peches";
    private static final String COL_ID = "ID";
    private static final String COL_TITRE = "TITRE";
    private static final String COL_DATE_DEBUT = "DATE_DEBUT";
    private static final String COL_DATE_FIN = "DATE_FIN";
    private static final String COL_TYPE_PECHE = "TYPE_PECHE";
    private static final String COL_TYPE_EAU = "TYPE_EAU";
    private static final String COL_COMMENTAIRE = "COMMENTAIRE";

    private static final String CREATE_BDD = "CREATE TABLE " + TABLE + " (" +
            COL_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
            COL_TITRE + " TEXT, " +
            COL_DATE_DEBUT + " TEXT, " +
            COL_DATE_FIN + " TEXT, " +
            COL_TYPE_PECHE + " TEXT, " +
            COL_TYPE_EAU + " TEXT, " +
            COL_COMMENTAIRE + " TEXT);";

    public BasePecheSQlite(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
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
