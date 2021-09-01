package com.example.myapplication.Database;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import com.example.myapplication.Task;

import java.util.List;

@Dao
public interface TaskDAO {

    @Query("SELECT * FROM dish")
        List<Dish> getAllTasks();

    @Insert
    void insertTask(Dish... dish);
}
