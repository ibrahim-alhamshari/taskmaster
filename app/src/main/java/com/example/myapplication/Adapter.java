package com.example.myapplication;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.Database.Dish;

import java.util.ArrayList;
import java.util.List;

// 1- Create the class without extensions ..
// 6- Extend RecyclerView.Adapter - StudentAdapter.StudentViewHolder
public class Adapter extends RecyclerView.Adapter<Adapter.TaskViewHolder> {

    // 2- create the list the the adapter will use to bind data
    List<Dish> allTasks = new ArrayList<Dish>();

    public Adapter(List<Dish> allTasks){
        this.allTasks=allTasks;
    }

    // 3- create the ViewHolder class (Wraps the data and the view)
    public static class TaskViewHolder extends RecyclerView.ViewHolder {
        private TextView title;
        private TextView body;
        private TextView state;

        // + setting the itemView value
        public TaskViewHolder(@NonNull View view){
            super(view);
            title= view.findViewById(R.id.titleFragment);
            body = view.findViewById(R.id.bodyFragment);
            state=view.findViewById(R.id.stateFragment);
        }
    }

    @NonNull
    @Override
    public TaskViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // 7- create the view
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.fragment_task, parent, false);
        return new TaskViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull TaskViewHolder holder, int position) {
    String dishName=allTasks.get(position).dishName;
    String price = allTasks.get(position).price;
    String ingradient= allTasks.get(position).ingradient;

    holder.title.setText(dishName);
    holder.body.setText(price);
    holder.state.setText(ingradient);
    }

    @Override
    public int getItemCount() {
        return allTasks.size();
    }


}
