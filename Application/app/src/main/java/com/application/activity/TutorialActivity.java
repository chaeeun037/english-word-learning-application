package com.application.activity;

import android.databinding.DataBindingUtil;
import android.media.AudioAttributes;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.media.SoundPool;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.application.R;
import com.application.databinding.ActivityTutorialBinding;
import com.application.fragment.TutorialGameFragment;
import com.application.fragment.TutorialLearningFragment;
import com.application.fragment.TutorialMainFragment;

public class TutorialActivity extends AppCompatActivity {

    private ActivityTutorialBinding binding;

    TutorialMainFragment tutorialMainFragment;
    TutorialLearningFragment tutorialLearningFragment;
    TutorialGameFragment tutorialGameFragment;

    MediaPlayer player;

    private SoundPool soundPool;

    private int sound_pop;
    private int sound_mumbling;

    public void onLearningMenuClick(View v) {
        soundPool.play(sound_pop, 1, 1, 0, 0, 1);

        FragmentManager manager = getSupportFragmentManager();
        manager.beginTransaction().replace(R.id.container, tutorialLearningFragment).commit();
    }

    public void onGameMenuClick(View v) {
        soundPool.play(sound_pop, 1, 1, 0, 0, 1);

        FragmentManager manager = getSupportFragmentManager();
        manager.beginTransaction().replace(R.id.container, tutorialGameFragment).commit();
    }

    public void onExerciseMenuClick(View v) {
        soundPool.play(sound_pop, 1, 1, 0, 0, 1);
    }

    public void onTutorialPrevButtonClick(View v) {
        soundPool.play(sound_pop, 1, 1, 0, 0, 1);

        finish();
    }

    private void initSoundPool() {
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
        sound_mumbling = soundPool.load(this, R.raw.mumbling, 1);
    }

    public void backgroundMusicPlay() {
        if (player == null) {
            player = MediaPlayer.create(this, R.raw.nice_surprise);
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

    @Override
    public void onBackPressed() {
        //super.onBackPressed();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        hideNavigationBar();

        binding = DataBindingUtil.setContentView(this, R.layout.activity_tutorial);
        binding.setActivity(this);

        tutorialMainFragment = new TutorialMainFragment();
        tutorialGameFragment = new TutorialGameFragment();
        tutorialLearningFragment = new TutorialLearningFragment();

        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.container, new TutorialMainFragment())
                    .commit();
        }

        initSoundPool();

        backgroundMusicPlay();
    }

    @Override
    protected void onResume() {
        super.onResume();

        final ImageView iv = (ImageView) findViewById(R.id.imageView3);
        final LinearLayout iv2 = (LinearLayout) findViewById(R.id.imageView4);

        Animation anim = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.game_chicken_anim);
        Animation anim2 = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.fade_in_anim);

        if (iv != null && iv2 != null) {
            iv.startAnimation(anim);
            iv2.startAnimation(anim2);

            final Handler handler = new Handler();

            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    if (soundPool != null) {
                        soundPool.play(sound_mumbling, 1, 1, 0, 0, 1);
                    }
                }
            }, 1500);
        }
    }

    @Override
    protected void onStop() {
        super.onStop();

        stopPlayer();

        if (soundPool != null) {
            soundPool.release();
        }

        soundPool = null;
    }

    @Override
    protected void onRestart() {
        super.onRestart();

        initSoundPool();

        backgroundMusicPlay();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        if (soundPool != null) {
            soundPool.release();
        }

        soundPool = null;
    }
}
