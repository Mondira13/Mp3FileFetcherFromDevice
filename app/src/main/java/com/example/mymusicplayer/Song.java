package com.example.mymusicplayer;

public class Song {

    private long id;
    private String title;
    private String artist;
    private String rawFile;

    public Song(long songID, String songTitle, String songArtist,String rawFile) {
        id=songID;
        title=songTitle;
        artist=songArtist;
        this.rawFile = rawFile;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getArtist() {
        return artist;
    }

    public void setArtist(String artist) {
        this.artist = artist;
    }

    public String getRawFile() {
        return rawFile;
    }

    public void setRawFile(String rawFile) {
        this.rawFile = rawFile;
    }
}
