package com.application.activity;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Bitmap;
import android.graphics.PorterDuff;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.app.Activity;
import android.widget.CheckBox;


import com.application.R;
import com.application.databinding.ActivityLearningHandwriteBinding;

import java.util.*;

/*
 * 코드 작성자 코천 지수
 */



public class LearningHandwriteActivity extends AppCompatActivity{
    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.activity_learning_handwrite);
        LearningHandwriteActivity2 lha2 = new LearningHandwriteActivity2(this);
        setContentView(R.layout.activity_learning_handwrite);
        //lha2 = (LearningHandwriteActivity2)findViewById(R.id.shadowCanvas);
    }

}