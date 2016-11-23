package com.example.john.finalproject;


import android.util.Log;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by John on 11/22/2016.
 */

public class LyricGenerator {

    private static List<Lyric> list;

    static {
        list = new ArrayList<>();
        Lyric lyric;

        lyric = new Lyric(0, " ");
        list.add(lyric);
        lyric = new Lyric(10, "We have gained wisdom and honor");
        list.add(lyric);
        lyric = new Lyric(14, "From our home of green and grey");
        list.add(lyric);
        lyric = new Lyric(18, "We will go forth and remember");
        list.add(lyric);
        lyric = new Lyric(22, "All we've learned along the way");
        list.add(lyric);
        lyric = new Lyric(26, "And with knowledge and compassion");
        list.add(lyric);
        lyric = new Lyric(30, "We will build communities");
        list.add(lyric);
        lyric = new Lyric(35, "Leading by example and with dignity");
        list.add(lyric);
        lyric = new Lyric(43, "Georgia Gwinnett we'll never forget");
        list.add(lyric);
        lyric = new Lyric(51, "How we have grown and those we've met");
        list.add(lyric);
        lyric = new Lyric(59, "Georgia Gwinnett with love and respect");
        list.add(lyric);
        lyric = new Lyric(67, "Our alma mater Georgia Gwinnett!");
        list.add(lyric);
        lyric = new Lyric(76, "Our alma mater Georgia Gwinnett!!");
        list.add(lyric);
        lyric = new Lyric(83, "Our alma mater Georgia Gwinnett!!");
        list.add(lyric);

    }

    public String generateLyrics(int currentPosition) {

        String lyric = "";

        try {
            for (int i = 0; i < list.size(); i++) {

                int time = list.get(i).getTime() * 1000;
                int nextTime = list.get(i + 1).getTime() * 1000;

                if (currentPosition >= time && currentPosition <= nextTime) {
                    lyric = list.get(i).getLyric();
                    break;
                }
            }
        }
        catch (IndexOutOfBoundsException e) {
            Log.d("EXCEPTION", "IndexOutOfBoundsException");
        }

        return lyric;

    }

    public List<Lyric> getList() {
        return list;
    }

}
