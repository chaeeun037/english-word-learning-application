package com.application.activity;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.media.AudioAttributes;
import android.media.AudioManager;
import android.media.SoundPool;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

import com.application.EWLApplication;
import com.application.R;
import com.application.database.EWLADbHelper;
import com.application.database.Word;
import com.application.databinding.ActivityGameSpeakBinding;
import com.application.fragment.SpeakInputFragment;
import com.application.fragment.SpeakMainFragment;

import java.util.ArrayList;
import java.util.Random;

public class GameSpeakActivity extends AppCompatActivity {

    private ActivityGameSpeakBinding binding;

    SpeakInputFragment speakInputFragment;
    SpeakMainFragment speakMainFragment;

    private SoundPool soundPool;

    private int sound_pop;
    EWLApplication application = EWLApplication.getInstance();

    int index;

    ArrayList<Word> quizWord  = new ArrayList<>();;
    int quiz1;
    int quiz2;

    public void makeQuizList() {
        for (int i = 0; i < 18; i++) {
            Log.d("UNITID", "" + application.getUnitList().get((application.getWordList().get(i).getUnit_id()) - 1).getHasCrown());
            if (application.getUnitList().get((application.getWordList().get(i).getUnit_id()) - 1).getHasCrown()) {
                Word word = application.getWordList().get(i);
                Log.d("nowWordKKKKKKKKKKKKKKK", "" + word.getEnglish());
                quizWord.add(word);
            }
        }
        Random random = new Random();
        quiz1 = random.nextInt(quizWord.size());
        quiz2 = quizWord.size() - quiz1 - 1;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        hideNavigationBar();

        binding = DataBindingUtil.setContentView(this, R.layout.activity_game_speak);
        binding.setActivity(this);

        speakMainFragment = new SpeakMainFragment();
        speakInputFragment = new SpeakInputFragment();

        Intent intent = getIntent();
        index = intent.getIntExtra("index", 0);

        if (quizWord.size() == 0) {
            makeQuizList();
        }
        if (index == 0)
            application.setNowWordId(quizWord.get(quiz1).getId() - 1);
        else
            application.setNowWordId(quizWord.get(quiz2).getId() - 1);

        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.container, new SpeakMainFragment())
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
        final ImageView iv = (ImageView) findViewById(R.id.imageView2);

        Animation anim = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.game_chicken_anim);
        if (iv != null) {
            iv.startAnimation(anim);
        }

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


    public void onMainNextButtonClick(View v) {
        soundPool.play(sound_pop, 1, 1, 0, 0, 1);

        FragmentManager manager = getSupportFragmentManager();
        manager.beginTransaction().replace(R.id.container, speakInputFragment).commit();
    }

    public void onInputNextButtonClick(View v) {
        soundPool.play(sound_pop, 1, 1, 0, 0, 1);

        /* 정적으로 하기 위해서 임의 수정 추후 재수정 요망 - 지수 190602 */
        Intent intent = new Intent(GameSpeakActivity.this, GameDrawActivity.class);
        //Intent intent = new Intent(GameSpeakActivity.this, GameDrawActivity.class);
        System.out.println("GameSpeakActivity\t" + index);
        System.out.println("GameSpeakActivity\t" + quizWord.get(quiz1).getEnglish());
        System.out.println("GameSpeakActivity\t" + quizWord.get(quiz2).getEnglish());

        intent.putExtra("index", index);
        intent.putExtra("quizString1", quizWord.get(quiz1).getEnglish());
        intent.putExtra("quizString2", quizWord.get(quiz2).getEnglish());
        startActivity(intent);
    }

}
