package com.example.project1;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;



public class ProfileActivityinput extends AppCompatActivity {

    // db
    DbService dbservice;
    MainActivity mainActivity;
    ProfileActivity profileActivity;
    Button finish_btn1, finish_btn2, input_btn;
    TextView input_name, input_blood, input_age, input_stature, input_weight, input_history;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile_in);

        finish_btn2 = (Button) findViewById(R.id.finish_btn1);
        finish_btn1 = (Button) findViewById(R.id.finish_btn2);
        input_btn = (Button) findViewById(R.id.input_btn);

        input_name = (TextView) findViewById(R.id.input_name);
        input_blood = (TextView) findViewById(R.id.input_bloodtype1);
        input_age = (TextView) findViewById(R.id.input_age);
        input_stature = (TextView) findViewById(R.id.input_height);
        input_weight = (TextView) findViewById(R.id.input_weight);
        input_history = (TextView) findViewById(R.id.input_operation);


        dbservice = new DbService(this);
        dbservice.open();

        input_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                if (input_name.getText().length() <= 0 || input_blood.getText().length() <= 0 ||
                input_age.getText().length() <= 0 || input_stature.getText().length() <= 0 || input_weight.getText().length() <= 0)
                {

                    Toast.makeText(getApplicationContext()," 아이디 입력 해주세요",Toast.LENGTH_SHORT).show();

                } else {
                    String name1 = input_name.getText().toString();
                    String blood = input_blood.getText().toString();
                    String a = input_age.getText().toString();
                    int age = Integer.parseInt(a);
                    a = input_stature.getText().toString();
                    int stature = Integer.parseInt(a);
                    a = input_weight.getText().toString();
                    int weight = Integer.parseInt(a);

                    String history = input_history.getText().toString();

                    try{
                        dbservice.userIn(name1, blood, age, stature, weight, history);
                    //    profileActivity.profileme();
                        finish();
                    }catch (Exception e){
                        Toast.makeText(getApplicationContext(), "빈칸 없이 입력해주세요",Toast.LENGTH_SHORT);
                    }



                }

            }
        });

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


    }
}
