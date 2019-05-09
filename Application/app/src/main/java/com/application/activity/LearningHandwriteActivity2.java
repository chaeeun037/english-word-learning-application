package com.application.activity;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PorterDuff;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.support.annotation.Nullable;

/*
 * 코드 작성자 코천 지수
 */


public class LearningHandwriteActivity2 extends View {

    private Path drawPath;
    private Paint drawPaint, canvasPaint;
    private int paintColor = Color.BLACK; //펜색
    private Canvas drawCanvas;
    private Bitmap canvasBitmap;

    //Button 정의
    // Button pen = findViewById(R.id.penButton);
    //Button eraser = (Button) findViewById(R.id.eraserButton);


    /* 생성자 */
    public LearningHandwriteActivity2(Context context){
        super(context);

        // setContentView(R.layout.activity_learning_handwrite);

        setupDrawing();//draw의 시작
    }

    /* 화면을 그려주는 메소드 */
    protected void onDraw(Canvas canvas) {
        //canvas.drawColor(Color.BLACK);
        //canvas.scale(canvas.getWidth()/(1080f), canvas.getHeight()/(1920f));

        canvas.drawBitmap(canvasBitmap, 0, 0, canvasPaint);
        canvas.drawPath(drawPath, drawPaint); // 저장된 path를 그리는 ~
    }

    /* Pen Button Clicked */
    public void penButtonClicked(View view){

    }

    /* Eraser Button Clicked */
    public void eraserButtonClicked(View view){
        drawCanvas.drawColor(Color.TRANSPARENT, PorterDuff.Mode.CLEAR); // 캔버스 초기화 함수
    }

    /* drawing을 위한 set up */
    private void setupDrawing(){

        drawPath = new Path(); // 이미 그어진 선을 저장하기 위한 객체
        drawPaint = new Paint();
        drawPaint.setColor(paintColor);
        drawPaint.setAntiAlias(true); //경계면을 부드럽게 처리
        drawPaint.setStrokeWidth(25); // 펜 굵기
        drawPaint.setStyle(Paint.Style.STROKE); // 선을 긋게 하는 부분
        drawPaint.setStrokeJoin(Paint.Join.ROUND);
        drawPaint.setStrokeCap(Paint.Cap.ROUND);
        canvasPaint = new Paint(Paint.DITHER_FLAG);
    }

    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged( w, h, oldw, oldh);
        canvasBitmap = Bitmap.createBitmap( w, h, Bitmap.Config.ARGB_8888);
        drawCanvas = new Canvas(canvasBitmap);
    }



    /* Touch 입력 받기 */
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        float touchX = event.getX();
        float touchY = event.getY();

        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN://누를 때
                drawPath.moveTo(touchX, touchY); // 위치 이동
                break;

            case MotionEvent.ACTION_MOVE://드래그
                drawPath.lineTo(touchX, touchY);// 위치에 선을 남김
                break;

            case MotionEvent.ACTION_UP://드롭
                drawPath.lineTo(touchX, touchY);
                drawCanvas.drawPath(drawPath, drawPaint);
                drawPath.reset();

                break;

            default:
                return false;
        }

        invalidate(); //화면을 다시 생성
        return true;

    }

}