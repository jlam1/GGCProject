package com.example.john.finalproject;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by John Lam on 11/9/16.
 */

public class NoteRecognizer {

    private static Map<Note, Float> map;
    private final float WEIGHT = 0.025f;

    /**
     * @method Puts notes into a Map<Note, Float> where float is the corresponding hertz to each musical notation.
     */
    static {
        map = new HashMap<Note, Float>();

        String sharp = "♯";
//        String flat = "♭";

        map.put(new Note("D", "", "1"), 36.71f);            map.put(new Note("D", sharp, "1"), 38.89f);
        map.put(new Note("E", "", "1"), 41.20f);
        map.put(new Note("F", "", "1"), 43.65f);            map.put(new Note("F", sharp, "1"), 46.25f);
        map.put(new Note("G", "", "1"), 49.00f);            map.put(new Note("G", sharp, "1"), 51.91f);
        map.put(new Note("A", "", "1"), 55.00f);            map.put(new Note("A", sharp, "1"), 58.27f);
        map.put(new Note("B", "", "1"), 61.74f);
        map.put(new Note("C", "", "2"), 65.41f);            map.put(new Note("C", sharp, "2"), 69.30f);
        map.put(new Note("D", "", "2"), 73.42f);            map.put(new Note("D", sharp, "2"), 77.78f);
        map.put(new Note("E", "", "2"), 82.41f);
        map.put(new Note("F", "", "2"), 87.31f);            map.put(new Note("F", sharp, "2"), 92.50f);
        map.put(new Note("G", "", "2"), 98.00f);            map.put(new Note("G", sharp, "2"), 103.83f);
        map.put(new Note("A", "", "2"), 110.00f);           map.put(new Note("A", sharp, "2"), 116.54f);
        map.put(new Note("B", "", "2"), 123.47f);
        map.put(new Note("C", "", "3"), 130.81f);           map.put(new Note("C", sharp, "3"), 138.59f);
        map.put(new Note("D", "", "3"), 146.83f);           map.put(new Note("D", sharp, "3"), 155.56f);
        map.put(new Note("E", "", "3"), 164.81f);
        map.put(new Note("F", "", "3"), 174.61f);           map.put(new Note("F", sharp, "3"), 185.00f);
        map.put(new Note("G", "", "3"), 196.00f);           map.put(new Note("G", sharp, "3"), 207.65f);
        map.put(new Note("A", "", "3"), 220.00f);           map.put(new Note("A", sharp, "3"), 233.08f);
        map.put(new Note("B", "", "3"), 246.94f);
        map.put(new Note("C", "", "4"), 261.63f);           map.put(new Note("C", sharp, "4"), 277.18f);
        map.put(new Note("D", "", "4"), 293.66f);           map.put(new Note("D", sharp, "4"), 311.13f);
        map.put(new Note("E", "", "4"), 329.63f);
        map.put(new Note("F", "", "4"), 349.23f);           map.put(new Note("F", sharp, "4"), 369.99f);
        map.put(new Note("G", "", "4"), 392.00f);           map.put(new Note("G", sharp, "4"), 415.30f);
        map.put(new Note("A", "", "4"), 440.00f);           map.put(new Note("A", sharp, "4"), 466.16f);
        map.put(new Note("B", "", "4"), 493.88f);
        map.put(new Note("C", "", "5"), 523.25f);           map.put(new Note("C", sharp, "5"), 554.37f);
        map.put(new Note("D", "", "5"), 587.33f);           map.put(new Note("D", sharp, "5"), 622.25f);
        map.put(new Note("E", "", "5"), 659.25f);
        map.put(new Note("F", "", "5"), 698.46f);           map.put(new Note("F", sharp, "5"), 739.99f);
        map.put(new Note("G", "", "5"), 783.99f);           map.put(new Note("G", sharp, "5"), 830.61f);
        map.put(new Note("A", "", "5"), 880.61f);           map.put(new Note("A", sharp, "5"), 932.33f);
        map.put(new Note("B", "", "5"), 987.77f);
        map.put(new Note("C", "", "6"), 1046.50f);          map.put(new Note("C", sharp, "6"), 1108.73f);
        map.put(new Note("D", "", "6"), 1174.66f);          map.put(new Note("D", sharp, "6"), 1244.51f);

    }

    /**
     * @method Iterates map and finds the key of value currentPitch.
     *          If the value of currentPitch is within range of the note's value(in this case depending on the WEIGHT), return note.
     * @param currentPitch
     * @return Note
     */
    public Note convertToNote(float currentPitch) {

        Note note = null;

        for(Map.Entry<Note, Float> entry : map.entrySet()) {
            Note keyNote = entry.getKey();
            float pitch = entry.getValue();

            float range = (pitch * WEIGHT);
            float minRange = (pitch - range);
            float maxRange = (pitch + range);

            if(currentPitch > minRange && currentPitch < maxRange) {
                note = keyNote;
                break;
            }
        }

        return note;
    }

}
