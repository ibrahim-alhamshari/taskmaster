package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class Settings extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


        Button saveDataButton = findViewById(R.id.settingsSaveButton);

        saveDataButton.setOnClickListener(view -> {
            Toast.makeText(getApplicationContext(),"saved!" , Toast.LENGTH_LONG).show();

            SharedPreferences sharedPreferences= PreferenceManager.getDefaultSharedPreferences(Settings.this);
            SharedPreferences.Editor sharedPreferenceEditor = sharedPreferences.edit();

            EditText userNameFromField = findViewById(R.id.userNameFromSettings);
            String userName = userNameFromField.getText().toString();

            sharedPreferenceEditor.putString("userName" , userName);
            sharedPreferenceEditor.apply();

        });
    }
}