package com.bldc.androidproject;

import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.GridLayout;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;

public class MainActivity extends AppCompatActivity {

    private Case[] TabCase;
    private final int nbLigneCol = 7;
    private final int nbCase = (int) Math.pow(nbLigneCol, 2);
    Integer[] listValue = {0,1,5,6};
    private final ArrayList<Integer> noneActivCase = new ArrayList<Integer>(Arrays.asList(listValue));
    private GridLayout gridCase = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        TabCase = new Case[nbCase];
        gridCase = (GridLayout) findViewById(R.id.gridCase);
        gridCase.setColumnCount(nbLigneCol);
        gridCase.setRowCount(nbLigneCol);
        createCase();
    }

    private void createCase() {
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        for (int i = 0; i < nbLigneCol; i++) {
            for (int j = 0; j < nbLigneCol; j++) {
                if(noneActivCase.contains(i) && noneActivCase.contains(j))
                    TabCase[i*j] = new Case(false,false);
                else
                    TabCase[i*j] = new Case(true,true);
                ft.add(R.id.gridCase, TabCase[i*j]);
            }
        }
        ft.commit();
    }
}
