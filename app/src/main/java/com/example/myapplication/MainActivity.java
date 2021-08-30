package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
 
        Button addABook = findViewById(R.id.addBook);
        Button addYourAge = findViewById(R.id.addYourAge);
        Button showOneTaskButton = findViewById(R.id.showOneTask);
        Button settingsSaveButton = findViewById(R.id.homePageSettingsButton);

        addABook.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent goToTaskDetail = new Intent(MainActivity.this , TaskDetail.class);
                //append a value to intent
                goToTaskDetail.putExtra("title", "ADD A BOOK");
                startActivity(goToTaskDetail);
            }
        });

        addYourAge.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(MainActivity.this, TaskDetail.class);
                intent.putExtra("title", "ADD YOUR AGE");
                startActivity(intent);

            }
        });

        showOneTaskButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(MainActivity.this, TaskDetail.class);
                intent.putExtra("title", "SHOW ONE TASK");
                startActivity(intent);

            }
        });

        settingsSaveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, Settings.class);
                startActivity(intent);

            }
        });
    }


    @Override
    protected void onStart() {
        super.onStart();

        String welcomeMessage = "’s tasks";
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(MainActivity.this);
        String userName = sharedPreferences.getString("userName" , "User" );

        TextView textView = findViewById(R.id.userNameHomePage);
        textView.setText(userName + welcomeMessage);
    }

    @Override
    protected void onStop() {
        super.onStop();
    }

    @Override
    protected void onPause() {
        super.onPause();



    }

    @Override
    protected void onResume() {
        super.onResume();
    }
}