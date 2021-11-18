package com.example.mymedia;

public class Song {
   private String Title;
   private int File;
    public Song(String Title, int File) {
        this.Title = Title;
        this.File = File;
    }
    public String getTitle() {
        return Title;
    }
    public int getFile() {
        return File;
    }
    public void setTitle(String Title) {
        this.Title = Title;
    }
    public void setFile(int File) {
        this.File = File;
    }
}
