package com.example.project1;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class ProfileActivitySosUpdate extends AppCompatActivity {

    // db
    private DbService dbservice;

    Button finish_btn1, sos_update_btn;
    TextView sos_update_name;
    EditText sos_update_phone, sos_update_relation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sos_update);

        finish_btn1 = (Button) findViewById(R.id.finish_btn);
        sos_update_btn = (Button) findViewById(R.id.update_btn);
        sos_update_phone = (EditText) findViewById(R.id.sos_update_phone);
        sos_update_name = (TextView) findViewById(R.id.sos_update_name);
        sos_update_relation = (EditText) findViewById(R.id.sos_update_relation);

        Intent getintent = getIntent();
        int count = 0 ;
        count = getintent.getExtras().getInt("count");
        dbservice = new DbService(this);
        dbservice.open();
        Cursor iCursor2 = dbservice.sossetlected();

        if( count > 1){
            if (count > 2){
                //3번 수정
                if (iCursor2.getCount() > 0) {
                    while (iCursor2.moveToNext()) {
                        sos_update_name.setText(iCursor2.getString(0));
                        sos_update_phone.setText(iCursor2.getString(1));
                        sos_update_relation.setText(iCursor2.getString(2));
                    }
                }
            }else {
                //2번 수정
                if (iCursor2.getCount() > 0) {
                    while (iCursor2.moveToNext()) {
                        sos_update_name.setText(iCursor2.getString(0));
                        sos_update_phone.setText(iCursor2.getString(1));
                        sos_update_relation.setText(iCursor2.getString(2));
                        break;
                    }
                    while (iCursor2.moveToNext()) {
                        sos_update_name.setText(iCursor2.getString(0));
                        sos_update_phone.setText(iCursor2.getString(1));
                        sos_update_relation.setText(iCursor2.getString(2));
                        break;
                    }
                }
            }
        }else {
            // 1번 수정
            if (iCursor2.getCount() > 0) {
                while (iCursor2.moveToNext()) {
                    sos_update_name.setText(iCursor2.getString(0));
                    sos_update_phone.setText(iCursor2.getString(1));
                    sos_update_relation.setText(iCursor2.getString(2));
                    break;
                }
            }
        }

        sos_update_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name = sos_update_name.getText().toString();
                String relation = sos_update_relation.getText().toString();
                String a = sos_update_phone.getText().toString(); int phone = Integer.parseInt(a);
                try {
                    dbservice.sosupdate(name, phone, relation);
                    dbservice.close();
                    finish();
                }catch (Exception e){
                    Toast.makeText(getApplicationContext(), "빈칸 없이 입력해주세요",Toast.LENGTH_SHORT);
                }
            }
        });

        finish_btn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
}
