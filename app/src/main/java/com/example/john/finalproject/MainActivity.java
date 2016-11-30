package com.example.john.finalproject;

import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Html;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;
import android.widget.SeekBar;
import android.widget.TextView;

import be.tarsos.dsp.AudioDispatcher;
import be.tarsos.dsp.AudioEvent;
import be.tarsos.dsp.AudioProcessor;
import be.tarsos.dsp.io.android.AudioDispatcherFactory;
import be.tarsos.dsp.pitch.PitchDetectionHandler;
import be.tarsos.dsp.pitch.PitchDetectionResult;
import be.tarsos.dsp.pitch.PitchProcessor;

public class MainActivity extends AppCompatActivity {

    private TextView tvCurrentDuration, tvTotalDuration, tvLyrics;
    private MediaPlayer mp;
    private Handler mHandler;
    private ImageButton playBtn, stopBtn, pauseBtn;
    private SeekBar audioBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setActionBarIcon();

        tvLyrics            = (TextView) 	findViewById(R.id.tvLyrics);
        tvCurrentDuration 	= (TextView) 	findViewById(R.id.tvDurationUpdate);
        tvTotalDuration 	= (TextView) 	findViewById(R.id.tvDurationMax);
        playBtn 			= (ImageButton) findViewById(R.id.resumeBtn);
        stopBtn 			= (ImageButton) findViewById(R.id.stopBtn);
        pauseBtn 			= (ImageButton) findViewById(R.id.pauseBtn);
        audioBar 			= (SeekBar) 	findViewById(R.id.audioSeekBar);

        mp          = MediaPlayer.create(this, R.raw.ggc_alma_mater);
        mHandler    = new Handler();

        tvTotalDuration.setText(getTimeString(mp.getDuration()));

        /**
         * This class is responsible for updating UI based on the current position(in milliseconds) of the media player.
         * It will update the seekbar and display lyrics in sync with the media player.
         */
        class Task implements Runnable {
            @Override
            public void run() {

                int currentPosition = 0;
                int total = mp.getDuration();
                audioBar.setMax(total);
                while (mp != null && currentPosition < total) {
                    try {
                        Thread.sleep(500);
                        currentPosition = mp.getCurrentPosition();
                    } catch (InterruptedException e) {
                        return;
                    } catch (Exception e) {
                        return;
                    }
                    final int finalCurrentPosition = currentPosition;
                    final String newLyric = new LyricGenerator().generateLyrics(finalCurrentPosition);
                    mHandler.post(new Runnable() {
                        @Override
                        public void run() {
                            audioBar.setProgress(finalCurrentPosition);
                            tvCurrentDuration.setText(getTimeString(finalCurrentPosition));
                            Log.d("currentPosition", "" + finalCurrentPosition);
                            Log.d("LYRICS", "" + newLyric);
                            tvLyrics.setText(newLyric);
                        }
                    });
                }
            }
        }

        new Thread(new Task()).start();

        /**
         * Media Player on complete
         */
        mp.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mp) {
                if(!mp.isPlaying()) {
                    mp.seekTo(0);
                    mp.pause();
                }
            }
        });

        /**
         * Seekbar listener
         */
        audioBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                if(fromUser)
                    mp.seekTo(progress);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

        /**
         * Play media button
         */
        playBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mp.start();
            }
        });

        /**
         * Stop media button
         */
        stopBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(mp.isPlaying()) {
                    mp.pause();
                    mp.seekTo(0);
                    audioBar.setProgress(0);
                }
            }
        });

        /**
         * Pause media button
         */
        pauseBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(mp.isPlaying()) {
                    mp.pause();
                }
            }
        });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.about_settings:
                Intent intent = new Intent(this, AboutActivity.class);
                this.startActivity(intent);
                break;
            default:
                return super.onOptionsItemSelected(item);
        }

        return true;
    }

    @Override
    public void onPause() {
        super.onPause();
        mp.pause();
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    public void onStop() {
        Log.i("void", "onStop()");
        super.onStop();
        mp.pause();
    }

    @Override
    public void onDestroy() {
        Log.i("void", "onDestroy()");
        super.onDestroy();
        mp.release();
    }

    /**
     * @method convert int to readable String formated "mm:ss"
     * @param millis
     * @return String
     */
    private String getTimeString(int millis) {
        StringBuffer buf = new StringBuffer();

        int minutes = ((millis % (1000 * 60 * 60)) / (1000 * 60));
        int seconds = (((millis % (1000 * 60 * 60)) % (1000 * 60)) / 1000);

        buf
                .append(String.format("%02d", minutes))
                .append(":")
                .append(String.format("%02d", seconds));

        return buf.toString();
    }

    /**
     * @method sets icon next to title in actionbar
     */
    public void setActionBarIcon() {
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setLogo(R.mipmap.ic_launcher);
        getSupportActionBar().setDisplayUseLogoEnabled(true);
    }
    /**
     * method:goToNoteRecognizer()
     * @param view
     * @method starts a new note recognizer activity
     */
    public void goToNoteRecognizer(View view){
        startActivity(new Intent(this, NoteRecognizerActivity.class));
    }
}
