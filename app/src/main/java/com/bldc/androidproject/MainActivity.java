package com.bldc.androidproject;


import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;
import android.media.MediaPlayer;
import android.os.Handler;

public class MainActivity extends AppCompatActivity {


    private MediaPlayer music;
    private Handler handler = new Handler();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        music = (MediaPlayer) MediaPlayer.create(this ,R.raw.plateaumusic);
        music.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
            @Override
            public void onPrepared(MediaPlayer mediaPlayer) {
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        music.start();
                    }
                });
            }
        });
        music.setLooping(true);
    }

    @Override
    protected void onResume() {
        super.onResume();
        music.start();
    }

    @Override
    protected void onStart() {
        super.onStart();
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        String namePlayer = prefs.getString("NamePlayer", null);
        if (namePlayer != null) {
            FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
            ft.add(R.id.linearMain, new Plateau(namePlayer));
            ft.commit();
        } else {
            Toast.makeText(getApplicationContext(), "Error name Player", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onRestart(){
        super.onRestart();
        music.start();
    }

    @Override
    public void onPause() {
        super.onPause();
        music.pause();
    }

    @Override
    public void onDestroy(){
        super.onDestroy();
        music.stop();
        music.release();
    }
}
