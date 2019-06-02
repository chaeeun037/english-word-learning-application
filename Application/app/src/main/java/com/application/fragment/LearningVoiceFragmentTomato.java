package com.application.fragment;

import android.os.Bundle;
import android.speech.tts.TextToSpeech;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.application.R;
import com.application.activity.LearningActivity;
import com.application.database.EWLADbHelper;
import com.application.database.Word;

import java.util.List;
import java.util.Locale;

import static android.speech.tts.TextToSpeech.ERROR;

public class LearningVoiceFragmentTomato extends Fragment {

    private TextToSpeech tts;
    private Button speakButton;
    Button btn2;
    List<Word> wordList = EWLADbHelper.WordList;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_learning_voice_tomato, container, false);

        speakButton = (Button) view.findViewById(R.id.speakButton);
        btn2 = (Button)view.findViewById(R.id.button2);

        tts = new TextToSpeech(getActivity(), new TextToSpeech.OnInitListener() {
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
                tts.speak("TOMATO", TextToSpeech.QUEUE_FLUSH, null);
            }
        });

        btn2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((LearningActivity) getActivity()).onVoiceNextButtonClick(v, wordList.get(0).getId());
            }
        });
        return view;
    }
}
