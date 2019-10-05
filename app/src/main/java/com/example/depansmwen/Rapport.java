package com.example.depansmwen;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;

import java.util.List;

public class Rapport extends AppCompatActivity {

    AccesLocal accesLocal;
    Spinner spincompte;
    Spinner  spincat;
    TextView tv_depenseTcatTcompte,tv_gain,tv_depense_jour,tv_depense_semaine,tv_gainCat;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.rapportetstat);
        accesLocal = new AccesLocal(Rapport.this);
        loadSpinnerDataForRapport();

        getSupportActionBar();//.setTitle("Rapports");

        tv_depenseTcatTcompte=findViewById(R.id.tv_depenseTCatTcompte);
         tv_gain=findViewById(R.id.tv_gain);
        tv_gainCat=findViewById(R.id.tv_gainCat);
        tv_depense_jour=findViewById(R.id.tv_depense_jour);
        tv_depense_semaine=findViewById(R.id.tv_depense_semaine);
        final Spinner spingrouppar=findViewById(R.id.spingrouppar);
        final Spinner spincompte=findViewById(R.id.spincompte);
        final Spinner spincat=findViewById(R.id.spincat);

        spingrouppar.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                String cat = spincat.getSelectedItem().toString();
                String group = spingrouppar.getSelectedItem().toString();
                String  CompteSelected = spincompte.getSelectedItem().toString();
                loadPrix(cat,group,CompteSelected);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        spincat.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                String cat = spincat.getSelectedItem().toString();
                String group = spingrouppar.getSelectedItem().toString();
                String  CompteSelected = spincompte.getSelectedItem().toString();
                loadPrix(cat,group,CompteSelected);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        spincompte.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                String cat = spincat.getSelectedItem().toString();
                String group = spingrouppar.getSelectedItem().toString();
                String  CompteSelected = spincompte.getSelectedItem().toString();
                loadPrix(cat,group,CompteSelected);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

    }

    private void loadPrix(String categorie,String group,String CompteSelected) {
        // depans ki fet pandan jour / cate/ compte
        double prixTJourCatetCompte=accesLocal.rapportJourCat(categorie,CompteSelected);  // par jour ou par semaine
        double prixTSemaineCatetCompte=accesLocal.rapportSemaineTotalCat(categorie,CompteSelected);  // par jour ou par semaine
        double soldeCompte=accesLocal.SoldeCompte(CompteSelected);
//        double PrixTotal =accesLocal.rapportJourSemaineMoisTotal();
        double GainTotal =accesLocal.SoldeCompte(CompteSelected);
        double tv_gain1 =accesLocal.SoldeAllCompte();
        tv_gain.setText(String.valueOf(tv_gain1)+" HTG");

        tv_depense_jour.setText(String.valueOf(prixTJourCatetCompte)+" HTG");
        tv_depense_semaine.setText(String.valueOf(prixTSemaineCatetCompte)+" HTG");
        tv_gainCat.setText(String.valueOf(GainTotal)+" HTG");

        // depans ki fet pandan jour / tout cate/ toutcompte, tout depans sou kelkeswa categorie ak compte lan
        double d=accesLocal.rapportJourTcomptTCat();
        tv_depenseTcatTcompte.setText(String.valueOf(d));





//        tv_depense.setText(String.valueOf(prixTJourCatetCompte)+" TotalJoucatetcompte");
//        tv_gain.setText(String.valueOf(soldeCompte)+" compte select");


    }

    private void loadSpinnerDataForRapport() {
        spincat= findViewById(R.id.spincat);
        spincompte= findViewById(R.id.spincompte);

        List<String> listCat=accesLocal.getAllSpinners1();
        List<String> listcompte=accesLocal.getAllSpinnerscompte2();

    //accesLocal.getAllSpinners();
      //  List<String> listCompte=accesLocal.getAllSpinners();
        // Creating adapter for spinner
        ArrayAdapter<String> dataAdaptercat = new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item, listCat);
        ArrayAdapter<String> dataAdaptercompte = new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item, listcompte);



        // Drop down layout style -
        dataAdaptercat.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        dataAdaptercompte.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);


        // attaching data adapter to spinner
        spincat.setAdapter(dataAdaptercat);
        spincompte.setAdapter(dataAdaptercompte);
    }
}
