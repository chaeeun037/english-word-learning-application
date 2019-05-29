package com.application.database;

import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
import android.widget.TextView;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;

// 해당 클래스는 helper로 데이터베이스를 컨넥션 연동 시키기 위해 제작된 클래스입니다.
public class EWLADbHelper extends SQLiteOpenHelper {

    private static EWLADbHelper sInstance;
    private Context mycontext;
    public static String DB_NAME = "EWLA.db";
    public SQLiteDatabase myDataBase;
    private static final int DB_VERSION = 1;
    private String DB_PATH = "data/data/com.application/databases/";
    private List<Theme> ThemeList = new ArrayList<Theme>();
    private List<Unit> UnitList = new ArrayList<Unit>();
    private List<Word> WordList = new ArrayList<Word>();

    public static EWLADbHelper getsInstance(Context context) throws IOException {
        sInstance = new EWLADbHelper(context);
        return sInstance;
    }

    private EWLADbHelper(Context context) throws IOException {
        super(context, DB_NAME, null, DB_VERSION);
        this.mycontext = context;
        boolean dbexist = checkdatabase();
        Log.d("Helperdatabase", String.valueOf(dbexist));
        if (dbexist) {
            Log.d("database", "exists");
            opendatabase();
        } else {
            Log.d("database", "doesn't exists");
            createdatabase();
        }
        getAllThemes();
        getAllUnits();
        getAllWords();
    }

    public void opendatabase() throws SQLException {
        String mypath = DB_PATH + DB_NAME;
        myDataBase = SQLiteDatabase.openDatabase(mypath, null, SQLiteDatabase.OPEN_READWRITE);
        Log.d("opendatabase", "finish");

    }

    public void getAllWords() {
        String selectQuery = "SELECT  * FROM " + "word";

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        if (cursor.moveToFirst()) {
            do {
                Word word = new Word();
                word.setId(Integer.parseInt(cursor.getString(0)));
                word.setUnit_id(Integer.parseInt(cursor.getString(1)));
                word.setImageSrc(cursor.getString(2));
                word.setKorean(cursor.getString(3));
                word.setEnglish(cursor.getString(4));
                word.setShadowSrc(cursor.getString(5));
                WordList.add(word);
            } while (cursor.moveToNext());
        }

        cursor.close();
        db.close();
    }

    public void getAllUnits() {
        String selectQuery = "SELECT  * FROM " + "unit";
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        if (cursor.moveToFirst()) {
            do {
                Unit unit = new Unit();
                unit.setTheme_id(Integer.parseInt(cursor.getString(0)));
                unit.setId(Integer.parseInt(cursor.getString(1)));
                unit.setTitle(cursor.getString(2));
                unit.setHasCrown(Boolean.parseBoolean(cursor.getString(3)));
                UnitList.add(unit);
            } while (cursor.moveToNext());
        }

        cursor.close();
        db.close();
    }

    public void getAllThemes() {
        String selectQuery = "SELECT * FROM " + "theme";
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        if (cursor.moveToFirst()) {
            do {
                Theme theme = new Theme();
                theme.setId(Integer.parseInt(cursor.getString(0)));
                theme.setTitle(cursor.getString(1));
                theme.setIsLocked(Boolean.getBoolean(cursor.getString(2)));
                theme.setUnlockPoint(Integer.parseInt(cursor.getString(3)));
                ThemeList.add(theme);
            } while (cursor.moveToNext());
        }
        cursor.close();
        db.close();
    }


    public void createdatabase() throws IOException {
        boolean dbexist = checkdatabase();
        Log.d("createdatabase", String.valueOf(dbexist));
        if (dbexist) {
            Log.d("createdatabase", "doesn't exists");
        } else {
            this.getReadableDatabase();
            this.close();
            try {
                copydatabase();
            } catch (IOException e) {
                Log.d("createdatabase", "copying error");
            }
        }
    }

    private boolean checkdatabase() {
        boolean checkdb = false;
        try {
            String myPath = DB_PATH + DB_NAME;
            Log.d("checkdatabase", myPath);
            File dbfile = new File(myPath);
            checkdb = dbfile.exists();
            Log.d("checkdatabase", String.valueOf(checkdb));
        } catch (SQLiteException e) {
            Log.d("checkdatabase", "doesn't exists");
        }

        return checkdb;
    }

    private void copydatabase() throws IOException {

        InputStream myinput = mycontext.getAssets().open(DB_NAME);
        String outfilename = DB_PATH + DB_NAME;
        OutputStream myoutput = new FileOutputStream(DB_PATH);
        byte[] buffer = new byte[1024];
        int length;

        while ((length = myinput.read(buffer)) > 0) {
            myoutput.write(buffer, 0, length);
        }

        myoutput.flush();
        myoutput.close();
        myinput.close();

    }


    @Override
    public void onCreate(SQLiteDatabase db) {
        try {
            createdatabase();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
