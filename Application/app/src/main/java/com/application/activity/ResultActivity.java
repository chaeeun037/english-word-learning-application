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
import android.widget.TextView;

import com.application.EWLApplication;
import com.application.R;
import com.application.database.EWLADbHelper;
import com.application.database.Point;
import com.application.databinding.ActivityResultBinding;

public class ResultActivity extends AppCompatActivity {

    public Point point;

    private ActivityResultBinding binding;

    private SoundPool soundPool;

    private int sound_pop;
    private int sound_coins;

    private EWLApplication application =  EWLApplication.getInstance();

    String quizString1;
    String quizString2;

    TextView quizNum1;
    TextView quizNum2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        hideNavigationBar();

        binding = DataBindingUtil.setContentView(this, R.layout.activity_result);
        binding.setActivity(this);

        Intent intent = getIntent();
        quizString1 = intent.getStringExtra("quizString1");
        quizString2 = intent.getStringExtra("quizString2");

        quizNum1 = (TextView)findViewById(R.id.quizNum1);
        quizNum1.setText(quizString1);

        quizNum2 = (TextView)findViewById(R.id.quizNum2);
        quizNum2.setText(quizString2);

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

    private void hideNavigationBar() {
        int uiOptions = getWindow().getDecorView().getSystemUiVisibility();
        int newUiOptions = uiOptions;
        newUiOptions ^= View.SYSTEM_UI_FLAG_HIDE_NAVIGATION;
        newUiOptions ^= View.SYSTEM_UI_FLAG_FULLSCREEN;
        newUiOptions ^= View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY;
        getWindow().getDecorView().setSystemUiVisibility(newUiOptions);
    }

    public void onMainButtonClick(View v) {
        soundPool.play(sound_pop, 1, 1, 0, 0, 1);

        final Handler handler = new Handler();

        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                soundPool.play(sound_coins, 1, 1, 0, 0, 1);
            }
        }, 500);

        Intent intent = new Intent(ResultActivity.this, MainActivity.class);
        startActivity(intent);
    }

}
