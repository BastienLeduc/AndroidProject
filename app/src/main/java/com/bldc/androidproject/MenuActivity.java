package com.bldc.androidproject;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MenuActivity extends AppCompatActivity {

    private Button btnPlay = null;
    private Button btnScore = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);

        btnPlay = (Button) findViewById(R.id.btn_play);
        btnScore = (Button) findViewById(R.id.btn_scoreFromMenu);
    }

    @Override
    protected void onResume() {
        super.onResume();

        btnPlay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Intent mainActivityIntent = new Intent(MenuActivity.this, MainActivity.class);
                startActivity(mainActivityIntent);

            }
        });

        btnScore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Intent mainActivityIntent = new Intent(MenuActivity.this, ScoreList.class);
                startActivity(mainActivityIntent);

            }
        });
    }
}
