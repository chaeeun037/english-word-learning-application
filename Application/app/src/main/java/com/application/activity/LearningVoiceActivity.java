package com.application.activity;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.application.R;
import com.application.databinding.ActivityLearningVoiceBinding;

public class LearningVoiceActivity extends AppCompatActivity {
    private ActivityLearningVoiceBinding binding;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_learning_voice);
    }

    public void onPrevButtonClick(View v) {
        Intent intent = new Intent(LearningVoiceActivity.this, LearningMainActivity.class);
        startActivity(intent);
    }

    public void onNextButtonClick(View v) {
        Intent intent = new Intent(LearningVoiceActivity.this, LearningHandwriteActivity.class);
        startActivity(intent);
    }
}
