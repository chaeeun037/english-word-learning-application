package com.application.activity;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.media.AudioAttributes;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.media.SoundPool;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.SurfaceHolder;
import android.view.View;

import com.application.EWLApplication;
import com.application.R;
import com.application.databinding.ActivityGameResultBinding;
import com.application.fragment.FailFragment;
import com.application.fragment.LearningUnitFragment;
import com.application.fragment.MainMenuFragment;
import com.application.fragment.SpeakInputFragment;
import com.application.fragment.SuccessFragment;

import java.util.ArrayList;

public class GameResultActivity extends AppCompatActivity {

    private ActivityGameResultBinding binding;

    SuccessFragment successFragment;
    FailFragment failFragment;

    MediaPlayer player;

    private SoundPool soundPool;

    private int sound_pop;

    int index;
    String quizString1;
    String quizString2;
    String mSpeakTerm;
    String mWriteTerm;

    static int result1;
    int result2;

    EWLApplication application = EWLApplication.getInstance();

    public void backgroundMusicPlay(int i) {
        if (player == null) {
            player = MediaPlayer.create(this, i);
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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        hideNavigationBar();

        binding = DataBindingUtil.setContentView(this, R.layout.activity_game_result);
        binding.setActivity(this);

        Intent intent = getIntent();
        index = intent.getIntExtra("index", 0);
        quizString1 = intent.getStringExtra("quizString1");
        quizString2 = intent.getStringExtra("quizString2");
        mSpeakTerm = intent.getStringExtra("speakTerm");
        mWriteTerm = intent.getStringExtra("writeTerm");

        //TODO: type 0 - 맞음, type 1 - 틀림
        int type;

        //맞았으면 SuccessFragment, 틀렸으면 FailFragment 세팅
        if(application.getWordList().get(application.getNowWordId()).getEnglish().equals(quizString1)) {
            boolean right = mSpeakTerm.equals(quizString1) && mWriteTerm.equals(quizString1);

            if (right) {
                getSupportFragmentManager().beginTransaction()
                        .add(R.id.container, new SuccessFragment())
                        .commit();
                type = 0;
                result1 = 1;

            } else {
                getSupportFragmentManager().beginTransaction()
                        .add(R.id.container, new FailFragment())
                        .commit();
                type = 1;
                result1= 0;
            }
        }
        else{
            boolean right = mSpeakTerm.equals(quizString2) && mWriteTerm.equals(quizString2);

            if (right) {
                getSupportFragmentManager().beginTransaction()
                        .add(R.id.container, new SuccessFragment())
                        .commit();
                type = 0;
                result2 = 1;

            } else {
                getSupportFragmentManager().beginTransaction()
                        .add(R.id.container, new FailFragment())
                        .commit();
                type = 1;
                result2 = 0;
            }
        }

        //음향 효과 따로 세팅해줘야 재생됨,,,
        if (type == 0) {
            backgroundMusicPlay(R.raw.cheering_clapping);
        } else {
            backgroundMusicPlay(R.raw.boo);
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
    protected void onStop() {
        super.onStop();

        if(soundPool != null) {
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

    public void onResultButtonClick(View v) {
        soundPool.play(sound_pop, 1, 1, 0, 0, 1);

        //TODO: 만약 첫번째 단어면 다음 단어 speak 액티비티로 이동 !!!!!!!!!
        if(application.getWordList().get(application.getNowWordId()).getEnglish().equals(quizString1)) {
            Intent intent = new Intent(GameResultActivity.this, GameSpeakActivity.class);

            intent.putExtra("index", 1);
            intent.putExtra("quizString1", quizString1);
            intent.putExtra("quizString2", quizString2);
            startActivity(intent);
        }
        //TODO: 만약 두번쨰 단어면 결과 액티비티로 이동
        else{
            Intent intent = new Intent(GameResultActivity.this, ResultActivity.class);

            intent.putExtra("quizString1", quizString1);
            intent.putExtra("quizString2", quizString2);
            intent.putExtra("firstQuizSol", result1);
            intent.putExtra("secondQuizSol", result2);

            startActivity(intent);
        }
    }
}
