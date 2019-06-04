package com.application.activity;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.media.AudioAttributes;
import android.media.AudioManager;
import android.media.SoundPool;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

import com.application.EWLApplication;
import com.application.R;
import com.application.database.Word;
import com.application.databinding.ActivityGameBinding;

import java.util.ArrayList;
import java.util.Random;

public class GameActivity extends AppCompatActivity {

    private ActivityGameBinding binding;
    EWLApplication application = EWLApplication.getInstance();
    private SoundPool soundPool;
    private int sound_pop;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        hideNavigationBar();

        binding = DataBindingUtil.setContentView(this, R.layout.activity_game);
        binding.setActivity(this);

        final ImageView iv = (ImageView)findViewById(R.id.imageView1);

        Animation anim = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.game_chick_anim);
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

        sound_pop = soundPool.load(this, R.raw.bubble_pop, 1);
    }

    @Override
    protected void onStop() {
        super.onStop();

        soundPool.release();
        soundPool = null;
    }

    private void hideNavigationBar() {
        int uiOptions = getWindow().getDecorView().getSystemUiVisibility();
        int newUiOptions = uiOptions;
        newUiOptions ^= View.SYSTEM_UI_FLAG_HIDE_NAVIGATION;
        newUiOptions ^= View.SYSTEM_UI_FLAG_FULLSCREEN;
        newUiOptions ^= View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY;
        getWindow().getDecorView().setSystemUiVisibility(newUiOptions);
    }

    public void onStartButtonClick(View v) {
        soundPool.play(sound_pop, 1, 1, 0, 0, 1);

        ArrayList<Word> quizWord = new ArrayList<>();

        for(int i=0; i<18; i++) {
            Log.d("UNITID", ""+application.getUnitList().get((application.getWordList().get(i).getUnit_id()) - 1).getHasCrown());
            if(application.getUnitList().get((application.getWordList().get(i).getUnit_id()) - 1).getHasCrown()) {
                Word word = application.getWordList().get(i);
                Log.d("nowWord", ""+word.getEnglish());
                quizWord.add(word);
            }
        }
        Random random = new Random();
        int quiz = random.nextInt(quizWord.size());
        Log.d("NowQuiz", application.getWordList().get(quiz).getEnglish());
        application.setNowWordId(quizWord.get(quiz).getId());

        //Intent intent = new Intent(GameActivity.this, GameSpeakActivity.class);
        Intent intent = new Intent(GameActivity.this, GameDrawActivity.class);
        startActivity(intent);
    }
}
