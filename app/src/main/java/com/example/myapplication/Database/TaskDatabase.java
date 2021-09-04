package com.example.myapplication.Database;

import androidx.room.Database;
import androidx.room.RoomDatabase;

import com.example.myapplication.Task;

@Database(entities = {Task.class} , version = 1)
public abstract class TaskDatabase extends RoomDatabase {

    public abstract TaskDao taskDao();
}
