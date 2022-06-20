package com.example.solofly;

import android.app.TimePickerDialog;
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

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.datepicker.MaterialDatePicker;
import com.google.android.material.datepicker.MaterialPickerOnPositiveButtonClickListener;

import java.util.Calendar;

public class NewReminder extends AppCompatActivity {

    String[] remindBeforeItems = {"No", "Before 5 MIN", "Before 10 MIN", "Before 15 MIN", "Before 30 MIN", "Before 1 HOUR", "Before 2 HOUR", "Before 6 HOUR", "Before 12 HOUR", "Before 1 DAY"};
    String[] reminderRepeatItems = {"No Repeat", "Repeat Daily", "Repeat Weekly", "Repeat Monthly", "Repeat Yearly"};
    AutoCompleteTextView reminderBefore, reminderRepeat;
    ArrayAdapter<String> reminderBeforeAdapter, reminderRepeatAdapter;
    String reminderDate, repeat, before;
    int hour, minute;
    Button reminderDateBtn, reminderTimeBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_reminder);

        reminderBefore = findViewById(R.id.remind_before);
        reminderRepeat = findViewById(R.id.reminder_repeat);
        reminderDateBtn = findViewById(R.id.reminder_date);
        reminderTimeBtn = findViewById(R.id.reminder_time);

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

        reminderBefore.setAdapter(reminderBeforeAdapter);
        reminderRepeat.setAdapter(reminderRepeatAdapter);

        reminderBefore.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                before = adapterView.getItemAtPosition(i).toString();
            }
        });
        reminderRepeat.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                repeat = adapterView.getItemAtPosition(i).toString();
            }
        });

    }

}