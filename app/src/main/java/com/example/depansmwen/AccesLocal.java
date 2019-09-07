package com.example.depansmwen;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.depansmwen.Database.MySqlLiteOpenHelper;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class AccesLocal {
    private String nomBase = "bdDepansMwen.sqlite";
    private Integer versionBase = 3;
    private MySqlLiteOpenHelper accesBd;
    private SQLiteDatabase bd;
    String currentDateandTime;
    SimpleDateFormat sdf;
    static MainActivity user;

    public AccesLocal(Context context) {
        accesBd = new MySqlLiteOpenHelper(context, nomBase, null, versionBase);
        sdf = new SimpleDateFormat("yyyy.MM.dd");
        currentDateandTime = sdf.format(new Date());
    }

//    public void Signup(){
//         bd = accesBd.getWritableDatabase();
////         String req = " insert into TableUtilisateur(nom, prenom, username, password) " +
////                 "values("+inscription.getTxtInputNom()+
////                 ", "+inscription.getTxtInputPrenom()+
////                 ", "+inscription.getTxtInputUsername()+
////                 ", "+inscription.getTxtInputPassword()+") ";
////         bd.execSQL(req);
//    }

    public boolean signup(String nom, String prenom, String username, String password){
        bd = accesBd.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("nom", nom);
        contentValues.put("prenom", prenom);
        contentValues.put("username", username);
        contentValues.put("password", password);

        long ins = bd.insert("TableUtilisateur2", null, contentValues);
        if(ins ==- 1) return false;
        else return true;
    }

    public  boolean checkUsername(String username){
        bd = accesBd.getWritableDatabase();
        Cursor cursor = bd.rawQuery("Select * from TableUtilisateur2 where username=?", new String[]{username});
        if (cursor.getCount() > 0) return false;
        else return true;
    }

    public  boolean login(String username, String password){
        bd = accesBd.getWritableDatabase();
        Cursor cursor = bd.rawQuery("Select * from TableUtilisateur2 where username=? and password=?", new String[]{username, password});
        if (cursor.getCount() > 0) return true;
        else return false;
    }

    public boolean AddCategorie(String categorie, String prix, String devise, String note, String date, String user){
        bd = accesBd.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("categorie", categorie);
        contentValues.put("prix", prix);
        contentValues.put("devise", devise);
        contentValues.put("note", note);
        contentValues.put("date", date);
        contentValues.put("user", user);

        long ins = bd.insert("TableCategorie1", null, contentValues);
        if(ins ==- 1) return false;
        else return true;
    }

    public ArrayList<InformationToday> ListInformationFromBd(){
        bd = accesBd.getReadableDatabase();
        String sql = "select * from TableCategorie1 where date = '"+currentDateandTime+"' and user = '"+String.valueOf(user.userName())+"' ";
        ArrayList<InformationToday> list = new ArrayList<>();
        Cursor cursor = bd.rawQuery(sql, null);
        if(cursor.moveToFirst()){
            do {
                String categorieBase = cursor.getString(0);
                String montantBase = cursor.getString(1);
                String deviseBase = cursor.getString(2);
                String noteBase = cursor.getString(3);
                list.add(new InformationToday(categorieBase, montantBase, deviseBase, noteBase));
            }while (cursor.moveToNext());
        }
        cursor.close();
        return list;
    }
}
