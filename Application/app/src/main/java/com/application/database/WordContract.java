package com.application.database;

import android.provider.BaseColumns;

public final class WordContract {

    private WordContract() {

    }

    public static class WordEntry implements BaseColumns {
        public static final String TABLE_NAME = "word";
        public static final String COLUMN_NAME_UNIT_ID = "unitId";
        public static final String COLUMN_NAME_IMAGE_SRC = "imageSrc";
        public static final String COLUMN_NAME_KOREAN = "korean";
        public static final String COLUMN_NAME_ENGLISH = "english";
        public static final String COLUMN_NAME_SHADOW_SRC= "shadowSrc";
    }
<<<<<<< HEAD




=======
>>>>>>> 8d7deac... 앱 이름 MARKET GO로 변경 / DB 연결 중...
}
