package com.application.activity;

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
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.application.CloudVision.CloudVision;
import com.application.EWLApplication;
import com.application.R;
import com.application.database.EWLADbHelper;
import com.application.database.Unit;
import com.application.database.Word;
import com.application.databinding.ActivityLearningBinding;
import com.application.fragment.LearningHandwriteFragment;
import com.application.fragment.LearningSummaryFragment;
import com.application.fragment.LearningUnitFragment;
import com.application.fragment.LearningVoiceFragment;

import java.io.ByteArrayOutputStream;
import java.util.List;

import static android.icu.text.DisplayContext.LENGTH_SHORT;

public class LearningActivity extends AppCompatActivity {

    private ActivityLearningBinding binding;

    LearningUnitFragment learningUnitFragment;
    LearningSummaryFragment learningSummaryFragment;
    LearningVoiceFragment learningVoiceFragment;
    LearningHandwriteFragment learningHandwriteFragment;

    EWLApplication application = EWLApplication.getInstance();

    int nowPage;
    List<Word> wordList;
    List<Unit> unitList;

    private SoundPool soundPool;

    private int sound_pop;
    private int sound_stamp;
    private int sound_coin;

    @Override
    public void onBackPressed() {
        //super.onBackPressed();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        hideNavigationBar();

        binding = DataBindingUtil.setContentView(this, R.layout.activity_learning);
        binding.setActivity(this);
        wordList = EWLADbHelper.WordList;
        unitList = EWLADbHelper.UnitList;

        /*
        Intent intent = getIntent();
        unitId = intent.getExtras().getInt("id");
        */

        learningUnitFragment = new LearningUnitFragment();
        learningSummaryFragment = new LearningSummaryFragment();
        learningVoiceFragment = new LearningVoiceFragment();
        learningHandwriteFragment = new LearningHandwriteFragment();

        if (savedInstanceState == null) {
            // 유닛 리스트에서 한 유닛을 선택하면 가장 처음 생기는 것
            nowPage = 0;
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.container, new LearningSummaryFragment())
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
        sound_stamp = soundPool.load(this, R.raw.stamping, 1);
        sound_coin = soundPool.load(this, R.raw.coins, 1);
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

    private void showLoadingToast() {
        View toastView = getLayoutInflater().inflate(R.layout.toast_complete, null);

        Toast toast = new Toast(getApplicationContext());
        toast.setView(toastView);
        toast.setDuration(Toast.LENGTH_SHORT);
        toast.setGravity(Gravity.CENTER, 0, 0);
        toast.show();
    }


    //이전 Handwrite 화면으로 되돌아가기
    public void onSummaryPreButtonClick(View v) {
        soundPool.play(sound_pop, 1, 1, 0, 0, 1);

        nowPage = nowPage - 1;

        if (nowPage == -1) {
            Intent intent = new Intent(LearningActivity.this, MainActivity.class);
            intent.putExtra("type", 1);
            startActivity(intent);
        } else {
            application.setNowWordId(application.getNowWordId() - 1);
            FragmentManager manager = getSupportFragmentManager();
            manager.beginTransaction().replace(R.id.container, learningHandwriteFragment).commit();
        }
    }

    //voice 화면으로 넘어가기
    public void onSummaryNextButtonClick(View v) {
        soundPool.play(sound_pop, 1, 1, 0, 0, 1);

        FragmentManager manager = getSupportFragmentManager();
        manager.beginTransaction().replace(R.id.container, learningVoiceFragment).commit();
    }

    //summary 화면으로 되돌아가기
    public void onVoicePrevButtonClick(View v) {
        soundPool.play(sound_pop, 1, 1, 0, 0, 1);

        FragmentManager manager = getSupportFragmentManager();
        manager.beginTransaction().replace(R.id.container, learningSummaryFragment).commit();
    }

    //handwrite 화면으로 넘어가기
    public void onVoiceNextButtonClick(View v) {
        soundPool.play(sound_pop, 1, 1, 0, 0, 1);

        FragmentManager manager = getSupportFragmentManager();
        manager.beginTransaction().replace(R.id.container, learningHandwriteFragment).commit();
    }

    //voice 화면으로 되돌아가기
    public void onHandwritePrevButtonClick(View v) {
        soundPool.play(sound_pop, 1, 1, 0, 0, 1);

        FragmentManager manager = getSupportFragmentManager();
        manager.beginTransaction().replace(R.id.container, learningVoiceFragment).commit();
    }

    //다음 summary 화면으로 넘어가기
    public void onHandwriteNextButtonClick(View v) {
        soundPool.play(sound_pop, 1, 1, 0, 0, 1);

        nowPage = nowPage + 1;

        if (nowPage == 3) {
            Button prev = (Button) v.findViewById(R.id.button1);
            Button next = (Button) v.findViewById(R.id.button2);

            if (prev != null) {
                prev.setEnabled(false);
            }
            if (next != null) {
                next.setEnabled(false);
            }

            showLoadingToast();

            final Handler handler = new Handler();

            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    int unitIdOfNowWord = application.getWordList().get(application.getNowWordId()).getUnit_id();
                    application.getUnitList().get(unitIdOfNowWord - 1).setHasCrown(true);

                    Intent intent = new Intent(LearningActivity.this, MainActivity.class);
                    intent.putExtra("type", 1);

                    startActivity(intent);

                    soundPool.play(sound_stamp, 0.6f, 0.6f, 0, 0, 1);
                }
            }, 1600);


        } else {
            application.setNowWordId(application.getNowWordId() + 1);
            FragmentManager manager = getSupportFragmentManager();
            manager.beginTransaction().replace(R.id.container, learningSummaryFragment).commit();
        }
    }
}
