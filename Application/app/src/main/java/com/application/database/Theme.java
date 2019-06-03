package com.application.database;

import com.application.EWLApplication;

public class Theme {

    private int id;
    private String title;
    private boolean isLocked;
    private int unlockPoint;
    EWLApplication application = EWLApplication.getInstance();


    public void setId(int id){
        this.id = id;
    }

    public void setTitle(String title){
        this.title = title;
    }

    public void setIsLocked(boolean isLocked){
        this.isLocked = isLocked;
    }

    public void setUnlockPoint(int unlockPoint){
        this.unlockPoint = unlockPoint;
    }

    public int getId(){
        return id;
    }

    public String getTltle(){
        return title;
    }

    public boolean getIsLocked(){
        return isLocked;
    }

    public int getUnlockPoint(){
        return unlockPoint;
    }
}
