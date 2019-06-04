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
import android.view.SurfaceHolder;
import android.view.View;

import com.application.R;
import com.application.databinding.ActivityGameResultBinding;
import com.application.fragment.FailFragment;
import com.application.fragment.LearningUnitFragment;
import com.application.fragment.MainMenuFragment;
import com.application.fragment.SuccessFragment;

public class GameResultActivity extends AppCompatActivity {

    private ActivityGameResultBinding binding;

    SuccessFragment successFragment;
    FailFragment failFragment;

    MediaPlayer player;

    private SoundPool soundPool;

    private int sound_pop;

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


        //TODO: type 0 - 맞음, type 1 - 틀림
        int type = 1;

        //맞았으면 SuccessFragment, 틀렸으면 FailFragment 세팅
        if (type == 0) {
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.container, new SuccessFragment())
                    .commit();

        } else {
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.container, new FailFragment())
                    .commit();
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

    public void onResultButtonClick(View v) {
        soundPool.play(sound_pop, 1, 1, 0, 0, 1);
      
// <<<<<<< newlsh 소현아 너의 코드란다 어짜피 여기는 수정될거니까 내껄로 대충 놔둘게 -
//         if(true){
//             Intent intent = new Intent(GameResultActivity.this, ResultActivity.class);
//             startActivity(intent);
//         }else {
//             Intent intent = new Intent(GameResultActivity.this, GameSpeakActivity.class);
//             startActivity(intent);
//         }

        //TODO: 만약 첫번째 단어면 다음 단어 draw 액티비티로 이동
        Intent intent = new Intent(GameResultActivity.this, ResultActivity.class);
        startActivity(intent);

        //TODO: 만약 두번쨰 단어면 결과 액티비티로 이동

    }
}
