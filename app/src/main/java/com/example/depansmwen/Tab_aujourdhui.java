package com.example.depansmwen;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class Tab_aujourdhui extends Fragment {
      private View.OnClickListener listener;
    View v;
    String recherche ="";
     AccesLocal accesLocal;
    Tab_semaine ts;
    private ArrayList<InformationToday> allInformationsToday = new ArrayList<>();
    private InformationTodayAdapter monAdapter;
    RecyclerView recyclerView;
    LinearLayoutManager linearLayoutManager;
    MainActivity user;


    @Override
    public View onCreateView(LayoutInflater inflater,ViewGroup container,Bundle savedInstanceState) {
         v   =inflater.inflate(R.layout.tab_aujourdhui,container,false);
         ts=new Tab_semaine();

        accesLocal = new AccesLocal(this.getContext());
        recyclerView = (RecyclerView) v.findViewById(R.id.IdRecycleView3);

        linearLayoutManager = new LinearLayoutManager(this.getContext());
        recyclerView.setLayoutManager(linearLayoutManager);
        allInformationsToday = accesLocal.ListInformationFromBd();
        if (allInformationsToday.size() > 0){
            recyclerView.setVisibility(View.VISIBLE);
            monAdapter = new InformationTodayAdapter(this.getContext(), allInformationsToday);
            recyclerView.setAdapter(monAdapter);
            monAdapter.SetOnItemClickListener(new InformationTodayAdapter.OnItemClickListener() {
                @Override
                public void onItemClick(final int position) {
                  final  AlertDialog.Builder alertlang=new AlertDialog.Builder(getContext());
                    alertlang.setTitle(R.string.messageoption);
                    alertlang.setIcon(R.drawable.ic_warning_black_24dp);
                    String sup=getResources().getString(R.string.text_suppimer);
                    String det=getResources().getString(R.string.text_voirdetail);
                    final String[] listItems ={sup,det};
                    ArrayAdapter adat = new ArrayAdapter<String>(getContext(),R.layout.support_simple_spinner_dropdown_item,listItems);
                    // alertlang.setSingleChoiceItems(listItems, -1, new DialogInterface.OnClickListener() {
                    alertlang.setAdapter(adat, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            if(i==0){
                                final View v = getLayoutInflater().inflate(R.layout.lay_veuxtudelete,null);
                                AlertDialog.Builder ad=new AlertDialog.Builder(getContext());
                                Button btnnon = v.findViewById(R.id.btn_non_delete);
                                Button btnoui = v.findViewById(R.id.btn_oui_delete);
                                ad.setView(v);
                                final AlertDialog a=ad.create();
                                btnoui.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View view) {
                                        String compte = allInformationsToday.get(position).getDevise();
                                        String soldeDep = allInformationsToday.get(position).getMontant();
                                        String soldeCompte = accesLocal.getSoldecompte(compte,user.userName());
                                        Double soldeDev=Double.parseDouble(soldeCompte)+Double.parseDouble(soldeDep);
                                        Boolean delete = accesLocal.deleteDepense(allInformationsToday.get(position).getId());
                                        if (delete == true) {
                                            accesLocal.UpdateSolde(soldeDev,compte);
                                            allInformationsToday.remove(position);
                                            monAdapter.notifyItemRemoved(position);
                                            refreshTabAujourdhui();
                                            a.cancel();
                                            Toast.makeText(getContext(),R.string.text_suppimer,Toast.LENGTH_SHORT).show();
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
                            else{
                                final View v = getLayoutInflater().inflate(R.layout.details,null);
                                AlertDialog.Builder ad=new AlertDialog.Builder(getContext());

                                TextView tx_prix1 = v.findViewById(R.id.tx_prix1);
                                TextView tx_cat1 = v.findViewById(R.id.tx_cat1);
                                TextView tx_compte1 = v.findViewById(R.id.tx_compte1);
                                TextView tx_date1 = v.findViewById(R.id.tx_date1);
                                TextView tx_note1 = v.findViewById(R.id.tx_note1);

                                ad.setView(v);
                                final AlertDialog a=ad.create();
                                // pick up les infos et les Afficher dans la listview
                                int id=allInformationsToday.get(position).getId();
                                String Prix=allInformationsToday.get(position).getMontant()+" HTG";
                                String Categorie=allInformationsToday.get(position).getCategorie();
                                String Compte=allInformationsToday.get(position).getDevise();
                                String Note=allInformationsToday.get(position).getNote();
                                String date=allInformationsToday.get(position).getDate();
                                tx_prix1.setText(Prix);
                                tx_cat1.setText(Categorie);
                                tx_compte1.setText(Compte);
                                tx_date1.setText(date);
                                if(Note.length() > 50)
                                    tx_note1.setText(Note.substring(0,50));
                                else tx_note1.setText(Note);
                                a.show();
                            }
                            dialogInterface.dismiss();
                        }
                    });
                    AlertDialog dia=alertlang.create();
                    dia.show();
                }
            });
       }
        else{
            recyclerView.setVisibility(View.GONE);//
        }
        return v;
    }

    public  void refreshTabAujourdhui(){
        accesLocal = new AccesLocal(this.getContext());
        recyclerView = (RecyclerView) v.findViewById(R.id.IdRecycleView3);
        linearLayoutManager = new LinearLayoutManager(this.getContext());
        recyclerView.setLayoutManager(linearLayoutManager);
        allInformationsToday = accesLocal.ListInformationFromBd();
        if (allInformationsToday.size() > 0){

            recyclerView.setVisibility(View.VISIBLE);

            monAdapter = new InformationTodayAdapter(this.getContext(), allInformationsToday);
            recyclerView.setAdapter(monAdapter);
            //AlldepenseToday();
            monAdapter.SetOnItemClickListener(new InformationTodayAdapter.OnItemClickListener() {
                @Override
                public void onItemClick(final int position) {
                    AlertDialog.Builder alertlang=new AlertDialog.Builder(getContext());
                    alertlang.setTitle(R.string.messageoption);
                    alertlang.setIcon(R.drawable.ic_warning_black_24dp);

                    String sup=getResources().getString(R.string.text_suppimer);
                    String det=getResources().getString(R.string.text_voirdetail);
                    final String[] listItems ={sup,det};
                    final ArrayAdapter adat = new ArrayAdapter<String>(getContext(),R.layout.support_simple_spinner_dropdown_item,listItems);
                   // alertlang.setSingleChoiceItems(listItems, -1, new DialogInterface.OnClickListener() {
                    alertlang.setAdapter(adat, new DialogInterface.OnClickListener() {

                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            if(i==0){
                                final View v = getLayoutInflater().inflate(R.layout.lay_veuxtudelete,null);
                                AlertDialog.Builder ad=new AlertDialog.Builder(getContext());
                                Button btnnon = v.findViewById(R.id.btn_non_delete);
                                Button btnoui = v.findViewById(R.id.btn_oui_delete);
                                ad.setView(v);
                                final AlertDialog a=ad.create();
                                btnoui.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View view) {
                                        String compte = allInformationsToday.get(position).getDevise();
                                        String soldeDep = allInformationsToday.get(position).getMontant();
                                        String soldeCompte = accesLocal.getSoldecompte(compte,user.userName());
                                        Double soldeDev=Double.parseDouble(soldeCompte)+Double.parseDouble(soldeDep);
                                        Boolean delete = accesLocal.deleteDepense(allInformationsToday.get(position).getId());
                                        if (delete == true) {
                                            accesLocal.UpdateSolde(soldeDev,compte);
                                            allInformationsToday.remove(position);
                                            monAdapter.notifyItemRemoved(position);
                                            refreshTabAujourdhui();

                                            a.cancel();
                                            Toast.makeText(getContext(),R.string.text_suppimer,Toast.LENGTH_SHORT).show();
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
                            else{
                                final View v = getLayoutInflater().inflate(R.layout.details,null);
                                AlertDialog.Builder ad=new AlertDialog.Builder(getContext());

                                TextView tx_prix1 = v.findViewById(R.id.tx_prix1);
                                TextView tx_cat1 = v.findViewById(R.id.tx_cat1);
                                TextView tx_compte1 = v.findViewById(R.id.tx_compte1);
                                TextView tx_date1 = v.findViewById(R.id.tx_date1);
                                TextView tx_note1 = v.findViewById(R.id.tx_note1);

                                ad.setView(v);
                                final AlertDialog a=ad.create();
                                // pick up les infos et les Afficher dans la listview
                                int id=allInformationsToday.get(position).getId();
                                String Prix=allInformationsToday.get(position).getMontant()+" HTG";
                                String Categorie=allInformationsToday.get(position).getCategorie();
                                String Compte=allInformationsToday.get(position).getDevise();
                                String Note=allInformationsToday.get(position).getNote();
                                String date=allInformationsToday.get(position).getDate();
                                tx_prix1.setText(Prix);
                                tx_cat1.setText(Categorie);
                                tx_compte1.setText(Compte);
                                tx_date1.setText(date);
                                if(Note.length() > 50)
                                    tx_note1.setText(Note.substring(0,50));
                                else tx_note1.setText(Note);
                                a.show();
                            }
                            dialogInterface.dismiss();
                        }
                    });
                    AlertDialog dia=alertlang.create();
                    dia.show();

                }
            });


        }else{
            recyclerView.setVisibility(View.GONE);
        }


    }

    public void AlldepenseToday(){
        Integer all = accesLocal.AllDepenseToday();
    }

    public void GestionRecherche(){
        if (monAdapter != null){
            monAdapter.getFilter().filter(recherche);
        }
    }

    public String textRecherche(String txtRecherche){
         recherche = txtRecherche;
         return txtRecherche;
    }

}
