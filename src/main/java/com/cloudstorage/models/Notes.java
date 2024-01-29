package com.cloudstorage.models;

public class Notes {

    private Integer userID;
    private Integer noteID;
    private String noteTitle;
    private String noteDescription;

    public Notes(Integer userID, Integer noteID, String noteTitle, String noteDescription) {
        this.userID = userID;
        this.noteID = noteID;
        this.noteTitle = noteTitle;
        this.noteDescription = noteDescription;
    }

    public Notes(){}

    public Integer getUserID() {
        return userID;
    }

    public void setUserID(Integer userID) {
        this.userID = userID;
    }

    public Integer getNoteID() {
        return noteID;
    }

    public void setNoteID(Integer noteID) {
        this.noteID = noteID;
    }

    public String getNoteTitle() {
        return noteTitle;
    }

    public void setNoteTitle(String noteTitle) {
        this.noteTitle = noteTitle;
    }

    public String getNoteDescription() {
        return noteDescription;
    }

    public void setNoteDescription(String noteDescription) {
        this.noteDescription = noteDescription;
    }

    @Override
    public String toString(){
        return "Note title: " + noteTitle + " Note description: " + noteDescription + " NoteID: " + noteID;
    }

}
