package com.example.project1;

import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;

public class DbService {

    final static String dbName = "t3.db";
    final static int dbVersion = 1;
    public static SQLiteDatabase sqliteDB;
    private DbServiceHelper dbHelper;
    private Context mCtx;



    private class DbServiceHelper extends SQLiteOpenHelper {

        // database 파일 생성
        public DbServiceHelper(Context context, String name, CursorFactory factory, int version) {
            super(context, name, factory, version);
        }


        //처음 테이블 생성
        public void onCreate(SQLiteDatabase db) {
                    // user 테이블
                    db.execSQL("CREATE TABLE IF NOT EXISTS USER (NAME CHAR(20) PRIMARY KEY, BLOODTYPE CHAR(20) not null,AGE INTEGER not null, " +
                            "HEIGHT INTEGER not null, WEIGHT INTEGER not null, HISTORYOFOFRATION CHAR(20));");
                    //  sos 테이블
                    db.execSQL("CREATE TABLE IF NOT EXISTS SOSUSER ( SOSNAME CHAR(20) PRIMARY KEY, PHONENEMBER CHAR(30) not null, RELATION CHAR(20) not null)");
                    //  SOS 타이머 테이블
                    db.execSQL("CREATE TABLE IF NOT EXISTS SOSTIMER (SECOND INTEGER)");

        }

        // db 업그레이드 필요할때
        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
            db.execSQL("DROP TABLE IF EXISTS USER");
            db.execSQL("DROP TABLE IF EXISTS SOSUSER");
            db.execSQL("DROP TABLE IF EXISTS SOSTIMER");
            onCreate(db);

        }
    }

    public DbService(Context context){
        this.mCtx = context;
    }

    public DbService open() throws SQLException{
        dbHelper = new DbServiceHelper(mCtx, dbName, null, dbVersion);
        sqliteDB = dbHelper.getWritableDatabase();
        return this;
    }


    public void create(){
        dbHelper.onCreate(sqliteDB);
    }

    public void close() {
        dbHelper.close();
    }

    //유저 검색
    public Cursor usersetlected() {
        if (sqliteDB != null) {
            Cursor c = sqliteDB.rawQuery("SELECT * FROM USER", null);
            return c;
        }else{ return null; }

    }

    //sos 연락망 검색
    public Cursor sossetlected(){
        if (sqliteDB != null) {
            Cursor c = sqliteDB.rawQuery("SELECT * FROM SOSUSER ORDER BY SOSNAME ASC", null);
            return c;
        }else{ return null; }
    }

    //유저 테이블 내용 삭제
    public void dropuser(){ if (sqliteDB != null) { sqliteDB.execSQL( "DELETE FROM user"); }}

    // 유저 정보 넣기
    public void userIn(String name1, String blood, int age, int stature, int weight, String history){
        //임시 데이터 날리기
        if (sqliteDB != null) { sqliteDB.execSQL( "DELETE FROM user"); }

        sqliteDB.execSQL("INSERT INTO USER  " +
                "VALUES ('" + name1 + "','" +  blood + "'," + age +","+ stature +","+ weight + ",'" + history + "')");

    }

    // 유저 정보 업데이트

    //sos 정보 입력
    public void sosIn(String name, int phone, String relation){

        sqliteDB.execSQL("INSERT INTO SOSUSER  " +
                "VALUES ('" + name + "','" +  phone + "','" + relation + "')");

    }
    //sos 정보 업데이트
    public void sosupdate(String name, int phone, String relation) {
        sqliteDB.execSQL("UPDATE SOSUSER SET PHONENEMBER = '"+ phone +"', RELATION = '" +relation + "'" +
                "WHERE SOSNAME ='"+ name +"'");
    }

    //sos 삭제
    public void delsosuser(String sosname){
        if (sqliteDB != null) { sqliteDB.execSQL( "DELETE FROM SOSUSER WHERE SOSNAME ='"+ sosname +"'"); }
    }

    //현재 타임 정보
    public Cursor timesetlected(){
        if (sqliteDB != null) {
            Cursor c = sqliteDB.rawQuery("SELECT SECOND FROM SOSTIMER", null);
            return c;
        }else{ return null; }
    }

    //타이머 정보 입력 / 업데이트
    public void timein (int time) {
        //임시 데이터 날리기
        if (sqliteDB != null) { sqliteDB.execSQL( "DELETE FROM SOSTIMER"); }

        sqliteDB.execSQL("INSERT INTO SOSTIMER (SECOND)  VALUES ('" + time +  "')");
    }


}
