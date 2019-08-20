package com.example.depansmwen;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.TextViewCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.InputType;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import java.io.File;


public class accueil extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.accueil1);
        getSupportActionBar().setTitle("Vue d'ensemble ...");
        //getSupportActionBar().hide();
        SectionsPagerAdapter sectionsPagerAdapter = new SectionsPagerAdapter(this, getSupportFragmentManager());
        ViewPager viewPager = findViewById(R.id.view_pager);
        viewPager.setAdapter(sectionsPagerAdapter);
        TabLayout tabs = findViewById(R.id.tabs);
        tabs.setupWithViewPager(viewPager);
    }


    public class SectionsPagerAdapter extends FragmentPagerAdapter {
        public SectionsPagerAdapter(accueil mainActivity, FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            switch(position){
                case 0:
                    Tab_aujourdhui tab_cat = new Tab_aujourdhui();
                    return tab_cat;
                case 1:
                    Tab_semaine tab_sem = new Tab_semaine();
                    return tab_sem;
                case 2:
                    Tab_mois tab_m = new Tab_mois();
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
                    return "AUJOURD'HUI";
                case 1:
                    return "SEMAINE";
                case 2:
                    return "MOIS";

            }
            return null;
        }
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        //noinspection SimplifiableIfStatement
        if (id == R.id.adddepense) {

            Toast.makeText(this,"Ajouter Depense",Toast.LENGTH_SHORT).show();
            startActivity(new Intent(this,Depense.class));
            return true;

        }

        if (id == R.id.apropos) {

//            Toast.makeText(this,"A Propos",Toast.LENGTH_SHORT).show();

                    final AlertDialog.Builder apropos = new AlertDialog.Builder(this);
                 apropos.setTitle("A Propos");
                 apropos.setMessage("Cette Application a été developpé par 6 Developpeurs de l’université INUKA.");
//                    final EditText input=new EditText(this);
//                    input.setInputType(InputType.TYPE_CLASS_TEXT);
//                    newFolderDialog.setView(input);


            apropos.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {

                        }
                    });
//                apropos.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
//                        @Override
//                        public void onClick(DialogInterface dialog, int which) {
//                            dialog.cancel();
//                        }
//                    });
            apropos.show();




            return true;

        }

        if (id == R.id.parametre) {

            Toast.makeText(this,"Parametre",Toast.LENGTH_SHORT).show();
            startActivity(new Intent(this,Parametre.class));
            return true;

        }

        return super.onOptionsItemSelected(item);
    }

}
