package com.application.activity;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.application.R;
import com.application.databinding.ActivityGameDrawBinding;
import com.application.fragment.DrawInputFragment;
import com.application.fragment.DrawMainFragment;

public class GameDrawActivity extends AppCompatActivity {

    private ActivityGameDrawBinding binding;
  
    DrawMainFragment drawMainFragment;
    DrawInputFragment drawInputFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        hideNavigationBar();

        binding = DataBindingUtil.setContentView(this, R.layout.activity_game_draw);
        binding.setActivity(this);

        drawMainFragment = new DrawMainFragment();
        drawInputFragment = new DrawInputFragment();

        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.container, new DrawMainFragment())
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


    public void onMainNextButtonClick(View v) {
        FragmentManager manager = getSupportFragmentManager();
        manager.beginTransaction().replace(R.id.container, drawInputFragment).commit();
    }

    public void onInputNextButtonClick(View v) {
        Intent intent = new Intent(GameDrawActivity.this, GameResultActivity.class);
        startActivity(intent);
    }

}