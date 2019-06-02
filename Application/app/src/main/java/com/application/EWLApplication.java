package com.application;

import android.app.Application;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.application.database.EWLADbHelper;
import com.application.database.Point;
import com.application.database.Theme;
import com.application.database.Unit;
import com.application.database.Word;

import java.io.IOException;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.ListIterator;

public class EWLApplication extends Application{

    public static EWLApplication application;
    public EWLADbHelper DBHelper = new EWLADbHelper();
    public Point point = new Point();
    public List<Theme> ThemeList = new ArrayList<Theme>();
    public List<Unit> UnitList = new ArrayList<Unit>();
    public List<Word> WordList = new ArrayList<Word>();

    public boolean MainOpen = true;

    // activity 전체에서 사용 가능하도록 만드는 선언 메소드
    public static EWLApplication getInstance(){
        if(application == null){
            application = new EWLApplication();
        }
        return application;
    }

    // 데이터베이스를 열고 값을 저장하는 메소드
    public void DBopen(Context context) throws IOException {
        DBHelper.DatabaseOpen(context);
        point = DBHelper.getPoint();
        ThemeList = DBHelper.getThemeList();
        UnitList = DBHelper.getUnitList();
        WordList = DBHelper.getWordList();
    }

    // 해당 클래스 내의 변수를 바꾸는 메소드들
    public void setAllTheme(List<Theme> ThemeList){
        this.ThemeList = ThemeList;
    }

    public void setAllUnit(List<Unit> UnitList){
        this.UnitList = UnitList;
    }

    public void setAllWord(List<Word> WordList){
        this.WordList = WordList;
    }

    public void setPointValue(int point) {
        this.point.setPoint(point);
    }

    public void setMainOpen(boolean open){
        this.MainOpen = open;
    }

    // 해당 클래스에 저장되어 있는 데이터 값을 제공하는 메소드들
    public Point getPoint() {
        return point;
    }

    public int getPointValue() {
        return point.getPointValue();
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

    public boolean getMainOpen(){
        return MainOpen;
    }
}
