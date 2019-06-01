package com.application;

import android.app.Application;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.application.database.EWLADbHelper;
import com.application.database.Point;
import com.application.database.Theme;
import com.application.database.Unit;
import com.application.database.Word;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.ListIterator;

public class EWLApplication extends Application {

    // 아래 내용은 DB data가 아닌 TEST용 fake data임

    // POINT
    private Integer point = new Point().getPoint();
    public List<Theme> ThemeList = new ArrayList<Theme>();
    public List<Unit> UnitList = new ArrayList<Unit>();
    public List<Word> WordList = new ArrayList<Word>();

    public void setAllTheme(List<Theme> ThemeList){
        this.ThemeList = ThemeList;
    }

    public void setAllUnit(List<Unit> UnitList){
        this.UnitList = UnitList;
    }

    public void setAllWord(List<Word> WordList){
        this.WordList = WordList;
    }

    public Integer getPoint() {
        return point;
    }

    public void setPoint(Integer point) {
        this.point = (point);
    }


}
