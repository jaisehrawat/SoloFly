package com.example.solofly;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.floatingactionbutton.ExtendedFloatingActionButton;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

public class NewDiaryNote extends AppCompatActivity {

    TextView diaryDateView;
    TextInputEditText newDiaryNoteTitleEdittext, newDiaryNoteContentEdittext;
    ExtendedFloatingActionButton diaryNoteSaveBtn;
    String id, diaryDate, diaryTitle, diaryNote;
    FirebaseFirestore firestore;
    String phone;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_diary_note);

        SharedPreferences preferences = getSharedPreferences("logInData", MODE_PRIVATE);
        phone = preferences.getString("phone", "");

        diaryDateView = findViewById(R.id.diary_date);
        newDiaryNoteTitleEdittext = findViewById(R.id.diary_title_edittext);
        newDiaryNoteContentEdittext = findViewById(R.id.diary_content_edittext);
        diaryNoteSaveBtn = findViewById(R.id.diary_save_btn);
        firestore = FirebaseFirestore.getInstance();

        Calendar c = Calendar.getInstance();
        SimpleDateFormat df = new SimpleDateFormat("dd-MM-yyyy / hh:mm aa");
        SimpleDateFormat idd = new SimpleDateFormat("dd-MM-yyyy");
        String formattedDate = df.format(c.getTime());

        diaryDateView.setText(formattedDate);

        diaryNoteSaveBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                id = idd.format(c.getTime());
                diaryDate = formattedDate;
                diaryTitle = newDiaryNoteTitleEdittext.getText().toString().trim();
                diaryNote = newDiaryNoteContentEdittext.getText().toString().trim();

                if (diaryTitle.isEmpty()) {
                    newDiaryNoteTitleEdittext.setError("Enter Title");
                } else if (diaryNote.isEmpty()) {
                    newDiaryNoteContentEdittext.setError("Enter Something...");
                } else {
                    Map<String, Object> obj = new HashMap<>();
                    obj.put("diaryDate", diaryDate);
                    obj.put("diaryTitle", diaryTitle);
                    obj.put("diaryNote", diaryNote);

                    firestore.collection(phone).document("diary").collection("diary").add(obj).addOnCompleteListener(new OnCompleteListener<DocumentReference>() {
                        @Override
                        public void onComplete(@NonNull Task<DocumentReference> task) {
                            Toast.makeText(NewDiaryNote.this, "Diary Note Inserted", Toast.LENGTH_SHORT).show();
                            finish();
                        }
                    });
                }
            }
        });

    }
}