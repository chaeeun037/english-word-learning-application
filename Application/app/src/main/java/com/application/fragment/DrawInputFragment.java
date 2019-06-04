package com.application.fragment;

import android.graphics.Bitmap;
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
        Button pen = (Button) view.findViewById(R.id.penButton);
        Button eraser = (Button) view.findViewById(R.id.eraserButton);

        /* 펜 버튼 눌렸을 때 */
        pen.setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View v) {
                shadowCanvasV.pen();
            }
        });

        /* 지우개 버튼 눌렸을 때 */
        eraser.setOnClickListener(new Button.OnClickListener() {
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

    /* 화면 캡쳐후 비트맵으로 리턴 */
    public Bitmap getCanvasBitmap() {
        shadowCanvasV.setDrawingCacheEnabled(true); //캐시 열고
        shadowCanvasV.buildDrawingCache(); // 캐시
        Bitmap screenshot = shadowCanvasV.getDrawingCache();//캐시를 비트맵에 저장

        return screenshot;
        }

}