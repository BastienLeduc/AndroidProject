package com.bldc.androidproject;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
public class MainActivity extends AppCompatActivity {


    Intent intentService = null;

    private Plateau plateau;
    private FragmentTransaction ft;

    /***
     * Activity creation, add plateau fragment on activty
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        intentService = new Intent(this, MyService.class);

        ft = getSupportFragmentManager().beginTransaction();
        plateau = new Plateau();
        ft.add(R.id.linearMain, plateau);
        ft.commit();
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    /**
     * Start service
     */
    @Override
    protected void onStart() {
        super.onStart();
        startService(intentService);
    }

    /***
     * Stop service
     */
    @Override
    protected void onStop() {
        super.onStop();
        stopService(intentService);
    }

}
