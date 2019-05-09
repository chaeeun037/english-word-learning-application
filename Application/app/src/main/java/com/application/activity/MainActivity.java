package com.application.activity;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.application.EWLApplication;
import com.application.R;
import com.application.databinding.ActivityMainBinding;
import com.application.fragment.MainMenuFragment;
import com.application.fragment.LearningThemeFragment;
import com.application.fragment.LearningUnitFragment;

public class MainActivity extends AppCompatActivity {

    private ActivityMainBinding binding;
    private EWLApplication application;

    public String point;

    MainMenuFragment MainMenuFragment;
    LearningThemeFragment LearningThemeFragment;
    LearningUnitFragment LearningUnitFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.application = (EWLApplication)getApplicationContext();

        binding = DataBindingUtil.setContentView(this, R.layout.activity_main);
        binding.setActivity(this);

        MainMenuFragment = new MainMenuFragment();
        LearningThemeFragment = new LearningThemeFragment();
        LearningUnitFragment = new LearningUnitFragment();

        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.container, new MainMenuFragment())
                    .commit();
        }

    }

    @Override
    protected void onResume() {
        super.onResume();

        application.setPoint(500);
        this.point = String.valueOf(application.getPoint());
    }

    public void onHomeButtonClick(View v) {
        FragmentManager manager = getSupportFragmentManager();
        manager.beginTransaction().replace(R.id.container, MainMenuFragment).commit();
    }

    public void onLearningButtonClick(View v) {
        FragmentManager manager = getSupportFragmentManager();
        manager.beginTransaction().replace(R.id.container, LearningThemeFragment).commit();
    }

    public void onThemeButtonClick(View v) {
        FragmentManager manager = getSupportFragmentManager();
        manager.beginTransaction().replace(R.id.container, LearningUnitFragment).commit();
    }

    public void onUnitButtonClick(View v) {
        Intent intent = new Intent(MainActivity.this, LearningMainActivity.class);
        startActivity(intent);
    }
}
