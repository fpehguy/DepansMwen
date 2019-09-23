package com.example.depansmwen;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import java.util.ArrayList;

public class Tab_mois extends Fragment {


  AccesLocal accesLocal;

  private ArrayList<InformationToday> allInformationsToday = new ArrayList<>();
  private InformationTodayAdapter monAdapter;
  RecyclerView recyclerView1;
  LinearLayoutManager linearLayoutManager;

  @Override
  public View onCreateView(LayoutInflater inflater,ViewGroup container,Bundle savedInstanceState) {
    View rootView   =inflater.inflate(R.layout.tab_mois,container,false);

    accesLocal = new AccesLocal(this.getContext());
    recyclerView1 = (RecyclerView) rootView.findViewById(R.id.IdRecycleView3);
    linearLayoutManager = new LinearLayoutManager(this.getContext());
    recyclerView1.setLayoutManager(linearLayoutManager);
    recyclerView1.setHasFixedSize(true);
    allInformationsToday = accesLocal.ListInformationSemaineFromBd();
    if (allInformationsToday.size() > 0){
      recyclerView1.setVisibility(View.VISIBLE);
      monAdapter = new InformationTodayAdapter(this.getContext(), allInformationsToday);
      recyclerView1.setAdapter(monAdapter);
    }else{
      recyclerView1.setVisibility(View.GONE);
      Toast.makeText(this.getContext(), "Il n'a pas d'enregistrement de depense dans la base de donnees!", Toast.LENGTH_LONG).show();
    }
    return rootView;
  }

  public  void refreshTabMois(){
    allInformationsToday = accesLocal.ListInformationSemaineFromBd();
    if (allInformationsToday.size() > 0){
      recyclerView1.setVisibility(View.VISIBLE);
      monAdapter = new InformationTodayAdapter(this.getContext(), allInformationsToday);
      recyclerView1.setAdapter(monAdapter);
    }else{
      recyclerView1.setVisibility(View.GONE);
      Toast.makeText(this.getContext(), "Il n'a pas d'enregistrement de depense dans la base de donnees pour aujourd'hui!", Toast.LENGTH_LONG).show();
    }
  }
}
