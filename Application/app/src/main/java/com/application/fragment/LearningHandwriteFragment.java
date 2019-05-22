package com.application.fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;

import com.application.R;

/*
 * 지수 작성
 * < 손글씨 화면 거기>
 * 1. 캔버스 나오게
 * 2. 그림자 글씨 나오게
 * 3. 지우개 버튼 먹게*
 *
 * 20190521 - 화면은 뜨는데 뷰가 생기지는 X
 */

public class LearningHandwriteFragment extends Fragment {
    DrawCanvasView shadowCanvasV;

    public LearningHandwriteFragment() {
    }

    public static LearningSummaryFragment newInstance() {
        return new LearningSummaryFragment();
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        View view = inflater.inflate(R.layout.fragment_learning_handwrite, container, false);
        //shadowCanvasV= new DrawCanvasView(getContext()); //캔버스
        init(view);
        shadowCanvasV = (DrawCanvasView) view.findViewById(R.id.shadowCanvas);

        //Button 정의 ****추후 수정
        Button pen = (Button)view.findViewById(R.id.penButton);
        Button eraser = (Button) view.findViewById(R.id.eraserButton);



        //LayoutInflater li = (LayoutInflater)getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);


       /* LinearLayout shadowCanvas = (LinearLayout) view.findViewById(R.id.shadowCanvas); //캔버스가 추가될 linearLayout

        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT, 1f);


        shadowCanvas.addView(shadowCanvasV);
*/

/*
        pen.setOnClickListener(new Button.OnClickListener(){
            @Override
            public void onClick(View v)
            {
                LayoutInflater inflater1 = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                LinearLayout a = (LinearLayout) inflater1.inflate(R.layout.fragment_learning_handwrite, null);
                shadowCanvas.addView(a);
                }

        });

        minianvas.addView(shadowCanvasV);
        */

        return view;
    }

    public void init(View view) {
        shadowCanvasV = (DrawCanvasView) view.findViewById(R.id.shadowCanvas);
    }

    /* 버튼을 클릭하면 뷰가 생성되도록 하고 싶었으나... */
    public void penButtonClicked(){
        /*
        LinearLayout miniCanvas = (LinearLayout)getView().findViewById(R.id.shadowCanvas);
        LayoutInflater inflater1 = (LayoutInflater)getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.fragment_learning_handwrite, container, false);
        miniCanvas.addView(view);
        */
    }

    public void eraserButtonClicked(){
        shadowCanvasV.eraseAll();
    }
}