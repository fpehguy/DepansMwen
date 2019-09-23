package com.example.depansmwen;

import android.app.Activity;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;


import com.example.depansmwen.Database.MySqlLiteOpenHelper;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class AccesLocal extends Activity {
    private String nomBase = "bdDepansMwen.sqlite";
    private Integer versionBase = 10;
    private MySqlLiteOpenHelper accesBd;
    private SQLiteDatabase bd;
    String currentDateandTime;
    SimpleDateFormat sdf;
    static MainActivity user;
    Prepare_Rapport prepare_rapport ;
    ArrayList<Prepare_Rapport> Mylist;

    public AccesLocal(Context context) {
        accesBd = new MySqlLiteOpenHelper(context, nomBase, null, versionBase);
        sdf = new SimpleDateFormat("yyyy.MM.dd");
        currentDateandTime = sdf.format(new Date());
    }


    public boolean signup(String nom, String prenom, String username, String password){
        bd = accesBd.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("nom", nom);
        contentValues.put("prenom", prenom);
        contentValues.put("username", username);
        contentValues.put("password", password);

        long ins = bd.insert("TableUtilisateur9", null, contentValues);
        if(ins ==- 1) return false;
        else return true;
    }

    public  boolean checkUsername(String username){
        bd = accesBd.getWritableDatabase();
        Cursor cursor = bd.rawQuery("Select * from TableUtilisateur9 where username=?", new String[]{username});
        if (cursor.getCount() > 0) return false;
        else return true;
    }

    public  boolean checkTransact(){
        bd = accesBd.getWritableDatabase();  //currentDateandTime
        Cursor cursor = bd.rawQuery("Select * from TableCategorie9 where user=? and date=?", new String[]{user.userName().toString(),currentDateandTime.toString()});
        if (cursor.getCount() > 0) return false;
        else return true;
    }

    public  boolean checkCatAvantSppre(){
        bd = accesBd.getWritableDatabase();
        Cursor cursor = bd.rawQuery("Select * from TableEnregistreCategorie9 where user=?", new String[]{user.userName().toString()});
        if (cursor.getCount() > 0) return false;
        else return true;
    }

    public  boolean login(String username, String password){
        bd = accesBd.getWritableDatabase();
        Cursor cursor = bd.rawQuery("Select * from TableUtilisateur9 where username=? and password=?", new String[]{username, password});
        if (cursor.getCount() > 0) return true;
        else return false;
    }

    public boolean AddCategorie(String categorie, String prix, String devise, String note, String date, String user){
        bd = accesBd.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("categorie", categorie);
        contentValues.put("prix", prix);
        contentValues.put("devise", devise);// se li ki ranplase compte
        contentValues.put("note", note);
        contentValues.put("date", date);
        contentValues.put("user", user);

        long ins = bd.insert("TableCategorie9", null, contentValues);
        if(ins ==- 1) return false;
        else return true;
    }

    public boolean EnregistreCategorie(String categorie,  String user){
        bd = accesBd.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("categorie", categorie);
        contentValues.put("user", user);

        long ins = bd.insert("TableEnregistreCategorie9", null, contentValues);
        if(ins ==- 1) return false;
        else return true;
    }


    public boolean updateEnregistreCategorie(String oldCategorie,String categorie,  String user){
        bd = accesBd.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put("categorie", categorie);
        String CAT="categorie";
        String USER="user";
        long ins = bd.update("TableEnregistreCategorie9", values, ""+CAT+"= '"+ oldCategorie+"' AND "+USER+"='"+user+"'" , null);
        if(ins ==- 1) return false;
        else return true;
    }

    public boolean EnregistreCompte(String user,String bank,String noducompte,String typedecompte,String etat,String solde){
        bd = accesBd.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("user",user);
        contentValues.put("bank",bank);
        contentValues.put("noducompte",noducompte);
        contentValues.put("typedecompte",typedecompte);
        contentValues.put("etat",etat);
        contentValues.put("solde",solde);

        long ins = bd.insert("TableEnregistreCompte9", null, contentValues);
        if(ins ==- 1) return false;
        else return true;
    }

    public boolean UpdateEtat(String etat,String user,String nombank){
        bd = accesBd.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("etat",etat);
        long upt=bd.update("TableEnregistreCompte9",contentValues,"user=? and bank=?",new String[]{user,nombank});
        if(upt==-1)return false;
        else
        return true;
    }

    public boolean UpdateSolde(Double solde,String nombank){
        bd = accesBd.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("solde",solde);
        long upt=bd.update("TableEnregistreCompte9",contentValues,"user=? and bank=?",new String[]{user.userName().toString(),nombank});
        if(upt==-1)return false;
        else
            return true;
    }


    public boolean updateSolde(String etat,String user,String nombank){
        bd = accesBd.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("etat",etat);
        long upt=bd.update("TableEnregistreCompte9",contentValues,"user=? and bank=?",new String[]{user,nombank});
        if(upt==-1)return false;
        else
            return true;
    }



    public boolean deleteEnregistreCategorie(String categorie,  String user){
        bd = accesBd.getWritableDatabase();
        Cursor cursor = bd.rawQuery("Delete from TableEnregistreCategorie9 where categorie=? and user=?", new String[]{categorie, user});
        if (cursor.getCount() < 0) return false;
        else return true;
    }

    public ArrayList<InformationToday> ListInformationFromBd(){
        bd = accesBd.getReadableDatabase();
        String sql = "select * from TableCategorie9 where date = '"+currentDateandTime+"' and user = '"+String.valueOf(user.userName())+"' ";
        ArrayList<InformationToday> list = new ArrayList<>();
        Cursor cursor = bd.rawQuery(sql, null);
        if(cursor.moveToLast()){
            do {
                String categorieBase = cursor.getString(0);
                String montantBase = cursor.getString(1);
                String deviseBase = cursor.getString(2);
                String noteBase = cursor.getString(3);
                int id = cursor.getInt(6);
                list.add(new InformationToday(categorieBase, montantBase, deviseBase, noteBase,id));
            }while (cursor.moveToPrevious());
        }
        cursor.close();
        return list;
    }

    public ArrayList<InformationToday> ListInformationSemaineFromBd(){
        bd = accesBd.getReadableDatabase();
        String sql = "select * from TableCategorie9";
        ArrayList<InformationToday> list = new ArrayList<>();
        Cursor cursor = bd.rawQuery(sql, null);
        if(cursor.moveToLast()){
            do {
                String categorieBase = cursor.getString(0);
                String montantBase = cursor.getString(1);
                String deviseBase = cursor.getString(2);
                String noteBase = cursor.getString(3);
                int id = cursor.getInt(6);
                list.add(new InformationToday(categorieBase, montantBase, deviseBase, noteBase,id));
            }while (cursor.moveToPrevious());
        }
        cursor.close();
        return list;
    }



    public List<String> getAllSpinners(){
        List<String> list = new ArrayList<String>();
        list.add("Liste des categories");

        // Select All Query
        String selectQuery = "SELECT  * FROM TableEnregistreCategorie9 where user='"+user.userName().toString()+"' ";

        bd = accesBd.getReadableDatabase();
        Cursor cursor = bd.rawQuery(selectQuery, null);//selectQuery,selectedArguments

        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                list.add(cursor.getString(0));//adding first column data
            } while (cursor.moveToNext());
        }
        // closing connection
        cursor.close();
        bd.close();
        // returning lables
        return list;
    }

    public List<String> getAllSpinners1(){
        List<String> list = new ArrayList<String>();
        list.add("Transport");
        list.add("Sante");
        list.add("Nourriture");
        list.add("Divers");
        list.add("Loisirs");

        // Select All Query
        String selectQuery = "SELECT  * FROM TableEnregistreCategorie9 where user='"+user.userName().toString()+"' ";

        bd = accesBd.getReadableDatabase();
        Cursor cursor = bd.rawQuery(selectQuery, null);//selectQuery,selectedArguments

        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                list.add(cursor.getString(0));//adding first column data
            } while (cursor.moveToNext());
        }
        // closing connection
        cursor.close();
        bd.close();
        // returning lables
        return list;
    }
     //pour retourner la listes de compte
    public List<String> getAllSpinnerscompte(){
        List<String> list = new ArrayList<String>();
        list.add("Liste des Comptes");

        // Select All Query
        String selectQuery = "SELECT  * FROM TableEnregistreCompte9 where user='"+String.valueOf(user.userName())+"'";

        bd = accesBd.getReadableDatabase();
        Cursor cursor = bd.rawQuery(selectQuery, null);//selectQuery,selectedArguments

        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                list.add(cursor.getString(1));//adding first column data
            } while (cursor.moveToNext());
        }
        // closing connection
        cursor.close();
        bd.close();
        // returning lables
        return list;
    }

    public List<String> getAllSpinnerscompte2(){
        List<String> list = new ArrayList<String>();
        // Select All Query
        String selectQuery = "SELECT  * FROM TableEnregistreCompte9 where user='"+String.valueOf(user.userName())+"'";

        bd = accesBd.getReadableDatabase();
        Cursor cursor = bd.rawQuery(selectQuery, null);//selectQuery,selectedArguments

        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                list.add(cursor.getString(1));//adding first column data
            } while (cursor.moveToNext());
        }
        // closing connection
        cursor.close();
        bd.close();
        // returning lables
        return list;
    }

    public List<String> getAllSpinnerscompte1(){
        List<String> list = new ArrayList<String>();
        // Select All Query
        String selectQuery = "SELECT  * FROM TableEnregistreCompte9 where user='"+String.valueOf(user.userName())+"' and etat='"+"1"+"' and solde > 0.0 ";

        bd = accesBd.getReadableDatabase();
        Cursor cursor = bd.rawQuery(selectQuery, null);//selectQuery,selectedArguments

        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                list.add(cursor.getString(1));//adding first column data
            } while (cursor.moveToNext());
        }
        // closing connection
        cursor.close();
        bd.close();
        // returning lables
        return list;
    }




    public String getSoldecompte(String nonBank,String user){
        String solde=null;
        // Select All Query
        String selectQuery = "SELECT  * FROM TableEnregistreCompte9 where bank = '"+nonBank+"' and user ='"+user+"' ";
        bd = accesBd.getReadableDatabase();
        Cursor cursor = bd.rawQuery(selectQuery, null);//selectQuery,selectedArguments

        // looping through all rows and adding to list
        if (cursor.moveToNext()) { solde = cursor.getString(5); }
        // closing connection
        cursor.close();
        bd.close();
        // returning lables
        return solde;
    }


    public  boolean checkcategorie(String categorie){
        bd = accesBd.getWritableDatabase();
        Cursor cursor = bd.rawQuery("Select * from TableEnregistreCategorie9 where categorie=? and user=?", new String[]{categorie,user.userName().toString()});
        if (cursor.getCount() > 0) return false;
        else return true;
    }


    public String getNomBank(String nonBank){
        String nom="";
        // Select All Query
        String selectQuery = "SELECT  * FROM TableEnregistreCompte9 where bank = '"+nonBank+"' and user ='"+user.userName().toString()+"' ";
        bd = accesBd.getReadableDatabase();
        Cursor cursor = bd.rawQuery(selectQuery, null);//selectQuery,selectedArguments
        // looping through all rows and adding to list
        if (cursor.moveToNext()) { nom = cursor.getString(1); }
        // closing connection
        cursor.close();
        bd.close();
        // returning lables
        return nom;
    }

    public String getnocompte(String nonBank,String user){
        String nokont=null;
        // Select All Query
        String selectQuery = "SELECT  * FROM TableEnregistreCompte9 where bank = '"+nonBank+"' and user ='"+user+"' ";
        bd = accesBd.getReadableDatabase();
        Cursor cursor = bd.rawQuery(selectQuery, null);//selectQuery,selectedArguments

        // looping through all rows and adding to list
        if (cursor.moveToNext()) { nokont = cursor.getString(2); }
        // closing connection
        cursor.close();
        bd.close();
        // returning lables
        return nokont;
    }

    public String gettypecompte(String nonBank,String user){
        String typecompte=null;
        // Select All Query
        String selectQuery = "SELECT  * FROM TableEnregistreCompte9 where bank = '"+nonBank+"'and user ='"+user+"'";
        bd = accesBd.getReadableDatabase();
        Cursor cursor = bd.rawQuery(selectQuery, null);//selectQuery,selectedArguments

        // looping through all rows and adding to list
        if (cursor.moveToNext()) { typecompte = cursor.getString(3); }
        // closing connection
        cursor.close();
        bd.close();
        // returning lables
        return typecompte;
    }

    public String getetat(String nonBank,String user){
        String etat=null;
        // Select All Query
        String selectQuery = "SELECT  * FROM TableEnregistreCompte9 where bank = '"+nonBank+"' and user ='"+user+"'";
        bd = accesBd.getReadableDatabase();
        Cursor cursor = bd.rawQuery(selectQuery, null);//selectQuery,selectedArguments

        // looping through all rows and adding to list
        if (cursor.moveToNext()) { etat = cursor.getString(4); }
        // closing connection
        cursor.close();
        bd.close();
        // returning lables
        return etat;
    }



    public Integer AllDepenseToday() {
        Integer allDepense = 0;

        bd = accesBd.getReadableDatabase();
        String sql = "select * from TableCategorie9 where date = '"+currentDateandTime+"' and user = '"+String.valueOf(user.userName())+"' ";
        ArrayList<InformationToday> list = new ArrayList<>();
        Cursor cursor = bd.rawQuery(sql, null);
        if(cursor.moveToLast()){
            do {
                String montantBase = cursor.getString(1);
               // allDepense += Integer.parseInt(montantBase);
            }while (cursor.moveToPrevious());
        }
        cursor.close();
        return allDepense;
    }

    public Double Toute_Depense() {
        double allDepense = 0.0;

        bd = accesBd.getReadableDatabase();
        String sql = "select * from TableCategorie9 where user = '"+String.valueOf(user.userName())+"'";
        ArrayList<InformationToday> list = new ArrayList<>();
        Cursor cursor = bd.rawQuery(sql, null);
        if(cursor.moveToLast()){
            do {
                String montantBase = cursor.getString(1);
                allDepense += Double.parseDouble(montantBase);
            }while (cursor.moveToPrevious());
        }
        cursor.close();
        return allDepense;
    }



//// derniere methode rapport

    public double rapportJourSemaineMois(String categorie,String group,String CompteSelected){
        double prixtotal=0.0;
        String grouppar=group;
        // Select All Query
        String selectQuery="";
        if(grouppar.equalsIgnoreCase("Aujourdhui")) {
            selectQuery="SELECT  * FROM TableCategorie9 where user= '" + String.valueOf(user.userName()) + "' and categorie='" + categorie + "' and date = '" + currentDateandTime + "' and devise = '"+CompteSelected+"'";
        }
         else  {
            selectQuery = "SELECT  * FROM TableCategorie9 where user= '" + String.valueOf(user.userName()) + "' and categorie='"+categorie+"' and devise = '"+CompteSelected+"'";
        }


         bd = accesBd.getReadableDatabase();
         Cursor cursor = bd.rawQuery(selectQuery, null);//selectQuery,selectedArguments
        // looping through all rows and adding to list
        if(cursor.moveToLast()){
            do {
                String prix1 = cursor.getString(1);
                prixtotal += Double.parseDouble(prix1);
            }while (cursor.moveToPrevious());
        }        // closing connection
        cursor.close();
        bd.close();
        // returning lables
        return prixtotal;
    }

    // retourner somme lan
    public double SoldeCompte(String CompteSelected){
        double solde=0.0;
        // Select All Query
        String selectQuery="";

            selectQuery="SELECT  * FROM TableEnregistreCompte9 where user= '" + String.valueOf(user.userName()) + "' and bank = '"+CompteSelected+"'";


            bd = accesBd.getReadableDatabase();
            Cursor cursor = bd.rawQuery(selectQuery, null);//selectQuery,selectedArguments
            // looping through all rows and adding to list
            if (cursor.moveToNext()) {
                String s = cursor.getString(5);
                solde = Double.parseDouble(s);
            }        // closing connection
            cursor.close();
            bd.close();
            // returning lables

        return solde;
    }

    public boolean deleteDepense(Integer id){
        bd = accesBd.getWritableDatabase();
        Cursor cursor = bd.rawQuery("Delete from TableCategorie9 where id=?", new String[]{String.valueOf(id)});
        if (cursor.getCount() < 0) return false;
        else return true;
    }

}