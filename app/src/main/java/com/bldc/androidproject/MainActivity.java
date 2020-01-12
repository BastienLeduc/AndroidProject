package com.bldc.androidproject;

import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.GridLayout;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;

public class MainActivity extends AppCompatActivity {

    private Case[][] TabCase;
    private final int nbLigneCol = 7;
    private final int nbCase = (int) Math.pow(nbLigneCol, 2);
    Integer[] listValue = {0, 1, 5, 6};
    private final ArrayList<Integer> noneActivCase = new ArrayList<Integer>(Arrays.asList(listValue));
    private GridLayout gridCase = null;
    private int nbCoups;
    private int nbBilles;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        TabCase = new Case[nbLigneCol][nbLigneCol];
        gridCase = (GridLayout) findViewById(R.id.gridCase);
        gridCase.setColumnCount(nbLigneCol);
        gridCase.setRowCount(nbLigneCol);
        createCase();
        nbCoups = 0;
        nbBilles = 0;
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    private void createCase() {
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        for (int i = 0; i < nbLigneCol; i++) {
            for (int j = 0; j < nbLigneCol; j++) {
                if (noneActivCase.contains(i) && noneActivCase.contains(j))
                    TabCase[i][j] = new Case(false, false);
                else {
                    if (i == Math.round(nbLigneCol / 2) && j == Math.round(nbLigneCol / 2)) {
                        TabCase[i][j] = new Case(true, false);
                    } else
                        TabCase[i][j] = new Case(true, true);
                    nbBilles++;
                }
                ft.add(R.id.gridCase, TabCase[i][j]);
            }
        }
        ft.commit();
    }

    private boolean playOneBall(int x1, int y1, int x2, int y2) {
        int valMidX = (x1 + x2) / 2;
        int valMidY = (y1 + y2) / 2;
        if ((x1 == x2 && Math.abs(y1 - y2) == 2) || (y1 == y2 && Math.abs(x1 - x2) == 2)) {
            boolean b = TabCase[valMidX][valMidY].isSeleceted() && !TabCase[x2][y2].isSeleceted();
            if (b) {
                TabCase[x1][y1].setSeleceted(false);
                TabCase[valMidX][valMidY].setSeleceted(false);
                TabCase[x2][y2].setSeleceted(true);
                nbCoups++;
                nbBilles--;
            }
            return b;
        } else return false;

    }

    private int getNbBilleReste() {
        return nbBilles;
    }
}
