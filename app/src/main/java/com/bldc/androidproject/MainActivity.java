package com.bldc.androidproject;

import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private ArrayList<Bille> listLigneBille;
    private final int nbBille = 7;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        listLigneBille = new ArrayList<>();
        createBille();
    }

    private void createBille() {
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        for(int i=0; i<nbBille ; i++) {
            listLigneBille.add(new Bille());
            ft.add(R.id.lineBille,listLigneBille.get(i));
        }
        ft.commit();
    }
}
