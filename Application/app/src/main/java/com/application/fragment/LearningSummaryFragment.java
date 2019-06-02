package com.application.fragment;

import android.media.Image;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;

import com.application.EWLApplication;
import com.application.R;
import com.application.activity.LearningActivity;
import com.application.activity.MainActivity;
import com.application.database.EWLADbHelper;
import com.application.database.Word;

import java.util.List;

public class LearningSummaryFragment extends Fragment {

    Button btn;
    ImageView imageView;
    EWLApplication application = EWLApplication.getInstance();

    public static LearningSummaryFragment newInstance() {
        return new LearningSummaryFragment();
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_learning_summary, container, false);
        imageView = (ImageView)view.findViewById(R.id.imageView2);
        int id = getResources().getIdentifier(application.getWordList().get(application.getNowWordId()).getImageSrc(), "drawable", getContext().getPackageName());
        imageView.setImageResource(id);

        btn=(Button)view.findViewById(R.id.button);
        btn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                ((LearningActivity) getActivity()).onSummaryNextButtonClick(v, application.getWordList().get(7).getId()); }
        });

        return view;
    }
}