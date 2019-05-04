package com.application.activity;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import com.application.R;
import com.application.databinding.ActivityLearningBinding;

public class LearningVoiceActivity extends AppCompatActivity {
    private ActivityLearningBinding binding;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_learning_main);
    }
}
