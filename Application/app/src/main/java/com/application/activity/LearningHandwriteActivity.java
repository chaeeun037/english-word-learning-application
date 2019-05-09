package com.application.activity;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.application.R;
import com.application.databinding.ActivityLearningHandwriteBinding;

public class LearningHandwriteActivity extends AppCompatActivity {
    private ActivityLearningHandwriteBinding binding;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_learning_handwrite);
    }

    public void onPrevButtonClick(View v) {
        Intent intent = new Intent(LearningHandwriteActivity.this, LearningVoiceActivity.class);
        startActivity(intent);
    }

    public void onNextButtonClick(View v) {
        Intent intent = new Intent(LearningHandwriteActivity.this, MainActivity.class);
        startActivity(intent);
    }
}
