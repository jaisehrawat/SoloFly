package com.example.solofly;

import android.app.TimePickerDialog;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.os.Bundle;
import android.text.format.DateFormat;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.TimePicker;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.datepicker.MaterialDatePicker;
import com.google.android.material.datepicker.MaterialPickerOnPositiveButtonClickListener;
import com.google.android.material.floatingactionbutton.ExtendedFloatingActionButton;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

public class NewReminder extends AppCompatActivity {

    String[] remindBeforeItems = {"No", "Before 5 MIN", "Before 10 MIN", "Before 15 MIN", "Before 30 MIN", "Before 1 HOUR", "Before 2 HOUR", "Before 6 HOUR", "Before 12 HOUR", "Before 1 DAY"};
    String[] reminderRepeatItems = {"No Repeat", "Repeat Daily", "Repeat Weekly", "Repeat Monthly", "Repeat Yearly"};
    AutoCompleteTextView reminderBeforeTextView, reminderRepeatTextView;
    ArrayAdapter<String> reminderBeforeAdapter, reminderRepeatAdapter;
    String reminderTitle, reminderShortNote, reminderDate, reminderTime, reminderRepeat, reminderBefore;
    int hour, minute;
    TextInputEditText remindEdittext, reminderShortNoteEdittext;
    Button reminderDateBtn, reminderTimeBtn;
    ExtendedFloatingActionButton newReminderSaveBtn;
    FirebaseFirestore firestore;
    String phone;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_reminder);

        SharedPreferences preferences = getSharedPreferences("logInData", MODE_PRIVATE);
        phone = preferences.getString("phone", "");
        firestore = FirebaseFirestore.getInstance();

        remindEdittext = findViewById(R.id.reminder_edittext);
        reminderShortNoteEdittext = findViewById(R.id.reminder_short_note_edittext);
        reminderBeforeTextView = findViewById(R.id.remind_before);
        reminderRepeatTextView = findViewById(R.id.reminder_repeat);
        reminderDateBtn = findViewById(R.id.reminder_date);
        reminderTimeBtn = findViewById(R.id.reminder_time);
        newReminderSaveBtn = findViewById(R.id.new_reminder_save_btn);

        reminderDateBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                MaterialDatePicker datePicker = MaterialDatePicker.Builder.datePicker().setTitleText("Select date").setSelection(MaterialDatePicker.todayInUtcMilliseconds()).build();
                datePicker.show(getSupportFragmentManager(), "MaterialDatePicker");
                datePicker.addOnPositiveButtonClickListener(new MaterialPickerOnPositiveButtonClickListener() {
                    @Override
                    public void onPositiveButtonClick(Object selection) {
                        reminderDate = datePicker.getHeaderText();
                        reminderDateBtn.setText(reminderDate);
                    }
                });
            }
        });
        reminderTimeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                TimePickerDialog timePickerDialog = new TimePickerDialog(NewReminder.this, android.R.style.Theme_Holo_Light_Dialog_MinWidth, new TimePickerDialog.OnTimeSetListener() {
                    @RequiresApi(api = Build.VERSION_CODES.N)
                    @Override
                    public void onTimeSet(TimePicker timePicker, int i, int i1) {
                        hour = i;
                        minute = i1;
                        Calendar calendar = Calendar.getInstance();
                        calendar.set(0, 0, 0, hour, minute);
                        reminderTimeBtn.setText(DateFormat.format("hh:mm aa", calendar));
                    }
                }, 12, 0, false);
                timePickerDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                timePickerDialog.updateTime(hour, minute);
                timePickerDialog.show();
            }
        });

        reminderBeforeAdapter = new ArrayAdapter<String>(this, R.layout.dropdown_textview, remindBeforeItems);
        reminderRepeatAdapter = new ArrayAdapter<String>(this, R.layout.dropdown_textview, reminderRepeatItems);

        reminderBeforeTextView.setAdapter(reminderBeforeAdapter);
        reminderRepeatTextView.setAdapter(reminderRepeatAdapter);

        reminderBeforeTextView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                reminderBefore = adapterView.getItemAtPosition(i).toString();
            }
        });
        reminderRepeatTextView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                reminderRepeat = adapterView.getItemAtPosition(i).toString();
            }
        });

        newReminderSaveBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                reminderTitle = remindEdittext.getText().toString().trim();
                reminderShortNote = reminderShortNoteEdittext.getText().toString().trim();
                reminderDate = reminderDateBtn.getText().toString().trim();
                reminderTime = reminderTimeBtn.getText().toString().trim();
//                reminderRepeat, reminderBefore

                if (reminderTitle.isEmpty()) {
                    remindEdittext.setError("Enter Title");
                } else if (reminderShortNote.isEmpty()) {
                    reminderShortNoteEdittext.setError("Enter Short Note...");
                } else if (reminderDate.isEmpty()) {
                    reminderDateBtn.setError("Enter date");
                } else if (reminderTime.isEmpty()) {
                    reminderTimeBtn.setError("Enter time");
                } else if (reminderBefore.isEmpty()) {
                    reminderBeforeTextView.setError("Enter before time");
                } else if (reminderRepeat.isEmpty()) {
                    reminderRepeatTextView.setError("Enter repeat time");
                } else {
                    Map<String, Object> obj = new HashMap<>();
                    obj.put("reminderTitle", reminderTitle);
                    obj.put("reminderShortNote", reminderShortNote);
                    obj.put("reminderDate", reminderDate);
                    obj.put("reminderTime", reminderTime);
                    obj.put("reminderBefore", reminderBefore);
                    obj.put("reminderRepeat", reminderRepeat);

                    firestore.collection(phone).document("reminder").collection("reminder").add(obj).addOnCompleteListener(new OnCompleteListener<DocumentReference>() {
                        @Override
                        public void onComplete(@NonNull Task<DocumentReference> task) {
                            Toast.makeText(NewReminder.this, "Reminder Inserted", Toast.LENGTH_SHORT).show();
                            finish();
                        }
                    });
                }
            }
        });

    }

}