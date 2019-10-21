package com.example.project1;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class ProfileActivitySosinput  extends AppCompatActivity {

    // db
    private DbService dbservice;

    Button finish_btn1, sos_input_btn;
    TextView sos_input_name, sos_input_phone, sos_input_relation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile_sos_in);

        finish_btn1 = (Button) findViewById(R.id.button1);
        sos_input_btn = (Button) findViewById(R.id.button2);
        sos_input_phone = (TextView) findViewById(R.id.input_phone2);
        sos_input_name = (TextView) findViewById(R.id.input_name2);
        sos_input_relation = (TextView) findViewById(R.id.input_relation2);

        dbservice = new DbService(this);
        dbservice.open();

        sos_input_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name = sos_input_name.getText().toString();
                String relation = sos_input_relation.getText().toString();
                String a = sos_input_phone.getText().toString(); int phone = Integer.parseInt(a);

                try {
                    dbservice.sosIn(name, 0+phone, relation);
                    ProfileActivity.profile_reclick.callOnClick();
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
