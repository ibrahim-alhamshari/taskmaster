package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;
import androidx.room.Room;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

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
            }
        });
    }

    private void saveNewTask(String title , String body , String state){
        TaskDatabase db = Room.databaseBuilder(this, TaskDatabase.class, "task").fallbackToDestructiveMigration().allowMainThreadQueries().build();

       Task task = new Task();
       task.title= title;
       task.body=body;
       task.state=state;

        db.taskDao().insertTask(task);
    }
}