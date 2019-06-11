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
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

// 해당 클래스는 helper로 데이터베이스를 컨넥션 연동 시키기 위해 제작된 클래스입니다.
public class EWLADbHelper{

    private EWLADatabaseOpener opener;

    public static String DB_NAME = "EWLA.db";
    private String DB_PATH = "data/data/com.application/databases/";

    public static List<Theme> ThemeList = new ArrayList<Theme>();
    public static List<Unit> UnitList = new ArrayList<Unit>();
    public static List<Word> WordList = new ArrayList<Word>();
    public static Point point = new Point();


    public List<Theme> getThemeList(){
        return ThemeList;
    }

    public List<Unit> getUnitList(){
        return UnitList;
    }

    public List<Word> getWordList(){ return WordList; }

    public Point getPoint(){  return point;  }


    public void DatabaseOpen(Context context) throws IOException {
        opener = new EWLADatabaseOpener(context);
        ThemeList =opener.getThemeList();
        UnitList = opener.getUnitList();
        WordList = opener.getWordList();
        point = opener.getPoint();
    }

    public void DBPointUpdate(Point point){
        opener.UpgradePoint(point);
    }

    public void DBHasCrownUpdate(Unit unit){
        opener.UpgradeHasCrown(unit);
    }

    public void DBIsLockUpdate(Theme theme){
        opener.UpgradeIsLocked(theme);
    }
}
