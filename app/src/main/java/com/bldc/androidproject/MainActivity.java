package com.bldc.androidproject;

import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private ArrayList<Bille> listBille;
    private final int nbBille = 33;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        listBille = new ArrayList<>();
    }

    private void createBille() {
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        for(int i=0; i<nbBille ; i++) {
            listBille.add(new Bille());
            ft.add(R.id.gridbilles,listBille.get(i));
        }
        ft.commit();
    }
}
