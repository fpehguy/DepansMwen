package com.example.depansmwen;

import android.Manifest;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.Notification;
import android.app.NotificationManager;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Environment;
import android.preference.DialogPreference;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.design.widget.TabLayout;
import android.support.design.widget.TextInputLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.NotificationManagerCompat;
import android.support.v4.view.MenuItemCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.text.Html;
import android.text.InputType;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.depansmwen.Database.MySqlLiteOpenHelper;
import com.github.clans.fab.FloatingActionMenu;
import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Document;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;

import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;




public class accueil extends AppCompatActivity {



    Spinner spinnerCategorie,spinnerCompte;
    private TextInputLayout etPrix;
    Spinner spinnerDevise;
    private TextInputLayout etNote;
    private static AccesLocal accesLocal;
    static MainActivity user;
    SimpleDateFormat sdf;
    String currentDateandTime;
    Resources res = null;
    View view,viewvc1,viewvc;
    Tab_aujourdhui tab_auj;
    Tab_semaine tab_sem;
    Tab_mois tab_m;
    Integer allDepense = 0;
    private long backpressed;

    //Add
    View viewModifierCat;
    Spinner spinnerModifierCat;
    View viewDeleteCat;
    Spinner spinnerDeleteCat,spinnercompte;
    CheckBox chk;

    FloatingActionMenu   floatingMenuBtn;
    FloatingActionButton floatingActionButtonAjouterDepense;
    com.github.clans.fab.FloatingActionButton act1,act2,act3;
    private DrawerLayout mDrawer;
    private Toolbar toolbar;
    private NavigationView nvDrawer;
    private ActionBarDrawerToggle drawerToggle;

    // pou pdf la

    File pdfFile;
    Prepare_Rapport prepare_rapport ;
    Prepare_Rapport categorie,prix,compte,date,note,prix_total;
    ArrayList<Prepare_Rapport> Mylist;
    public static final int STORAGE_CODE=1000;
    Dologin log;


    @Override
    protected void onCreate(Bundle savedInstanceState) {


        super.onCreate(savedInstanceState);
        log = new Dologin(accueil.this);
        log.execute();
        setContentView(R.layout.accueil1);
        loadLocale();
      //  getSupportActionBar().hide();
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        // This will display an Up icon (<-), we will replace it with hamburger later
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        // Find our drawer view
        mDrawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawerToggle = setupDrawerToggle();
        // Setup toggle to display hamburger icon with nice animation
        drawerToggle.setDrawerIndicatorEnabled(true);
        drawerToggle.syncState();
        // Tie DrawerLayout events to the ActionBarToggle
        mDrawer.addDrawerListener(drawerToggle);
        // Find our drawer view
        nvDrawer = (NavigationView) findViewById(R.id.nvView);
        // Setup drawer view
        setupDrawerContent(nvDrawer);
        //Add
        spinnercompte = (Spinner) findViewById(R.id.spinnercompte);
        accesLocal = new AccesLocal(accueil.this);
        SectionsPagerAdapter sectionsPagerAdapter = new SectionsPagerAdapter(this, getSupportFragmentManager());
        ViewPager viewPager = findViewById(R.id.view_pager);
        viewPager.setAdapter(sectionsPagerAdapter);
        TabLayout tabs = findViewById(R.id.tabs);
        tabs.setupWithViewPager(viewPager);
        sdf = new SimpleDateFormat("yyyy.MM.dd");
        currentDateandTime = sdf.format(new Date());
        loadSpinnerData1();
        floatingMenuBtn =findViewById(R.id.floatingMenuBtn);
       // floatingMenuBtn.setMenuButtonColorNormal(R.color.colorAccent);
        act1 = findViewById(R.id.act1);
        act2 = findViewById(R.id.act2);
        act3 = findViewById(R.id.act3);
        act2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Ajouter_categorie();
                floatingMenuBtn.close(true);
            }
        });
        act3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Ajouter_compte();
                floatingMenuBtn.close(true);
            }
        });
        act1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                floatingMenuBtn.close(true);
                // Creation Afficharge dialog
            final AlertDialog.Builder depCategorie = new AlertDialog.Builder(accueil.this);
            loadSpinnerData1();
            etPrix = (TextInputLayout) view.findViewById(R.id.cd);
            etNote = (TextInputLayout) view.findViewById(R.id.etNote);
            Button btn_ajouter=view.findViewById(R.id.btn_ajouter);
            depCategorie.setView(view);
            final AlertDialog dialog= depCategorie.create();
            dialog.show();
            btn_ajouter.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        String textCategorie = String.valueOf(spinnerCategorie.getSelectedItem());
                        String prix= etPrix.getEditText().getText().toString();
                        String note= etNote.getEditText().getText().toString();
                        String compte=String.valueOf(spinnerCompte.getSelectedItem());

                        if (prix.equals("") || prix.equals("0") || prix.equals("0.0") || prix.equals(".") || Double.parseDouble(prix) < 1 || note.equals("")){
                            Toast.makeText(accueil.this, R.string.remplirleschan, Toast.LENGTH_SHORT).show();
                        }else if(spinnerCompte.getCount()==0){
                            Toast.makeText(accueil.this, " Veuillez Initialiser ou creer des comptes \n dans Parametre/Visualiser/Activer Compte", Toast.LENGTH_LONG).show();

                        }else{
                            Double soldeBase = accesLocal.SoldeCompte(compte);
                            Double prixdepense =Double.parseDouble(prix);
                            if(soldeBase>=prixdepense) {
                                double resteSolde=soldeBase-prixdepense;
                                boolean trouve=accesLocal.UpdateSolde(resteSolde,compte);

                                Boolean insert = accesLocal.AddCategorie(textCategorie, prix, compte, note, currentDateandTime, String.valueOf(user.userName()));
                                if (insert == true) {
                                    Toast.makeText(accueil.this, R.string.enregis_succes, Toast.LENGTH_SHORT).show();
                                    tab_auj.refreshTabAujourdhui();
                                    tab_sem.refreshTabSemaine();
                                    //tab_m.refreshTabMois();
                                    log = new Dologin(accueil.this);
                                    log.execute();
                                    dialog.cancel();
                                } else {
                                    Toast.makeText(accueil.this, R.string.operEchou, Toast.LENGTH_SHORT).show();
                                }
                            }
                            else{
                                View viewError = getLayoutInflater().inflate(R.layout.lay_error_argentinsu,null);
                                final AlertDialog.Builder alertError = new AlertDialog.Builder(accueil.this);
                                alertError.setView(viewError);
                                final AlertDialog dialogError = alertError.create();
                                dialogError.show();
                            }
                        }
                    }
                });

            }
        });



    }

    private void loadSpinnerData1() {
        List<String> labels = accesLocal.getAllSpinners1();
        List<String> labels1 = accesLocal.getAllSpinnerscompte1();
        // Creating adapter for spinner
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item, labels);
        ArrayAdapter<String> dataAdapter1 = new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item, labels1);
        //ArrayAdapter<String> dataAdapter1 = new ArrayAdapter<String>(this,android.R.layout.simple_spinner_dropdown_item, labels);
        view=getLayoutInflater().inflate(R.layout.depense,null);
        spinnerCategorie =  view.findViewById(R.id.spinnerCategorie);
        spinnerCompte =view.findViewById(R.id.spinnerCompte);
        // Drop down layout style
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        dataAdapter1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        // attaching data adapter to spinner
        spinnerCategorie.setAdapter(dataAdapter);
        spinnerCompte.setAdapter(dataAdapter1);
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
       // recreate();

    }
    public void loadLocale(){
        SharedPreferences prefs = getSharedPreferences("Settings",accueil.MODE_PRIVATE);
        String language = prefs.getString("My_lang","");
        setLocale(language);
    }

    @Override
    public void onBackPressed() {

        dialogDeconnect();

    }

    public String getCurrentDateandTime() {
        return currentDateandTime;
    }

    public class SectionsPagerAdapter extends FragmentPagerAdapter {
        public SectionsPagerAdapter(accueil mainActivity, FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            switch(position){
                case 0:
                    tab_auj = new Tab_aujourdhui();
                    return tab_auj;
                case 1:
                     tab_sem = new Tab_semaine();
                    return tab_sem;

                case 2:
                     tab_m = new Tab_mois();
                    return tab_m;

                default:
                    return null;

            }
        }

        @Override
        public int getCount() {

            return 3;
        }


        @Override
        public CharSequence getPageTitle(int position) {
            switch (position){
                case 0:
                    res = getResources();
                    return res.getString(R.string.jour);
                case 1:
                     return res.getString(R.string.semaine);
                case 2:
                    return res.getString(R.string.mois);

            }
            return null;
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_recherche, menu);
        MenuItem search = menu.findItem(R.id.search);
        SearchView searchView = (SearchView) MenuItemCompat.getActionView(search);
        search(searchView);
        return true;
    }
    private void search(SearchView searchView) {

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {

                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                tab_auj.textRecherche(newText);
                tab_auj.GestionRecherche();

//                tab_m.textRecherche(newText);
//                tab_m.GestionRecherche();

//                tab_sem.textRecherche(newText);
//                tab_sem.GestionRecherche();
                return true;
            }
        });
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

    private ActionBarDrawerToggle setupDrawerToggle() {
        // NOTE: Make sure you pass in a valid toolbar reference.  ActionBarDrawToggle() does not require it
        // and will not render the hamburger icon without it.
        return new ActionBarDrawerToggle(this, mDrawer, toolbar, R.string.droouverture,  R.string.drfermeture);
    }

    private void setupDrawerContent(NavigationView navigationView) {
        navigationView.setNavigationItemSelectedListener(
                new NavigationView.OnNavigationItemSelectedListener() {
                    @Override
                    public boolean onNavigationItemSelected(MenuItem menuItem) {
                        selectDrawerItem(menuItem);
                        return true;
                    }
                });
    }
    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        // Sync the toggle state after onRestoreInstanceState has occurred.
        drawerToggle.syncState();
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        // Pass any configuration change to the drawer toggles
        drawerToggle.onConfigurationChanged(newConfig);
    }
    public void selectDrawerItem(MenuItem menuItem) {
        switch(menuItem.getItemId()) {
            case R.id.addCategorie:
               Ajouter_categorie();
                break;
            case R.id.modifierCategorie:
                Modifier_categorie();
                break;
            case R.id.supCat:
              Supprimer_categorie();

                break;
            case R.id.addCompte:
               Ajouter_compte();
               break;
            case R.id.modifierCompte:
               Visualiser_compte();
                break;
            case R.id.rapportdepense:
                startActivity(new Intent(accueil.this,Rapport.class));
                break;
            case R.id.exportenpdf:
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

                break;
            case R.id.apropos:
                View v = getLayoutInflater().inflate(R.layout.apropos,null);
                final AlertDialog.Builder apropos = new AlertDialog.Builder(this);
                apropos.setView(v);
                AlertDialog alertDialog = apropos.create();

                alertDialog.show();
                break;

            case R.id.logout:
                // gade eske moun lan anrejistre jodi a si non notification si oui rien
                dialogDeconnect();
                break;
            default:
                //fragmentClass = FirstFragment.class;
        }

        // Highlight the selected item has been done by NavigationView
        menuItem.setChecked(true);
        // Set action bar title
        //setTitle(menuItem.getTitle());
        // Close the navigation drawer
        mDrawer.closeDrawers();
    }



    private void Visualiser_compte() {

        loadSpinnerDataForCompte();

        final AlertDialog.Builder dialogcompte = new AlertDialog.Builder(accueil.this);
        final Button btn_annuler_visau = viewvc.findViewById(R.id.btn_annuler_visau);

        dialogcompte.setView(viewvc);
        final AlertDialog dialog=dialogcompte.create();
        btn_annuler_visau.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.cancel();
            }
        });

        dialog.show();
        viewvc1=getLayoutInflater().inflate(R.layout.visualiser_compte,null);
        spinnercompte.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                if(!(spinnercompte.getSelectedItem().toString().equalsIgnoreCase("Liste des Comptes"))) {
                        final AlertDialog.Builder visual_cpte = new AlertDialog.Builder(accueil.this);
                        viewvc1=getLayoutInflater().inflate(R.layout.visualiser_compte,null);
                        Button btn_modif_compte=viewvc1.findViewById(R.id.btn_modif_compte);
                        visual_cpte.setView(viewvc1);
                        final AlertDialog dialog1= visual_cpte.create();
                        visual_cpte.setNegativeButton("NOn", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            dialog1.cancel();
                        }
                    });
                        btn_modif_compte.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                    updateEtat();
                                    dialog.cancel();
                                    dialog1.cancel();


                            }
                        });


                       dialog1.show();
                       champsHide();
                        viewvc1.findViewById(R.id.checketat).setVisibility(View.VISIBLE);
                        // rampli bank lan sil se cash jere sa paske cash lan pa gen numewo rire et li pa gen type tou
                        visual(""+spinnercompte.getSelectedItem().toString());

                        chk=viewvc1.findViewById(R.id.checketat);
                        chk.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                            @Override
                            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                                if(chk.isChecked()){
                                    chk.setText("Compte actif");
                                }else chk.setText("Compte inactif");
                            }
                        });



                }

            }

            private void champsHide() {
                viewvc1.findViewById(R.id.tv_no_compte).setVisibility(View.GONE);
                viewvc1.findViewById(R.id.editnocompteV).setVisibility(View.GONE);
                viewvc1.findViewById(R.id.tv_nocompte).setVisibility(View.GONE);
                viewvc1.findViewById(R.id.tv_etat).setVisibility(View.GONE);
                viewvc1.findViewById(R.id.spinnertypedecompte).setVisibility(View.GONE);
                viewvc1.findViewById(R.id.checketat).setVisibility(View.GONE);
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

            private void updateEtat() {
                String chktext = chk.getText().toString();
                String chkavant = accesLocal.getetat(spinnercompte.getSelectedItem().toString(),String.valueOf(user.userName()));

                EditText edit_solde=viewvc1.findViewById(R.id.edit_solde);
                String solde=edit_solde.toString();


                if(solde.equals("")){
                    solde="0.0";
                    updateSolde();
                }
                else {
                }updateSolde();
                if(chkavant.equals("1") && chktext.equalsIgnoreCase("Compte inActif"))
                {
                    // do something
                    boolean trouve=accesLocal.UpdateEtat("0",String.valueOf(user.userName()),spinnercompte.getSelectedItem().toString());
                    if(trouve == true){
                        toastShow("L'etat du compe: Inactif");
                    }
                }
                if(chkavant.equals("0") && chktext.equalsIgnoreCase("Compte Actif"))
                {
                    // do something
                    boolean trouve=accesLocal.UpdateEtat("1",String.valueOf(user.userName()),spinnercompte.getSelectedItem().toString());
                    if(trouve == true){
                        toastShow("L'etat du compe: Actif");

                    }
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
    }

    private void toastShow(String remplirleschan) {
        Toast.makeText(accueil.this,remplirleschan,Toast.LENGTH_SHORT).show();
    }
    public void dialogDeconnect(){
        View v = getLayoutInflater().inflate(R.layout.lay_deconnect,null);
        AlertDialog.Builder ad=new AlertDialog.Builder(accueil.this);
        Button btnnon = v.findViewById(R.id.btnnon);
        Button btnoui = v.findViewById(R.id.btnoui);
        ad.setView(v);
        final AlertDialog a=ad.create();
        btnoui.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                boolean trouve=accesLocal.checkTransact();
                if(trouve==false){
                    startActivity(new Intent(accueil.this,MainActivity.class));
                    finish();
                }
                else{
                    notificationNoDepense();
                    startActivity(new Intent(accueil.this,MainActivity.class));
                    finish();
                }

            }
        });
        btnnon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                a.cancel();
            }
        });
        a.show();
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

    private void updateSolde() {

        EditText chansolde = viewvc1.findViewById(R.id.edit_solde);
        String chansolde1 = chansolde.getText().toString();
        if(chansolde1.equals("0.0") || chansolde1.equals(".") || chansolde1.equals("")){
            toastShow("Solde inchange");
        }
        else {

            Double soldeBase = accesLocal.SoldeCompte(spinnercompte.getSelectedItem().toString());
            Double soldeDev = (soldeBase) + (Double.parseDouble(chansolde1));
            boolean trouve = accesLocal.UpdateSolde(soldeDev, spinnercompte.getSelectedItem().toString());
            if (trouve == true) {
               // Toast.makeText(accueil.this, R.string.modifSucces, Toast.LENGTH_SHORT).show();
            }
        }
    }

    private void Ajouter_compte() {
        final View view=getLayoutInflater().inflate(R.layout.creer_compte,null);
        final AlertDialog.Builder ajoutcat = new AlertDialog.Builder(accueil.this);
        final Button btn_creer = view.findViewById(R.id.btn_creer);
        final EditText editbank =(EditText) view.findViewById(R.id.editbank);
        final EditText editnocompte = (EditText) view.findViewById(R.id.editnocompte);
        final EditText solde1 = (EditText) view.findViewById(R.id.edit_solde);
        final Spinner spinnertypedecompte = (Spinner)view.findViewById(R.id.spinnertypedecompte);
        ajoutcat.setView(view);
        final AlertDialog dialog= ajoutcat.create();
        btn_creer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                if(editbank.getText().toString().equals("") || solde1.getText().toString().equals("")){
                    Toast.makeText(accueil.this,R.string.remplirleschan,Toast.LENGTH_SHORT).show();
                }
                else {
                    String nom = String.valueOf(user.userName());
                    String bank = editbank.getText().toString();


                    Toast.makeText(accueil.this, " "+bank, Toast.LENGTH_SHORT).show();

                    String nocompte = editnocompte.getText().toString();
                    String typedecompte = String.valueOf(spinnertypedecompte.getSelectedItem());
                    String etat = "1";
                    String solde = solde1.getText().toString();


                    // verification du nom de la bankpour eviter de Doublon
                    String nombank = accesLocal.getNomBank(bank);
                    if (bank.equalsIgnoreCase(nombank)){
                        Toast.makeText(accueil.this, R.string.compteexist, Toast.LENGTH_SHORT).show();
                    }
                    else {
                        Boolean insert = accesLocal.EnregistreCompte(nom, bank, "", "", etat, solde);
                        if (insert == true) {
                            dialog.cancel();
                            Toast.makeText(accueil.this, R.string.enregis_succes, Toast.LENGTH_SHORT).show();
                        }
                    }
                }
            }
        });


        dialog.show();
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

    private void Supprimer_categorie() {
        boolean cheSuppresion =accesLocal.checkCatAvantSppre();
        if(cheSuppresion==true){
            supCat();
        }
            else{
            loadSpinnerDataForDelete();
            final AlertDialog.Builder msupressCategorie = new AlertDialog.Builder(accueil.this);
            msupressCategorie.setView(viewDeleteCat);
            final AlertDialog dialog = msupressCategorie.create();
            final Button btn_annuler = viewDeleteCat.findViewById(R.id.btn_annuler);
            btn_annuler.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    dialog.cancel();
                }
            });

            dialog.show();

            spinnerDeleteCat.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

                @Override
                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                    if (!(spinnerDeleteCat.getSelectedItem().toString().equalsIgnoreCase("Liste des categories"))) {
                        {
                            View v = getLayoutInflater().inflate(R.layout.deleteadhesion,null);
                            AlertDialog.Builder ad=new AlertDialog.Builder(accueil.this);
                            Button btnnon = v.findViewById(R.id.buttonnon);
                            Button btnoui = v.findViewById(R.id.buttonoui);
                            ad.setView(v);
                            final AlertDialog a=ad.create();
                            btnoui.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View view) {

                                    Boolean delete = accesLocal.deleteEnregistreCategorie(spinnerDeleteCat.getSelectedItem().toString(), String.valueOf(user.userName()));
                                    if (delete == true) {
                                        Toast.makeText(accueil.this, R.string.suppress_succes, Toast.LENGTH_SHORT).show();
                                        loadSpinnerDataForDelete();
                                    } else {
                                        Toast.makeText(accueil.this, R.string.operEchou, Toast.LENGTH_SHORT).show();
                                    }
                                    a.cancel();
                                    dialog.cancel();
                                }
                            });
                            btnnon.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View view) {
                                    a.cancel();
                                }
                            });
                            a.show();

                        }
                    }


                }

                @Override
                public void onNothingSelected(AdapterView<?> parent) {

                }
            });
        }

    }
    public void supCat(){
        View viewError = getLayoutInflater().inflate(R.layout.lay_error_cat,null);
        final AlertDialog.Builder alertError = new AlertDialog.Builder(accueil.this);
        alertError.setView(viewError);
        final AlertDialog dialogError = alertError.create();
        dialogError.show();
    }

    private void Modifier_categorie() {
        boolean cheSuppresion =accesLocal.checkCatAvantSppre();
        if(cheSuppresion==true){

            supCat();
        }
        else {

            final AlertDialog.Builder modifCategorie = new AlertDialog.Builder(accueil.this);
            loadSpinnerData();
            final EditText edit = viewModifierCat.findViewById(R.id.edit);
            final Button btn_modif = viewModifierCat.findViewById(R.id.btn_modif);
            final Spinner spinner = viewModifierCat.findViewById(R.id.spinner);
            modifCategorie.setView(viewModifierCat);
            final AlertDialog dialog = modifCategorie.create();

            btn_modif.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    String a = String.valueOf(edit.getText());
                    if (spinner.getSelectedItem().toString().equalsIgnoreCase("Liste des categories")) {
                        Toast.makeText(accueil.this, R.string.selectCatamodif, Toast.LENGTH_SHORT).show();
                    } else if (a.equals("")) {
                        Toast.makeText(accueil.this, R.string.champCatnepeutvide, Toast.LENGTH_SHORT).show();
                    } else {
                        Boolean modification = accesLocal.updateEnregistreCategorie(spinnerModifierCat.getSelectedItem().toString(), String.valueOf(edit.getText()), String.valueOf(user.userName()));
                        if (modification == true) {
                            dialog.cancel();
                            Toast.makeText(accueil.this, R.string.modifSucces, Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(accueil.this, R.string.operEchou, Toast.LENGTH_SHORT).show();
                        }
                    }
                }
            });
            spinnerModifierCat.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

                @Override
                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                    if (!(spinnerModifierCat.getSelectedItem().toString().equalsIgnoreCase("Liste des categories"))) {
                        edit.setText(spinnerModifierCat.getSelectedItem().toString());
                    }

                    if ((spinnerModifierCat.getSelectedItem().toString().equalsIgnoreCase("Liste des categories")) &&
                            (spinnerModifierCat.getCount() == 1)) {
                        edit.setText("");
                    }

                }

                @Override
                public void onNothingSelected(AdapterView<?> parent) {

                }
            });

            dialog.show();
        }
    }

    private void Ajouter_categorie() {
        final View viewCategorie = getLayoutInflater().inflate(R.layout.ajoutercategorie,null);
        final Button btnCreer_categorie=viewCategorie.findViewById(R.id.btnCreer_categorie);
        final EditText editnomcat=viewCategorie.findViewById(R.id.editnomcat);
        final AlertDialog.Builder alertC = new AlertDialog.Builder(accueil.this);
        alertC.setView(viewCategorie);
        final AlertDialog ad=alertC.create();
        btnCreer_categorie.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String textCategorie = String.valueOf(editnomcat.getText());
                if (textCategorie.equals("") ){
                    Toast.makeText(accueil.this, R.string.champobl, Toast.LENGTH_SHORT).show();
                }else{

                    boolean categoriebase = accesLocal.checkcategorie(textCategorie);
                    if (categoriebase==false){
                        Toast.makeText(accueil.this, R.string.catexistedeja, Toast.LENGTH_SHORT).show();
                    }else {

                        Boolean insert = accesLocal.EnregistreCategorie(textCategorie, String.valueOf(user.userName()));
                        if (insert == true) {
                            Toast.makeText(accueil.this, R.string.enregis_succes, Toast.LENGTH_SHORT).show();
                            loadSpinnerData();
                            editnomcat.setText("");
                           // ad.cancel();
                        }
                    }
                }
            }
        });
        ad.show();

    }

    private void notificationNoDepense() {
        NotificationCompat.Builder notif =(NotificationCompat.Builder) new NotificationCompat.Builder(accueil.this);
        notif.setDefaults(NotificationCompat.DEFAULT_ALL);
        notif.setSmallIcon(R.drawable.ic_warning_black_24dp);
        notif.setLargeIcon(BitmapFactory.decodeResource(getResources(),R.drawable.ic_warning_black_24dp));
        notif.setContentTitle("Lajan Pa'm");
        notif.setContentText("Aucune Depense n'est enregistre aujourd'hui !!");
        notif.setAutoCancel(true);
             // .setContentInfo("determination++");
        NotificationManager notificationManager= (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        notificationManager.notify(1,notif.build());

    }



    private void savePdf() {
 if (Mylist.size()<=0) {
            Toast.makeText(accueil.this, R.string.rienEnregistre,Toast.LENGTH_LONG).show();
        }else
        {

            try {

                File docsFolderr = new File(Environment.getExternalStorageDirectory() + "/Rapports_LajanPam");
                if (!docsFolderr.exists()) {
                    docsFolderr.mkdir();
                }


                String pdfname = user.userName().toString() + "_Rapport.pdf";
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



                View v = getLayoutInflater().inflate(R.layout.layout_pdf,null);
                AlertDialog.Builder ad=new AlertDialog.Builder(accueil.this);
                TextView tv_pdf=v.findViewById(R.id.tv_pdf);
                ad.setView(v);
                final AlertDialog a=ad.create();
                // Environment.getExternalStorageDirectory()
                tv_pdf.setText("Path:" +Environment.getExternalStorageDirectory()+"/Rapports_LajanPam/"+ pdfname);
               // a.setMessage("Path:" +Environment.getExternalStorageDirectory()+"/Rapports_LajanPam/"+ pdfname);
                a.show();


            } catch (Exception e) {
                Toast.makeText(accueil.this, e.getMessage(), Toast.LENGTH_SHORT).show();

            }
        }

    }

     class Dologin extends AsyncTask {
        MySqlLiteOpenHelper accesBd1;
        SQLiteDatabase bd;
        Tab_semaine Ts;


        public Dologin(Context context){
            accesBd1 = new MySqlLiteOpenHelper(context,"bdDepansMwen.sqlite", null, 10);
            bd = accesBd1.getReadableDatabase();
          //  Toast.makeText(accueil.this,"Dologin",Toast.LENGTH_SHORT).show();

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
                }while (cursor.moveToPrevious());
            }
            cursor.close();

            return null;
        }


    }


}
