package com.application;

import android.app.Application;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.application.database.EWLADbHelper;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.ListIterator;

public class EWLApplication extends Application {






    // 아래 내용은 DB data가 아닌 TEST용 fake data임

    // POINT
    private Integer point = 50;

    public Integer getPoint() {
        return point;
    }

    public void setPoint(Integer point) {
        this.point = point;
    }

    // THEME
    public class Theme {
        private int id;
        private String title;
        private boolean isLocked;
        private int unlockPoint;

        public Theme(int id, String title, boolean isLocked, int unlockPoint ) {
            this.id = id;
            this.title = title;
            this.isLocked = isLocked;
            this.unlockPoint = unlockPoint;
        }

        public int getId() {
            return id;
        }

        public String getTitle() {
            return title;
        }

        public boolean isLocked() {
            return isLocked;
        }

        public int getUnlockPoint() {
            return unlockPoint;
        }
    }
/*
// 자바 개어려워 ^^ 자바스크립트만 하다보니 데이터 참조를 못하겠다 ^^,, -채은

    public List<Theme> getTheme() {
        Theme t1 = new Theme(0, "과일", false, 0);
        Theme t2 = new Theme(1, "고기", true, 30);
        Theme t3 = new Theme(2, "채소", true, 30);
        Theme t4 = new Theme(3, "유제품", true, 30);
        Theme t5 = new Theme(4, "간식", true, 30);

        return Arrays.asList(t1, t2, t3, t4, t5);
    }
    */


    // UNIT

    // WORD


}
