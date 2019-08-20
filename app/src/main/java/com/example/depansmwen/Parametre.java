package com.example.depansmwen;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.InputType;
import android.view.View;
import android.widget.*;

public class Parametre extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_parametre);
        getSupportActionBar().setTitle("Parametre");

        final TextView tv_ajout_categorie= findViewById(R.id.a_categorie);
        final TextView tv_modif_categorie= findViewById(R.id.m_categorie);
        final TextView tv_suppre_categorie= findViewById(R.id.s_categorie);
        final TextView tv_ajout_compte= findViewById(R.id.s_categorie);

        tv_ajout_categorie.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final AlertDialog.Builder ajoutCategorie = new AlertDialog.Builder(Parametre.this);
                ajoutCategorie.setTitle("Ajouter Categorie");
                    final EditText input=new EditText(Parametre.this);
                    input.setInputType(InputType.TYPE_CLASS_TEXT);
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
        });


    }


}
