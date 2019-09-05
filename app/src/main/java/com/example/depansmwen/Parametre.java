package com.example.depansmwen;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.InputType;
import android.view.View;
import android.widget.*;

import java.util.ArrayList;
import java.util.List;


public class Parametre extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_parametre);
        getSupportActionBar().setTitle("Parametre");


        List<String> list = new ArrayList<>();
        List<String> list1 = new ArrayList<>();
        list.add("Rappel en fin de journee");
        list1.add("Devise");


        final ListView list_cat= findViewById(R.id.list_cat);
        final ListView list_param= findViewById(R.id.list_param1);
        final ListView param= findViewById(R.id.param);

       // ArrayAdapter na = new ArrayAdapter(Parametre.this, R.layout.param_gene, list);
        ArrayAdapter na = new ArrayAdapter(Parametre.this,android.R.layout.simple_list_item_checked,list);
        ArrayAdapter na1 = new ArrayAdapter(Parametre.this,android.R.layout.simple_list_item_1,list1);
        list_param.setAdapter(na1);
        param.setAdapter(na);



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







    }

    private void Supprimer_categorie() {

        final AlertDialog.Builder msupressCategorie = new AlertDialog.Builder(Parametre.this);
        msupressCategorie.setTitle("Supprimer une  Categorie");
        View view=getLayoutInflater().inflate(R.layout.activity_spinner_cat_supress,null);

        final  Spinner spinner=view.findViewById(R.id.spinner);
        ArrayAdapter<String> adapter=new ArrayAdapter<String>(Parametre.this,android.R.layout.simple_spinner_item,
                getResources().getStringArray(R.array.compte));
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);

        msupressCategorie.setNegativeButton("Annuler", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });

        msupressCategorie.setView(view);
        final AlertDialog dialog=msupressCategorie.create();
        dialog.show();

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener()
        {

            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if(!(spinner.getSelectedItem().toString().equalsIgnoreCase("Select une Categorie"))) {

                    final AlertDialog.Builder msupression = new AlertDialog.Builder(Parametre.this);
                    msupression.setTitle("Voulez-vous supprimer la categorie "+spinner.getSelectedItem().toString()+" ?");

                    msupression.setPositiveButton("Oui", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog1, int which) {
                            Toast.makeText(Parametre.this,"La categorie "+spinner.getSelectedItem().toString()
                                            +" a ete bien supprime !",
                                    Toast.LENGTH_SHORT)
                                    .show();

                            dialog.cancel();
                        }
                    });
                    msupression.setNegativeButton("Non", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.cancel();
                        }
                    });

                    msupression.show();

                }

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    private void Modifier_categorie() {

        final AlertDialog.Builder modifCategorie = new AlertDialog.Builder(Parametre.this);
        modifCategorie.setTitle("Modifier une  Categorie");
        View view=getLayoutInflater().inflate(R.layout.activity_spinner_cat,null);

        final  Spinner spinner=view.findViewById(R.id.spinner);
        ArrayAdapter<String> adapter=new ArrayAdapter<String>(Parametre.this,android.R.layout.simple_spinner_item,
                getResources().getStringArray(R.array.compte));
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);

        final EditText edit=view.findViewById(R.id.edit);

        modifCategorie.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });
        modifCategorie.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });


        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener()
        {

            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if(!(spinner.getSelectedItem().toString().equalsIgnoreCase("Select une Categorie"))) {
                    edit.setText(spinner.getSelectedItem().toString());
                }
                else {
                    Toast.makeText(Parametre.this, "Selectionnez une categorie !", Toast.LENGTH_SHORT).show();
                    edit.setText("");
                }

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        modifCategorie.setView(view);
        AlertDialog dialog= modifCategorie.create();
        dialog.show();
    }


    public void Ajouter_categorie(){

        final AlertDialog.Builder ajoutCategorie = new AlertDialog.Builder(Parametre.this);
        ajoutCategorie.setTitle("Ajouter une Categorie");
        final EditText input=new EditText(Parametre.this);
        input.setInputType(InputType.TYPE_CLASS_TEXT);
        input.setHint("Saisir le nom...");
        ajoutCategorie.setView(input);
        ajoutCategorie.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

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




}
