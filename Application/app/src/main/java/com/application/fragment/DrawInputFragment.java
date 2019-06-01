package com.application.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

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
        Button handwirteReco = (Button)view.findViewById(R.id.handwirteRecoButton);

        //필기 인식 결과를 임시로 보여줄 textView
       // TextView textView = (TextView)view.findViewById(R.id.visionResult);

        /* 펜 버튼 눌렸을 때 */
        pen.setOnClickListener(new Button.OnClickListener(){
            @Override
            public void onClick(View v) {
                shadowCanvasV.pen();
            }
        });

        /* 지우개 버튼 눌렸을 때 */
        eraser.setOnClickListener(new Button.OnClickListener(){
            @Override
            public void onClick(View v) {
                shadowCanvasV.eraser();
            }
        });

        /* 필기 인식 버튼 눌렀을 때 - CouldVision 호출 */
        handwirteReco.setOnClickListener(new Button.OnClickListener(){
            @Override
            public void onClick(View v) {
                Toast.makeText(getContext(), "맞았어요! 정말 멋져요~", Toast.LENGTH_SHORT).show();
                /* 구현 필요
                 * 캔버스 이미지 가져와서
                  * 구글비전한테 보내기
                  * 결과값 가져오기
                  */
            }
        });

        return view;
    }

    private void init(View view) {
        shadowCanvasV = (DrawCanvasView) view.findViewById(R.id.shadowCanvas);
    }
}