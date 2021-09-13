package com.example.myapplication.Tasks;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import com.example.myapplication.R;

public class TaskDetail extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_task_detail);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        Intent intent = getIntent();
        String title = intent.getStringExtra("title");
        String body = intent.getStringExtra("body");
        String state= intent.getStringExtra("state");
        TextView textTitle = findViewById(R.id.taskDetailsTitle);
        TextView textBody = findViewById(R.id.taskDetailsBody);
        TextView textState=findViewById(R.id.taskDetailsState);

        textTitle.setText(title);
        textBody.setText(body);
        textState.setText(state);
    }
}