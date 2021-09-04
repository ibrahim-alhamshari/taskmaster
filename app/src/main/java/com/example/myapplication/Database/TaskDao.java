package com.example.myapplication.Database;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import com.example.myapplication.Task;

import java.util.List;

@Dao
public interface TaskDao {

    @Query("SELECT * FROM task")
        List<Task> getAllTasks();

    @Insert
    void insertTask(Task... task);
}
