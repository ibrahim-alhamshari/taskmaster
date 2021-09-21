package com.example.myapplication.Tasks;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.net.Uri;
import android.os.Bundle;
import android.provider.DocumentsContract;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.amplifyframework.api.graphql.model.ModelMutation;
import com.amplifyframework.api.graphql.model.ModelQuery;
import com.amplifyframework.core.Amplify;
import com.amplifyframework.datastore.generated.model.GeneratedTaskModel;
import com.amplifyframework.datastore.generated.model.Team;
import com.example.myapplication.MainActivity;
import com.example.myapplication.R;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesUtil;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;

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
import java.util.Locale;

public class AddTask extends AppCompatActivity {

    Team teamName = null;
    String fileName;
    private Uri fileData;
    private String bodyFromAnotherApp = null;
    private FusedLocationProviderClient fusedLocationClient;
    private Double latitudeNum;
    private Double longitudeNum;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_task);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this);

        Button getLocation = findViewById(R.id.getLocationButton);
        getLocation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                System.out.println(checkPlayServices() + "============================");

                if (ActivityCompat.checkSelfPermission(AddTask.this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
                    getLocationMethod();
                } else {
                    ActivityCompat.requestPermissions(AddTask.this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 44);
                    getLocationMethod();
                }
            }
        });


        Intent recive = getIntent();
        if (recive.getType() != null && recive.getType().equals("text/plain")) {
            Log.i("Hello world", recive.getExtras().get(Intent.EXTRA_TEXT).toString());
            bodyFromAnotherApp = recive.getExtras().get(Intent.EXTRA_TEXT).toString();
        }

        List<Team> teamList = new ArrayList<>();
        Amplify.API.query(
                ModelQuery.list(Team.class),
                response -> {
                    for (Team todo : response.getData()) {
                        Log.i("MyAmplifyApp", todo.getName());
                        teamList.add(todo);
                    }
                },
                error -> Log.e("MyAmplifyApp", "Query failure", error)
        );

//***************************************************** Spinner start ***********************************************

        Spinner spinner = findViewById(R.id.spinnerButton);

        List<String> list = new ArrayList<>();
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
                    if (teamList.get(i).getName().equals(text)) {
                        teamName = teamList.get(i);
                    }
                }

                Toast.makeText(getApplicationContext(), text, Toast.LENGTH_LONG).show();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
//***************************************************** Spinner End **********************************************

//***************************************************** Get data start *******************************************

        EditText title = findViewById(R.id.titleInAddTask);
        EditText body = findViewById(R.id.bodyInAddTask);
        EditText state = findViewById(R.id.stateInAddTask);
        body.setText(bodyFromAnotherApp);
        Button submitButton = findViewById(R.id.submitInAddDish);

        submitButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                if (teamName != null) {
                    Toast.makeText(getApplicationContext(), "submitted!", Toast.LENGTH_LONG).show();

                    if (bodyFromAnotherApp != null) {
                        saveNewTask(title.getText().toString(), bodyFromAnotherApp, state.getText().toString());
                        bodyFromAnotherApp = null;
                    } else {
                        saveNewTask(title.getText().toString(), body.getText().toString(), state.getText().toString());
                    }

                    Intent intent = new Intent(AddTask.this, MainActivity.class);
                    startActivity(intent);
                } else {
                    Toast.makeText(getApplicationContext(), "Please choose the team!", Toast.LENGTH_LONG).show();
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
//***************************************************** Get data End *******************************************
    }

    @SuppressLint("MissingPermission")
    private void getLocationMethod() {
        TextView locationName= findViewById(R.id.locationNameId);
        fusedLocationClient.getLastLocation()
                .addOnSuccessListener(this, new OnSuccessListener<Location>() {
                    @Override
                    public void onSuccess(Location location) {
                        // Got last known location. In some rare situations this can be null.
                        if (location != null) {

                            Geocoder geocoder = new Geocoder(AddTask.this, Locale.getDefault());
                            List<Address> address = null;
                            try {
                                address = geocoder.getFromLocation(location.getLatitude(), location.getLongitude(), 1);
                            } catch (IOException e) {
                                e.printStackTrace();
                            }

                            locationName.setText(address.get(0).getCountryName());
                            latitudeNum=  address.get(0).getLatitude();
                            longitudeNum= address.get(0).getLongitude();
                       Log.i("Location", String.valueOf(address.get(0).getLatitude()));
                        System.out.println("======================================" +address.get(0).getCountryName() );
                        }else {
                            System.out.println("Location is null ==============================");
                        }
                    }
                });
    }

    private boolean checkPlayServices() {
        int resultCode = GooglePlayServicesUtil
                .isGooglePlayServicesAvailable(this);
        if (resultCode != ConnectionResult.SUCCESS) {
            if (GooglePlayServicesUtil.isUserRecoverableError(resultCode)) {
                GooglePlayServicesUtil.getErrorDialog(resultCode, this,
                        1234).show();
            } else {
                Log.i("TAG", "This device is not supported.");
                finish();
            }
            return false;
        }
        return true;
    }

    private void pickFile(){
        Intent chooseFile=new Intent(Intent.ACTION_GET_CONTENT);
        chooseFile.setType("*/*");
        chooseFile= Intent.createChooser(chooseFile , "Choose file");
        startActivityForResult(chooseFile , 1235); // Activity is started with requestCode 1235
    }


    @Override  // Call Back method  to get the Message form other Activity
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
                    .latitude(latitudeNum)
                    .longitude(longitudeNum)
                    .build();



            Amplify.API.mutate( // create or update on the database
                    ModelMutation.create(generatedTaskModel),
                    response -> Log.i("MyAmplifyApp", "Added task with id: " + response.getData().getId()),
                    error -> Log.e("MyAmplifyApp", "Create failed", error)
            );

    }

}

//amplify-tJvMv