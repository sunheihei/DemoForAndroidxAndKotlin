package com.sunexample.demoforandroidxandkotlin.customView;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.RectF;
import android.graphics.Shader;
import android.graphics.SweepGradient;

import android.util.AttributeSet;
import android.view.View;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.view.animation.LinearInterpolator;


import com.sunexample.demoforandroidxandkotlin.R;

import java.util.Calendar;

public class SunClockView extends View {

    public static final String TAG = "SunClockView";

    private Context mContext;
    private Paint mCircleProgressPaint, mCircleBg, mSecondPaint, mTextBgPaint, TextPaint;
    private Path mfinishPath;
    private int mWidth, mHeight;//View的长宽
    private int mRadius; //半径
    private int StrokeWidth;//进度粗细
    private int color[];
    private RectF oval;
    private float mStartArc, mArc;// 扫过的角度
    private int CurrentHour, CurrentMin, CurrentSecond;
    private int AnimDuration, StartSecond, EndSecond;
    private ValueAnimator valueAnimator, finalvalueAnimator;

    public SunClockView(Context context) {
        this(context, null);
    }

    public SunClockView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public SunClockView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.mContext = context;
        initView();
        initdata();
    }

    private void initdata() {
        getCurrentTime();
    }

    private void initView() {
        StrokeWidth = 30;

        mfinishPath = new Path();

        mCircleProgressPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mCircleProgressPaint.setStrokeWidth(StrokeWidth);
        mCircleProgressPaint.setStyle(Paint.Style.STROKE);
        mCircleProgressPaint.setStrokeCap(Paint.Cap.ROUND);
        color = new int[]{getResources().getColor(R.color.clockthirdcocolor), getResources().getColor(R.color.clocksecondcolor), getResources().getColor(R.color.clockthirdcocolor)};

        mCircleBg = new Paint(Paint.ANTI_ALIAS_FLAG);
        mCircleBg.setColor(getResources().getColor(R.color.clockbgcocolor));
        mCircleBg.setStrokeWidth(StrokeWidth);
        mCircleProgressPaint.setStrokeCap(Paint.Cap.ROUND);
        mCircleBg.setStyle(Paint.Style.STROKE);

        mSecondPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mSecondPaint.setStrokeWidth(5);
        mSecondPaint.setStyle(Paint.Style.STROKE);


        mTextBgPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mTextBgPaint.setColor(getResources().getColor(R.color.timebgcolor));
        mTextBgPaint.setStyle(Paint.Style.FILL_AND_STROKE);

    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        mWidth = w;
        mHeight = h;
        mRadius = (Math.min(mHeight, mWidth) - StrokeWidth * 2) / 2;
        oval = new RectF(StrokeWidth, StrokeWidth, mWidth - StrokeWidth, mHeight - StrokeWidth);

        Shader mSweepGradient = new SweepGradient((float) mWidth / 2, (float) mHeight / 2, color, null);
        Matrix mMatrix = new Matrix();
        mMatrix.setRotate(-93, (float) mWidth / 2, (float) mHeight / 2);
        mSweepGradient.setLocalMatrix(mMatrix);
        mCircleProgressPaint.setShader(mSweepGradient);
        SeconCircleAnim();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.drawCircle(mWidth / 2, mHeight / 2, mRadius, mCircleBg);
        canvas.drawCircle(mWidth / 2, mHeight / 2, mRadius - 60, mTextBgPaint);

        canvas.drawArc(oval, -90, mArc, false, mCircleProgressPaint);
        drawSecond(canvas);
    }


    private void drawSecond(Canvas canvas) {
        if (mArc >= 0) {
            for (int i = 0; i <= 360; i += 15) {
                if (i == 360 || i <= mArc) {
                    mSecondPaint.setColor(getResources().getColor(R.color.clockthirdcocolor));
                } else {
                    mSecondPaint.setColor(getResources().getColor(R.color.clockbgcocolor));
                }
                canvas.drawLine(mWidth / 2, StrokeWidth + 45, mWidth / 2, StrokeWidth + 30, mSecondPaint);
                canvas.rotate(15, mWidth / 2, mHeight / 2);
            }
        }
        if (mArc <= 0) {
            for (int i = 0; i <= 360; i += 15) {
                if (i == 360 || i <= Math.abs(360 + mArc)) {
                    mSecondPaint.setColor(getResources().getColor(R.color.clockbgcocolor));
                } else {
                    mSecondPaint.setColor(getResources().getColor(R.color.clockthirdcocolor));
                }
                canvas.drawLine(mWidth / 2, StrokeWidth + 45, mWidth / 2, StrokeWidth + 30, mSecondPaint);
                canvas.rotate(15, mWidth / 2, mHeight / 2);
            }
        }
    }


    private void SeconCircleAnim() {
        valueAnimator = ValueAnimator.ofFloat(StartSecond, EndSecond);
        valueAnimator.setDuration(AnimDuration);
        valueAnimator.setInterpolator(new LinearInterpolator());
        valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                mArc = (355 * (Float.parseFloat(animation.getAnimatedValue().toString()) / (float) 58));
                postInvalidate();
            }
        });
        valueAnimator.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                super.onAnimationEnd(animation);
                valueAnimator.cancel();
                SecondFinishAnim();
            }

            @Override
            public void onAnimationRepeat(Animator animation) {
                super.onAnimationRepeat(animation);
            }

            @Override
            public void onAnimationResume(Animator animation) {
                super.onAnimationResume(animation);
            }
        });
        valueAnimator.start();
    }

    private void SecondFinishAnim() {
        finalvalueAnimator = ValueAnimator.ofFloat(-355, 0);
        finalvalueAnimator.setDuration(2000);
        finalvalueAnimator.setInterpolator(new AccelerateDecelerateInterpolator());
        finalvalueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator valueAnimator) {
                mArc = Float.parseFloat(valueAnimator.getAnimatedValue().toString());
                postInvalidate();
            }
        });
        finalvalueAnimator.start();

        finalvalueAnimator.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animator) {

            }

            @Override
            public void onAnimationEnd(Animator animator) {
                finalvalueAnimator.cancel();
                AnimDuration = 58 * 1000;
                StartSecond = 0;
                SeconCircleAnim();
            }

            @Override
            public void onAnimationCancel(Animator animator) {

            }

            @Override
            public void onAnimationRepeat(Animator animator) {

            }
        });
    }

    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        valueAnimator.cancel();
        finalvalueAnimator.cancel();
    }

    //获取当前时间秒
    private void getCurrentTime() {
        Calendar CD = Calendar.getInstance();
        CurrentHour = CD.get(Calendar.HOUR);
        CurrentMin = CD.get(Calendar.MINUTE);
        CurrentSecond = CD.get(Calendar.SECOND);

        StartSecond = CurrentSecond;
        AnimDuration = (59 - CurrentSecond) * 1000;
        EndSecond = 59;
    }
}
