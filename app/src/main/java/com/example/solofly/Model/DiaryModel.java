package com.example.solofly.Model;

public class DiaryModel {
    public String diaryDate, diaryTitle, diaryNote;

    public DiaryModel(String diaryDate, String diaryTitle, String diaryNote) {
        this.diaryDate = diaryDate;
        this.diaryTitle = diaryTitle;
        this.diaryNote = diaryNote;
    }

    public DiaryModel() {
    }

    public String getDiaryDate() {
        return diaryDate;
    }

    public void setDiaryDate(String diaryDate) {
        this.diaryDate = diaryDate;
    }

    public String getDiaryTitle() {
        return diaryTitle;
    }

    public void setDiaryTitle(String diaryTitle) {
        this.diaryTitle = diaryTitle;
    }

    public String getDiaryNote() {
        return diaryNote;
    }

    public void setDiaryNote(String diaryNote) {
        this.diaryNote = diaryNote;
    }
}
