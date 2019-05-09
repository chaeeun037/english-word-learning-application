package com.application.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

// 해당 클래스는 helper로 데이터베이스를 컨넥션 연동 시키기 위해 제작된 클래스입니다.
public class MYSQLiteOpenHelper extends SQLiteOpenHelper{

    public static final int DATABASE_VERSION = 1;
    public MYSQLiteOpenHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version){
        super(context, name, factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String SQL = "create table word " +
                "(id integer, imageSrc char(20), korean char(20), english char(20))";
        db.execSQL(SQL);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        if(newVersion == DATABASE_VERSION){
            db.execSQL("drop table word");
            onCreate(db);
        }
    }
}
