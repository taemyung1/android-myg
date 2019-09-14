package com.example.project1;

import android.bluetooth.BluetoothAdapter;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import static com.example.project1.bluetoothService.BT_REQUEST_ENABLE;


//메인화면
public class MainActivity extends AppCompatActivity {

    // DB 관리
    private DbService dbservice;
    // 블루투스 관리
    private BluetoothAdapter btAdater;
    public bluetoothService btService = new bluetoothService();
    Handler mBluetoothHandler;
    BluetoothAdapter mBluetoothAdapter;


    Button bt_on, bt_off, setting_btn, profile_btn, sensing_btn, gps_btn;
    TextView main_name, main_blood, main_age, main_height;
    private static final int REQUEST_ENABLE_BT = 2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        gps_btn = (Button) findViewById(R.id.gps_btn);
        sensing_btn = (Button) findViewById(R.id.sensing_btn);
        profile_btn = (Button) findViewById(R.id.profile_btn);
        main_name = (TextView) findViewById(R.id.main_name);
        main_blood = (TextView) findViewById(R.id.main_blood);
        main_age = (TextView) findViewById(R.id.main_age);
        main_height = (TextView) findViewById(R.id.main_height);
        setting_btn = (Button) findViewById(R.id.setting_btn);
        bt_on = (Button) findViewById(R.id.btn_connected);
        bt_off = (Button) findViewById(R.id.btn_connect);
        btAdater = BluetoothAdapter.getDefaultAdapter();

        if(btAdater == null) {
            Toast.makeText(getApplicationContext(), "블루투스를 지원하지 않는 기기입니다.", Toast.LENGTH_LONG).show();
        }
        else {
            if (btAdater.isEnabled()) {
                bt_off.setVisibility(View.VISIBLE);
                bt_on.setVisibility(View.GONE);
                sensing_btn.setVisibility(View.VISIBLE);
            }
            else {
                bt_off.setVisibility(View.GONE);
                bt_on.setVisibility(View.VISIBLE);
                sensing_btn.setVisibility(View.GONE);

            }
        }

        dbservice = new DbService(this);
        dbservice.open();
        dbservice.create();

        Cursor iCursor = dbservice.usersetlected();
        if (iCursor.getCount() > 0){
            while (iCursor.moveToNext()) {
                main_name.setText(String.format("   %s", iCursor.getString(0)));
                main_blood.setText(String.format("   혈액형: %s", iCursor.getString(1)));
                main_age.setText(String.format(" 나이: %s", iCursor.getString(2)));
                main_height.setText(String.format(" 키: %s", iCursor.getString(3)));
            }
        }else{
            main_blood.append("사용자 프로필 먼저 등록해 주세요");
        }

        iCursor.close();
        dbservice.close();

        //블루투스 켜기 버튼
        bt_on.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(btAdater == null) {
                    Toast.makeText(getApplicationContext(), "블루투스를 지원하지 않는 기기입니다.", Toast.LENGTH_LONG).show();
                }
                else {
                    if (btAdater.isEnabled()) {
                        sensing_btn.setVisibility(View.VISIBLE);
                        bt_off.setVisibility(View.VISIBLE);
                        bt_on.setVisibility(View.GONE);
                        Toast.makeText(getApplicationContext(), "블루투스가 이미 활성화 되어 있습니다.", Toast.LENGTH_LONG).show();
                    }
                    else {
                        Intent intentBluetoothEnable = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
                        startActivityForResult(intentBluetoothEnable, BT_REQUEST_ENABLE);
                        sensing_btn.setVisibility(View.VISIBLE);
                        bt_off.setVisibility(View.VISIBLE);
                        bt_on.setVisibility(View.GONE);

                    }
                }
            }
        });

        // 블루투스 끄기 버튼
        bt_off.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                btService.bluetoothOff();
                sensing_btn.setVisibility(View.GONE);
                bt_off.setVisibility(View.GONE);
                bt_on.setVisibility(View.VISIBLE);
            }
        });

        // 셋팅 화면 버튼
        setting_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), SettingActivity.class);
                startActivity(intent);
            }
        });

        //감지 시작 화면
        sensing_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), SensingActivity.class);
                startActivity(intent);
            }
        });
        // 프로필 확인 시작화면
        profile_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), ProfileActivity.class);
                startActivity(intent);
            }
        });

        gps_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), GpsActivity.class);
                startActivity(intent);
            }
        });

    }

    //startActivityForResult 실행 후 결과를 처리하는 부분
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data){
        if(requestCode == REQUEST_ENABLE_BT){
            if(resultCode == RESULT_OK){
                //블루투스가 활성화 되었다.
                Toast.makeText(getApplicationContext(), "블루투스가 활성화 되었습니다.", Toast.LENGTH_SHORT).show();

            } else if(resultCode == RESULT_CANCELED){
                //블루투스 켜는것을 취소했다.
                Toast.makeText(getApplicationContext(), "블루투스가 활성화 되지 않았습니다.", Toast.LENGTH_SHORT).show();

            }
        }
    }
}




