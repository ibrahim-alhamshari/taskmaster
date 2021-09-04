package com.example.myapplication;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;


import java.util.ArrayList;
import java.util.List;

// 1- Create the class without extensions ..
// 6- Extend RecyclerView.Adapter - StudentAdapter.StudentViewHolder
public class Adapter extends RecyclerView.Adapter<Adapter.TaskViewHolder> {

    // 2- create the list the the adapter will use to bind data
    List<Task> allTasks = new ArrayList<Task>();

    public Adapter(List<Task> allTasks){
        this.allTasks=allTasks;
    }

    // 3- create the ViewHolder class (Wraps the data and the view)
    public static class TaskViewHolder extends RecyclerView.ViewHolder {
        public Task task;
        View itemView;

        // + setting the itemView value
        public TaskViewHolder(@NonNull View view){
            super(view);
            this.itemView=view;
            itemView.findViewById(R.id.seeMoreInMainPage).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent= new Intent(view.getContext() , TaskDetail.class);
                    intent.putExtra("title" , task.title);
                    intent.putExtra("body" , task.body);
                    intent.putExtra("state" , task.state);
                    view.getContext().startActivity(intent);
                }
            });
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
        holder.task= allTasks.get(position);
    TextView title=holder.itemView.findViewById(R.id.titleFragment);
//    TextView body= holder.itemView.findViewById(R.id.bodyFragment);
//    TextView state = holder.itemView.findViewById(R.id.stateFragment);

    title.setText(holder.task.title);
//    body.setText(holder.task.body);
//    state.setText(holder.task.state);
    }

    @Override
    public int getItemCount() {
        return allTasks.size();
    }


}
