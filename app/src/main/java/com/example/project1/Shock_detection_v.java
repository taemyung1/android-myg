package com.example.project1;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;

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
        final LocationManager lm = (LocationManager) getSystemService(Context.LOCATION_SERVICE);

        String sendtext1;
        String sendpnum;
        dbservice = dbservice = new DbService(this);
        dbservice.open();
        Cursor iCursor;
        iCursor = dbservice.usersetlected();
        if (iCursor.getCount() > 0) {
            while (iCursor.moveToNext()) {
                detection_name.setText(String.format("   %s", iCursor.getString(0)));
                detection_blood.setText(String.format("   혈액형: %s", iCursor.getString(1)));
                detection_age.setText(String.format(" 나이: %s", iCursor.getString(2)));
                detection_height.setText(String.format(" 키: %s", iCursor.getString(3)));
            }
        } else {
            detection_age.append("사용자 프로필 먼저 등록해 주세요");
        }
        sendtext1 = detection_name.getText().toString() + "님이 사고를 당했습니다. \n";

        iCursor.close();

        if (checkSelfPermission(Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED
                && checkSelfPermission(Manifest.permission.ACCESS_COARSE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {
            return;
        }
        Location location = lm.getLastKnownLocation(LocationManager.GPS_PROVIDER);
        String provider = location.getProvider();
        double longitude = location.getLongitude();
        double latitude = location.getLatitude();
        double altitude = location.getAltitude();
        sendtext1 += "확인바랍니다. \n ";
        sendtext1 += "위도 :"+latitude+"\n 경도 :"+longitude+"\n 고도 :"+altitude +"\n"+
                    "https://maps.google.com/maps?f=q&q=("+latitude+","+longitude+")";


        iCursor = dbservice.sossetlected();
        if (iCursor.getCount() > 0) {
            while (iCursor.moveToNext()) {
                sendpnum = iCursor.getString(1);
                SendSMS(sendpnum,sendtext1);
                Log.i("3","3");

            }
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
    private void SendSMS(String phonenumber, String message) {
        SmsManager smsManager = SmsManager.getDefault();

        String sendTo = "0"+phonenumber;
        ArrayList<String> partMessage = smsManager.divideMessage(message);
        Log.i("4",sendTo + message);
        smsManager.sendMultipartTextMessage(sendTo, null, partMessage, null, null);
        Toast.makeText(getApplicationContext(), "전송되었습니다.", Toast.LENGTH_SHORT).show();

    }
    final LocationListener gpsLocationListener = new LocationListener() {
        public void onLocationChanged(Location location) {

            String provider = location.getProvider();
            double longitude = location.getLongitude();
            double latitude = location.getLatitude();
            double altitude = location.getAltitude();


        }

        public void onStatusChanged(String provider, int status, Bundle extras) {
        }

        public void onProviderEnabled(String provider) {
        }

        public void onProviderDisabled(String provider) {
        }
    };

}