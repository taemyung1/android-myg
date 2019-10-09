package com.example.project1;

import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class Shock_detection extends AppCompatActivity {

    private TextView  countdownText;
    private Button  btnbigbutton;
    private long btnPressTime = 0;

    private CountDownTimer countDownTimer;
    private long timeLeftInMilliseconds = 30000; //30초
    private boolean timerRunning;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detection);

        countdownText = findViewById(R.id.countdown_text);
        btnbigbutton = findViewById(R.id.btnbigbutton);
        startTimer();

btnbigbutton.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View view) {
        if (System.currentTimeMillis() > btnPressTime + 1000) {
            btnPressTime = System.currentTimeMillis();
            Toast.makeText(Shock_detection.this, "한번더 터치 해주세요", Toast.LENGTH_SHORT).show();
            return;
        }
        if (System.currentTimeMillis() <= btnPressTime + 1000) {
            Toast.makeText(Shock_detection.this, "사용자의 상태를 확인했습니다", Toast.LENGTH_SHORT).show();
            stopTimer();

            finish();
            //인텐트

        }
    }
});


    }

    public void startStop() {
        if (timerRunning) {
            stopTimer();
        } else {
            startTimer();
        }
    }

    public void startTimer() {
        countDownTimer = new CountDownTimer(timeLeftInMilliseconds, 1000) {
            @Override
            public void onTick(long l) {
                timeLeftInMilliseconds = l;
                updateTimer();
            }
            @Override
            public void onFinish() {

            }
        }.start();
        timerRunning = true;
    }

    public void stopTimer() {
        countDownTimer.cancel();
        timerRunning = false;

    }

    public void updateTimer() {
        int minutes = (int) timeLeftInMilliseconds / 30000;
        int seconds = (int) timeLeftInMilliseconds % 30000 / 1000 ;


        String timeLeftText;

        timeLeftText = "" + minutes;
        timeLeftText += ":";
        if (seconds < 10) timeLeftText += "0";
        timeLeftText += seconds;
        Log.d("1", "updateTimer: 1");

        countdownText.setText(timeLeftText);
        if (timeLeftText.equals("0:00")){
            Intent intent = new Intent(getApplicationContext(), Shock_detection_v.class);
            startActivity(intent);
        }
    }

}