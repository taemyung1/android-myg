package com.example.project1;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TabHost;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

// 프로필 보는 창
public class ProfileActivity extends AppCompatActivity {

    // db
    private DbService dbservice;
    public static Button profile_reclick;

    Button finish_btn3, finish_btn2, finish_btn1, user_in_btn, sos_in_btn, update_btn,
    sos_update_btn1, sos_update_btn2, sos_update_btn3, update_end_btn, sos_del_btn,
    sos_del_btn1, sos_del_btn2, sos_del_btn3;
    TextView profile_name, profile_blood, profile_age, profile_stature, profile_weight, profile_history;
    TextView sos_profile1, sos_profile2, sos_profile3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile );
        profile_reclick = (Button) findViewById(R.id.reClick);
        sos_del_btn1 = (Button) findViewById(R.id.sos_del_btn1);
        sos_del_btn2 = (Button)findViewById(R.id.sos_del_btn2);
        sos_del_btn3 = (Button) findViewById(R.id.sos_del_btn3);
        sos_del_btn = (Button) findViewById(R.id.sos_del_btn);

        update_end_btn = (Button) findViewById(R.id.update_end_btn);
        sos_update_btn1 = (Button) findViewById(R.id.sos_update_btn1);
        sos_update_btn2 = (Button) findViewById(R.id.sos_update_btn2);
        sos_update_btn3 = (Button) findViewById(R.id.sos_update_btn3);
        update_btn = (Button) findViewById(R.id.update_btn);

        sos_in_btn = (Button) findViewById(R.id.sos_in_btn);
        sos_profile1 = (TextView) findViewById(R.id.sos_profile1);
        sos_profile2 = (TextView) findViewById(R.id.sos_profile2);
        sos_profile3 = (TextView) findViewById(R.id.sos_profile3);

        profile_name = (TextView) findViewById(R.id.profile_name);
        profile_blood = (TextView) findViewById(R.id.profile_blood);
        profile_age = (TextView) findViewById(R.id.profile_age);
        profile_stature = (TextView) findViewById(R.id.profile_stature);
        profile_weight = (TextView) findViewById(R.id.profile_weight);
        profile_history = (TextView) findViewById(R.id.profile_history);


        user_in_btn = (Button) findViewById(R.id.user_in_btn);
        finish_btn1 = (Button) findViewById(R.id.finish_btn1);
        finish_btn2 = (Button) findViewById(R.id.finish_btn2);
        finish_btn3 = (Button) findViewById(R.id.finish_btn3);
        TabHost tabHost1 = (TabHost) findViewById(R.id.tabHost1) ;
        tabHost1.setup() ;

        // 첫 번째 Tab. (탭 표시 텍스트:"TAB 1"), (페이지 뷰:"content1")
        TabHost.TabSpec ts1 = tabHost1.newTabSpec("Tab Spec 1") ;
        ts1.setContent(R.id.content1) ;
        ts1.setIndicator("사용자");
        tabHost1.addTab(ts1)  ;

        dbservice = new DbService(this);
        dbservice.open();

        final Cursor iCursor = dbservice.usersetlected();
        if (iCursor.getCount() > 0){
            while (iCursor.moveToNext()) {
                profile_name.setText(String.format("이름 :   %s", iCursor.getString(0)));
                profile_blood.setText(String.format("혈액형 :   %s", iCursor.getString(1)));
                profile_age.setText(String.format("나이 :   %s", iCursor.getString(2)));
                profile_stature.setText(String.format("키 :   %s", iCursor.getString(3)));
                profile_weight.setText(String.format("몸무게 :  %s", iCursor.getString(4)));
                profile_history.setText(String.format("특이사항 :   %s", iCursor.getString(5)));
            }
        }else{
            // profile_blood.setText("사용자 프로필 먼저 등록해 주세요");
        }


        // 두 번째 Tab. (탭 표시 텍스트:"TAB 2"), (페이지 뷰:"content2")
        TabHost.TabSpec ts2 = tabHost1.newTabSpec("Tab Spec 2") ;
        ts2.setContent(R.id.content2) ;
        ts2.setIndicator("SOS") ;
        tabHost1.addTab(ts2) ;

        final String s_name1, s_name2, s_name3, s_phone1, s_phone2, s_phone3, s_rethion1, s_rethion2, s_rethion3;
        Cursor iCursor2 = dbservice.sossetlected();
        if (iCursor2.getCount() > 0){
            while (iCursor2.moveToNext()) {
                s_name1 = iCursor2.getString(0);
                s_phone1 = iCursor2.getString(1);
                s_rethion1 = iCursor2.getString(2);
                sos_profile1.setText(String.format("1 : %s %s %s", s_name1, s_phone1, s_rethion1));
                break;
            }
            while (iCursor2.moveToNext()){
                s_name2 = iCursor2.getString(0);
                s_phone2 = iCursor2.getString(1);
                s_rethion2 = iCursor2.getString(2);
                sos_profile2.setText(String.format("2 : %s %s %s", s_name2, s_phone2, s_rethion2));
                break;
            }
            while (iCursor2.moveToNext()){
                s_name3 = iCursor2.getString(0);
                s_phone3 = iCursor2.getString(1);
                s_rethion3 = iCursor2.getString(2);
                sos_profile3.setText(String.format("3 : %s %s %s", s_name3, s_phone3, s_rethion3));
                break;
            }
        }else{
           // profile_blood.setText("사용자 프로필 먼저 등록해 주세요");
        }

        iCursor.close();
        iCursor2.close();

        // 뒤로가기
        finish_btn3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        finish_btn2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) { finish(); } });
        finish_btn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) { finish(); } });

        user_in_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), ProfileActivityinput.class);
                startActivity(intent);
            }
        });

        finish_btn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        sos_in_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), ProfileActivitySosinput.class);
                startActivity(intent);
            }
        });

        // 삭제 on
        sos_del_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (sos_profile1.getText() != "") {
                    sos_del_btn1.setVisibility(View.VISIBLE);
                    sos_update_btn1.setVisibility(View.GONE);
                }
                if (sos_profile2.getText() != "") {
                    sos_del_btn2.setVisibility(View.VISIBLE);
                    sos_update_btn2.setVisibility(View.GONE);
                }
                if (sos_profile3.getText() != "") {
                    sos_del_btn3.setVisibility(View.VISIBLE);
                    sos_update_btn3.setVisibility(View.GONE);
                }
                update_btn.setVisibility(View.GONE);
                update_end_btn.setVisibility(View.VISIBLE);
                sos_del_btn.setVisibility(View.GONE);
            }
        });

        sos_del_btn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder dig = new AlertDialog.Builder(ProfileActivity.this);
                dig.setMessage("sos 이용자 1를 삭제하시겠습니까?");
                // 확인
                dig.setPositiveButton("삭제", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dbservice.open();
                        Cursor iCursor3 = dbservice.sossetlected();
                        iCursor3.moveToFirst();
                        String sosname1 = iCursor3.getString(0);
                        dbservice.delsosuser(sosname1);

                        iCursor3.close();
                        profile_reclick.callOnClick();
                        dbservice.close();
                        update_end_btn.callOnClick();
                    }
                });
                //취소
                dig.setNegativeButton("취소", null);
                dig.show();
            }
        });

        sos_del_btn2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder dig = new AlertDialog.Builder(ProfileActivity.this);
                dig.setMessage("sos 이용자 2를 삭제하시겠습니까?");
                // 확인

                dig.setPositiveButton("삭제", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dbservice.open();
                        Cursor iCursor3 = dbservice.sossetlected();

                            iCursor3.moveToNext(); iCursor3.moveToNext();
                                String sosname1 = iCursor3.getString(0);
                                dbservice.delsosuser(sosname1);

                        iCursor3.close();
                        profile_reclick.callOnClick();
                        dbservice.close();
                        update_end_btn.callOnClick();
                    }
                });
                //취소
                dig.setNegativeButton("취소", null);
                dig.show();
            }
        });

        sos_del_btn3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder dig = new AlertDialog.Builder(ProfileActivity.this);
                dig.setMessage("sos 이용자 3를 삭제하시겠습니까?");
                // 확인
                dig.setPositiveButton("삭제", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dbservice.open();
                        Cursor iCursor3 = dbservice.sossetlected();
                        iCursor3.moveToLast();
                        String sosname1 = iCursor3.getString(0);
                        dbservice.delsosuser(sosname1);

                        iCursor3.close();
                        profile_reclick.callOnClick();
                        dbservice.close();
                        update_end_btn.callOnClick();
                    }
                });
                //취소
                dig.setNegativeButton("취소", null);
                dig.show();
            }
        });

        // 수정 on
        update_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (sos_profile1.getText() != "") {
                    sos_del_btn1.setVisibility(View.GONE);
                    sos_update_btn1.setVisibility(View.VISIBLE);
                }
                if (sos_profile2.getText() != "") {
                    sos_del_btn2.setVisibility(View.GONE);
                    sos_update_btn2.setVisibility(View.VISIBLE);
                }
                if (sos_profile3.getText() != "") {
                    sos_del_btn3.setVisibility(View.GONE);
                    sos_update_btn3.setVisibility(View.VISIBLE);
                }
                update_btn.setVisibility(View.GONE);
                update_end_btn.setVisibility(View.VISIBLE);
                sos_del_btn.setVisibility(View.GONE);
            }
        });


        sos_update_btn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), ProfileActivitySosUpdate.class);
                intent.putExtra("count", 1);

                startActivity(intent);
            }
        });

        sos_update_btn2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), ProfileActivitySosUpdate.class);
                intent.putExtra("count", 2);

                startActivity(intent);
            }
        });

        sos_update_btn3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), ProfileActivitySosUpdate.class);
                intent.putExtra("count", 3);

                startActivity(intent);
            }
        });

        // 수정 off
        update_end_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sos_update_btn1.setVisibility(View.GONE);
                sos_update_btn2.setVisibility(View.GONE);
                sos_update_btn3.setVisibility(View.GONE);
                sos_del_btn1.setVisibility(View.GONE);
                sos_del_btn2.setVisibility(View.GONE);
                sos_del_btn3.setVisibility(View.GONE);
                update_btn.setVisibility(View.VISIBLE);
                update_end_btn.setVisibility(View.GONE);
                sos_del_btn.setVisibility(View.VISIBLE);
            }
        });

        profile_reclick.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dbservice.open();

                final Cursor iCursor = dbservice.usersetlected();
                if (iCursor.getCount() > 0){
                    while (iCursor.moveToNext()) {
                        profile_name.setText(String.format("이름 :   %s", iCursor.getString(0)));
                        profile_blood.setText(String.format("혈액형 :   %s", iCursor.getString(1)));
                        profile_age.setText(String.format("나이 :   %s", iCursor.getString(2)));
                        profile_stature.setText(String.format("키 :   %s", iCursor.getString(3)));
                        profile_weight.setText(String.format("몸무게 :  %s", iCursor.getString(4)));
                        profile_history.setText(String.format("특이사항 :   %s", iCursor.getString(5)));
                    }
                }else{
                    // profile_blood.setText("사용자 프로필 먼저 등록해 주세요");
                }
                iCursor.close();

                final String s_name1, s_name2, s_name3, s_phone1, s_phone2, s_phone3, s_rethion1, s_rethion2, s_rethion3;
                Cursor iCursor2 = dbservice.sossetlected();
                if (iCursor2.getCount() > 0){
                    while (iCursor2.moveToNext()) {
                        s_name1 = iCursor2.getString(0);
                        s_phone1 = iCursor2.getString(1);
                        s_rethion1 = iCursor2.getString(2);
                        sos_profile1.setText(String.format("1 : %s %s %s", s_name1, s_phone1, s_rethion1));
                        sos_profile2.setText("");
                        sos_profile3.setText("");
                        break;
                    }
                    while (iCursor2.moveToNext()){
                        s_name2 = iCursor2.getString(0);
                        s_phone2 = iCursor2.getString(1);
                        s_rethion2 = iCursor2.getString(2);
                        sos_profile2.setText(String.format("2 : %s %s %s", s_name2, s_phone2, s_rethion2));
                        sos_profile3.setText("");
                        break;
                    }
                    while (iCursor2.moveToNext()){
                        s_name3 = iCursor2.getString(0);
                        s_phone3 = iCursor2.getString(1);
                        s_rethion3 = iCursor2.getString(2);
                        sos_profile3.setText(String.format("3 : %s %s %s", s_name3, s_phone3, s_rethion3));
                        break;
                    }
                }else{
                    // profile_blood.setText("사용자 프로필 먼저 등록해 주세요");
                }

                iCursor2.close();
                dbservice.close();
            }
        });
    }
}
