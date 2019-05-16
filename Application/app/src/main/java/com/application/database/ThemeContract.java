package com.application.database;

import android.provider.BaseColumns;

public class ThemeContract {

    public static class Theme implements  BaseColumns {
        public static final String TABLE_NAME = "theme";
        public static final String THEME_NAME_ID = "id";
        public static final String THEME_NAME_TITLE = "title";
        public static final String THEME_NAME_ISLOCKED = "isLocked";
        public static final String THEME_NAME_UNLOCKPOINT = "unlockPoint";
    }
}

