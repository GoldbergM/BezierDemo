package com.github.goldberg.bezierdemo.berzierball;

import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Point;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.AccelerateDecelerateInterpolator;

/**
 * Created by mengzhun on 2016/10/24.
 */

public class BezierBallView extends View {

    private Paint mPaint;
    private Path mPath;
    private Point startPoint;
    private Point endPoint;
    private static final float M = 0.551915024494f;
    private int centreX;
    private int centreY;
    private long DURATION = 1000;
    private int radius;
    private int radius_tem;
    private float MValue;
    private static final float STATE0 = 0.0f;//静止
    private static final float STATE1 = 0.2f;//开始到整个开始移动
    private static final float STATE2 = 0.5f;//
    private static final float STATE3 = 0.8f;//
    private static final float STATE4 = 0.9f;//
    private static final float STATE5 = 1.0f;//

    private int unitDistance;// 最小开始移动单位
    private static final int MOVE_RIGHT = 101;
    private static final int MOVE_LEFT = 102;
    private int currentOrientation = MOVE_RIGHT;


    public BezierBallView(Context context) {
        this(context, null, 0);
    }

    public BezierBallView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public BezierBallView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        mPaint = new Paint();
        mPaint.setColor(Color.GREEN);
        mPaint.setStyle(Paint.Style.FILL);
        mPaint.setStrokeWidth(8);
        mPaint.setAntiAlias(true);
        mPaint.setDither(true);
        mPath = new Path();
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldW, int oldH) {
        super.onSizeChanged(w, h, oldW, oldH);
        centreX = w / 2;
        centreY = h / 2;
        radius = centreX / 5;
        radius_tem = radius;
        MValue = M * radius;
        movedDistance = 6 * radius;
        unitDistance = radius;
        startPoint = new Point(2 * radius, centreY);
        endPoint = new Point(centreX + 3 * radius, centreY);

    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        mPath.reset();
        if (fraction == STATE0) {
            drawStep0(canvas);
        } else if (fraction < STATE1) {
            drawStep1(canvas);
        } else if (fraction < STATE2) {
            drawStep2(canvas);
        } else if (fraction < STATE3) {
            drawStep3(canvas);
        } else if (fraction <= STATE4) {
            drawStep4(canvas);
        } else if (fraction <= STATE5) {
            drawStep5(canvas);
        }

    }

    private void drawStep0(Canvas canvas) {
        if (currentOrientation == MOVE_RIGHT) {

            mPath.moveTo(startPoint.x, startPoint.y + radius);

            mPath.cubicTo(startPoint.x - MValue, startPoint.y + radius, startPoint.x - radius,
                    startPoint.y + MValue, startPoint.x - radius, startPoint.y);//第三象限


            mPath.cubicTo(startPoint.x - radius, startPoint.y - MValue, startPoint.x - MValue, startPoint.y - radius,
                    startPoint.x, startPoint.y - radius);//第二象限

            mPath.cubicTo(startPoint.x + MValue, startPoint.y - radius, startPoint.x + radius, startPoint.y - MValue,
                    startPoint.x + radius, startPoint.y);//第一象限

            mPath.cubicTo(startPoint.x + radius, startPoint.y + MValue, startPoint.x + MValue, startPoint.y + radius,
                    startPoint.x, startPoint.y + radius);//第四象限

            canvas.drawPath(mPath, mPaint);
        } else {

        }

    }

    private void drawStep1(Canvas canvas) {
        if (currentOrientation == MOVE_RIGHT) {

            mPath.moveTo(startPoint.x, startPoint.y + radius);

            mPath.cubicTo(startPoint.x - MValue, startPoint.y + radius, startPoint.x - radius,
                    startPoint.y + MValue, startPoint.x - radius, startPoint.y);//第三象限


            mPath.cubicTo(startPoint.x - radius, startPoint.y - MValue, startPoint.x - MValue, startPoint.y - radius,
                    startPoint.x, startPoint.y - radius);//第二象限

            mPath.cubicTo(startPoint.x + MValue, startPoint.y - radius, startPoint.x + radius + radius * (fraction / STATE1), startPoint.y - MValue,
                    startPoint.x + radius + radius * (fraction / STATE1), startPoint.y);//第一象限

            mPath.cubicTo(startPoint.x + radius + radius * (fraction / STATE1), startPoint.y + MValue, startPoint.x + MValue, startPoint.y + radius,
                    startPoint.x, startPoint.y + radius);//第四象限

            canvas.drawPath(mPath, mPaint);
        } else {

        }
    }


    private void drawStep2(Canvas canvas) {
        if (currentOrientation == MOVE_RIGHT) {
            float MValue = M * radius * (1 + ((fraction - STATE1) / (STATE2 - STATE1)) * 0.3f);
            float deltaX = unitDistance * (fraction - STATE1) / (STATE2 - STATE1);
            float radius_tem = deltaX / 6;//y轴缩小
            mPath.moveTo(startPoint.x + deltaX * 3 / 2, startPoint.y + radius - radius_tem);

            mPath.cubicTo(startPoint.x + deltaX * 3 / 2 - MValue, startPoint.y + radius - radius_tem,
                    startPoint.x - radius + deltaX, startPoint.y + MValue,
                    startPoint.x + deltaX - radius, startPoint.y);//第三象限


            mPath.cubicTo(startPoint.x + deltaX - radius, startPoint.y - MValue,
                    startPoint.x + deltaX * 3 / 2 - MValue, startPoint.y - radius + radius_tem,
                    startPoint.x + deltaX * 3 / 2, startPoint.y - radius + radius_tem);//第二象限

            mPath.cubicTo(startPoint.x + deltaX * 3 / 2 + MValue, startPoint.y - radius + radius_tem,
                    startPoint.x + 2 * radius + deltaX, startPoint.y - MValue,
                    startPoint.x + 2 * radius + deltaX, startPoint.y);//第一象限

            mPath.cubicTo(startPoint.x + 2 * radius + deltaX, startPoint.y + MValue,
                    startPoint.x + MValue + deltaX * 3 / 2, startPoint.y + radius - radius_tem,
                    startPoint.x + deltaX * 3 / 2, startPoint.y + radius - radius_tem);//第四象限

            canvas.drawPath(mPath, mPaint);
        } else {

        }
    }

    private void drawStep3(Canvas canvas) {
        if (currentOrientation == MOVE_RIGHT) {
            float MValue = M * radius * (1 + ((STATE3 - fraction) / (STATE3 - STATE2)) * 0.3f);
            float deltaX = unitDistance * (fraction - STATE2) / (STATE3 - STATE2);
            float radius_tem = unitDistance * (STATE3 - fraction) / (STATE3 - STATE2) / 6;//y轴拉伸
            mPath.moveTo(startPoint.x + (deltaX + unitDistance) * 3 / 2, startPoint.y + radius - radius_tem);

            mPath.cubicTo(startPoint.x + (deltaX + unitDistance) * 3 / 2 - MValue, startPoint.y + radius - radius_tem,
                    startPoint.x - radius + deltaX * 2 + unitDistance, startPoint.y + MValue,
                    startPoint.x + deltaX * 2 - radius + unitDistance, startPoint.y);//第三象限


            mPath.cubicTo(startPoint.x + deltaX * 2 - radius + unitDistance, startPoint.y - MValue,
                    startPoint.x + (deltaX + unitDistance) * 3 / 2 - MValue, startPoint.y - radius + radius_tem,
                    startPoint.x + (deltaX + unitDistance) * 3 / 2, startPoint.y - radius + radius_tem);//第二象限

            mPath.cubicTo(startPoint.x + (deltaX + unitDistance) * 3 / 2 + MValue, startPoint.y - radius + radius_tem,
                    startPoint.x + 2 * radius + deltaX + unitDistance, startPoint.y - MValue,
                    startPoint.x + 2 * radius + deltaX + unitDistance, startPoint.y);//第一象限

            mPath.cubicTo(startPoint.x + 2 * radius + deltaX + unitDistance, startPoint.y + MValue,
                    startPoint.x + MValue + (deltaX + unitDistance) * 3 / 2, startPoint.y + radius - radius_tem,
                    startPoint.x + (deltaX + unitDistance) * 3 / 2, startPoint.y + radius - radius_tem);//第四象限

            canvas.drawPath(mPath, mPaint);
        } else {

        }
    }

    private void drawStep4(Canvas canvas) {
        {
            if (currentOrientation == MOVE_RIGHT) {
                float MValue = M * radius;
                float deltaX = radius * (fraction - STATE3) / (STATE4 - STATE3) / 4;
                float radius_tem = radius * (fraction - STATE3) / (STATE4 - STATE3) / 6;//y轴拉伸
                float x = startPoint.x + 3 * unitDistance;
                mPath.moveTo(x, endPoint.y + radius + radius_tem);

                mPath.cubicTo(x + -MValue, startPoint.y + radius + radius_tem,
                        x - radius + deltaX, startPoint.y + MValue,
                        x - radius + deltaX, startPoint.y);//第三象限


                mPath.cubicTo(x + deltaX - radius, endPoint.y - MValue,
                        x + -MValue, startPoint.y - radius - radius_tem,
                        x, startPoint.y - radius - radius_tem);//第二象限

                mPath.cubicTo(x + MValue, endPoint.y - radius - radius_tem,
                        x + radius, endPoint.y - MValue,
                        x + radius, endPoint.y);//第一象限

                mPath.cubicTo(x + radius, endPoint.y + MValue,
                        x + MValue, endPoint.y + radius + radius_tem,
                        x, endPoint.y + radius + radius_tem);//第四象限

                canvas.drawPath(mPath, mPaint);
            } else {

            }

        }

    }

    private void drawStep5(Canvas canvas) {
        {
            if (currentOrientation == MOVE_RIGHT) {
                float MValue = M * radius;
                float deltaX = radius * (STATE5 - fraction) / (STATE5 - STATE4) / 4;
                float radius_tem = radius * (STATE5 - fraction) / (STATE5 - STATE4) / 6;//y轴拉伸
                float x = startPoint.x + 3 * unitDistance;
                mPath.moveTo(x, endPoint.y + radius + radius_tem);

                mPath.cubicTo(x + -MValue, startPoint.y + radius + radius_tem,
                        x - radius + deltaX, startPoint.y + MValue,
                        x - radius + deltaX, startPoint.y);//第三象限


                mPath.cubicTo(x + deltaX - radius, endPoint.y - MValue,
                        x + -MValue, startPoint.y - radius - radius_tem,
                        x, startPoint.y - radius - radius_tem);//第二象限

                mPath.cubicTo(x + MValue, endPoint.y - radius - radius_tem,
                        x + radius, endPoint.y - MValue,
                        x + radius, endPoint.y);//第一象限

                mPath.cubicTo(x + radius, endPoint.y + MValue,
                        x + MValue, endPoint.y + radius + radius_tem,
                        x, endPoint.y + radius + radius_tem);//第四象限

                canvas.drawPath(mPath, mPaint);
            } else {

            }

        }

    }


    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getActionMasked()) {
            case MotionEvent.ACTION_DOWN:
                anim();
                break;
        }
        return super.onTouchEvent(event);
    }

    private float fraction = 0.0f;
    private int movedDistance;//圓心距
    private ValueAnimator valueAnimator;

    private void anim() {
        valueAnimator = ValueAnimator.ofFloat(0, 1.0f);
        valueAnimator.setInterpolator(new AccelerateDecelerateInterpolator());
        valueAnimator.setDuration(DURATION);
        valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                fraction = animation.getAnimatedFraction();
                invalidate();
            }
        });
        valueAnimator.start();
    }
}
