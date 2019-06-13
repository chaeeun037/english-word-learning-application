package com.application.activity;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.media.AudioAttributes;
import android.media.AudioManager;
import android.media.SoundPool;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.Toast;

import com.application.EWLApplication;
import com.application.R;
import com.application.database.EWLADbHelper;
import com.application.database.Word;
import com.application.databinding.ActivityGameSpeakBinding;
import com.application.fragment.SpeakInputFragment;
import com.application.fragment.SpeakMainFragment;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class GameSpeakActivity extends AppCompatActivity implements SpeakInputFragment.returnSpeakTermListener {

    private ActivityGameSpeakBinding binding;

    SpeakInputFragment speakInputFragment;
    SpeakMainFragment speakMainFragment;

    private SoundPool soundPool;

    private int sound_pop;
    private int sound_mumbling;
    String mSpeakTerm;

    EWLApplication application = EWLApplication.getInstance();

    int index;
    ArrayList<String> quizWord = GameActivity.getRightQuizWord();
    List<Word> wordList;

    @Override
    public void returnSpeakTerm(String speakTerm) {
        mSpeakTerm = speakTerm;
    }

    @Override
    public void onBackPressed() {
        //super.onBackPressed();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        hideNavigationBar();

        binding = DataBindingUtil.setContentView(this, R.layout.activity_game_speak);
        binding.setActivity(this);

        speakMainFragment = new SpeakMainFragment();
        speakInputFragment = new SpeakInputFragment();

        wordList = EWLADbHelper.WordList;

        Intent intent = getIntent();
        index = intent.getIntExtra("index", 0);

        if (index == 0) {
            for (int i = 0; i < wordList.size(); i++)
                if (wordList.get(i).getEnglish().equals(quizWord.get(0)))
                    application.setNowWordId(wordList.get(i).getId() - 1);
        } else {
            for (int i = 0; i < wordList.size(); i++)
                if (wordList.get(i).getEnglish().equals(quizWord.get(1)))
                    application.setNowWordId(wordList.get(i).getId() - 1);
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

        if (mSpeakTerm == null) {
            Toast.makeText(this, "녹음이 제대로 되지 않았어요.\n다시 한 번 녹음해 볼까요?", Toast.LENGTH_SHORT).show();
            FragmentManager manager = getSupportFragmentManager();
            manager.beginTransaction().replace(R.id.container, speakInputFragment).commit();
        } else {
            /* 정적으로 하기 위해서 임의 수정 추후 재수정 요망 - 지수 190602 */
            Intent intent = new Intent(GameSpeakActivity.this, GameDrawActivity.class);
            //Intent intent = new Intent(GameSpeakActivity.this, GameDrawActivity.class);
            intent.putExtra("quizString1", quizWord.get(0));
            intent.putExtra("quizString2", quizWord.get(1));
            intent.putExtra("speakTerm", mSpeakTerm);

            //게임이 두번돌면 quizWord 비우기
            if (application.getWordList().get(application.getNowWordId()).getEnglish().equals(quizWord.get(1)))
                GameActivity.setQuizList();

            startActivity(intent);
        }
    }
}
