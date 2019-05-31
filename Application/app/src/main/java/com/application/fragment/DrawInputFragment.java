package com.application.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.application.R;

public class DrawInputFragment extends Fragment {

    private DrawCanvasView shadowCanvasV;

    public static DrawInputFragment newInstance() {
        return new DrawInputFragment();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.fragment_draw_input, container, false);
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

    private void init(View view) {

        shadowCanvasV = (DrawCanvasView) view.findViewById(R.id.shadowCanvas);
    }
}