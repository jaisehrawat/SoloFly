package com.example.solofly.Adapter;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.solofly.Model.ToDoModel;
import com.example.solofly.NewTask;
import com.example.solofly.R;
import com.example.solofly.ToDo;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.List;

public class ToDoAdapter extends RecyclerView.Adapter<ToDoAdapter.MyViewHolder> {

    private List<ToDoModel> todoList;
    private ToDo activity;
    private FirebaseFirestore firestore;

    ToDo temp = new ToDo();
    String phone = ToDo.phone;

    public ToDoAdapter(ToDo todoActivity, List<ToDoModel> todoList) {
        this.todoList = todoList;
        activity = todoActivity;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(activity).inflate(R.layout.task_layout, parent, false);
        firestore = FirebaseFirestore.getInstance();

        return new MyViewHolder(view);
    }

    public void deleteTask(int position) {
        ToDoModel toDoModel = todoList.get(position);
        firestore.collection(phone).document(toDoModel.TaskId).delete();
        todoList.remove(position);
        notifyItemRemoved(position);
    }

    public Context getContext() {
        return activity;
    }

    public void editTask(int position) {
        ToDoModel toDoModel = todoList.get(position);

        Bundle bundle = new Bundle();
        bundle.putString("task", toDoModel.getTask());
        bundle.putString("id", toDoModel.TaskId);

        NewTask addNewTask = new NewTask();
        addNewTask.setArguments(bundle);
        addNewTask.show(activity.getSupportFragmentManager(), addNewTask.getTag());
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {

        ToDoModel toDoModel = todoList.get(position);
        ToDo temp = new ToDo();
        String phone = ToDo.phone;
        holder.mCheckBox.setText(toDoModel.getTask());

        holder.mCheckBox.setChecked(toBoolean(toDoModel.getStatus()));

        holder.mCheckBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    firestore.collection(phone).document(toDoModel.TaskId).update("status", 1);
                } else {
                    firestore.collection(phone).document(toDoModel.TaskId).update("status", 0);
                }
            }
        });

    }

    private boolean toBoolean(int status) {
        return status != 0;
    }

    @Override
    public int getItemCount() {
        return todoList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        CheckBox mCheckBox;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            mCheckBox = itemView.findViewById(R.id.todo_checkbox);
        }
    }

}
