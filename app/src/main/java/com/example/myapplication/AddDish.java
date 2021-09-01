package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;
import androidx.room.Room;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.myapplication.Database.Dish;
import com.example.myapplication.Database.TaskDatabase;

public class AddDish extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_dish);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        EditText dishName = findViewById(R.id.dishNameAddDish);
        EditText price = findViewById(R.id.dishPriceAddDish);
        EditText ingredient = findViewById(R.id.ingrediantsAddDish);
        Button submitButton = findViewById(R.id.submitInAddDish);

        submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getApplicationContext(),"submitted!" , Toast.LENGTH_LONG).show();

                saveNewDish(dishName.getText().toString() , price.getText().toString(), ingredient.getText().toString());
            }
        });
    }

    private void saveNewDish(String dishName , String price , String ingredient){
        TaskDatabase db = Room.databaseBuilder(this, TaskDatabase.class, "dish").fallbackToDestructiveMigration().allowMainThreadQueries().build();

        Dish task = new Dish();
        task.dishName=dishName;
        task.price= price;
        task.ingradient= ingredient;

        db.taskDAO().insertTask(task);
    }
}