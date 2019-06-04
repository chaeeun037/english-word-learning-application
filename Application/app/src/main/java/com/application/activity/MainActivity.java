package com.application.activity;

import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.databinding.DataBindingUtil;
import android.media.AudioAttributes;
import android.media.AudioManager;
import android.media.AudioTimestamp;
import android.media.MediaPlayer;
import android.media.SoundPool;
import android.os.Build;
import android.os.Handler;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.application.EWLApplication;
import com.application.R;
import com.application.database.EWLADbHelper;
import com.application.database.Point;
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

    MainMenuFragment mainMenuFragment;
    LearningThemeFragment learningThemeFragment;
    LearningUnitFragment learningUnitFragment;

    boolean DataBaseOpen = true;

    TextView textView;

    private SoundPool soundPool;

    private int sound_pop;
    private int sound_coins;
    private int sound_stamp;

    MediaPlayer player;

    public void backgroundMusicPlay() {
        if (player == null) {
            player = MediaPlayer.create(this, R.raw.background_bunny_garden);
            player.setVolume((float) 0.1, (float) 0.1);
            player.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                @Override
                public void onCompletion(MediaPlayer mp) {
                    stopPlayer();
                }
            });
        }

        player.start();
    }
    
    private void stopPlayer() {
        if (player != null) {
            player.release();
            player = null;
        }
    }

    private void hideNavigationBar() {
        int uiOptions = getWindow().getDecorView().getSystemUiVisibility();
        int newUiOptions = uiOptions;
        newUiOptions ^= View.SYSTEM_UI_FLAG_HIDE_NAVIGATION;
        newUiOptions ^= View.SYSTEM_UI_FLAG_FULLSCREEN;
        newUiOptions ^= View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY;
        getWindow().getDecorView().setSystemUiVisibility(newUiOptions);
    }

    public void onHelpButtonClick(View v) {
        soundPool.play(sound_pop, 1, 1, 0, 0, 1);
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

    public void onThemeButtonClick(View v, int id) {
        soundPool.play(sound_pop, 1, 1, 0, 0, 1);

        int tag = Integer.parseInt(v.getTag().toString());
        Log.d("***", "tag : " + tag);

        //TODO: db에서 가져온 unitList에서 id가 tag와 일치하는 데이터를 가져와서 filteredUnitList에 저장
        FragmentManager manager = getSupportFragmentManager();
        manager.beginTransaction().replace(R.id.container, learningUnitFragment).commit();
    }

    public void onUnitButtonClick(View v, int id) {
        soundPool.play(sound_pop, 1, 1, 0, 0, 1);

        Intent intent = new Intent(MainActivity.this, LearningActivity.class);
        intent.putExtra("id", id);
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

        binding = DataBindingUtil.setContentView(this, R.layout.activity_main);
        binding.setActivity(this);

        mainMenuFragment = new MainMenuFragment();
        learningThemeFragment = new LearningThemeFragment();
        learningUnitFragment = new LearningUnitFragment();

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
        sound_stamp = soundPool.load(this, R.raw.stamping, 1);

        Intent intent = getIntent();
        int type = intent.getIntExtra("type", 0);

        if (savedInstanceState == null) {
            // 학습을 완료한 후 unit 화면에 스탬프 찍히기
            if (type == 1) {
                getSupportFragmentManager().beginTransaction()
                        .add(R.id.container, new LearningUnitFragment())
                        .commit();
            } else {
                getSupportFragmentManager().beginTransaction()
                        .add(R.id.container, new MainMenuFragment())
                        .commit();
            }
        }

        backgroundMusicPlay();
        if (type == 1) {
            final Handler handler = new Handler();

            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    soundPool.play(sound_stamp, (float) 0.5, (float) 0.5, 0, 0, 1);
                }
            }, 500 );
        }

    }

    @Override
    protected void onResume() {
        super.onResume();


            //db = EWLADbHelper.getsInstance().DatabaseOpen(this).getReadableDatabase();

        application = EWLApplication.getInstance();

        if(application.getMainOpen()) {
            try {
                application.DBopen(this);
            } catch (IOException e) {
                e.printStackTrace();
            }
            application.setMainOpen(false);
        }

        Log.d("now point", ""+application.getPointValue());
        textView = (TextView)findViewById(R.id.textPoint);
        textView.setText(""+application.getPoint().getPointValue());
    }

    @Override
    protected void onStop() {
        super.onStop();

        stopPlayer();

        soundPool.release();
        soundPool = null;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        soundPool.release();
        soundPool = null;
    }
}
