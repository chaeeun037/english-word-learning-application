package com.application.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.application.R;

public class ExerciseDrawFragment extends Fragment {
    private DrawCanvasView shadowCanvasV;

    Button pen;
    Button eraser;
    Button bluePen;
    Button redPen;
    Button greenPen;
    Button yellowPen;
    Button pinkPen;
    Button purplePen;
    Button reset;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.fragment_exercise_draw, container, false);
        init(view);

        //Button 정의
        pen = (Button) view.findViewById(R.id.penButton);
        eraser = (Button) view.findViewById(R.id.eraserButton);

        bluePen = (Button) view.findViewById(R.id.penColorBlue);
        redPen = (Button) view.findViewById(R.id.penColorRed);
        greenPen = (Button) view.findViewById(R.id.penColorGreen);
        yellowPen = (Button) view.findViewById(R.id.penColorYellow);
        pinkPen = (Button) view.findViewById(R.id.penColorPink);
        purplePen = (Button) view.findViewById(R.id.penColorPurple);

        reset = (Button)view.findViewById(R.id.reset);

        /* 펜 버튼 눌렸을 때 */
        pen.setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View v) {
                shadowCanvasV.exercisePen();
            }
        });

        /* 지우개 버튼 눌렸을 때 */
        eraser.setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View v) {
                shadowCanvasV.ExerciseEraser();
            }
        });

        /* 펜 색 */

        bluePen.setOnClickListener(new Button.OnClickListener(){
            @Override
            public void onClick(View v) {
                shadowCanvasV.bluePen();
            }
        });

        redPen.setOnClickListener(new Button.OnClickListener(){
            @Override
            public void onClick(View v) {
                shadowCanvasV.redPen();
            }
        });


        greenPen.setOnClickListener(new Button.OnClickListener(){
            @Override
            public void onClick(View v) {
                shadowCanvasV.greenPen();
            }
        });


        yellowPen.setOnClickListener(new Button.OnClickListener(){
            @Override
            public void onClick(View v) {
                shadowCanvasV.yellowPen();
            }
        });

        pinkPen.setOnClickListener(new Button.OnClickListener(){
            @Override
            public void onClick(View v) {
                shadowCanvasV.pinkPen();
            }
        });


        purplePen.setOnClickListener(new Button.OnClickListener(){
            @Override
            public void onClick(View v) {
                shadowCanvasV.purplePen();
            }
        });

        reset.setOnClickListener(new Button.OnClickListener(){
            @Override
            public void onClick(View v) {
                shadowCanvasV.eraser();
            }
        });


        return view;
    }

    private void init(View view) {
        shadowCanvasV = (DrawCanvasView) view.findViewById(R.id.shadowCanvas);
    }
}
