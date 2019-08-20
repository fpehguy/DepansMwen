package com.example.depansmwen.Database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class MySqlLiteOpenHelper extends SQLiteOpenHelper {

    private String TableUtilisateur = "create table TableUtilisateur(nom TEXT, prenom TEXT, username TEXT, password TEXT)";

    public MySqlLiteOpenHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(TableUtilisateur);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("drop table if exists TableUtilisateur");
    }
}
