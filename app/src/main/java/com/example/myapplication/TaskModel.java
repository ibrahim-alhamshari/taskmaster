package com.example.myapplication;

public class TaskModel {

    public String title;
    public String body;
    public String state;
    public TaskModel(){}

    public TaskModel(String title, String body , String state){
        this.title=title;
        this.body=body;
        this.state=state;
    }
}
