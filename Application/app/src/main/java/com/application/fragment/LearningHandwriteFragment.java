package com.application.fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.application.EWLApplication;
import com.application.R;
import com.application.activity.LearningActivity;
import com.application.database.EWLADbHelper;
import com.application.database.Word;

import java.util.List;

/*
 * 지수 작성
 * < 손글씨 화면 거기>
 * 1. 캔버스 나오게
 * 2. 그림자 글씨 나오게
 * 3. 지우개 버튼 먹게*
 *
 * 20190521 - 화면은 뜨는데 뷰가 생기지는 X
 * 20190522 - 캔버스 생성 성공
 */

public class LearningHandwriteFragment extends Fragment {
    DrawCanvasView shadowCanvasV;
    Button btn2;
    EWLApplication application = EWLApplication.getInstance();
    LinearLayout linearLayout;
    public LearningHandwriteFragment() {
    }

    public static LearningSummaryFragment newInstance() {
        return new LearningSummaryFragment();
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        final View view = inflater.inflate(R.layout.fragment_learning_handwrite, container, false);
        linearLayout = (LinearLayout)view.findViewById(R.id.linearlayout);
        int id = getResources().getIdentifier(application.getWordList().get(application.getNowWordId()).getShadowSrc(), "drawable", getContext().getPackageName());
        linearLayout.setBackgroundResource(id);

        btn2 = (Button)view.findViewById(R.id.button2);

        btn2.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                ((LearningActivity) getActivity()).onHandwriteNextButtonClick(v, application.getWordList().get(0).getId()); }
        });

        init(view);

        //Button 정의
        Button pen = (Button)view.findViewById(R.id.penButton);
        Button eraser = (Button) view.findViewById(R.id.eraserButton);

        eraser.setOnClickListener(new Button.OnClickListener(){
            @Override
            public void onClick(View v) {
                shadowCanvasV.eraser();
            }
        });

        pen.setOnClickListener(new Button.OnClickListener(){
            @Override
            public void onClick(View v) {
                shadowCanvasV.pen();
            }
        });

        return view;
    }

    public void init(View view) {
        shadowCanvasV = (DrawCanvasView) view.findViewById(R.id.shadowCanvas);
    }
}