package com.example.solofly.Model;

public class NotesModel {
    public String notesTitle, notesContent;

    public NotesModel(String notesTitle, String notesContent) {
        this.notesTitle = notesTitle;
        this.notesContent = notesContent;
    }

    public NotesModel() {
    }

    public String getNotesTitle() {
        return notesTitle;
    }

    public void setNotesTitle(String notesTitle) {
        this.notesTitle = notesTitle;
    }

    public String getNotesContent() {
        return notesContent;
    }

    public void setNotesContent(String notesContent) {
        this.notesContent = notesContent;
    }
}
