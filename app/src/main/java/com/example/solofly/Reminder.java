package com.example.solofly;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.solofly.Adapter.ReminderAdapter;
import com.example.solofly.Model.ReminderModel;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class Reminder extends AppCompatActivity {

    //  appbar
    ImageView profile_icon, back_button;
    TextView activity_name;
    String profileUrl, phone;

    FloatingActionButton newReminderBtn;
    RecyclerView reminderRecyclerView;
    ArrayList<ReminderModel> reminderModelArrayList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reminder);
        //    appBar
        back_button = findViewById(R.id.back_button);
        activity_name = findViewById(R.id.activity_name);
        profile_icon = findViewById(R.id.profile_icon);

        reminderRecyclerView = findViewById(R.id.reminder_recyclerview);
        newReminderBtn = findViewById(R.id.new_reminder_btn);

        newReminderBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(Reminder.this, NewReminder.class));
            }
        });

        reminderRecyclerView.setLayoutManager(new LinearLayoutManager(this, RecyclerView.VERTICAL, false));

        reminderModelArrayList.add(new ReminderModel("Birthday", "Jai Sehrawat Birthday", "Remind 1 Day Before", "Every Year", "08/01/2023", "5:00 AM"));
        reminderModelArrayList.add(new ReminderModel("Anniversary", "Rohit wedding anniversary", "Remind 1 Day Before", "Every Year", "03/05/2023", "8:00 AM"));

        ReminderAdapter reminderAdapter = new ReminderAdapter(this, reminderModelArrayList);
        reminderRecyclerView.setAdapter(reminderAdapter);

    }


    @Override
    protected void onStart() {
        super.onStart();
        appbarFunctionality();
    }

    protected void appbarFunctionality() {
        SharedPreferences preferences = getSharedPreferences("logInData", MODE_PRIVATE);
        phone = preferences.getString("phone", "");
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("User").child(phone);

//        Back button
        back_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Reminder.super.onBackPressed();
            }
        });

//      Activity name set
        activity_name.setText("Reminder");

//      Profile icon set
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()) {
                    Data d = snapshot.getValue(Data.class);
                    profileUrl = d.getProfileUrl();
                    Picasso.get().load(profileUrl).into(profile_icon);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        profile_icon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), Profile.class);
                startActivity(intent);
            }
        });
    }
}