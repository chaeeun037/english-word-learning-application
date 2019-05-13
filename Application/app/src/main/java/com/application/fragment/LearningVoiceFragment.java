package com.application.fragment;

import android.os.Bundle;
import android.speech.tts.TextToSpeech;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.application.R;

import java.util.Locale;

import static android.speech.tts.TextToSpeech.ERROR;

public class LearningVoiceFragment extends Fragment {

    private TextToSpeech tts;
    private Button speakButton;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_learning_voice, container, false);

        speakButton = (Button) view.findViewById(R.id.speakButton);

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
                tts.speak("APPLE", TextToSpeech.QUEUE_FLUSH, null);
            }
        });

        return view;
    }
}