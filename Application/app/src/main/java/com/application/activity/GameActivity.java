package com.application.activity;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

import com.application.R;
import com.application.databinding.ActivityGameBinding;

public class GameActivity extends AppCompatActivity {

    private ActivityGameBinding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        hideNavigationBar();

        binding = DataBindingUtil.setContentView(this, R.layout.activity_game);
        binding.setActivity(this);

        final ImageView iv = (ImageView)findViewById(R.id.imageView1);

        Animation anim = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.game_chick_anim);
        iv.startAnimation(anim);
    }

    private void hideNavigationBar() {
        int uiOptions = getWindow().getDecorView().getSystemUiVisibility();
        int newUiOptions = uiOptions;
        newUiOptions ^= View.SYSTEM_UI_FLAG_HIDE_NAVIGATION;
        newUiOptions ^= View.SYSTEM_UI_FLAG_FULLSCREEN;
        newUiOptions ^= View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY;
        getWindow().getDecorView().setSystemUiVisibility(newUiOptions);
    }

    public void onStartButtonClick(View v) {
        Intent intent = new Intent(GameActivity.this, GameDrawActivity.class);
        startActivity(intent);

        /* 문제 완성 될 때까지 봉인 - 지수
        // 홀수이면
        if (true) {
            Intent intent = new Intent(GameActivity.this, GameSpeakActivity.class);
            startActivity(intent);
        }

        // 짝수이면
        else {
            Intent intent = new Intent(GameActivity.this, GameDrawActivity.class);
            startActivity(intent);
        }

*/



    }

}
