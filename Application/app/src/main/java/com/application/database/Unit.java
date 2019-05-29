package com.application.database;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

public class Unit {

    private int theme_id;
    private int id;
    private String title;
    private boolean hasCrown;

    public void setTheme_id(int theme_id){
        this.theme_id = theme_id;
    }

    public void setId(int id){
        this.id = id;
    }

    public void setTitle(String title){
        this.title = title;
    }

    public void setHasCrown(boolean hasCrown){
        this.hasCrown = hasCrown;
    }

    public int getTheme_id(){
        return theme_id;
    }

    public int getId(){
        return id;
    }

    public String getTitle(){
        return title;
    }

    public boolean getHasCrown(){
        return hasCrown;
    }
}

