package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

public class AllTasks extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_all_tasks);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
//        Intent intent = getIntent();
//        String title = intent.getExtras().getString("Title");
//        String description = intent.getExtras().getString("Description");



    }
}