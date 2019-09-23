package com.example.depansmwen;

import android.Manifest;
import android.app.AlertDialog;
import android.app.Application;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.content.res.Configuration;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Environment;
import android.support.annotation.NonNull;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.InputFilter;
import android.text.InputType;
import android.text.TextWatcher;
import android.text.style.UnderlineSpan;
import android.view.View;
import android.widget.*;

import com.example.depansmwen.Database.MySqlLiteOpenHelper;
import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Document;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPRow;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;

import org.w3c.dom.Text;

import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;


public class Parametre extends AppCompatActivity   {
    AccesLocal accesLocal;
    static MainActivity user;
    ListView list_cat = null;
    ListView list_param = null;
    ListView list_compte=null;
    ListView list_autres=null;
     CheckBox chk;
    ListView param = null;
    View viewModifierCat;
    View viewDeleteCat;
    View  viewvc;
    View viewvc1,viewLangue;
    Spinner spinnerModifierCat,spinnercompte;
    Spinner spinnerDeleteCat,spinnerlangue;

    File pdfFile;
    Prepare_Rapport prepare_rapport ;
    Prepare_Rapport categorie,prix,compte,date,note,prix_total;
    ArrayList<Prepare_Rapport> Mylist;
    public static final int STORAGE_CODE=1000;
    Dologin log;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // DoLogin

        log = new Dologin(Parametre.this);
        log.execute();

        setContentView(R.layout.activity_parametre);
        getSupportActionBar().setTitle("Parametre");
        accesLocal = new AccesLocal(Parametre.this);
        List<String> list = new ArrayList<>();
        List<String> list1 = new ArrayList<>();
        list.add("Rappel en fin de journee");
        list1.add("Devise");
        loadLocale();




        /// chanje titre app lan
//        ActionBar actionBar = getSupportActionBar();
//        actionBar.setTitle(getResources().getString(R.string.app_name));

        list_cat= findViewById(R.id.list_cat);
//        list_param= findViewById(R.id.list_param1);
//        param= findViewById(R.id.param);
        list_compte=findViewById(R.id.list_compte);
        list_autres=findViewById(R.id.list_autres);
        spinnercompte = (Spinner) findViewById(R.id.spinnercompte);

        list_autres.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                switch(i){
                    case 0:{
                        rapportStat();
                    }break;
                    case 1:{

                            // action pour mande permission sou tel lan
                        if(Build.VERSION.SDK_INT > Build.VERSION_CODES.M){
                            if(checkSelfPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE)== PackageManager.PERMISSION_DENIED){
                                // no permision
                                String[] permissions = {Manifest.permission.WRITE_EXTERNAL_STORAGE};
                                requestPermissions(permissions, STORAGE_CODE);
                            }else{
                                        // per ok
                                savePdf();
                            }
                        }else{
                            // permi ok
                            savePdf();

                        }


                    }break;
                }
            }
        });


        list_compte.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                switch(position){
                    case 0:{
                        Ajouter_compte();
                    }
                    break;
                    case 1:{
                        Visualiser_compte();
                    }
                    break;
                }
            }


        });

        list_cat.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                switch(position){
                    case 0:{
                        Ajouter_categorie();
                    }
                    break;
                    case 1:{
                        Modifier_categorie();
                    }
                    break;
                    case 2:{
                        Supprimer_categorie();
                    }
                    break;
                }
            }
        });
        loadSpinnerData();
        //loadSpinnerDataForDelete();

    }

    private void savePdf() {
        // Verifier si user sa a gen element nan ki anrejistre deja sinon lpral save yon rapport ki vide
        //donk pou sa nap eseye al verifye eske li gen omwen yon grenn element deja ann suiv sak pral fet la. lajan pam li ye tonton
        //eske nou la regade byen
//        boolean tr = accesLocal.checkUser(user.userName());
        if (Mylist.size()<=0) {
            Toast.makeText(Parametre.this, "Vous ne pouvez pas exporter de Rapport" +
                            " sans rien enregistrer Merci, pa pran'n pou con",Toast.LENGTH_LONG).show();
        }else
            {

        try {

            File docsFolderr = new File(Environment.getExternalStorageDirectory() + "/Rapports_LajanPam");
            if (!docsFolderr.exists()) {
                docsFolderr.mkdir();
            }


            String pdfname = user.userName().toString() + "_Rapport.pdf";
            Toast.makeText(Parametre.this, "Succes: \n" + " Exporter dans Phone/Rapports_LajanPam/" + pdfname, Toast.LENGTH_LONG).show();
            pdfFile = new File(docsFolderr.getAbsolutePath(), pdfname);
            OutputStream output = new FileOutputStream(pdfFile);
            Document document = new Document(PageSize.A4);

            PdfPTable table = new PdfPTable(new float[]{2, 2, 3, 3, 3});
            table.getDefaultCell().setHorizontalAlignment(Element.ALIGN_CENTER);
            table.getDefaultCell().setFixedHeight(50);
            table.setTotalWidth(PageSize.A4.getWidth());
            table.setWidthPercentage(100);
            table.getDefaultCell().setVerticalAlignment(Element.ALIGN_BASELINE);
            table.addCell("CATEGORIE");
            table.addCell("PRIX");
            table.addCell("COMPTE");
            table.addCell("Note/Raison");
            table.addCell("Date");

            table.setHeaderRows(1);
            PdfPCell[] cells = table.getRow(0).getCells();
            for (int j = 0; j < cells.length; j++) {
                cells[j].setBackgroundColor(BaseColor.ORANGE);
            }


            for (int i = 0; i < Mylist.size(); i++) {

                categorie = Mylist.get(i);
                prix = Mylist.get(i);
                compte = Mylist.get(i);
                note = Mylist.get(i);
                date = Mylist.get(i);
                prix_total = Mylist.get(i);

                String cat = categorie.getCategorie();
                String pr = prix.getPrix();
                String comp = compte.getCompte();
                String no = note.getNote();
                String da = date.getDate();

                table.addCell(String.valueOf(cat));
                table.addCell(String.valueOf(pr));
                table.addCell(String.valueOf(comp));
                table.addCell(String.valueOf(no));
                table.addCell(String.valueOf(da));
            }

            PdfWriter.getInstance(document, output);
            document.open();
            Font f = new Font(Font.FontFamily.TIMES_ROMAN, 16.0f, 0, BaseColor.BLUE);
            Font d = new Font(Font.FontFamily.TIMES_ROMAN, 16.0f, 0, BaseColor.RED);
            Font e = new Font(Font.FontFamily.TIMES_ROMAN, 16.0f, Font.UNDERLINE, BaseColor.RED);
            Font g = new Font(Font.FontFamily.TIMES_ROMAN, 20.0f, Font.UNDERLINE, BaseColor.RED);
            Font h = new Font(Font.FontFamily.TIMES_ROMAN, 15.0f, 0, BaseColor.BLACK);
            document.add(new Paragraph("                                                         Lajan Pa'm                                                      ", f));
            document.add(new Paragraph("                                              Rapports des Depenses                                            ", d));
            document.add(new Paragraph("                                                                                                                                ", e));

            document.add(new Paragraph("\n"));
            //    ajout tab nn  document
            document.add(table);

            document.add(new Paragraph("Total :" + prix_total.getPrix_total() + " Gourdes", g));
            document.add(new Paragraph("Pour toutes les categories et tous les Comptes", h));
            document.add(new Paragraph("\n\n\n\n"));
            document.add(new Paragraph("                                   ==== Merci d'avoir choisi LAJAN PAM! ====", h));
            document.close();


        } catch (Exception e) {
            Toast.makeText(Parametre.this, e.getMessage(), Toast.LENGTH_SHORT).show();

        }
    }

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {

        switch(requestCode){
            case STORAGE_CODE:{
                if(grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED){
                    // permision an akode nan popup
                    savePdf();
                }else{

                    Toast.makeText(Parametre.this,"Permission Denied",Toast.LENGTH_SHORT).show();
                }
            }
        }
    }

    private void rapportStat() {
        //Toast.makeText(Parametre.this,"Version 2.0",Toast.LENGTH_SHORT).show();
        startActivity(new Intent(Parametre.this,Rapport.class));
    }



    private void setLocale(String langue) {
        Locale locale =new Locale(langue);
        Locale.setDefault(locale);
        Configuration config = new Configuration();
        config.locale = locale;
        getBaseContext().getResources().updateConfiguration(config,getBaseContext().getResources().getDisplayMetrics());

        //save data sharedPreferences
        SharedPreferences.Editor editor =getSharedPreferences("Settings",MODE_PRIVATE).edit();
        editor.putString("My_lang",langue);
        editor.apply();
    }

    // load langue
    public void loadLocale(){
        SharedPreferences prefs = getSharedPreferences("Settings",Parametre.MODE_PRIVATE);
        String language = prefs.getString("My_lang","");
        setLocale(language);
    }


    public void Ajouter_compte() {
        final View view=getLayoutInflater().inflate(R.layout.creer_compte,null);
        final AlertDialog.Builder ajoutcat = new AlertDialog.Builder(Parametre.this);
        ajoutcat.setTitle(R.string.ajouter_compte);
        ajoutcat.setPositiveButton(R.string.adhesionOk, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                EditText editbank =(EditText) view.findViewById(R.id.editbank);

               // editbank.setFilters(new InputFilter[]{new InputFilter.AllCaps()});

                EditText editnocompte = (EditText) view.findViewById(R.id.editnocompte);
                EditText solde1 = (EditText) view.findViewById(R.id.edit_solde);
                Spinner spinnertypedecompte = (Spinner)view.findViewById(R.id.spinnertypedecompte);

                if(editbank.getText().toString().equals("") || solde1.getText().toString().equals("")){
                    Toast.makeText(Parametre.this,R.string.remplirleschan,Toast.LENGTH_SHORT).show();
                }
                else {
                    String nom = String.valueOf(user.userName());
                    String bank = editbank.getText().toString();


                    Toast.makeText(Parametre.this, " "+bank, Toast.LENGTH_SHORT).show();

                    String nocompte = editnocompte.getText().toString();
                    String typedecompte = String.valueOf(spinnertypedecompte.getSelectedItem());
                    String etat = "1";
                    String solde = solde1.getText().toString();


                    // verification du nom de la bankpour eviter de Doublon
                    String nombank = accesLocal.getNomBank(bank);
                    if (bank.equalsIgnoreCase(nombank)){
                        Toast.makeText(Parametre.this, R.string.compteexist, Toast.LENGTH_SHORT).show();
                    }
                    else {
                        Boolean insert = accesLocal.EnregistreCompte(nom, bank, "", "", etat, solde);
                        if (insert == true) {
                            Toast.makeText(Parametre.this, R.string.enregis_succes, Toast.LENGTH_SHORT).show();
                        }
                    }
                    Toast.makeText(Parametre.this, ""+nombank+""+bank, Toast.LENGTH_SHORT).show();




                }

            }
        });
        ajoutcat.setNegativeButton(R.string.adhesionAnnuler, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });

        ajoutcat.setView(view);
        AlertDialog dialog= ajoutcat.create();
        dialog.show();
    }

    public void visual(String nombank){
            EditText editkont = viewvc1.findViewById(R.id.editbankV);
            TextView tv_solde = viewvc1.findViewById(R.id.tv_solde);
            EditText editnocompteV = viewvc1.findViewById(R.id.editnocompteV);
            EditText solde = viewvc1.findViewById(R.id.edit_solde);
            Spinner spinnertypedecompte = viewvc1.findViewById(R.id.spinnertypedecompte);
            CheckBox checketat = viewvc1.findViewById(R.id.checketat);

            editkont.setText(nombank);
            editnocompteV.setText(accesLocal.getnocompte(nombank, String.valueOf(user.userName())));
            String spinne = accesLocal.gettypecompte(nombank, String.valueOf(user.userName()));
            tv_solde.setText("Solde                   : "+accesLocal.getSoldecompte(nombank, String.valueOf(user.userName()))+" HTG");
            solde.setHint(R.string.ajouterSolde);
            if (spinne.equalsIgnoreCase("Courant"))
                spinnertypedecompte.setSelection(0);
            else
                spinnertypedecompte.setSelection(1);

            String etat = accesLocal.getetat(nombank, String.valueOf(user.userName()));
            if (etat.equals("1")) {
                checketat.setChecked(true);
                checketat.setText("Compte Actif");
            } else {
                checketat.setChecked(false);
                checketat.setText("Compte inActif");
            }


    }


    public void Visualiser_compte() {

        loadSpinnerDataForCompte();

        final AlertDialog.Builder dialogcompte = new AlertDialog.Builder(Parametre.this);


        dialogcompte.setTitle(R.string.visualisercomp);

        dialogcompte.setNegativeButton(R.string.adhesionAnnuler, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });

        dialogcompte.setView(viewvc);
        final AlertDialog dialog=dialogcompte.create();
        dialog.show();

        spinnercompte.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

                if(!(spinnercompte.getSelectedItem().toString().equalsIgnoreCase("Liste des Comptes"))) {
                if((spinnercompte.getSelectedItem().toString().equalsIgnoreCase("CASH"))) {

                    final AlertDialog.Builder visual_cpte = new AlertDialog.Builder(Parametre.this);
                    visual_cpte.setTitle(R.string.comptecashmo);

//                  Toast.makeText(Parametre.this," sa se "+nombank,Toast.LENGTH_SHORT).show();
                    visual_cpte.setPositiveButton(R.string.adhesionOk, new DialogInterface.OnClickListener() {

                        // Retourner les infos Concernant le compte selectionner

                        @Override
                        public void onClick(DialogInterface dialog1, int which) {

                            updateEtat();
                            dialog.cancel();

                        }
                    });
                    visual_cpte.setNegativeButton(R.string.adhesionAnnuler, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.cancel();
                        }
                    });

                    viewvc1=getLayoutInflater().inflate(R.layout.visualiser_compte,null);


                    visual_cpte.setView(viewvc1);
                    AlertDialog dialog= visual_cpte.create();
                    dialog.show();
                    champsHide();

                    // rampli bank lan sil se cash jere sa paske cash lan pa gen numewo rire et li pa gen type tou
                    visual(""+spinnercompte.getSelectedItem().toString());


                    chk=viewvc1.findViewById(R.id.checketat);
                    chk.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                        @Override
                        public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                            if(chk.isChecked()){
                                chk.setText("compte actif");
                            }else chk.setText("compte Inactif");
                        }
                    });

                }
                else{
                    final AlertDialog.Builder visual_cpte = new AlertDialog.Builder(Parametre.this);
                    visual_cpte.setTitle(R.string.visualisercomp);
//                    Toast.makeText(Parametre.this," sa se "+nombank,Toast.LENGTH_SHORT).show();
                    visual_cpte.setPositiveButton(R.string.adhesionOk, new DialogInterface.OnClickListener() {

                        // Retourner les infos Concernant le compte selectionner

                        @Override
                        public void onClick(DialogInterface dialog1, int which) {

                            updateEtat();
                            dialog.cancel();

                        }
                    });
                    visual_cpte.setNegativeButton(R.string.adhesionAnnuler, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.cancel();
                        }
                    });

                    viewvc1=getLayoutInflater().inflate(R.layout.visualiser_compte,null);


                    visual_cpte.setView(viewvc1);
                    AlertDialog dialog= visual_cpte.create();
                    dialog.show();
                        champsHide();
                    viewvc1.findViewById(R.id.checketat).setVisibility(View.VISIBLE);
                    // rampli bank lan sil se cash jere sa paske cash lan pa gen numewo rire et li pa gen type tou
                    visual(""+spinnercompte.getSelectedItem().toString());


                    chk=viewvc1.findViewById(R.id.checketat);
                    chk.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                        @Override
                        public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                            if(chk.isChecked()){
                                chk.setText("compte actif");
                            }else chk.setText("compte Inactif");
                        }
                    });
                }

                }

            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        }

    private void champsHide() {
         viewvc1.findViewById(R.id.tv_no_compte).setVisibility(View.GONE);
         viewvc1.findViewById(R.id.editnocompteV).setVisibility(View.GONE);
         viewvc1.findViewById(R.id.tv_nocompte).setVisibility(View.GONE);
         viewvc1.findViewById(R.id.tv_etat).setVisibility(View.GONE);
         viewvc1.findViewById(R.id.spinnertypedecompte).setVisibility(View.GONE);
         viewvc1.findViewById(R.id.checketat).setVisibility(View.GONE);
    }




    private void updateSolde(){
            EditText chansolde=viewvc1.findViewById(R.id.edit_solde);
            String chansolde1=chansolde.getText().toString();


      if(chansolde1.equals("")){
          chansolde1="0.0";
      }
        Double soldeBase = accesLocal.SoldeCompte(spinnercompte.getSelectedItem().toString());
        Double soldeDev = (soldeBase)+(Double.parseDouble(chansolde1));

        boolean trouve=accesLocal.UpdateSolde(soldeDev,spinnercompte.getSelectedItem().toString());
        if(trouve == true){
            Toast.makeText(Parametre.this,R.string.modifSucces,Toast.LENGTH_SHORT).show();
        }

    }
    private void updateEtat() {

        String chktext = chk.getText().toString();
        String chkavant = accesLocal.getetat(spinnercompte.getSelectedItem().toString(),String.valueOf(user.userName()));

        EditText edit_solde=viewvc1.findViewById(R.id.edit_solde);
        String solde=edit_solde.toString();
        if(solde.equals("")){
            solde="0.0";
            updateSolde();
        }
        else updateSolde();
        if(chkavant.equals("1") && chktext.equalsIgnoreCase("Compte inActif"))
        {
            // do something
            boolean trouve=accesLocal.UpdateEtat("0",String.valueOf(user.userName()),spinnercompte.getSelectedItem().toString());
            if(trouve == true){
            }
        }
        if(chkavant.equals("0") && chktext.equalsIgnoreCase("Compte Actif"))
        {
            // do something
            boolean trouve=accesLocal.UpdateEtat("1",String.valueOf(user.userName()),spinnercompte.getSelectedItem().toString());
            if(trouve == true){
            }
        }
    }

    private void loadSpinnerData() {
        List<String> labels = accesLocal.getAllSpinners();
        // Creating adapter for spinner
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item, labels);
        //ArrayAdapter<String> dataAdapter1 = new ArrayAdapter<String>(this,android.R.layout.simple_spinner_dropdown_item, labels);

        viewModifierCat=getLayoutInflater().inflate(R.layout.activity_spinner_cat,null);

        spinnerModifierCat=viewModifierCat.findViewById(R.id.spinner);

        // Drop down layout style - list view with radio button
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        // attaching data adapter to spinner
        spinnerModifierCat.setAdapter(dataAdapter);
    }




    private void loadSpinnerDataForCompte() {
        List<String> labels=accesLocal.getAllSpinnerscompte();
        // Creating adapter for spinner
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item, labels);
        //ArrayAdapter<String> dataAdapter1 = new ArrayAdapter<String>(this,android.R.layout.simple_spinner_dropdown_item, labels);

        viewvc=getLayoutInflater().inflate(R.layout.spinnervisualcompte,null);
        spinnercompte=viewvc.findViewById(R.id.spinnercompte);
        // Drop down layout style - list view with radio button
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        // attaching data adapter to spinner
        spinnercompte.setAdapter(dataAdapter);
    }

    private void loadSpinnerDataForDelete() {
        List<String> labels=accesLocal.getAllSpinners();
        // Creating adapter for spinner
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item, labels);
        //ArrayAdapter<String> dataAdapter1 = new ArrayAdapter<String>(this,android.R.layout.simple_spinner_dropdown_item, labels);

        viewDeleteCat=getLayoutInflater().inflate(R.layout.activity_spinner_cat_supress,null);
        spinnerDeleteCat=viewDeleteCat.findViewById(R.id.spinner);

        // Drop down layout style - list view with radio button
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);


        // attaching data adapter to spinner
        spinnerDeleteCat.setAdapter(dataAdapter);
    }

    public void Supprimer_categorie() {
        loadSpinnerDataForDelete();
        final AlertDialog.Builder msupressCategorie = new AlertDialog.Builder(Parametre.this);
        msupressCategorie.setTitle(R.string.supp_une_cat);

        msupressCategorie.setNegativeButton(R.string.adhesionAnnuler, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });

        msupressCategorie.setView(viewDeleteCat);
        final AlertDialog dialog=msupressCategorie.create();
        dialog.show();

        spinnerDeleteCat.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener()
        {

            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if(!(spinnerDeleteCat.getSelectedItem().toString().equalsIgnoreCase("Liste des categories"))) {

                    final AlertDialog.Builder msupression = new AlertDialog.Builder(Parametre.this);

                    msupression.setTitle(R.string.titreSupreCat +spinnerDeleteCat.getSelectedItem().toString()+" ?");

                    msupression.setPositiveButton("Oui", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog1, int which) {
                            Boolean delete = accesLocal.deleteEnregistreCategorie(spinnerDeleteCat.getSelectedItem().toString(),String.valueOf(user.userName()));
                            if (delete == true){
                                Toast.makeText(Parametre.this, R.string.suppress_succes, Toast.LENGTH_SHORT).show();
                                loadSpinnerDataForDelete();
                            }else{
                                Toast.makeText(Parametre.this, R.string.operEchou, Toast.LENGTH_SHORT).show();
                            }
                            dialog.cancel();
                        }
                    });
                    msupression.setNegativeButton(R.string.adhesionNon, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.cancel();
                        }
                    });
                    msupression.show();
                }

                if((spinnerModifierCat.getSelectedItem().toString().equalsIgnoreCase("Liste des categories")) &&
                        (spinnerModifierCat.getCount()==1)) {

                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    public void Modifier_categorie() {

        final AlertDialog.Builder modifCategorie = new AlertDialog.Builder(Parametre.this);
        modifCategorie.setTitle(R.string.modifCat);
        loadSpinnerData();

        final EditText edit=viewModifierCat.findViewById(R.id.edit);

        modifCategorie.setPositiveButton(R.string.adhesionOui, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

                String a = String.valueOf(edit.getText());
                Boolean modification = accesLocal.updateEnregistreCategorie(spinnerModifierCat.getSelectedItem().toString(), String.valueOf(edit.getText()), String.valueOf(user.userName()));
                if (modification == true){
                    Toast.makeText(Parametre.this, R.string.modifSucces +a, Toast.LENGTH_SHORT).show();
                    //loadSpinnerDataForDelete();
                }else{
                    Toast.makeText(Parametre.this, R.string.operEchou, Toast.LENGTH_SHORT).show();
                }

            }
        });
        modifCategorie.setNegativeButton(R.string.adhesionAnnuler, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });


        spinnerModifierCat.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener()
        {

            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if(!(spinnerModifierCat.getSelectedItem().toString().equalsIgnoreCase("Liste des categories"))) {
                    edit.setText(spinnerModifierCat.getSelectedItem().toString());
                }

                if((spinnerModifierCat.getSelectedItem().toString().equalsIgnoreCase("Liste des categories")) &&
                        (spinnerModifierCat.getCount()==1)) {
                    edit.setText("");
                }

            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        modifCategorie.setView(viewModifierCat);
        AlertDialog dialog= modifCategorie.create();
        dialog.show();
    }



    public void Ajouter_categorie(){

        final AlertDialog.Builder ajoutCategorie = new AlertDialog.Builder(Parametre.this);
        ajoutCategorie.setTitle(R.string.ajouterunecat);
        final EditText input=new EditText(Parametre.this);
        input.setInputType(InputType.TYPE_CLASS_TEXT);
        input.setHint("Saisir le nom...");
        ajoutCategorie.setView(input);
        ajoutCategorie.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                String textCategorie = String.valueOf(input.getText());
                if (textCategorie.equals("") ){
                    Toast.makeText(Parametre.this, R.string.champobl, Toast.LENGTH_SHORT).show();
                }else{

                    boolean categoriebase = accesLocal.checkcategorie(textCategorie);
                    if (categoriebase==false){
                        Toast.makeText(Parametre.this, R.string.catexistedeja, Toast.LENGTH_SHORT).show();
                    }else {

                        Boolean insert = accesLocal.EnregistreCategorie(textCategorie, String.valueOf(user.userName()));
                        if (insert == true) {
                            Toast.makeText(Parametre.this, R.string.enregis_succes, Toast.LENGTH_SHORT).show();
                            loadSpinnerData();
                        }
                    }
                }
            }
        });
        ajoutCategorie.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });
        ajoutCategorie.show();
    }

    class Dologin extends AsyncTask{
          MySqlLiteOpenHelper accesBd1;
          SQLiteDatabase bd;

          public Dologin(Context context){
              accesBd1 = new MySqlLiteOpenHelper(context,"bdDepansMwen.sqlite", null, 9);

              //Mylist=new ArrayList<Prepare_Rapport>();

              bd = accesBd1.getReadableDatabase();

          }
        @Override
        protected Object doInBackground(Object[] objects) {
                String sql = "select * from TableCategorie9 where user='"+user.userName().toString()+"'";
                Cursor cursor = bd.rawQuery(sql, null);
                Mylist=new ArrayList<Prepare_Rapport>();
                double allDepense=0.0;
                if(cursor.moveToLast()){
                    do {
                        prepare_rapport = new Prepare_Rapport();

                        prepare_rapport.setCategorie(cursor.getString(0));
                        prepare_rapport.setPrix(cursor.getString(1));
                        prepare_rapport.setCompte(cursor.getString(2));
                        prepare_rapport.setNote(cursor.getString(3));
                        prepare_rapport.setDate(cursor.getString(4));
                        //additionner prix yo
                        String montantBase = cursor.getString(1);
                        allDepense += Double.parseDouble(montantBase);
                        prepare_rapport.setPrix_total(String.valueOf(allDepense));

                        Mylist.add(prepare_rapport);
                      //  prepare_rapport = new Prepare_Rapport();
                     }while (cursor.moveToPrevious());
                }
                cursor.close();

            return null;
        }

          }

}
