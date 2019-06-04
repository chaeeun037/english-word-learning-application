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
        Button pen = (Button)view.findViewById(R.id.penButton);
        Button eraser = (Button) view.findViewById(R.id.eraserButton);

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

        return view;
    }

    private void init(View view) {
        shadowCanvasV = (DrawCanvasView) view.findViewById(R.id.shadowCanvas);
    }


    public Bitmap getCanvasBitmap() {
        shadowCanvasV.setDrawingCacheEnabled(true);
        shadowCanvasV.buildDrawingCache();
        Bitmap b = shadowCanvasV.getDrawingCache();
        // 캐쉬에서 가져온 비트맵을 복사해서 새로운 비트맵(스크린샷) 생성
        // Bitmap screenshot = Bitmap.createBitmap(this.getDrawingCache());
        //shadowCanvasV.setDrawingCacheEnabled(false);   // 캐쉬닫기
        return b;
        //return screenshot;
    }

}