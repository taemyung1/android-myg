package com.example.project1;

import android.content.Intent;
import android.database.Cursor;
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

    private CountDownTimer mCountDown, countDownTimer;
    private long timeLeftInMilliseconds; //30초
    private boolean timerRunning;
    private DbService dbservice;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detection);

        countdownText = findViewById(R.id.countdown_text);
        btnbigbutton = findViewById(R.id.btnbigbutton);

        dbservice = dbservice = new DbService(this);
        dbservice.open();
        try {
            Cursor icursor = dbservice.timesetlected();
            if(icursor.getCount() == 0){
                timeLeftInMilliseconds = 30000;
            }else{
                icursor.moveToLast();
                timeLeftInMilliseconds = Integer.parseInt(icursor.getString(0) + "000");

            }
            icursor.close();
        }catch (Exception e){
            timeLeftInMilliseconds = 30000;
        }
        dbservice.close();

        mCountDown = new CountDownTimer(timeLeftInMilliseconds, 1000) {

            public void onTick(long millisUntilFinished) {
                countdownText.setText(""+ millisUntilFinished / 1000);
                Log.i("@@@","!@#123");
            }

            public void onFinish() {
                Intent intent = new Intent(getApplicationContext(), Shock_detection_v.class);
                startActivity(intent);
                finish();
            }

        }.start();


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
            mCountDown.cancel();
            finish();
            //인텐트

        }
    }
});
    }
}