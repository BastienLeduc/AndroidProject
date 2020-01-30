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

    /**
     * Activity creation
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scorelist);
        displayScore();
    }

    /***
     * Display score
     */
    public void displayScore() {

        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        SharedPreferences.Editor editor = prefs.edit();
        Gson gson;
        String json;
        Type type;
        gson = new Gson();
        //Deserialize data
        json = prefs.getString("ListScore", "");
        type = new TypeToken<ArrayList<Score>>() {
        }.getType();
        //Set data in ArrayList<Score> format
        listScore = gson.fromJson(json, type);

        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        NewScoreFragment nsf = new NewScoreFragment(getResources().getString(R.string.rang), getResources().getString(R.string.nom), getResources().getString(R.string.score), getResources().getString(R.string.chrono), true);
        ft.add(R.id.list_score, nsf);
        if (listScore != null) {
            for (int i = 0; i < listScore.size(); i++) {
                String valueScore = "";
                if (listScore.get(i).getScore() == 0)
                    valueScore = getResources().getString(R.string.parfait);
                else {
                    if (listScore.get(i).getScore() == 1)
                        valueScore = getResources().getString(R.string.gagne);
                    else valueScore = listScore.get(i).getScore() + "";
                }
                //To display chrono like mm.ss
                int m = (int) (listScore.get(i).getChrono() / 60000);
                int s = (int) (listScore.get(i).getChrono() - m * 60000) / 1000;
                String mm = m < 10 ? "0" + m : m + "";
                String ss = s < 10 ? "0" + s : s + "";
                ft.add(R.id.list_score, new NewScoreFragment(listScore.get(i).getPosition() + "", listScore.get(i).getName(), valueScore + "", mm + "." + ss + "", false));

            }
        } else {
            Toast.makeText(getApplicationContext(), getResources().getString(R.string.error_list_score), Toast.LENGTH_SHORT).show();
        }
        ft.commit();
    }

    /***
     * Close activity on backpressed
     */
    @Override
    public void onBackPressed() {
        finish();
    }

    @Override
    public void onResume() {
        super.onResume();
    }


}
