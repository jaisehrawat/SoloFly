package com.example.solofly;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.solofly.Adapter.NoteAdapter;
import com.example.solofly.Model.NotesModel;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class Notes extends AppCompatActivity {
    //  appbar
    ImageView profile_icon, back_button;
    TextView activity_name;
    String profileUrl, phone;

    FloatingActionButton newNoteBtn;
    RecyclerView notesRecyclerView;
    ArrayList<NotesModel> notesArrayList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notes);

//    appBar
        back_button = findViewById(R.id.back_button);
        activity_name = findViewById(R.id.activity_name);
        profile_icon = findViewById(R.id.profile_icon);

        newNoteBtn = findViewById(R.id.new_note_btn);
        newNoteBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(Notes.this, NewNote.class);
                startActivity(i);
            }
        });

        notesRecyclerView = findViewById(R.id.notesRecyclerView);
        notesRecyclerView.setLayoutManager(new StaggeredGridLayoutManager(2, 1));

        notesArrayList.add(new NotesModel("1", "The RecyclerView class extends the ViewGroup class and implements ScrollingView interface. It is introduced in Marshmallow. It is an advanced version of the ListView with improved performance and other benefits. RecyclerView is mostly used to design the user interface with the fine-grain control over the lists and grids of android application."));
        notesArrayList.add(new NotesModel("2", "In this tutorial, we will create a list of items with ImageView (for the icon) and TextView (for description) using RecyclerView and performs click listener on the item of its list."));
        notesArrayList.add(new NotesModel("3", "In this tutorial, we will create a list of items with ImageView (for the icon) and TextView (for description) using RecyclerView and performs click listener on the item of its list."));
        notesArrayList.add(new NotesModel("4", "The RecyclerView class extends the ViewGroup class and implements ScrollingView interface. It is introduced in Marshmallow. It is an advanced version of the ListView with improved performance and other benefits. RecyclerView is mostly used to design the user interface with the fine-grain control over the lists and grids of android application."));
        notesArrayList.add(new NotesModel("5", "In this tutorial, we will create a list of items with ImageView (for the icon) and TextView (for description) using RecyclerView and performs click listener on the item of its list."));
        notesArrayList.add(new NotesModel("6", "The RecyclerView class extends the ViewGroup class and implements ScrollingView interface. It is introduced in Marshmallow. It is an advanced version of the ListView with improved performance and other benefits. RecyclerView is mostly used to design the user interface with the fine-grain control over the lists and grids of android application."));
        notesArrayList.add(new NotesModel("7", "The RecyclerView class extends the ViewGroup class and implements ScrollingView interface. It is introduced in Marshmallow. It is an advanced version of the ListView with improved performance and other benefits. RecyclerView is mostly used to design the user interface with the fine-grain control over the lists and grids of android application."));

        NoteAdapter noteAdapter = new NoteAdapter(notesArrayList, this);
        notesRecyclerView.setAdapter(noteAdapter);
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
                Notes.super.onBackPressed();
            }
        });

//      Activity name set
        activity_name.setText("Notes");

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