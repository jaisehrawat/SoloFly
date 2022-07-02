package com.example.solofly;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class NewNote extends AppCompatActivity {

    Button saveNoteBtn;
    TextInputEditText noteTitleEdit, noteContentEdit;
    String notesTitle, notesContent, phone;
    FirebaseFirestore firestore;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_note);

        SharedPreferences preferences = getSharedPreferences("logInData", MODE_PRIVATE);
        phone = preferences.getString("phone", "");

        saveNoteBtn = findViewById(R.id.save_note_btn);
        noteTitleEdit = findViewById(R.id.note_title);
        noteContentEdit = findViewById(R.id.note_content);

        firestore = FirebaseFirestore.getInstance();

        saveNoteBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                notesTitle = noteTitleEdit.getText().toString().trim();
                notesContent = noteContentEdit.getText().toString().trim();

                if (notesTitle.isEmpty()) {
                    noteTitleEdit.setError("Enter Title");
                } else if (notesContent.isEmpty()) {
                    noteContentEdit.setError("Enter Something...");
                }
                else{
                    Map<String, Object> obj = new HashMap<>();
                    obj.put("notesTitle", notesTitle);
                    obj.put("notesContent", notesContent);

                    firestore.collection(phone).document("notes").collection("notes").add(obj).addOnCompleteListener(new OnCompleteListener<DocumentReference>() {
                        @Override
                        public void onComplete(@NonNull Task<DocumentReference> task) {
                            Toast.makeText(NewNote.this, "Note Inserted", Toast.LENGTH_SHORT).show();
                            finish();
                        }
                    });
                }
            }
        });



    }
}