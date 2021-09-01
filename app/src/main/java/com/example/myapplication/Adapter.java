package com.example.myapplication;

import android.content.Intent;
import android.os.Parcelable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.Database.Dish;
import com.example.myapplication.Database.OneDish;

import java.util.ArrayList;
import java.util.Arrays;
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
    public class TaskViewHolder extends RecyclerView.ViewHolder {
        private TextView dichName;
        private TextView price;
        private TextView ingredients;

        // + setting the itemView value
        public TaskViewHolder(@NonNull View view){
            super(view);
            dichName= view.findViewById(R.id.titleFragment);
            price = view.findViewById(R.id.bodyFragment);

           view.findViewById(R.id.seeMoreButtonInMenu).setOnClickListener(new View.OnClickListener() {
               @Override
               public void onClick(View view) {
                   System.out.println(getItemId() +"+++++++++++++++++++++");
                   Intent intent = new Intent(view.getContext(), OneDish.class);
                   intent.putExtra("dishName" , allTasks.get(3).dishName);
                   intent.putExtra("price" , allTasks.get(3).price);
                   intent.putExtra("ingredients" , allTasks.get(3).ingradient);
                   view.getContext().startActivity(intent);
               }
           }

           );
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


    holder.dichName.setText(dishName);
    holder.price.setText(price);
//    holder.ingredients.setText(ingradient);
    }

    @Override
    public int getItemCount() {
        return allTasks.size();
    }


}
