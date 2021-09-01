package com.example.myapplication;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "task")
public class Task {

    @PrimaryKey
    public int id;

    public String dishName;
    public String price;
    public String ingradient;

    public Task(){}

    public Task(String dishName, String price , String ingradient){
        this.dishName=dishName;
        this.price=price;
        this.ingradient=ingradient;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getDishName() {
        return dishName;
    }

    public void setDishName(String dishName) {
        this.dishName = dishName;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getIngradient() {
        return ingradient;
    }

    public void setIngradient(String ingradient) {
        this.ingradient = ingradient;
    }
}
