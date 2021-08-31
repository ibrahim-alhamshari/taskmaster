package com.example.myapplication;

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
    List<TaskModel> allTasks = new ArrayList<TaskModel>();

    public Adapter(List<TaskModel> allTasks){
        this.allTasks=allTasks;
    }

    // 3- create the ViewHolder class (Wraps the data and the view)
    public static class TaskViewHolder extends RecyclerView.ViewHolder {
        // 4- The model object
        public TaskModel taskModel;
        // 5- view object
        View itemView;
        // + setting the itemView value
        public TaskViewHolder(@NonNull View itemView){
            super(itemView);
            this.itemView= itemView;

        }
    }

    @NonNull
    @Override
    public TaskViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // 7- create the view
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.fragment_task, parent, false);
        TaskViewHolder taskViewHolder= new TaskViewHolder(view);
        return taskViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull TaskViewHolder holder, int position) {
    holder.taskModel = allTasks.get(position);

        TextView taskTitle= holder.itemView.findViewById(R.id.titleFragment);
        TextView taskBody = holder.itemView.findViewById(R.id.bodyFragment);
        TextView taskState = holder.itemView.findViewById(R.id.stateFragment);

        taskTitle.setText(holder.taskModel.title);
        taskBody.setText(holder.taskModel.body);
        taskState.setText(holder.taskModel.state);
    }

    @Override
    public int getItemCount() {
        return allTasks.size();
    }


}
