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

        addTaskButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                Toast.makeText(getApplicationContext(),"The button was clicked" , Toast.LENGTH_LONG).show();

                //move from(MainActivity.this) to (profile1.class) which is a new activity using an intent
                Intent goToProfile = new Intent(MainActivity.this , AddTask.class);
                //append a value to intent
//                goToProfile.putExtra("userName", userNameValue);

                startActivity(goToProfile);
            }
        });

        allTasksButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(MainActivity.this, AllTasks.class);
                startActivity(intent);

            }
        });

    }
}