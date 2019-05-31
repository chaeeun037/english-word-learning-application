package com.application.activity;

import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.databinding.DataBindingUtil;
import android.media.AudioAttributes;
import android.media.AudioManager;
import android.media.AudioTimestamp;
import android.media.SoundPool;
import android.os.Build;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.application.EWLApplication;
import com.application.R;
import com.application.database.EWLADbHelper;
import com.application.database.Theme;
import com.application.database.Unit;
import com.application.database.Word;
import com.application.databinding.ActivityMainBinding;
import com.application.fragment.MainMenuFragment;
import com.application.fragment.LearningThemeFragment;
import com.application.fragment.LearningUnitFragment;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private ActivityMainBinding binding;
    private EWLApplication application;
    SQLiteDatabase db = null;
    EWLADbHelper helper;
    public String point;

    MainMenuFragment mainMenuFragment;
    LearningThemeFragment learningThemeFragment;
    LearningUnitFragment learningUnitFragment;

    public List<Theme> ThemeList = new ArrayList<Theme>();
    public List<Unit> UnitList = new ArrayList<Unit>();
    public List<Word> WordList = new ArrayList<Word>();

    private SoundPool soundPool;

    private int sound_pop;
    private int sound_coins;

    private void hideNavigationBar() {
        int uiOptions = getWindow().getDecorView().getSystemUiVisibility();
        int newUiOptions = uiOptions;
        newUiOptions ^= View.SYSTEM_UI_FLAG_HIDE_NAVIGATION;
        newUiOptions ^= View.SYSTEM_UI_FLAG_FULLSCREEN;
        newUiOptions ^= View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY;
        getWindow().getDecorView().setSystemUiVisibility(newUiOptions);
    }

    public void onHomeButtonClick(View v) {
        soundPool.play(sound_pop, 1, 1, 0, 0, 1);

        FragmentManager manager = getSupportFragmentManager();
        manager.beginTransaction().replace(R.id.container, mainMenuFragment).commit();
    }

    public void onCoinButtonClick(View v) {
        soundPool.play(sound_coins, 1, 1, 0, 0, 1);
    }

    public void onLearningButtonClick(View v) {
        soundPool.play(sound_pop, 1, 1, 0, 0, 1);

        FragmentManager manager = getSupportFragmentManager();
        manager.beginTransaction().replace(R.id.container, learningThemeFragment).commit();
    }

    public void onThemeButtonClick(View v) {
        soundPool.play(sound_pop, 1, 1, 0, 0, 1);

        int tag = Integer.parseInt(v.getTag().toString());
        Log.d("***", "tag : " + tag);

        //TODO: db에서 가져온 unitList에서 id가 tag와 일치하는 데이터를 가져와서 filteredUnitList에 저장

        FragmentManager manager = getSupportFragmentManager();
        manager.beginTransaction().replace(R.id.container, learningUnitFragment).commit();
    }

    public void onUnitButtonClick(View v) {
        soundPool.play(sound_pop, 1, 1, 0, 0, 1);

        Intent intent = new Intent(MainActivity.this, LearningActivity.class);
        startActivity(intent);
    }

    public void onGameButtonClick(View v) {
        soundPool.play(sound_pop, 1, 1, 0, 0, 1);

        Intent intent = new Intent(MainActivity.this, GameActivity.class);
        startActivity(intent);
    }

    @Override
    public void onBackPressed() {
        //super.onBackPressed();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        hideNavigationBar();

        this.application = (EWLApplication) getApplicationContext();

        binding = DataBindingUtil.setContentView(this, R.layout.activity_main);
        binding.setActivity(this);

        mainMenuFragment = new MainMenuFragment();
        learningThemeFragment = new LearningThemeFragment();
        learningUnitFragment = new LearningUnitFragment();

        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.container, new MainMenuFragment())
                    .commit();
        }

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            AudioAttributes audioAttributes = new AudioAttributes.Builder()
                    .setUsage(AudioAttributes.USAGE_ASSISTANCE_SONIFICATION)
                    .setContentType(AudioAttributes.CONTENT_TYPE_SONIFICATION)
                    .build();

            soundPool = new SoundPool.Builder()
                    .setMaxStreams(6)
                    .setAudioAttributes(audioAttributes)
                    .build();
        } else {
            soundPool = new SoundPool(6, AudioManager.STREAM_MUSIC, 0);
        }

        sound_pop = soundPool.load(this, R.raw.bubble_pop, 1);
        sound_coins = soundPool.load(this, R.raw.coins, 1);
    }

    @Override
    protected void onResume() {
        super.onResume();

        application.setPoint(500);
        this.point = String.valueOf(application.getPoint());

        try {
            Log.d("MainOnResume", "dbMake");
            db = EWLADbHelper.getsInstance(this).getReadableDatabase();
            helper = EWLADbHelper.getsInstance(this);
            ThemeList = helper.getThemeList();
            UnitList = helper.getUnitList();
            WordList = helper.getWordList();

            application.setAllTheme(ThemeList);
            application.setAllUnit(UnitList);
            application.setAllWord(WordList);
        } catch (IOException e) {
            e.printStackTrace();
        }
        Log.d("***", "" + db);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        soundPool.release();
        soundPool = null;
    }
}
