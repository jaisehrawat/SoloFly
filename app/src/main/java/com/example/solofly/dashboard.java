package com.example.solofly;

import android.content.Intent;
import android.os.Bundle;

import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


public class dashboard extends Fragment {

    CardView diary, notes, toDo, reminder, storage, settings;

    public dashboard() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_dashboard, container, false);

        settings = v.findViewById(R.id.settings);
        storage = v.findViewById(R.id.storage);
        toDo = v.findViewById(R.id.toDo);
        reminder = v.findViewById(R.id.reminder);
        notes = v.findViewById(R.id.notes);
        diary = v.findViewById(R.id.diary);

        diary.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getActivity(), Diary.class));
            }
        });

        notes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getActivity(), Notes.class);
                startActivity(i);
            }
        });

        toDo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getActivity(), ToDo.class);
                startActivity(i);
            }
        });

        reminder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getActivity(), Reminder.class);
                startActivity(i);
            }
        });

        settings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getActivity(), settings.class);
                startActivity(i);
            }
        });

        storage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getActivity(), Storage.class);
                startActivity(i);
            }
        });
        return v;
    }

}