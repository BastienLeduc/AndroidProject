package com.bldc.androidproject;

import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.GridLayout;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private Case[] TabCase;
    private final int nbCase = 49;
    private GridLayout gridCase = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        TabCase = new Case[nbCase];
        gridCase = (GridLayout) findViewById(R.id.gridCase);
        gridCase.setColumnCount(7);
        gridCase.setRowCount(7);
        createLigneCase();
    }

    private void createLigneCase() {
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        for (int i = 0; i < 7; i++) {
            for (int j = 0; j < 7; j++) {
                TabCase[i * j] = new Case();
                if (i * j == 0 || i * j == 1 || i * j == 2) TabCase[i * j].setDisable(false);
                ft.add(R.id.gridCase, TabCase[i * j]);
            }
        }
        ft.commit();
    }
}
