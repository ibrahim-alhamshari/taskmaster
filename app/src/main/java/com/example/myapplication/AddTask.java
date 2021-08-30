package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class AddTask extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_task);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        EditText editText1 = findViewById(R.id.editTextTextPersonName);
        String storingTitle= editText1.getText().toString();

        EditText editText2 = findViewById(R.id.editTextTextPersonName2);
        String storingDescriptio = editText2.getText().toString();

        final int[] counter = {0};
        Button button= findViewById(R.id.button3);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Toast.makeText(getApplicationContext(),"submitted!" , Toast.LENGTH_LONG).show();
//
//                Intent goToAllTasks = new Intent(AddTask.this , AllTasks.class );
//                goToAllTasks.putExtra("Title" , storingTitle);
//                goToAllTasks.putExtra("Description" , storingDescriptio);
//
//                counter[0]++;
//
//
//                EditText totalTasks = findViewById(R.id.totalTasksValue);
//                totalTasks.setText(counter);
            }
        });


//        //getting the sending data
//        Intent intent = getIntent();
//        // store the data inside a string
//        String data = intent.getStringExtra("userName");
//
//        //get the element where you are going to put the sending data inside it
//        TextView textView = findViewById(R.id.textView2);
//        //give the element the data that you send
//        textView.setText(data);

    }
}