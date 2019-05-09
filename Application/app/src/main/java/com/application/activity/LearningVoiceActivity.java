package com.application.activity;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.speech.tts.TextToSpeech;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import com.application.R;
import com.application.databinding.ActivityLearningVoiceBinding;

import java.util.Locale;

import static android.speech.tts.TextToSpeech.ERROR;

public class LearningVoiceActivity extends AppCompatActivity {

    private TextToSpeech tts;
    private Button speakButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_learning_voice);

        speakButton = (Button) findViewById(R.id.speakButton);

        tts = new TextToSpeech(this, new TextToSpeech.OnInitListener() {
            @Override
            public void onInit(int status) {
                if (status != ERROR) {
                    tts.setLanguage(Locale.ENGLISH);
                }
            }
        });

        speakButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                tts.speak("APPLE", TextToSpeech.QUEUE_FLUSH, null);
            }
        });
    }
    @Override
    protected void onDestroy(){
        super.onDestroy();
        if(tts!=null){
            tts.stop();
            tts.shutdown();
            tts = null;
        }
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
