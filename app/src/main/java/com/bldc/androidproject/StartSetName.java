package com.bldc.androidproject;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class StartSetName extends AppCompatActivity {

    Button btnPlay = null;
    EditText editName = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start_set_name);

        btnPlay = (Button) findViewById(R.id.btn_play);
        editName = (EditText) findViewById(R.id.editName);

        btnPlay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!editName.getText().toString().isEmpty()) {
                    finish();
                    final Intent mainActivityIntent = new Intent(StartSetName.this, MainActivity.class);
                    mainActivityIntent.putExtra("NamePlayer", editName.getText().toString());
                    startActivity(mainActivityIntent);
                } else {
                    Toast.makeText(getApplicationContext(), "Saisir un nom", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
    }
}
