package com.example.depansmwen;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class Tab_mois extends Fragment {


    @Override
    public View onCreateView(LayoutInflater inflater,ViewGroup container,Bundle savedInstanceState) {
        View rootView   =inflater.inflate(R.layout.tab_mois,container,false);
        return rootView;
    }
}
