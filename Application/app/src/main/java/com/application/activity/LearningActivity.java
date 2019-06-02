package com.application.activity;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

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
    LearningSummaryFragmentTomato learningSummaryFragmentTomato;
    LearningSummaryFragmentOrange learningSummaryFragmentOrange;

    LearningVoiceFragment learningVoiceFragment;
    LearningVoiceFragmentTomato learningVoiceFragmentTomato;
    LearningVoiceFragmentOrange learningVoiceFragmentOrange;

    LearningHandwriteFragment learningHandwriteFragment;
    LearningHandwriteFragmentTomato learningHandwriteFragmentTomato;
    LearningHandwriteFragmentOrange learningHandwriteFragmentOrange;

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
        learningSummaryFragmentTomato = new LearningSummaryFragmentTomato();
        learningSummaryFragmentOrange = new LearningSummaryFragmentOrange();

        learningVoiceFragment = new LearningVoiceFragment();
        learningVoiceFragmentTomato = new LearningVoiceFragmentTomato();
        learningVoiceFragmentOrange = new LearningVoiceFragmentOrange();

        learningHandwriteFragment = new LearningHandwriteFragment();
        learningHandwriteFragmentTomato = new LearningHandwriteFragmentTomato();
        learningHandwriteFragmentOrange = new LearningHandwriteFragmentOrange();

        if (savedInstanceState == null) {
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
        if(id == 1) {
            FragmentManager manager = getSupportFragmentManager();
            manager.beginTransaction().replace(R.id.container, learningVoiceFragmentTomato).commit();
        }
        else if(id == 2){
            FragmentManager manager = getSupportFragmentManager();
            manager.beginTransaction().replace(R.id.container, learningVoiceFragmentOrange).commit();
        }
        else{
            FragmentManager manager = getSupportFragmentManager();
            manager.beginTransaction().replace(R.id.container, learningVoiceFragment).commit();
        }
    }

    public void onVoicePrevButtonClick(View v) {
        FragmentManager manager = getSupportFragmentManager();
        manager.beginTransaction().replace(R.id.container, learningSummaryFragment).commit();
    }

    public void onVoiceNextButtonClick(View v, int id) {
        if(id == 1) {
            FragmentManager manager = getSupportFragmentManager();
            manager.beginTransaction().replace(R.id.container, learningHandwriteFragmentTomato).commit();
        }
        else if(id == 2) {
            FragmentManager manager = getSupportFragmentManager();
            manager.beginTransaction().replace(R.id.container, learningHandwriteFragmentOrange).commit();
        }
        else{
            FragmentManager manager = getSupportFragmentManager();
            manager.beginTransaction().replace(R.id.container, learningHandwriteFragment).commit();
        }
    }

    public void onHandwritePrevButtonClick(View v) {
        FragmentManager manager = getSupportFragmentManager();
        manager.beginTransaction().replace(R.id.container, learningVoiceFragment).commit();
    }

    public void onHandwriteNextButtonClick(View v, int id) {
        if(id == 1){
            FragmentManager manager = getSupportFragmentManager();
            manager.beginTransaction().replace(R.id.container, learningSummaryFragmentTomato).commit();
        }
        else if(id ==2){
            FragmentManager manager = getSupportFragmentManager();
            manager.beginTransaction().replace(R.id.container, learningSummaryFragmentOrange).commit();
        }
        else {
            // MainActivity의 Unit fragment로 가는것으로 수정 필요
            unitList.get(0).setHasCrown(true);
            Intent intent = new Intent(LearningActivity.this, MainActivity.class);
            startActivity(intent);
            //unitList.get(0).setHasCrown(true);
            //FragmentManager manager = getSupportFragmentManager();
            //manager.beginTransaction().replace(R.id.container, learningUnitFragment).commit();
        }

    }

}
