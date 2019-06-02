package com.application.fragment;

import android.os.Bundle;
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

public class LearningSummaryFragmentOrange extends Fragment {

    List<Word> wordList = EWLADbHelper.WordList;
    Button btn;

    public static LearningSummaryFragmentOrange newInstance() {
        return new LearningSummaryFragmentOrange();
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_learning_summary_orange, container, false);

        btn = (Button)view.findViewById(R.id.button);

        btn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                ((LearningActivity) getActivity()).onSummaryNextButtonClick(v, wordList.get(1).getId()); }
        });

        return view;
    }
}