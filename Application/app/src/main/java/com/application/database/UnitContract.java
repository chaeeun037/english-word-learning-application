package com.application.database;

import android.provider.BaseColumns;

public class UnitContract {

    public static class Unit implements BaseColumns {
        public static final String TABLE_NAME = "unit";
        public static final String UNIT_NAME_THEME_ID = "themeId";
        public static final String UNIT_NAME_ID = "id";
        public static final String UNIT_NAME_TITLE = "title";
        public static final String UNIT_NAME_HASCROWN = "hasCrown";
    }

}
