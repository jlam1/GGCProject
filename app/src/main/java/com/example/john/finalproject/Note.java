package com.example.john.finalproject;

/**
 * Created by John on 11/10/2016.
 */

public class Note {

    private String noteLetter, subscript, superscript;

    public Note(String noteLetter, String superscript, String subscript) {
        this.noteLetter = noteLetter;
        this.subscript = superscript;
        this.superscript = subscript;
    }

    public String getNoteLetter() {
        return noteLetter;
    }

    public String getSuperscript() {
        return superscript;
    }

    public String getSubscript() {

        return subscript;
    }
}
