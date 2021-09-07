package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;
import androidx.room.Room;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.amplifyframework.AmplifyException;
import com.amplifyframework.api.aws.AWSApiPlugin;
import com.amplifyframework.api.graphql.model.ModelMutation;
import com.amplifyframework.core.Amplify;
import com.amplifyframework.datastore.generated.model.GeneratedTaskModel;
import com.example.myapplication.Database.TaskDatabase;

public class AddTask extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_task);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


        EditText title = findViewById(R.id.titleInAddTask);
        EditText body = findViewById(R.id.bodyInAddTask);
        EditText state = findViewById(R.id.stateInAddTask);

        Button submitButton = findViewById(R.id.submitInAddDish);

        submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getApplicationContext(),"submitted!" , Toast.LENGTH_LONG).show();
                saveNewTask(title.getText().toString() , body.getText().toString(), state.getText().toString());
                Intent intent = new Intent(AddTask.this, MainActivity.class);
                startActivity(intent);

            }
        });
    }

    private void saveNewTask(String title , String body , String state){
//        TaskDatabase db = Room.databaseBuilder(this, TaskDatabase.class, "task1").fallbackToDestructiveMigration().allowMainThreadQueries().build();

//       Task task = new Task();
//
//       task.title= title;
//       task.body=body;
//       task.state=state;

        GeneratedTaskModel generatedTaskModel = GeneratedTaskModel.builder()  //creating the tasks instance
                    .taskName(title)
                    .body(body)
                    .state(state)
                    .build();

        System.out.println(title + "=============================================================");

            Amplify.API.mutate( // create or update on the database
                    ModelMutation.create(generatedTaskModel),
                    response -> Log.i("MyAmplifyApp", "Added task with id: " + response.getData().getId()),
                    error -> Log.e("MyAmplifyApp", "Create failed", error)
            );

//        db.taskDao().insertTask(task);



    }
}