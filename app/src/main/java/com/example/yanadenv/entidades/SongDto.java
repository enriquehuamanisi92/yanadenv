package com.example.yanadenv.entidades;

import android.graphics.Bitmap;

public class SongDto {

    public long songId;
    public String songTitle;
    public String songArtist;
    public String path;
    public short genre;
    public long duration;
    public String album;
    public Bitmap albumArt;

    public String toString() {
        return String.format("songId: %d, Title: %s, Artist: %s, Path: %s, Genere: %d, Duration %s",
                songId, songTitle, songArtist, path, genre, duration);
    }
}