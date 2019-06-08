package com.application.activity;

import android.app.Dialog;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.graphics.Bitmap;
import android.media.AudioAttributes;
import android.media.AudioManager;
import android.media.SoundPool;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.application.CloudVision.CloudVision;
import com.application.R;
import com.application.databinding.ActivityGameDrawBinding;
import com.application.fragment.DrawInputFragment;
import com.application.fragment.DrawMainFragment;

import java.io.ByteArrayOutputStream;

public class GameDrawActivity extends AppCompatActivity {

    private ActivityGameDrawBinding binding;

    DrawMainFragment drawMainFragment;
    DrawInputFragment drawInputFragment;

    Bitmap handwriteBitmap;

    private SoundPool soundPool;
    private int sound_pop;
    private int sound_mumbling;

    String quizString1;
    String quizString2;
    String mSpeakTerm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        hideNavigationBar();

        binding = DataBindingUtil.setContentView(this, R.layout.activity_game_draw);
        binding.setActivity(this);

        drawMainFragment = new DrawMainFragment();
        drawInputFragment = new DrawInputFragment();

        Intent intent = getIntent();
        quizString1 = intent.getStringExtra("quizString1");
        quizString2 = intent.getStringExtra("quizString2");
        mSpeakTerm = intent.getStringExtra("speakTerm");


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
        sound_mumbling = soundPool.load(this, R.raw.mumbling, 1);
    }

    @Override
    protected void onResume() {
        super.onResume();
        final ImageView iv = (ImageView) findViewById(R.id.imageView2);

        Animation anim = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.game_chicken_anim);
        if (iv != null) {
            iv.startAnimation(anim);

            final Handler handler = new Handler();

            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    soundPool.play(sound_mumbling, 1, 1, 0, 0, 1);
                }
            }, 1000);
        }
    }

    @Override
    protected void onStop() {
        super.onStop();

        if (soundPool != null) {
            soundPool.release();
        }

        //soundPool = null;
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

        intent.putExtra("quizString1", quizString1);
        intent.putExtra("quizString2", quizString2);
        intent.putExtra("speakTerm", mSpeakTerm);

        startActivity(intent);
    }


    public void callCouldVision(View v) throws Exception {
//        soundPool.play(sound_pop, 1, 1, 0, 0, 1);

        showLoadingToast();

        final Handler handler = new Handler();

        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                handwriteBitmap = drawInputFragment.getCanvasBitmap();

                ByteArrayOutputStream bs = new ByteArrayOutputStream();
                handwriteBitmap.compress(Bitmap.CompressFormat.JPEG, 90, bs);

                Intent intent = new Intent(GameDrawActivity.this, CloudVision.class);
                intent.putExtra("handwriteImage", bs.toByteArray());

                intent.putExtra("quizString1", quizString1);
                intent.putExtra("quizString2", quizString2);
                intent.putExtra("speakTerm", mSpeakTerm);

                startActivity(intent);


            }
        }, 300);
    }

    private void showLoadingToast() {
        View toastView = getLayoutInflater().inflate(R.layout.loading_toast, null);

        Toast toast = new Toast(getApplicationContext());

        toast.setView(toastView);
        toast.setDuration(Toast.LENGTH_SHORT);
        toast.setGravity(Gravity.CENTER, 0,0);
        toast.show();
    }
}