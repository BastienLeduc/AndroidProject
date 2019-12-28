package com.bldc.androidproject;

import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.GridLayout;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private ArrayList<Case> listCase;
    private final int nbCase = 49;
    private GridLayout gridCase = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        listCase = new ArrayList<>();
        gridCase = (GridLayout)  findViewById(R.id.gridCase);
        gridCase.setColumnCount(7);
        gridCase.setRowCount(7);
        createLigneCase();
    }

    private void createLigneCase() {
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        for(int i=0; i<nbCase ; i++) {
            listCase.add(new Case());
            ft.add(R.id.gridCase,listCase.get(i));
        }
        ft.commit();
    }
}
