package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class Settings extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        final String[] teamName = {""};

        Button saveDataButton = findViewById(R.id.settingsSaveButton);

        saveDataButton.setOnClickListener(view -> {
            Toast.makeText(getApplicationContext(),"saved!" + " and the team is: " + teamName[0], Toast.LENGTH_LONG).show();

            SharedPreferences sharedPreferences= PreferenceManager.getDefaultSharedPreferences(Settings.this);
            SharedPreferences.Editor sharedPreferenceEditor = sharedPreferences.edit();

            EditText userNameFromField = findViewById(R.id.userNameFromSettings);
            String userName = userNameFromField.getText().toString();

            sharedPreferenceEditor.putString("userName" , userName);
            sharedPreferenceEditor.putString("teamName" , teamName[0]);

            sharedPreferenceEditor.apply();
            Intent intent = new Intent(Settings.this, MainActivity.class);
            startActivity(intent);
        });




        Spinner spinner=findViewById(R.id.spinerInSettingPage);

        List<String> list=new ArrayList<>();
        list.add("Irbid");
        list.add("Amman");
        list.add("Jarash");
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, list);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int position, long l) {
                spinner.setSelection(position);
                String text = adapterView.getItemAtPosition(position).toString();
                teamName[0] =text;
//                Toast.makeText(getApplicationContext(), text, Toast.LENGTH_LONG).show();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
    }
}