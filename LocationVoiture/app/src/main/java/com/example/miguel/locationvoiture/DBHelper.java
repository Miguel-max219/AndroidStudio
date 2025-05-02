package com.example.miguel.locationvoiture;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DBHelper extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "voitures.db";
    private static final int DATABASE_VERSION = 1;

    public DBHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_TABLE = "CREATE TABLE voitures (" +
                "id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "nom TEXT, " +
                "marque TEXT, " +
                "numero TEXT)";
        db.execSQL(CREATE_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS voitures");
        onCreate(db);
    }

    // Ajoute une voiture dans la base de données
    public boolean ajouterVoiture(String nom, String marque, String numero) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put("nom", nom);
        cv.put("marque", marque);
        cv.put("numero", numero);
        long result = db.insert("voitures", null, cv);
        return result != -1;
    }

    // Retourne un Cursor sur toutes les voitures
    public Cursor listerVoitures() {
        SQLiteDatabase db = this.getReadableDatabase();
        return db.rawQuery("SELECT * FROM voitures", null);
    }

    // Supprime une voiture en fonction de son ID
    public boolean supprimerVoiture(int id) {
        SQLiteDatabase db = this.getWritableDatabase();
        int result = db.delete("voitures", "id=?", new String[]{String.valueOf(id)});
        return result > 0;
    }

    // Modifie une voiture existante
    public boolean modifierVoiture(int id, String nom, String marque, String numero) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put("nom", nom);
        cv.put("marque", marque);
        cv.put("numero", numero);
        int result = db.update("voitures", cv, "id=?", new String[]{String.valueOf(id)});
        return result > 0;
    }
}
