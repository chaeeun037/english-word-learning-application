package com.application.activity;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

import com.application.R;
import com.application.databinding.ActivityGameSpeakBinding;
import com.application.fragment.SpeakInputFragment;
import com.application.fragment.SpeakMainFragment;

public class GameSpeakActivity extends AppCompatActivity {

    private ActivityGameSpeakBinding binding;

    SpeakInputFragment speakInputFragment;
    SpeakMainFragment speakMainFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        hideNavigationBar();

        binding = DataBindingUtil.setContentView(this, R.layout.activity_game_speak);
        binding.setActivity(this);

        speakMainFragment = new SpeakMainFragment();
        speakInputFragment = new SpeakInputFragment();

        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.container, new SpeakMainFragment())
                    .commit();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        final ImageView iv = (ImageView)findViewById(R.id.imageView2);

        Animation anim = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.game_chicken_anim);
        if (iv != null) {
            iv.startAnimation(anim);
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
        manager.beginTransaction().replace(R.id.container, speakInputFragment).commit();
    }

    public void onInputNextButtonClick(View v) {
        Intent intent = new Intent(GameSpeakActivity.this, GameResultActivity.class);
        startActivity(intent);
    }

}
