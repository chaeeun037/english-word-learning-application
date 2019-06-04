package com.application.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

import com.application.EWLApplication;
import com.application.R;

import org.w3c.dom.Text;

public class SpeakMainFragment extends Fragment {

    ImageView imageView;
    TextView englishText;
    EWLApplication application = EWLApplication.getInstance();

    public static SpeakMainFragment newInstance() {
        return new SpeakMainFragment();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_speak_main, container, false);
        imageView = (ImageView) view.findViewById(R.id.imageView);
        englishText = (TextView) view.findViewById(R.id.textView);

        int id = getResources().getIdentifier(application.getWordList().get(application.getNowWordId()).getImageSrc(), "drawable", getContext().getPackageName());
        String english = application.getWordList().get(application.getNowWordId()).getEnglish();

        imageView.setImageResource(id);
        englishText.setText(english);

        return view;
    }
}