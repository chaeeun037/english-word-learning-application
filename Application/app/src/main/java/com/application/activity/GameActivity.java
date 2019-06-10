package com.application.activity;

import android.content.DialogInterface;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.media.AudioAttributes;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.media.SoundPool;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AlertDialog;
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
import com.application.databinding.ActivityGameBinding;
import com.application.fragment.LearningThemeFragment;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class GameActivity extends AppCompatActivity {

    private ActivityGameBinding binding;
    EWLApplication application = EWLApplication.getInstance();

    MediaPlayer player;

    private SoundPool soundPool;

    private int sound_pop;

    //hasCrownWordList
    static ArrayList<Word> canQuizWord = new ArrayList<>();
    //확정된 quiz word list
    static ArrayList<String> rightQuizWord = new ArrayList<>();
    List<Word> wordList;

    int quiz1;
    int quiz2;
    String quizString1;
    String quizString2;

    private int sound_coins;

    public void makeQuizList() {

        for (int i = 0; i < 18; i++) {
            if (application.getUnitList().get((application.getWordList().get(i).getUnit_id()) - 1).getHasCrown()) {
                Word word = application.getWordList().get(i);
                canQuizWord.add(word);
            }
        }

        //만들어진 canQuizWord가 비어있는 경우 == hasCrown이 없는 경우
        if (canQuizWord.isEmpty()) {
            AlertDialog.Builder dialog = new AlertDialog.Builder(this);
            dialog.setMessage("손님을 맞이 하기 위해선 교육을 충분히 받아야 해요.\n학습을 선택해서 교육을 받아봐요!").setCancelable(
                    false).setPositiveButton("알겠습니다.",
                    new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            // Action for 'Yes' Button
                            soundPool.play(sound_pop, 1, 1, 0, 0, 1);

                            Intent intent = new Intent(GameActivity.this, MainActivity.class);
                            startActivity(intent);
                        }
                    });
            AlertDialog alert = dialog.create();
            // Title for AlertDialog
            alert.setTitle("교육을 제대로 듣지 않았군요!");
            // Icon for AlertDialog
            alert.setIcon(R.drawable.crown_basket);
            alert.show();

            return;
        }

        Random random = new Random();
        quiz1 = random.nextInt(canQuizWord.size());
        quiz2 = random.nextInt(canQuizWord.size());

        while (true) {
            if (quiz2 == quiz1)
                quiz2 = random.nextInt(canQuizWord.size());
            else
                break;
        }

        for (int i = 0; i < wordList.size(); i++) {
            if (wordList.get(i).getEnglish().equals(canQuizWord.get(quiz1).getEnglish()))
                quizString1 = wordList.get(i).getEnglish();
            if (wordList.get(i).getEnglish().equals(canQuizWord.get(quiz2).getEnglish()))
                quizString2 = wordList.get(i).getEnglish();
        }

        rightQuizWord.add(quizString1);
        rightQuizWord.add(quizString2);
    }

    public static ArrayList<String> getRightQuizWord() {
        return rightQuizWord;
    }

    public static void setQuizList() {
        rightQuizWord.clear();
        canQuizWord.clear();
    }

    public void onPrevButtonClick(View v) {
        soundPool.play(sound_pop, 1, 1, 0, 0, 1);

        Intent intent = new Intent(GameActivity.this, MainActivity.class);
        startActivity(intent);
        finish();
    }

    public void onStartButtonClick(View v) {
        soundPool.play(sound_pop, 1, 1, 0, 0, 1);

        //Intent intent = new Intent(GameActivity.this, GameSpeakActivity.class);
        Intent intent = new Intent(GameActivity.this, GameSpeakActivity.class);
        intent.putExtra("index", 0);
        startActivity(intent);
    }

    public void initSoundPool() {
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

    public void backgroundMusicPlay() {
        if (player == null) {
            player = MediaPlayer.create(this, R.raw.wexford);
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

        binding = DataBindingUtil.setContentView(this, R.layout.activity_game);
        binding.setActivity(this);

        final ImageView iv = (ImageView) findViewById(R.id.imageView1);

        wordList = EWLADbHelper.WordList;

        if (canQuizWord.isEmpty()) {
            makeQuizList();
        }

        Animation anim = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.game_chick_anim);
        iv.startAnimation(anim);

        initSoundPool();

        backgroundMusicPlay();
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

        stopPlayer();

        if (soundPool != null) {
            soundPool.release();
        }

        soundPool = null;
    }
}
