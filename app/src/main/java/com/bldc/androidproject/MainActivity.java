package com.bldc.androidproject;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    private ViewPager viewPager = null;
    private TabLayout tableLayout = null;
    private SimpleFragmentPagerAdapter adapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        viewPager = (ViewPager) findViewById(R.id.viewerPager);
        tableLayout = (TabLayout) findViewById(R.id.tabs);
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onStart() {
        super.onStart();
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        String namePlayer = prefs.getString("NamePlayer", null);
        if (namePlayer != null) {
            adapter = new SimpleFragmentPagerAdapter(this, getSupportFragmentManager(), namePlayer);
            viewPager.setOffscreenPageLimit(adapter.getCount() - 1);
            viewPager.setAdapter(adapter);
            tableLayout.setupWithViewPager(viewPager);
        } else {
            Toast.makeText(getApplicationContext(), "Error name Player", Toast.LENGTH_SHORT).show();
        }
    }
}
