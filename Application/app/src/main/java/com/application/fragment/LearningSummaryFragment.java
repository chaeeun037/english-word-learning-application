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
import android.widget.TextView;

import com.application.EWLApplication;
import com.application.R;
import com.application.activity.LearningActivity;
import com.application.activity.MainActivity;
import com.application.database.EWLADbHelper;
import com.application.database.Word;

import org.w3c.dom.Text;

import java.util.List;

public class LearningSummaryFragment extends Fragment {

    Button btn;
    ImageView imageView;
    TextView engView;
    TextView korView;
    Button preBtn;

    EWLApplication application = EWLApplication.getInstance();

    public static LearningSummaryFragment newInstance() {
        return new LearningSummaryFragment();
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_learning_summary, container, false);
        imageView = (ImageView)view.findViewById(R.id.imageView2);
        engView = (TextView)view.findViewById(R.id.english);
        korView = (TextView)view.findViewById(R.id.korean);

        int id = getResources().getIdentifier(application.getWordList().get(application.getNowWordId()).getImageSrc(), "drawable", getContext().getPackageName());
        String english = application.getWordList().get(application.getNowWordId()).getEnglish();
        String korean = application.getWordList().get(application.getNowWordId()).getKorean();

        imageView.setImageResource(id);
        engView.setText(english);
        korView.setText(korean);

        btn=(Button)view.findViewById(R.id.button);
        btn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                ((LearningActivity) getActivity()).onSummaryNextButtonClick(v);
            }
        });

        preBtn = (Button)view.findViewById(R.id.button1);
        preBtn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                ((LearningActivity)getActivity()).onSummaryPreButtonClick(v);
            }
        });

        return view;
    }
}