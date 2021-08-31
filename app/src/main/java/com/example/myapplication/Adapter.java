package com.example.myapplication;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class Adapter extends RecyclerView.Adapter<Adapter.TaskViewHolder> {

    List<TaskModel> allTasks = new ArrayList<TaskModel>();

    public Adapter(List<TaskModel> allTasks){
        this.allTasks=allTasks;
    }

    @NonNull
    @Override
    public TaskViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

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

    public static class TaskViewHolder extends RecyclerView.ViewHolder {
        public TaskModel taskModel;
        View itemView;

        public TaskViewHolder(@NonNull View itemView){
            super(itemView);
            this.itemView= itemView;

        }
    }
}
