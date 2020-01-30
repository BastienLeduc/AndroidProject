package com.bldc.androidproject;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.Toast;

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
        displayScore();
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

        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        NewScoreFragment nsf = new NewScoreFragment("Rang", "Nom", "Score", "Timer", true);
        ft.add(R.id.list_score, nsf);
        if (listScore != null) {
            for (int i = 0; i < listScore.size(); i++) {
                String valueScore = "";
                if (listScore.get(i).getScore() == 0) valueScore = "Perfect";
                else {
                    if (listScore.get(i).getScore() == 1) valueScore = "Win";
                    else valueScore = listScore.get(i).getScore() + "";
                }
                int m = (int) (listScore.get(i).getChrono() / 60000);
                int s = (int) (listScore.get(i).getChrono() - m * 60000) / 1000;
                String mm = m < 10 ? "0" + m : m + "";
                String ss = s < 10 ? "0" + s : s + "";
                ft.add(R.id.list_score, new NewScoreFragment(listScore.get(i).getPosition() + "", listScore.get(i).getName(), valueScore + "", mm + "." + ss + "", false));

            }
        } else {
            Toast.makeText(getApplicationContext(), "Aucun score pour le moment", Toast.LENGTH_SHORT).show();
        }
        ft.commit();
    }

    @Override
    public void onBackPressed() {
        finish();
    }

    @Override
    public void onResume() {
        super.onResume();
    }


}
