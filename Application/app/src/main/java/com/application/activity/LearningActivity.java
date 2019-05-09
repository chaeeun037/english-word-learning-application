package com.application.activity;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.application.R;
import com.application.databinding.ActivityLearningBinding;
import com.application.fragment.LearningHandwriteFragment;
import com.application.fragment.LearningSummaryFragment;
import com.application.fragment.LearningVoiceFragment;

public class LearningActivity extends AppCompatActivity {

    private ActivityLearningBinding binding;

    LearningSummaryFragment learningSummaryFragment;
    LearningVoiceFragment learningVoiceFragment;
    LearningHandwriteFragment learningHandwriteFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = DataBindingUtil.setContentView(this, R.layout.activity_learning);
        binding.setActivity(this);

        learningSummaryFragment = new LearningSummaryFragment();
        learningVoiceFragment = new LearningVoiceFragment();
        learningHandwriteFragment = new LearningHandwriteFragment();

        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.container, new LearningSummaryFragment())
                    .commit();
        }
    }


    public void onSummaryNextButtonClick(View v) {
        FragmentManager manager = getSupportFragmentManager();
        manager.beginTransaction().replace(R.id.container, learningVoiceFragment).commit();
    }

    public void onVoicePrevButtonClick(View v) {
        FragmentManager manager = getSupportFragmentManager();
        manager.beginTransaction().replace(R.id.container, learningSummaryFragment).commit();
    }

    public void onVoiceNextButtonClick(View v) {
        FragmentManager manager = getSupportFragmentManager();
        manager.beginTransaction().replace(R.id.container, learningHandwriteFragment).commit();
    }

    public void onHandwritePrevButtonClick(View v) {
        FragmentManager manager = getSupportFragmentManager();
        manager.beginTransaction().replace(R.id.container, learningVoiceFragment).commit();
    }

    public void onHandwriteNextButtonClick(View v) {
        // MainActivity의 Unit fragment로 가는것으로 수정 필요
        Intent intent = new Intent(LearningActivity.this, MainActivity.class);
        startActivity(intent);
    }

}
