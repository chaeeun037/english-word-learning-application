package com.application.database;

import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;

public class EWLADatabaseOpener extends SQLiteOpenHelper {

    private Context mycontext;

    public static String DB_NAME = "EWLA.db";
    private String DB_PATH = "data/data/com.application/databases/";
    private static final int DB_VERSION = 1;

    public SQLiteDatabase myDataBase;

    public static List<Theme> ThemeList = new ArrayList<Theme>();
    public static List<Unit> UnitList = new ArrayList<Unit>();
    public static List<Word> WordList = new ArrayList<Word>();
    public static Point point = new Point();

    public EWLADatabaseOpener(Context context) throws IOException {
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
        setAllThemes();
        setAllUnits();
        setAllWords();
        setAllPoint();
    }

    public List<Theme> getThemeList(){
        return ThemeList;
    }

    public List<Unit> getUnitList(){
        return UnitList;
    }

    public List<Word> getWordList(){
        return WordList;
    }

    public Point getPoint(){
        return point;
    }

    public void setAllWords() {
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
                Log.d("wordUnitID", Integer.toString(word.getUnit_id()));
                Log.d("wordID", Integer.toString(word.getId()));
                Log.d("wordEnglish", word.getEnglish());
                Log.d("wordKorean", word.getKorean());
                Log.d("wordImageSrc", word.getImageSrc());
                Log.d("wordShadowSrc", word.getShadowSrc());
            } while (cursor.moveToNext());


        }
        cursor.close();
        db.close();
    }

    public void setAllUnits() {
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
                Log.d("UnitID", Integer.toString(unit.getId()));
                Log.d("UnitThemeId", Integer.toString(unit.getTheme_id()));
                Log.d("UnitTitle", unit.getTitle());
                Log.d("UnitHasCrown", Boolean.toString(unit.getHasCrown()));
            } while (cursor.moveToNext());
        }

        cursor.close();
        db.close();
    }

    public void setAllPoint() {
        String selectQuery = "SELECT  * FROM " + "point";
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        if (cursor.moveToFirst()) {
            do {
                point.setId(Integer.parseInt(cursor.getString(0)));
                point.setPoint(Integer.parseInt(cursor.getString(1)));
                Log.d("PointId", Integer.toString(point.getId()));
                Log.d("PointPoint", Integer.toString(point.getPointValue()));
            } while (cursor.moveToNext());
        }

        cursor.close();
        db.close();
    }

    public void setAllThemes() {
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
                Log.d("UnitID", Integer.toString(theme.getId()));
                Log.d("UnitTitle", theme.getTltle());
                Log.d("UnitIsLocked", Boolean.toString(theme.getIsLocked()));
                Log.d("UnitUnlockPoint", Integer.toString(theme.getUnlockPoint()));
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

    public void opendatabase() throws SQLException {
        String mypath = DB_PATH + DB_NAME;
        myDataBase = SQLiteDatabase.openDatabase(mypath, null, SQLiteDatabase.OPEN_READWRITE);
        Log.d("opendatabase", "finish");

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
