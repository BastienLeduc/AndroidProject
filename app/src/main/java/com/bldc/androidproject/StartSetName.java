package com.bldc.androidproject;

import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class StartSetName extends AppCompatActivity {

    Button btnNext = null;
    EditText editName = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start_set_name);

        btnNext = (Button) findViewById(R.id.btn_next);
        editName = (EditText) findViewById(R.id.editName);

        btnNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!editName.getText().toString().isEmpty()) {
                    finish();
                    SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
                    SharedPreferences.Editor editor = prefs.edit();
                    editor.putString("NamePlayer", editName.getText().toString());
                    editor.apply();

                    final Intent mainActivityIntent = new Intent(StartSetName.this, MenuActivity.class);
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
