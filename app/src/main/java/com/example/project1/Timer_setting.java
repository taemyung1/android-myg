package com.example.project1;

import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.SeekBar;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class Timer_setting  extends AppCompatActivity {

    private DbService dbService;
    Button finish_btn2, finish_btn1, input_btn;
    SeekBar timebar;
    TextView time_v, now_time_v;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting_timer);

        now_time_v = (TextView) findViewById(R.id.now_time_v);
        time_v = (TextView) findViewById(R.id.time_v);
        timebar = (SeekBar) findViewById(R.id.seekBar);
        input_btn = (Button) findViewById(R.id.input_btn);
        finish_btn1 = (Button) findViewById(R.id.finish_btn1);
        finish_btn2 = (Button) findViewById(R.id.finish_btn2);

        dbService = new DbService(this);
        dbService.open();


        Cursor iCursor = dbService.timesetlected();
        if (iCursor.getCount() > 0){
            while (iCursor.moveToNext()) {
                now_time_v.setText(String.format("현재 타이머 : %s", iCursor.getString(0)));
            }
        }else{
            now_time_v.setText("타이머 설정 해주세요 ");
        }

        // 돌아가기
        finish_btn2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        finish_btn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        //bar 설정
        timebar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                time_v.setText(String.valueOf(progress + 30 + "초"));
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

        //넣기
        input_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int time;
                time = 30 + timebar.getProgress();
                dbService.timein(time);

                dbService.close();
                finish();
            }
        });



    }

}
