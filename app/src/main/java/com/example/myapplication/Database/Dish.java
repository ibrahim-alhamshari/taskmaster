package com.example.myapplication.Database;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "dish")
public class Dish {


    @PrimaryKey(autoGenerate = true)
    public int id;

    public String dishName;
    public String price;
    public String ingradient;

    public Dish(){}

    public Dish(String dishName, String price , String ingradient){
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
