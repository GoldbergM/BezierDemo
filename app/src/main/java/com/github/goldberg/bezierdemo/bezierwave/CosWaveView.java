package com.github.goldberg.bezierdemo.bezierwave;

/**
 * Created by mengzhun on 2016/11/1.
 */

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.view.View;


public class CosWaveView extends View {

    NextFrameAction nextFrameAction;
    RectF rectF;
    Paint paint;
    Paint paint2;
    Paint paint3;
    Path path;
    Path path1;
    Path path2;
    int width;
    int height;
    int w = 0;
    double startTime;
    int waveAmplitude;
    int waveRange;
    int highLevel;

    public CosWaveView(Context context) {
        super(context);
    }

    public CosWaveView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public CosWaveView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    private void init() {
        nextFrameAction = new NextFrameAction();
        width = getMeasuredWidth();
        height = getMeasuredHeight();
        waveRange = width;
        rectF = new RectF(5, 5, width - 5, height - 5);
        paint = new Paint();
        paint2 = new Paint();
        paint3 = new Paint();
        paint.setAntiAlias(true);
        paint.setStyle(Paint.Style.FILL);
        paint.setColor(0x440000ff);
        paint3.setAntiAlias(true);
        paint3.setStyle(Paint.Style.FILL);
        paint3.setColor(0x440000ff);

        paint2.setAntiAlias(true);
        paint2.setStyle(Paint.Style.STROKE);
        paint2.setStrokeWidth(5);
        paint2.setColor(0x440000ff);
        path = new Path();
        path1 = new Path();
        path2= new Path();
        startTime = System.currentTimeMillis();
        waveAmplitude = 20;
        highLevel = (int) (height * (0.5) );
    }

    protected class NextFrameAction implements Runnable {

        @Override
        public void run() {
            path.reset();
            path1.reset();
            path.addArc(rectF, 0f, 180f);
            path1.addArc(rectF, 0f, 180f);
            w += 5;
            if (w >= (width - 5) * 2) {
                w = 0;
            }
//            path.moveTo(5,highLevel);
//            path1.moveTo(5,highLevel);
            for (int i = 5; i < width - 5; i++) {
                path.lineTo(i, (float) (highLevel + waveAmplitude * Math.cos((float) (i + w) / (float) (width - 5) * Math.PI)));
                path1.lineTo(i, (float) (highLevel - waveAmplitude * Math.cos((float) (i + w) / (float) (width - 5) * Math.PI)));
            }
            path.close();
            path1.close();
            invalidate();
        }
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        init();
    }

    @Override
    protected void onDraw(Canvas canvas) {

//        path2.addArc(rectF,0,360);
//        canvas.drawPath(path2,paint2);
        canvas.drawCircle(width / 2, height / 2, width / 2 - 5, paint2);
//        canvas.drawLine(0f,height/2,width,height/2,paint);
        //canvas.drawArc(rectF,90.0f-145.0f/2.0f,145.0f,false,paint);

//        canvas.clipPath(path2);
        canvas.drawPath(path, paint);
        canvas.drawPath(path1, paint3);
        postDelayed(nextFrameAction, 4);

    }
}
