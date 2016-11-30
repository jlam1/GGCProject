package com.example.john.finalproject;
/**
 * Created by King on 11/29/2016.
 */
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Html;
import android.widget.TextView;

import be.tarsos.dsp.AudioDispatcher;
import be.tarsos.dsp.AudioEvent;
import be.tarsos.dsp.AudioProcessor;
import be.tarsos.dsp.io.android.AudioDispatcherFactory;
import be.tarsos.dsp.pitch.PitchDetectionHandler;
import be.tarsos.dsp.pitch.PitchDetectionResult;
import be.tarsos.dsp.pitch.PitchProcessor;

public class NoteRecognizerActivity extends AppCompatActivity {
    private Note note;
    private TextView text;
    private Handler handler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_note_recognizer);

        handler = new Handler();

        /**
         * This will handle inputs from microphone and display a Note depending on pitch(in hertz).
         * Note will display the letter, subscript, and superscript.
         */
        AudioDispatcher dispatcher = AudioDispatcherFactory.fromDefaultMicrophone(22050,1024,0);
        PitchDetectionHandler pdh = new PitchDetectionHandler() {

            @Override
            public void handlePitch(PitchDetectionResult result, AudioEvent e) {
                final float pitchInHz = result.getPitch();
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        text = (TextView) findViewById(R.id.tvNote);
                        note = new NoteRecognizer().convertToNote(pitchInHz);

                        handler.post(new Runnable() {
                            @Override
                            public void run() {
                                if(note != null) {
                                    text.setText(Html.fromHtml(note.getNoteLetter() + "<sup><small>" + note.getSubscript() + "</small></sup><sub><small>" + note.getSuperscript() + "</small></sub>"));
                                }
                                else {
                                    text.setText("--");
                                }
                            }
                        });
                    }
                });
            }
        };

        AudioProcessor p = new PitchProcessor(PitchProcessor.PitchEstimationAlgorithm.FFT_YIN, 22050, 1024, pdh);
        dispatcher.addAudioProcessor(p);
        new Thread(dispatcher,"Audio Dispatcher").start();


    }



}
