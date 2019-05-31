package com.application.activity;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.media.AudioAttributes;
import android.media.AudioManager;
import android.media.SoundPool;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

import com.application.R;
import com.application.databinding.ActivityStartBinding;

public class StartActivity extends AppCompatActivity {

    private ActivityStartBinding binding;

    private SoundPool soundPool;

    private int sound_walk;
    private int sound_door;


    private void hideNavigationBar() {
        int uiOptions = getWindow().getDecorView().getSystemUiVisibility();
        int newUiOptions = uiOptions;
        newUiOptions ^= View.SYSTEM_UI_FLAG_HIDE_NAVIGATION;
        newUiOptions ^= View.SYSTEM_UI_FLAG_FULLSCREEN;
        newUiOptions ^= View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY;
        getWindow().getDecorView().setSystemUiVisibility(newUiOptions);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        hideNavigationBar();

        binding = DataBindingUtil.setContentView(this, R.layout.activity_start);
        binding.setActivity(this);

        final ImageView iv = (ImageView)findViewById(R.id.imageView1);

        Animation anim = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.start_chick_anim);
        iv.startAnimation(anim);

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

        sound_walk = soundPool.load(this, R.raw.walk_step, 1);
        sound_door = soundPool.load(this, R.raw.door_bell, 1);

    }

    @Override
    protected void onResume() {
        super.onResume();

        final Handler handler = new Handler();

        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                soundPool.play(sound_walk, 1, 1, 0, 0, 1);
            }
        }, 0 );

        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                soundPool.play(sound_door, 2, 2, 0, 0, 1);
            }
        }, 4000 );

        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent intent = new Intent(StartActivity.this, MainActivity.class);
                startActivity(intent);
                overridePendingTransition(R.anim.fade_anim,R.anim.hold_anim);
            }
        }, 6000 );

    }

    @Override
    public void onBackPressed() {
        //super.onBackPressed();
    }
}
