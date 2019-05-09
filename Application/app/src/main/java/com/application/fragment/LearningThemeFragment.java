package com.application.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.application.R;

public class LearningThemeFragment extends Fragment {

    public static LearningThemeFragment newInstance() {
        return new LearningThemeFragment();
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        // click시 Fragment를 전환할 event를 발생시킬 버튼을 정의합니다.
        //Button button1 = (Button)view.findViewById(R.id.study_button);

        return inflater.inflate(R.layout.fragment_learning_theme, container, false);
    }
}