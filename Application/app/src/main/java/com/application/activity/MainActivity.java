package com.application.activity;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

// import com.application.EWLApplication;
import com.application.R;
import com.application.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity {

    private ActivityMainBinding binding;
    //private EWLApplication application;

    public String point;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //this.application = (EWLApplication)getApplicationContext();

        binding = DataBindingUtil.setContentView(this, R.layout.activity_main);
        binding.setActivity(this);
    }
/*
    @Override
    protected void onResume() {
        super.onResume();

        application.setPoint(500);
        this.point = String.valueOf(application.getPoint());
    }

    public void onLearningButtonClick() {
        Intent intent = new Intent(MainActivity.this, LearningMainActivity.class);
        startActivity(intent);
    }
    */
}
