package com.application.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.application.R;
import com.application.activity.LearningActivity;
import com.application.activity.MainActivity;
import com.application.database.EWLADbHelper;
import com.application.database.Word;

import java.util.List;

public class LearningSummaryFragment extends Fragment {

    List<Word> wordList = EWLADbHelper.WordList;
    Button btn;

    public static LearningSummaryFragment newInstance() {
        return new LearningSummaryFragment();
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_learning_summary, container, false);

        btn=(Button)view.findViewById(R.id.button);
        btn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                ((LearningActivity) getActivity()).onSummaryNextButtonClick(v, wordList.get(7).getId()); }
        });

        return view;
    }
}