package com.example.depansmwen.Database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class MySqlLiteOpenHelper extends SQLiteOpenHelper {

    private static final String TableUtilisateur9 = "create table TableUtilisateur9(nom TEXT, prenom TEXT, username TEXT, password TEXT)";
    private String TableCategorie9 = "create table TableCategorie9(categorie TEXT, prix TEXT, devise TEXT, note TEXT, date TEXT, user TEXT,ID INTEGER PRIMARY KEY AUTOINCREMENT)";
    private String TableEnregistreCategorie9= "create table TableEnregistreCategorie9(categorie TEXT, user TEXT)";
    private String TableEnregistreCompte9 ="create table TableEnregistreCompte9(user TEXT,bank TEXT,noducompte TEXT,typedecompte TEXT,etat TEXT,solde TEXT)";

    String tab1="TableUtilisateur9";
    String tab2="TableCategorie9";
    String tab3="TableEnregistreCategorie9";
    String tab4="TableEnregistreCompte9";

    public MySqlLiteOpenHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }


    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(TableCategorie9);
        db.execSQL(TableEnregistreCategorie9);
        db.execSQL(TableUtilisateur9);
        db.execSQL(TableEnregistreCompte9);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("drop table if exists '"+tab1+"'");
        db.execSQL("drop table if exists '"+tab2+"'");
        db.execSQL("drop table if exists '"+tab3+"'");
        db.execSQL("drop table if exists '"+tab4+"'");
        onCreate(db);
    }
}
