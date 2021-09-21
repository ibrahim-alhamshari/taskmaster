package com.example.myapplication.Tasks;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

import com.amplifyframework.core.Amplify;
import com.example.myapplication.R;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.io.File;

public class TaskDetail extends AppCompatActivity implements OnMapReadyCallback {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_task_detail);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        Intent intent = getIntent();
        String title = intent.getStringExtra("title");
        String body = intent.getStringExtra("body");
        String state= intent.getStringExtra("state");
        String fileData= intent.getStringExtra("fileData");
        String latitude=intent.getStringExtra("latitude");
        String longitude=intent.getStringExtra("longitude");

        TextView textTitle = findViewById(R.id.taskDetailsTitle);
        TextView textBody = findViewById(R.id.taskDetailsBody);
        TextView textState=findViewById(R.id.taskDetailsState);
        ImageView imageView=findViewById(R.id.imageView);
        MapView mapView= findViewById(R.id.mapView);

        Amplify.Storage.downloadFile(
                fileData,
                new File(getApplicationContext().getFilesDir()+"/" +intent.getStringExtra("fileData") +".jpg"),
                result ->{
                    Bitmap bitmap = BitmapFactory.decodeFile(result.getFile().getPath());
                    imageView.setImageBitmap(bitmap);
                    Log.i("MyAmplifyApp", "Successfully downloaded: " + result.getFile().getName());
                } ,
                error -> Log.e("MyAmplifyApp",  "Download Failure", error)
        );

        textTitle.setText(title);
        textBody.setText(body);
        textState.setText(state);


//        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
//                .findFragmentById(R.id.map);
//        mapFragment.getMapAsync(this);
    }

    // Get a handle to the GoogleMap object and display marker.
    @Override
    public void onMapReady(GoogleMap googleMap) {
        googleMap.addMarker(new MarkerOptions()
                .position(new LatLng(0, 0))
                .title("Marker"));
    }
}