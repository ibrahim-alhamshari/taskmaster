package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;
import androidx.room.Room;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.amplifyframework.AmplifyException;
import com.amplifyframework.api.aws.AWSApiPlugin;
import com.amplifyframework.api.graphql.model.ModelMutation;
import com.amplifyframework.core.Amplify;
import com.amplifyframework.datastore.generated.model.GeneratedTaskModel;
import com.amplifyframework.datastore.generated.model.Team;
import com.example.myapplication.Database.TaskDatabase;

import java.util.ArrayList;
import java.util.List;

public class AddTask extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_task);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        final String[] teamName = {""};


        Spinner spinner=findViewById(R.id.spinnerButton);

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
                Toast.makeText(getApplicationContext(), text, Toast.LENGTH_LONG).show();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        EditText title = findViewById(R.id.titleInAddTask);
        EditText body = findViewById(R.id.bodyInAddTask);
        EditText state = findViewById(R.id.stateInAddTask);

        Button submitButton = findViewById(R.id.submitInAddDish);

        submitButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                Toast.makeText(getApplicationContext(),"submitted!" , Toast.LENGTH_LONG).show();
                saveNewTask(title.getText().toString() , body.getText().toString(), state.getText().toString() , teamName[0]);
                Intent intent = new Intent(AddTask.this, MainActivity.class);
                startActivity(intent);
            }
        });
    }


    private void saveNewTask(String title , String body , String state , String teamName){

        Team team = Team.builder().name(teamName).build();
        GeneratedTaskModel generatedTaskModel = GeneratedTaskModel.builder()  //creating the tasks instance
                    .taskName(title)
                    .body(body)
                    .state(state)
                    .team(team)
                    .build();

        Amplify.API.mutate( // create or update on the database
                ModelMutation.create(team),
                response -> Log.i("MyAmplifyApp", "Added task with id: " + response.getData().getId()),
                error -> Log.e("MyAmplifyApp", "Create failed", error)
        );

            Amplify.API.mutate( // create or update on the database
                    ModelMutation.create(generatedTaskModel),
                    response -> Log.i("MyAmplifyApp", "Added task with id: " + response.getData().getId()),
                    error -> Log.e("MyAmplifyApp", "Create failed", error)
            );

    }

}