package com.application.activity;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;

import com.application.EWLApplication;
import com.application.R;
import com.application.database.EWLADbHelper;
import com.application.databinding.ActivityMainBinding;
import com.application.fragment.MainMenuFragment;
import com.application.fragment.LearningThemeFragment;
import com.application.fragment.LearningUnitFragment;

public class MainActivity extends AppCompatActivity {

    private ActivityMainBinding binding;
    private EWLApplication application;

    public String point;

    MainMenuFragment mainMenuFragment;
    LearningThemeFragment learningThemeFragment;
    LearningUnitFragment learningUnitFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        hideNavigationBar();

        this.application = (EWLApplication) getApplicationContext();

        binding = DataBindingUtil.setContentView(this, R.layout.activity_main);
        binding.setActivity(this);

        mainMenuFragment = new MainMenuFragment();
        learningThemeFragment = new LearningThemeFragment();
        learningUnitFragment = new LearningUnitFragment();

        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.container, new MainMenuFragment())
                    .commit();
        }

        Log.d("***DB 생성", "" + EWLADbHelper.getsInstance(this));
    }

    @Override
    protected void onResume() {
        super.onResume();

        application.setPoint(500);
        this.point = String.valueOf(application.getPoint());
    }

    private void hideNavigationBar() {
        int uiOptions = getWindow().getDecorView().getSystemUiVisibility();
        int newUiOptions = uiOptions;
        newUiOptions ^= View.SYSTEM_UI_FLAG_HIDE_NAVIGATION;
        newUiOptions ^= View.SYSTEM_UI_FLAG_FULLSCREEN;
        newUiOptions ^= View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY;
        getWindow().getDecorView().setSystemUiVisibility(newUiOptions);
    }

    public void onHomeButtonClick(View v) {
        FragmentManager manager = getSupportFragmentManager();
        manager.beginTransaction().replace(R.id.container, mainMenuFragment).commit();
    }

    public void onLearningButtonClick(View v) {
        FragmentManager manager = getSupportFragmentManager();
        manager.beginTransaction().replace(R.id.container, learningThemeFragment).commit();
    }

    public void onThemeButtonClick(View v) {
        FragmentManager manager = getSupportFragmentManager();
        manager.beginTransaction().replace(R.id.container, learningUnitFragment).commit();
    }

    public void onUnitButtonClick(View v) {
        Intent intent = new Intent(MainActivity.this, LearningActivity.class);
        startActivity(intent);
    }
}
