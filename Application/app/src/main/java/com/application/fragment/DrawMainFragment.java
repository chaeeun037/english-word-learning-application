package com.application.fragment;

import android.media.Image;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.application.EWLApplication;
import com.application.R;

import org.w3c.dom.Text;

public class DrawMainFragment extends Fragment {

    EWLApplication application = EWLApplication.getInstance();
    ImageView imageView;
    TextView koreanText;

    public static DrawMainFragment newInstance() {
        return new DrawMainFragment();
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_speak_main, container, false);
        imageView = (ImageView) view.findViewById(R.id.imageView);
        koreanText = (TextView) view.findViewById(R.id.textView);

        int id = getResources().getIdentifier(application.getWordList().get(application.getNowWordId()).getImageSrc(), "drawable", getContext().getPackageName());
        String korean = application.getWordList().get(application.getNowWordId()).getKorean();

        imageView.setImageResource(id);
        koreanText.setText(korean);

        return view;
    }
}