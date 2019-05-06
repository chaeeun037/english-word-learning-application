package com.application;

import android.app.Application;

public class EWLApplication extends Application {

    private Integer point = 50;

    public Integer getPoint() {
        return point;
    }

    public void setPoint(Integer point) {
        this.point = point;
    }
}
