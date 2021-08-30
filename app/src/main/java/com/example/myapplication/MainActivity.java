package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

 
        Button addTaskButton = findViewById(R.id.button);
        Button allTasksButton = findViewById(R.id.button2);
        Button showOneTaskButton = findViewById(R.id.showOneTask);

        addTaskButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent goToTaskDetail = new Intent(MainActivity.this , TaskDetail.class);
                //append a value to intent
                goToTaskDetail.putExtra("title", "ADD TASK");
                startActivity(goToTaskDetail);
            }
        });

        allTasksButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(MainActivity.this, TaskDetail.class);
                intent.putExtra("title", "ALL TASKS");
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


    }
}