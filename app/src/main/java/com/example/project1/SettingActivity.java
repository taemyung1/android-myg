package com.example.project1;


import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class SettingActivity extends AppCompatActivity {

    private DbService dbservice;
    Button finish_btn, delete_btn, setting_btn2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);

        finish_btn = (Button) findViewById(R.id.finish_btn);
        delete_btn = (Button) findViewById(R.id.delete_btn);
        setting_btn2 = (Button) findViewById(R.id.setting_btn2);

        // 돌아가기
        finish_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        dbservice = new DbService(this);

        //사용자 정보 삭제버튼
        delete_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder dig = new AlertDialog.Builder(SettingActivity.this);
                dig.setMessage("사용장 프로필을 삭제 하시겠습니까?");

                // 확인
                dig.setPositiveButton("확인", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Toast.makeText(SettingActivity.this, "확인 처리",Toast.LENGTH_SHORT).show();
                        dbservice.open();
                        dbservice.dropuser();
                        dbservice.close();
                    }
                });

                //취소
                dig.setNegativeButton("취소", null);
                dig.show();

            }
        });

        // sos 타이머 설정 버튼
        setting_btn2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), Timer_setting.class);
                startActivity(intent);

            }
        });



    }
}
