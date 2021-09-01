package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.room.Room;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.myapplication.Database.Dish;
import com.example.myapplication.Database.TaskDAO;
import com.example.myapplication.Database.TaskDatabase;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Menu extends AppCompatActivity {

    private List<Dish> taskList = new ArrayList<>();
    private RecyclerView recyclerView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        setUserInfo();
        setAdapter();

        Button homeButtonInMenuPage = findViewById(R.id.homeButtonInMenuPage);
        homeButtonInMenuPage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Menu.this, MainActivity.class);
                startActivity(intent);
            }
        });
    }

    public void setUserInfo() {
        TaskDatabase db = Room.databaseBuilder(getApplicationContext(), TaskDatabase.class, "dish").fallbackToDestructiveMigration().allowMainThreadQueries().build();
        TaskDAO userDao = db.taskDAO();
       taskList = userDao.getAllTasks();
//        System.out.println("++++++++++++++++++++++++++"+ taskList.get(0).dishName);
    }

    private void setAdapter(){
        recyclerView= findViewById(R.id.menuRecyclerView);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);

        Adapter adapter= new Adapter(taskList);
        recyclerView.setAdapter(adapter);
    }
}