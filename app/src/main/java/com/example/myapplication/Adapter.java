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
    String title=allTasks.get(position).title;
    String body = allTasks.get(position).body;
    String state= allTasks.get(position).state;

    holder.title.setText(title);
    holder.body.setText(body);
    holder.state.setText(state);
    }

    @Override
    public int getItemCount() {
        return allTasks.size();
    }


}
