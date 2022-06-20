package com.example.solofly.Model;

public class ReminderModel {
    public String reminderTitle, reminderShortNote, remindBefore, reminderRepeat, reminderDate, reminderTime;

    public ReminderModel(String reminderTitle, String reminderShortNote, String remindBefore, String reminderRepeat, String reminderDate, String reminderTime) {
        this.reminderTitle = reminderTitle;
        this.reminderShortNote = reminderShortNote;
        this.remindBefore = remindBefore;
        this.reminderRepeat = reminderRepeat;
        this.reminderDate = reminderDate;
        this.reminderTime = reminderTime;
    }
}
