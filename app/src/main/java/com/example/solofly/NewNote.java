package com.example.solofly;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.textfield.TextInputEditText;

public class NewNote extends AppCompatActivity {

    Button saveNoteBtn;
    TextInputEditText noteTitleEdit, noteContentEdit;
    String noteTitle="", noteContent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_note);

        saveNoteBtn = findViewById(R.id.save_note_btn);
        noteTitleEdit = findViewById(R.id.note_title);
        noteContentEdit = findViewById(R.id.note_content);

        saveNoteBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                noteTitle = noteTitleEdit.getText().toString().trim();
                noteContent = noteContentEdit.getText().toString().trim();

                if (noteContent.isEmpty()) {
                    Toast.makeText(NewNote.this, "Enter something", Toast.LENGTH_SHORT).show();
                } else {

                }
            }
        });

    }
}