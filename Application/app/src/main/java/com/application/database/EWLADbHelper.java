package com.application.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

// 해당 클래스는 helper로 데이터베이스를 컨넥션 연동 시키기 위해 제작된 클래스입니다.
public class EWLADbHelper extends SQLiteOpenHelper {

    private static EWLADbHelper sInstance;
    private Context mycontext;
    private static final int DB_VERSION = 1;
    private static final String DB_NAME = "EWLA.db";


    private static final String SQL_DELETE_ENTRIES =
            "DROP TABLE IF EXISTS " + WordContract.WordEntry.TABLE_NAME;

    private static final String SQL_DELETE_THEME =
            "DROP TABLE IF EXISTS " + ThemeContract.Theme.TABLE_NAME;

    private static final String SQL_DELETE_UNIT =
            "DROP TABLE IF EXISTS " + UnitContract.Unit.TABLE_NAME;

    public static EWLADbHelper getsInstance(Context context) {
        if (sInstance == null) {
            sInstance = new EWLADbHelper(context);
        }
        return sInstance;
    }

    private EWLADbHelper(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    private void copydatabase() throws IOException {
        InputStream myInput = mycontext.getAssets().open(DB_NAME);
        String outFileName = DB_NAME + DB_NAME;
        OutputStream myOutput = new FileOutputStream("/data/data/com.application/databases/EWLA.db");
        byte[] buffer = new  byte[1024];
        int length;
        while((length = myInput.read(buffer))>0)
            myOutput.write(buffer, 0, length);

        myOutput.flush();
        myOutput.close();
        myInput.close();
    }


    @Override
    public void onCreate(SQLiteDatabase db) {

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        if (newVersion == DB_VERSION) {
            db.execSQL(SQL_DELETE_ENTRIES);
            db.execSQL(SQL_DELETE_THEME);
            db.execSQL(SQL_DELETE_UNIT);
            onCreate(db);
        }
    }
}
