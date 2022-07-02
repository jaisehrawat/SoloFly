package com.example.solofly.Model;

public class ReminderModel {
    public String reminderTitle, reminderShortNote, reminderBefore, reminderRepeat, reminderDate, reminderTime;

    public ReminderModel() {
    }

    public ReminderModel(String reminderTitle, String reminderShortNote, String reminderBefore, String reminderRepeat, String reminderDate, String reminderTime) {
        this.reminderTitle = reminderTitle;
        this.reminderShortNote = reminderShortNote;
        this.reminderBefore = reminderBefore;
        this.reminderRepeat = reminderRepeat;
        this.reminderDate = reminderDate;
        this.reminderTime = reminderTime;
    }

    public String getReminderTitle() {
        return reminderTitle;
    }

    public void setReminderTitle(String reminderTitle) {
        this.reminderTitle = reminderTitle;
    }

    public String getReminderShortNote() {
        return reminderShortNote;
    }

    public void setReminderShortNote(String reminderShortNote) {
        this.reminderShortNote = reminderShortNote;
    }

    public String getReminderBefore() {
        return reminderBefore;
    }

    public void setReminderBefore(String reminderBefore) {
        this.reminderBefore = reminderBefore;
    }

    public String getReminderRepeat() {
        return reminderRepeat;
    }

    public void setReminderRepeat(String reminderRepeat) {
        this.reminderRepeat = reminderRepeat;
    }

    public String getReminderDate() {
        return reminderDate;
    }

    public void setReminderDate(String reminderDate) {
        this.reminderDate = reminderDate;
    }

    public String getReminderTime() {
        return reminderTime;
    }

    public void setReminderTime(String reminderTime) {
        this.reminderTime = reminderTime;
    }
}
