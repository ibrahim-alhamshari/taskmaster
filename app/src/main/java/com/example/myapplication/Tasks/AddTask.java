package com.example.myapplication.Tasks;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.DocumentsContract;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.amplifyframework.api.graphql.model.ModelMutation;
import com.amplifyframework.api.graphql.model.ModelQuery;
import com.amplifyframework.core.Amplify;
import com.amplifyframework.datastore.generated.model.GeneratedTaskModel;
import com.amplifyframework.datastore.generated.model.Team;
import com.example.myapplication.MainActivity;
import com.example.myapplication.R;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;

public class AddTask extends AppCompatActivity {

    Team teamName = null;
    String fileName;
    private Uri fileData;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_task);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);



        List<Team> teamList = new ArrayList<>();

        Amplify.API.query(
                ModelQuery.list(Team.class),
                response -> {
                    for (Team todo : response.getData()) {
                        Log.i("MyAmplifyApp", todo.getName());
                        System.out.println("000000000000000000000000000000000000000000000000" + todo.getName());
                        teamList.add(todo);
                    }
                },
                error -> Log.e("MyAmplifyApp", "Query failure", error)
        );


        Spinner spinner=findViewById(R.id.spinnerButton);

        List<String> list=new ArrayList<>();
        list.add("Choose one");
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

                for (int i = 0; i < teamList.size(); i++) {
                    if(teamList.get(i).getName().equals(text)){
                        teamName = teamList.get(i);
                        System.out.println(teamName.getName().toString()+ "++++++++++++++++++++++++++++++++++++++++++++++++++++");
                    }
                }

//                teamName[0] =text;
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
                if(teamName!=null){
                    Toast.makeText(getApplicationContext(),"submitted!" , Toast.LENGTH_LONG).show();
                    saveNewTask(title.getText().toString() , body.getText().toString(), state.getText().toString() );
                    Intent intent = new Intent(AddTask.this, MainActivity.class);
                    startActivity(intent);
                }else {
                    Toast.makeText(getApplicationContext(),"Please choose the team!" , Toast.LENGTH_LONG).show();
                }
            }
        });

        Button button = findViewById(R.id.uploadFile);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                pickFile();
            }
        });
    }

    private void pickFile(){
        Intent chooseFile=new Intent(Intent.ACTION_GET_CONTENT);
        chooseFile.setType("*/*");
        chooseFile= Intent.createChooser(chooseFile , "Choose file");
        startActivityForResult(chooseFile , 1235);
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        Uri uri = data.getData(); // where data is the Intent returned in onActivityResult()
        fileData=uri;

        String src = uri.getPath();
        fileName=src;

    }

    private void saveNewTask(String title , String body , String state){

        if (fileData != null) {
            try {
                InputStream inputStream = getContentResolver().openInputStream(fileData);
                Amplify.Storage.uploadInputStream(
                        fileName,
                        inputStream,
                        result -> Log.i("MyAmplifyApp", "Successfully uploaded: " + result.getKey()),
                        storageFailure -> Log.e("MyAmplifyApp", "Upload failed", storageFailure)
                );
            } catch (FileNotFoundException error) {
                Log.e("MyAmplifyApp", "Could not find file to open for input stream.", error);
            }
        }


        GeneratedTaskModel generatedTaskModel = GeneratedTaskModel.builder()  //creating the tasks instance
                    .taskName(title)
                    .body(body)
                    .state(state)
                    .file(fileName)
                    .team(teamName)
                    .build();


            Amplify.API.mutate( // create or update on the database
                    ModelMutation.create(generatedTaskModel),
                    response -> Log.i("MyAmplifyApp", "Added task with id: " + response.getData().getId()),
                    error -> Log.e("MyAmplifyApp", "Create failed", error)
            );

    }

}