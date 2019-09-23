package com.example.depansmwen;



import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Adapter;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class Tab_aujourdhui extends Fragment {
      private View.OnClickListener listener;
    View v;
    String recherche ="";
    AccesLocal accesLocal;

    private ArrayList<InformationToday> allInformationsToday = new ArrayList<>();
    private InformationTodayAdapter monAdapter;
    RecyclerView recyclerView;
    LinearLayoutManager linearLayoutManager;
    TextView tvAllDepense;
     LinearLayout line,line1;

    @Override
    public View onCreateView(LayoutInflater inflater,ViewGroup container,Bundle savedInstanceState) {
         v   =inflater.inflate(R.layout.tab_aujourdhui,container,false);

        accesLocal = new AccesLocal(this.getContext());
        recyclerView = (RecyclerView) v.findViewById(R.id.IdRecycleView3);

        linearLayoutManager = new LinearLayoutManager(this.getContext());
        recyclerView.setLayoutManager(linearLayoutManager);
        //recyclerView.setHasFixedSize(true);
        allInformationsToday = accesLocal.ListInformationFromBd();
//        AlldepenseToday();
        if (allInformationsToday.size() > 0){
            recyclerView.setVisibility(View.VISIBLE);
            monAdapter = new InformationTodayAdapter(this.getContext(), allInformationsToday);
            recyclerView.setAdapter(monAdapter);
           // AlldepenseToday();
            monAdapter.SetOnItemClickListener(new InformationTodayAdapter.OnItemClickListener() {
                @Override
                public void onItemClick(final int position) {
                    AlertDialog.Builder alertlang=new AlertDialog.Builder(getContext());
                    alertlang.setTitle("Message d'option");
                    alertlang.setIcon(R.drawable.ic_warning_black_24dp);
                    final String[] listItems ={"Supprimer","Voir Details"};
                    ArrayAdapter adat = new ArrayAdapter<String>(getContext(),R.layout.support_simple_spinner_dropdown_item,listItems);
                    // alertlang.setSingleChoiceItems(listItems, -1, new DialogInterface.OnClickListener() {
                    alertlang.setAdapter(adat, new DialogInterface.OnClickListener() {

                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            if(i==0){
                                Boolean delete = accesLocal.deleteDepense(allInformationsToday.get(position).getId());
                                if (delete == true){
                                    allInformationsToday.remove(position);
                                    monAdapter.notifyItemRemoved(position);
                                    refreshTabAujourdhui();

                                }
                                Toast.makeText(getContext(),"Supprimer",Toast.LENGTH_SHORT).show();
                            }
                            else{
                                Toast.makeText(getContext(),"Details",Toast.LENGTH_SHORT).show();
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
                    alertlang.setTitle("Message d'option");
                    alertlang.setIcon(R.drawable.ic_warning_black_24dp);
                    final String[] listItems ={"Supprimer","Details"};
                    ArrayAdapter adat = new ArrayAdapter<String>(getContext(),R.layout.support_simple_spinner_dropdown_item,listItems);
                   // alertlang.setSingleChoiceItems(listItems, -1, new DialogInterface.OnClickListener() {
                    alertlang.setAdapter(adat, new DialogInterface.OnClickListener() {

                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            if(i==0){
                                Boolean delete = accesLocal.deleteDepense(allInformationsToday.get(position).getId());
                            if (delete == true){
                                allInformationsToday.remove(position);
                                monAdapter.notifyItemRemoved(position);
                                refreshTabAujourdhui();
                            }
                            Toast.makeText(getContext(),"Supprimer",Toast.LENGTH_SHORT).show();
                            }
                            else{
                                Toast.makeText(getContext(),"Details",Toast.LENGTH_SHORT).show();
                            }
                            dialogInterface.dismiss();
                        }
                    });
                    AlertDialog dia=alertlang.create();
                    dia.show();

                }
            });

            AlldepenseToday();
        }else{

            recyclerView.setVisibility(View.GONE);
            Toast.makeText(this.getContext(), "N'oubliez pas d'ajouter vos depenses quotidiennes!!", Toast.LENGTH_LONG).show();
        }


    }

    public void AlldepenseToday(){
        Integer all = accesLocal.AllDepenseToday();
       // tvAllDepense.setText("Depense d'aujourd'hui est: "+all);
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
