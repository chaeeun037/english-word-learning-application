package com.application.activity;

import android.content.Context;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.graphics.Bitmap;
import android.media.AudioAttributes;
import android.media.AudioManager;
import android.media.SoundPool;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

import com.application.R;
import com.application.databinding.ActivityGameDrawBinding;
import com.application.fragment.DrawCanvasView;
import com.application.fragment.DrawInputFragment;
import com.application.fragment.DrawMainFragment;

import java.io.ByteArrayOutputStream;

public class GameDrawActivity extends AppCompatActivity {

    private ActivityGameDrawBinding binding;
  
    DrawMainFragment drawMainFragment;
    DrawInputFragment drawInputFragment;

    private SoundPool soundPool;

    private int sound_pop;
    private int sound_coins;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        hideNavigationBar();

        binding = DataBindingUtil.setContentView(this, R.layout.activity_game_draw);
        binding.setActivity(this);

        drawMainFragment = new DrawMainFragment();
        drawInputFragment = new DrawInputFragment();

        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.container, new DrawMainFragment())
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
    }

    @Override
    protected void onResume() {
        super.onResume();
        final ImageView iv = (ImageView)findViewById(R.id.imageView2);

        Animation anim = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.game_chicken_anim);
        if (iv != null) {
            iv.startAnimation(anim);
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


    public void onMainNextButtonClick(View v) {
        soundPool.play(sound_pop, 1, 1, 0, 0, 1);

        FragmentManager manager = getSupportFragmentManager();
        manager.beginTransaction().replace(R.id.container, drawInputFragment).commit();
    }

    public void onInputNextButtonClick(View v) {
        soundPool.play(sound_pop, 1, 1, 0, 0, 1);

        Intent intent = new Intent(GameDrawActivity.this, GameResultActivity.class);
        startActivity(intent);
    }

    public void callCouldVision(View v, Context context){
        DrawCanvasView shadowCanvasV = new DrawCanvasView(context);
        Bitmap handwriteBitmap = shadowCanvasV.getCanvasBitmap();

        ByteArrayOutputStream bs = new ByteArrayOutputStream();
        handwriteBitmap.compress(Bitmap.CompressFormat.PNG, 50, bs);

        Intent intent = new Intent(GameDrawActivity.this, GameResultActivity.class);

        intent.putExtra("handwriteImage", bs.toByteArray());
        startActivity(intent);
    }


}