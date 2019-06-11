package com.application.fragment;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

import java.lang.reflect.Array;

public class DrawCanvasView extends View {

    private Path drawPath;
    private Paint drawPaint, canvasPaint;
    private int paintColor = Color.rgb(35, 87, 209); //펜색
    private Canvas drawCanvas;
    private Bitmap canvasBitmap;

    public DrawCanvasView(Context context) {
        super(context);
        this.setLayerType(View.LAYER_TYPE_SOFTWARE, null);
    }

    public DrawCanvasView(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        this.setLayerType(View.LAYER_TYPE_SOFTWARE, null);
    }

    /* drawing을 위한 set up */
    private void setupDrawing() {
        drawPath = new Path(); // 이미 그어진 선을 저장하기 위한 객체
        drawPaint = new Paint();
        drawPaint.setColor(paintColor);
        drawPaint.setAntiAlias(true); //경계면을 부드럽게 처리
        drawPaint.setStrokeWidth(15); // 펜 굵기
        drawPaint.setStyle(Paint.Style.STROKE); // 선을 긋게 하는 부분
        drawPaint.setStrokeJoin(Paint.Join.ROUND);
        drawPaint.setStrokeCap(Paint.Cap.ROUND);
        canvasPaint = new Paint(Paint.DITHER_FLAG);
    }

    /* 화면을 그려주는 메소드 */
    @Override
    protected void onDraw(Canvas canvas) {
        setupDrawing();
        canvas.drawBitmap(canvasBitmap, 0, 0, canvasPaint);
    }

    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        canvasBitmap = Bitmap.createBitmap(w, h, Bitmap.Config.ARGB_8888);
        drawCanvas = new Canvas(canvasBitmap);
        drawCanvas.drawARGB(0, 255, 255, 255);
    }

    /* Touch 입력 받기 */
    int oldX = -1, oldY = -1;

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        int touchX = (int) event.getX();
        int touchY = (int) event.getY();

        if (event.getAction() == MotionEvent.ACTION_DOWN) {
            oldX = touchX;
            oldY = touchY;
        } else if (event.getAction() == MotionEvent.ACTION_MOVE) {
            if (oldX != -1) {
                drawCanvas.drawLine(oldX, oldY, touchX, touchY, drawPaint);
                invalidate();
                oldX = touchX;
                oldY = touchY;
            }
        } else if (event.getAction() == MotionEvent.ACTION_UP) {
            if (oldX != -1) {
                drawCanvas.drawLine(oldX, oldY, touchX, touchY, drawPaint);
                invalidate();
            }
            oldX = -1;
            oldY = -1;
        }
        return true;
    }

    /* 지우개버튼이 눌렸을 때 */
    public void eraser() {
        paintColor = Color.argb(0, 255, 255, 255); //캔버스 지우개 - 컬러가 투명으로
        drawCanvas.drawARGB(0,255, 255, 255); // 캔버스 초기화 함수 - 한번에 다 지우기
        drawCanvas.drawColor(Color.TRANSPARENT, PorterDuff.Mode.CLEAR);
        drawPaint.setStrokeWidth(0); // 펜 굵기
    }

    /* 펜버튼이 눌렸을 때 */
    public void pen() {
        paintColor = Color.rgb(35, 87, 209);
        drawPaint.setStrokeWidth(15); //  펜굵기
    }

    public Bitmap getCanvasBitmap() {
        return this.canvasBitmap;
    }

    /* 펜 색 */
    public void bluePen() {
        paintColor = Color.parseColor("#0000FF");
    }

    public void redPen() {
        paintColor = Color.parseColor("#FF0000");
    }

    public void greenPen() {
        paintColor = Color.parseColor("#80E12A");
    }

    public void yellowPen() {
        paintColor = Color.parseColor("#FFF064");
    }

    public void pinkPen() {
        paintColor = Color.parseColor("#FF7A85");
    }

    public void purplePen() {
        paintColor = Color.parseColor("#DA70D6");
    }
}