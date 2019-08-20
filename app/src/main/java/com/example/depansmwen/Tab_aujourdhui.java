package com.example.depansmwen;



import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import java.util.ArrayList;
import java.util.List;

public class Tab_aujourdhui extends Fragment {
      private View.OnClickListener listener;
    View v;
    ArrayAdapter<String> adt;

    @Override
    public View onCreateView(LayoutInflater inflater,ViewGroup container,Bundle savedInstanceState) {
         v   =inflater.inflate(R.layout.tab_aujourdhui,container,false);

        Load();
        return v;
    }

    public void Load()
    {


        final ListView listview1=v.findViewById(R.id.listview1);
        List<String> list = new ArrayList<>();
        for(int i=0;i<20;i++)
        {
            list.add(String.valueOf("Depense "+i+" En date de 08/08/2019"));
        }
        adt =new ArrayAdapter<>(getActivity(),android.R.layout.simple_list_item_1,list);
        listview1.setAdapter(adt);

    }

}
