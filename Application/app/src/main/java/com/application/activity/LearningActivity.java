package com.application.activity;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;

import com.application.EWLApplication;
import com.application.R;
import com.application.database.EWLADbHelper;
import com.application.database.Unit;
import com.application.database.Word;
import com.application.databinding.ActivityLearningBinding;
import com.application.fragment.LearningHandwriteFragment;
import com.application.fragment.LearningHandwriteFragmentOrange;
import com.application.fragment.LearningHandwriteFragmentTomato;
import com.application.fragment.LearningSummaryFragment;
import com.application.fragment.LearningSummaryFragmentOrange;
import com.application.fragment.LearningSummaryFragmentTomato;
import com.application.fragment.LearningUnitFragment;
import com.application.fragment.LearningVoiceFragment;
import com.application.fragment.LearningVoiceFragmentOrange;
import com.application.fragment.LearningVoiceFragmentTomato;

import java.util.List;

public class LearningActivity extends AppCompatActivity {

    private ActivityLearningBinding binding;

    LearningUnitFragment learningUnitFragment;
    LearningSummaryFragment learningSummaryFragment;
    LearningVoiceFragment learningVoiceFragment;
    LearningHandwriteFragment learningHandwriteFragment;

    EWLApplication application = EWLApplication.getInstance();

    int unitId;
    List<Word> wordList;
    List<Unit> unitList;

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
            Log.d("now", "0");
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.container, new LearningSummaryFragment())
                    .commit();
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

    public void onSummaryNextButtonClick(View v, int id) {
        // 학습 버튼을 누르면 생기는 거
        Log.d("now", "1");
        FragmentManager manager = getSupportFragmentManager();
        manager.beginTransaction().replace(R.id.container, learningVoiceFragment).commit();
    }

    public void onVoiceNextButtonClick(View v, int id) {
        Log.d("now", "3");
        FragmentManager manager = getSupportFragmentManager();
        manager.beginTransaction().replace(R.id.container, learningHandwriteFragment).commit();
    }

    public void onHandwriteNextButtonClick(View v, int id) {
        Log.d("now", "5");
        application.setNowWordId(application.getNowWordId() + 1);
        FragmentManager manager = getSupportFragmentManager();
        manager.beginTransaction().replace(R.id.container, learningSummaryFragment).commit();
    }
}
