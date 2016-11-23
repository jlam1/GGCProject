package com.example.john.finalproject;

/**
 * Created by John on 11/22/2016.
 */

public class Lyric {

    private String lyric;
    private int time;

    public Lyric(int time, String lyric) {
        this.time = time;
        this.lyric = lyric;
    }

    public String getLyric() {
        return lyric;
    }

    public int getTime() {
        return time;
    }

}
