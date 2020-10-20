package com.sunexample.demoforandroidxandkotlin.VideoTest;

import android.animation.Animator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.RectF;
import android.graphics.Typeface;
import android.graphics.fonts.Font;
import android.util.AttributeSet;
import android.view.View;
import android.view.animation.LinearInterpolator;

import androidx.annotation.Nullable;
import androidx.core.content.res.ResourcesCompat;


import com.sunexample.demoforandroidxandkotlin.R;


public class SunCircleCounter extends View {

    public static final String TAG = "SunCircleCounter";

    private Context mContext;
    private int duration, currentdura, Temptime;
    private float mArc, mRadius, StrokeWidth;
    private int mAddedtime;//延长时间
    private int mHeight, mWidth;
    private float baseline;
    private Paint mPaint, mTextPaint, mAddTextPaint;
    RectF oval;  //矩形，确定进度位置

    public SunCircleCounter(Context context) {
        this(context, null);
    }

    public SunCircleCounter(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public SunCircleCounter(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.mContext = context;
        init();
    }

    private void init() {
        currentdura = 0;
        StrokeWidth = 10;
        mPaint = new Paint();
        mPaint.setAntiAlias(true);
        mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setStrokeWidth(StrokeWidth);
        mPaint.setColor(getResources().getColor(R.color.rp_main_blue));


        mTextPaint = new Paint();
        mTextPaint.setTextSize(160);
        mTextPaint.setAntiAlias(true);
        mTextPaint.setTextAlign(Paint.Align.CENTER);
        mTextPaint.setStrokeWidth(8);
//        Typeface customTypeface = ResourcesCompat.getFont(mContext, R.font.anton);
//        mTextPaint.setTypeface(customTypeface);
        mTextPaint.setColor(getResources().getColor(R.color.rp_main_blue));


        mAddTextPaint = new Paint();
        mAddTextPaint.setTextSize(45);
        mAddTextPaint.setAntiAlias(true);
        mAddTextPaint.setTextAlign(Paint.Align.CENTER);
        mAddTextPaint.setStrokeWidth(2);
        mAddTextPaint.setColor(getResources().getColor(R.color.rp_main_blue));

    }


    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        mHeight = h;
        mWidth = w;
        oval = new RectF(StrokeWidth, StrokeWidth, mWidth - StrokeWidth, mHeight - StrokeWidth);

        //计算基线
        Paint.FontMetrics fontMetrics = mTextPaint.getFontMetrics();
        float distance = (fontMetrics.bottom - fontMetrics.top) / 2 - fontMetrics.bottom;
        baseline = oval.centerY() + distance;


    }

    @Override
    protected void onDraw(Canvas canvas) {
        mRadius = mWidth / 2 - StrokeWidth;
        canvas.drawArc(oval, -90, mArc, false, mPaint);
        canvas.drawText(String.valueOf(duration - Temptime), oval.centerX(), oval.centerY(), mTextPaint);

        if (mAddedtime != 0) {
            canvas.drawText("+" + mAddedtime + "s", oval.centerX(), (float) (mHeight * 0.8), mAddTextPaint);
        }

    }

    ValueAnimator valueAnimator;

    public void setDuration(int duration, int mAddedtime, CircleCountStatus countStatus) {
        this.mAddedtime = mAddedtime;
        this.mCountStatus = countStatus;
        this.duration = duration;
    }

    public void start(int duration) {
        Temptime = 10000;
        valueAnimator = ValueAnimator.ofFloat(0, 1f);
        valueAnimator.setDuration(duration * 1000);
        valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator valueAnimator) {

                mArc = 360 - (float) valueAnimator.getAnimatedValue() * 360;

                int Currenttime = (int) (((float) valueAnimator.getAnimatedValue()) * duration);
                if (Temptime != Currenttime) {
                    Temptime = Currenttime;
                    mCountStatus.second(duration - Temptime);
                }

                invalidate();

            }
        });
        valueAnimator.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animator) {
                mCountStatus.start();
            }

            @Override
            public void onAnimationEnd(Animator animator) {
                mCountStatus.finish();
            }

            @Override
            public void onAnimationCancel(Animator animator) {
                mCountStatus.cancel();
            }

            @Override
            public void onAnimationRepeat(Animator animator) {

            }
        });
        valueAnimator.setInterpolator(new LinearInterpolator());
        valueAnimator.start();
    }

    public void startAndroidpause() {
        if (valueAnimator == null) {
            start(duration);
            return;
        }
        if (valueAnimator.isPaused()) {
            valueAnimator.resume();
        } else {
            valueAnimator.pause();

        }
    }

    public void release() {
        if (valueAnimator != null) {
            valueAnimator.cancel();
            valueAnimator = null;
        }
    }


    void addTime() {
        if (mAddedtime != 0) {
            mCountStatus.addtime();
            duration = duration - Temptime + mAddedtime;
            valueAnimator.cancel();
            start(duration);
            //延长一次后自动为0，不允许第二次延长
            mAddedtime = 0;
        }
    }


    /**
     * 接口
     */
    public interface CircleCountStatus {
        void start();

        void cancel();

        void finish();

        void addtime();

        void second(int current);
    }

    CircleCountStatus mCountStatus;


}
