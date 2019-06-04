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

    ArrayList<Word> quizWord;
    int quiz;

    //퀴즈 리스트 만드는 메소드
    public void makeQuiz(){
        quizWord = new ArrayList<>();

        for(int i=0; i<18; i++) {
            Log.d("UNITID", ""+application.getUnitList().get((application.getWordList().get(i).getUnit_id()) - 1).getHasCrown());
            if (application.getUnitList().get((application.getWordList().get(i).getUnit_id()) - 1).getHasCrown()) {
                Word word = application.getWordList().get(i);
                Log.d("nowWord", "" + word.getEnglish());
                quizWord.add(word);
            }
        }
        Random random = new Random();
        quiz = random.nextInt(quizWord.size()-1);
        Log.d("NowQuiz", quizWord.get(quiz).getEnglish());
        application.setNowWordId(quizWord.get(quiz).getId());
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        hideNavigationBar();

        binding = DataBindingUtil.setContentView(this, R.layout.activity_game_speak);
        binding.setActivity(this);

        speakMainFragment = new SpeakMainFragment();
        speakInputFragment = new SpeakInputFragment();

        if(quizWord == null){
            makeQuiz();
        }
        else{
            Random random = new Random();
            int quiz2;
            if(quiz!=0)
                quiz2 = random.nextInt(quiz);
            else
                quiz2 = random.nextInt((quizWord.size()-1) - quiz);
            Log.d("NowQuiz", "KKKKKKKKKKK\t"+quizWord.get(quiz+quiz2).getEnglish());
            application.setNowWordId(quizWord.get(quiz + quiz2).getId());
        }

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
        Intent intent = new Intent(GameSpeakActivity.this, GameResultActivity.class);
        //Intent intent = new Intent(GameSpeakActivity.this, GameDrawActivity.class);
        startActivity(intent);
    }

}
