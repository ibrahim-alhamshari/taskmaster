package com.example.myapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.room.Room;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.amplifyframework.AmplifyException;
import com.amplifyframework.api.aws.AWSApiPlugin;
import com.amplifyframework.api.graphql.model.ModelQuery;
import com.amplifyframework.core.Amplify;
import com.amplifyframework.datastore.AWSDataStorePlugin;
import com.amplifyframework.datastore.generated.model.GeneratedTaskModel;
import com.example.myapplication.Database.TaskDao;
import com.example.myapplication.Database.TaskDatabase;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private final List<GeneratedTaskModel> taskList = new ArrayList<>();
    private RecyclerView recyclerView;
    String teamNameFromSetting = null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        try {
            Amplify.addPlugin(new AWSApiPlugin());
            Amplify.addPlugin(new AWSDataStorePlugin());
            Amplify.configure(getApplicationContext());
            Log.i("Tutorial", "Initialized Amplify");
        } catch (AmplifyException failure) {
            Log.e("Tutorial", "Could not initialize Amplify", failure);
        }

        Amplify.DataStore.observe(GeneratedTaskModel.class,
                started -> Log.i("Tutorial", "Observation began."),
                change -> Log.i("Tutorial", change.item().toString()),
                failure -> Log.e("Tutorial", "Observation failed.", failure),
                () -> Log.i("Tutorial", "Observation complete.")
        );


        setAdapter();

        Button addTaskButton = findViewById(R.id.addTaskButton);
        Button settingsSaveButton = findViewById(R.id.homePageSettingsButton);

        addTaskButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent goToTaskDetail = new Intent(MainActivity.this , AddTask.class);
                startActivity(goToTaskDetail);
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

    private void setAdapter(){
        recyclerView= findViewById(R.id.mainPageRecyclerView);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);

        Handler handler = new Handler(Looper.getMainLooper(), new Handler.Callback() {
            @Override
            public boolean handleMessage(@NonNull Message message) {  //It will notify the recyclerview that there are a data changed
                recyclerView.getAdapter().notifyDataSetChanged();
                return false;
            }
        });

        Amplify.API.query(
                ModelQuery.list(com.amplifyframework.datastore.generated.model.GeneratedTaskModel.class),
                response -> {

                    for (GeneratedTaskModel todo : response.getData()) {
                        Log.i("MyAmplifyApp", todo.getTaskName());
//                        System.out.println("===================================================+: " + teamNameFromSetting.getClass().getSimpleName());
//                        System.out.println(" ======================================: " + todo.getTeam().getName().getClass().getSimpleName());
                        if(teamNameFromSetting.equals(todo.getTeam().getName())){
                        taskList.add(todo);
                        System.out.println(" ======================================: " + todo.getTeam().getName());
                        }
                    }
                    handler.sendEmptyMessage(1); // send to the handler
                },
                error -> Log.e("MyAmplifyApp", "Query failure", error)
        );

        Adapter adapter= new Adapter(taskList);
        recyclerView.setAdapter(adapter);
    }

    public void getUserAndTeam(){

        TextView userNameText = findViewById(R.id.userNameHomePage);
        TextView teamNameText = findViewById(R.id.textForTeamInMainPage);

        String welcomeMessage = "’s tasks";
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(MainActivity.this);
        String userName = sharedPreferences.getString("userName" , "User" );
        String teamName= sharedPreferences.getString("teamName" , "Team");

        teamNameFromSetting=teamName;
        System.out.println("===================================================: " + teamNameFromSetting);
        userNameText.setText(userName + welcomeMessage);
        teamNameText.setText(teamName+ "'s Tasks");
    }

    @Override
    protected void onStart() {
        super.onStart();
        getUserAndTeam();
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