package com.bldc.androidproject;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.bldc.androidproject.Entite.Score;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;

public class ScoreList extends AppCompatActivity {

    private ArrayList<Score> listScore;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scorelist);


    }

    public void displayScore() {

        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        SharedPreferences.Editor editor = prefs.edit();
        Gson gson;
        String json;
        Type type;
        gson = new Gson();
        json = prefs.getString("ListScore", "");
        type = new TypeToken<ArrayList<Score>>() {
        }.getType();
        listScore = gson.fromJson(json, type);

        if (listScore != null) {

            FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
            NewScoreFragment nsf = new NewScoreFragment("Position", "Nom", "Score", "Timer");
            ft.add(R.id.list_score, nsf);
            for (int i = 0; i < listScore.size(); i++) {
                ft.add(R.id.list_score, new NewScoreFragment(listScore.get(i).getPosition() + "", listScore.get(i).getName(), listScore.get(i).getScore() + "", listScore.get(i).getTimer() + ""));

            }
            ft.commit();
        }
    }

    @Override
    public void onBackPressed() {
        finish();
    }

    @Override
    public void onResume() {
        super.onResume();
        displayScore();
    }


}
