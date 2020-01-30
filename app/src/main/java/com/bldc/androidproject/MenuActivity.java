package com.bldc.androidproject;

import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MenuActivity extends AppCompatActivity {

    private Button btnPlay = null;
    private Button btnScore = null;
    private MediaPlayer pressBtn;
    private MediaPlayer music;
    private Handler handler = new Handler();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);

        btnPlay = (Button) findViewById(R.id.btn_play);
        btnScore = (Button) findViewById(R.id.btn_scoreFromMenu);

        music = (MediaPlayer) MediaPlayer.create(this ,R.raw.menumusic);
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

    private void playPress(){
        pressBtn = (MediaPlayer) MediaPlayer.create(this ,R.raw.menusound);
        pressBtn.start();
        pressBtn.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mediaPlayer) {
                pressBtn.stop();
                pressBtn.release();
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
    }

    @Override
    protected void onResume() {
        super.onResume();

        btnPlay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                playPress();
                final Intent mainActivityIntent = new Intent(MenuActivity.this, MainActivity.class);
                startActivity(mainActivityIntent);

            }
        });

        btnScore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                playPress();
                final Intent mainActivityIntent = new Intent(MenuActivity.this, ScoreList.class);
                startActivity(mainActivityIntent);

            }
        });
        music.start();
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
