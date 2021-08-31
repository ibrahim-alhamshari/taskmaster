package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        setUserInfo();

        Button addABook = findViewById(R.id.addBook);
        Button addYourAge = findViewById(R.id.addYourAge);
        Button showOneTaskButton = findViewById(R.id.showOneTask);
        Button settingsSaveButton = findViewById(R.id.homePageSettingsButton);

        addABook.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent goToTaskDetail = new Intent(MainActivity.this , AddTask.class);
                //append a value to intent
//                goToTaskDetail.putExtra("title", "ADD A BOOK");
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

    private void setUserInfo() {

        ArrayList<TaskModel> taskModelList = new ArrayList<>();

        taskModelList.add(new TaskModel("Sleeping" , "I will sleep after the breakfast", "complete"));
        taskModelList.add(new TaskModel("Playing" , "I will play fotball on 6:00pm", "in progress"));
        taskModelList.add(new TaskModel("Shopping" , "Planning to go to the market with my friend", "assigned"));
        taskModelList.add(new TaskModel("Cocking" , "You will make the lunch tomorrow", "new"));

        // get the recycler view
        RecyclerView allTestsRecycleView= findViewById(R.id.TestListRecyclerView3);
        // set a layout manager for this view
        allTestsRecycleView.setLayoutManager(new LinearLayoutManager(this));
        // set the adapter for this recyclerView
        allTestsRecycleView.setAdapter(new Adapter(taskModelList));
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