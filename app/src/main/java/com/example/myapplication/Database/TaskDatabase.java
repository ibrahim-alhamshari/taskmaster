package com.example.myapplication.Database;

import androidx.room.Database;
import androidx.room.RoomDatabase;

import com.example.myapplication.Task;

@Database(entities = {Dish.class} , version = 2)
public abstract class TaskDatabase extends RoomDatabase {

    public abstract TaskDAO taskDAO();
}
