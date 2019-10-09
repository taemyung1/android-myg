package com.example.project1;

import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class Shock_detection_v extends AppCompatActivity {

    Button back_btn;
    TextView detection_name, detection_blood, detection_age, detection_height;
    private DbService dbservice;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detection_v);



        back_btn = (Button) findViewById(R.id.back_btn);
        detection_name = (TextView) findViewById(R.id.detection_name);
        detection_blood = (TextView) findViewById(R.id.detection_blood);
        detection_age = (TextView) findViewById(R.id.detection_age);
        detection_height = (TextView) findViewById(R.id.detection_height);

        dbservice = dbservice = new DbService(this);
        dbservice.open();

        Cursor iCursor = dbservice.usersetlected();
        if (iCursor.getCount() > 0){
            while (iCursor.moveToNext()) {
                detection_name.setText(String.format("   %s", iCursor.getString(0)));
                detection_blood.setText(String.format("   혈액형: %s", iCursor.getString(1)));
                detection_age.setText(String.format(" 나이: %s", iCursor.getString(2)));
                detection_height.setText(String.format(" 키: %s", iCursor.getString(3)));
            }
        }else{
            detection_age.append("사용자 프로필 먼저 등록해 주세요");
        }

        iCursor.close();
        dbservice.close();

        back_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });


    }
}