package com.application.fragment;

import android.os.Bundle;
import android.speech.tts.TextToSpeech;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.application.EWLApplication;
import com.application.R;
import com.application.activity.LearningActivity;
import com.application.database.EWLADbHelper;
import com.application.database.Word;

import java.util.List;
import java.util.Locale;

import static android.speech.tts.TextToSpeech.ERROR;

public class LearningVoiceFragment extends Fragment {

    private TextToSpeech tts;
    private Button speakButton;
    Button preBtn1;
    Button nextBtn2;
    List<Word> wordList = EWLADbHelper.WordList;
    ImageView imageView;
    TextView textView;

    EWLApplication application = EWLApplication.getInstance();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_learning_voice, container, false);
        imageView = (ImageView) view.findViewById(R.id.imageView2);
        textView = (TextView) view.findViewById(R.id.english);

        int id = getResources().getIdentifier(application.getWordList().get(application.getNowWordId()).getImageSrc(), "drawable", getContext().getPackageName());
        imageView.setImageResource(id);

        String english = application.getWordList().get(application.getNowWordId()).getEnglish();
        textView.setText(english);

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
                tts.setPitch((float) 1.5);
                tts.setSpeechRate((float) 0.8);
                tts.speak(application.WordList.get(application.getNowWordId()).getEnglish(), TextToSpeech.QUEUE_FLUSH, null);
            }
        });

        nextBtn2 = (Button) view.findViewById(R.id.button2);

        nextBtn2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((LearningActivity) getActivity()).onVoiceNextButtonClick(v);
            }
        });

        preBtn1 = (Button) view.findViewById(R.id.button1);

        preBtn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((LearningActivity) getActivity()).onVoicePrevButtonClick(v);
            }
        });

        return view;
    }
}
