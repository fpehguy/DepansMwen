package com.example.depansmwen.Database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class MySqlLiteOpenHelper extends SQLiteOpenHelper {

    private String TableUtilisateur = "create table TableUtilisateur2(nom TEXT, prenom TEXT, username TEXT, password TEXT)";
   // private String TableCategorie = "create table TableCategorie(categorie TEXT, prix TEXT, devise TEXT, note TEXT, date LONG, user TEXT)";
    private String TableCategorie1 = "create table TableCategorie1(categorie TEXT, prix TEXT, devise TEXT, note TEXT, date TEXT, user TEXT)";

    public MySqlLiteOpenHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(TableCategorie1);
        db.execSQL(TableUtilisateur);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("drop table if exists TableCategorie1");
        db.execSQL("drop table if exists TableUtilisateur");
        onCreate(db);
    }
}
