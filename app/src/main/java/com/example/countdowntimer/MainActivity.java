package com.example.countdowntimer;

import androidx.appcompat.app.AppCompatActivity;

import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
    SeekBar timerSeekBar;
    TextView timerTextView;
    Boolean counterIsActive = false;
    CountDownTimer countDownTimer;
    Button controllerButton;
    ImageView faceImage;

    public void resetTimer() {
        timerTextView.setText(R.string.count);
        timerSeekBar.setEnabled(true);
        timerSeekBar.setProgress(30);
        controllerButton.setText(R.string.go);
        countDownTimer.cancel();
        counterIsActive = false;

    }


    public void updateTimer(int timeLeft) {

        int munites = timeLeft / 60;
        int seconds = timeLeft - munites * 60;

        String secondString = Integer.toString(seconds);
        String firstString = Integer.toString(munites);
        if (seconds <= 9) {
            secondString = "0" + secondString;
        }

        timerTextView.setText(String.format("%s:%s", firstString, secondString));

    }

    public void controlTimer(View view) {

        if (!counterIsActive) {
            counterIsActive = true;
            timerSeekBar.setEnabled(false);
            controllerButton.setText(R.string.stop);

            countDownTimer = new CountDownTimer(timerSeekBar.getProgress() * 1000, 1000) {
                @Override
                public void onTick(long millisUntilFinished) {
                    notify();
                    updateTimer((int) millisUntilFinished / 1000);
                }

                @Override
                public void onFinish() {
                    resetTimer();
                    MediaPlayer mediaPlayer = MediaPlayer.create(getApplicationContext(), R.raw.bellsound);
                    mediaPlayer.start();

                }
            }.start();

        } else {

            resetTimer();
         }
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        faceImage = findViewById(R.id.faceImage);
        timerSeekBar = findViewById(R.id.timerSeekbar);
        timerTextView = findViewById(R.id.timerTextView);
        controllerButton = findViewById(R.id.controllerButton);
        timerSeekBar.setMax(600);
        timerSeekBar.setProgress(30);

        timerSeekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                updateTimer(progress);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });
    }
}
