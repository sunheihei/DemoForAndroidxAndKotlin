package com.sunexample.demoforandroidxandkotlin.FitnessTest;

import android.animation.Animator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;
import android.view.animation.LinearInterpolator;

import androidx.annotation.Nullable;


import com.sunexample.demoforandroidxandkotlin.R;

public class SunBottomCounterView extends View {

    public static final String TAG = "CounterView";

    private int duration;
    private int Temptime;
    private float mtempwidth;
    private int mHeight, mWidth;
    private Paint mPaint;


    public SunBottomCounterView(Context context) {
        this(context, null);
    }

    public SunBottomCounterView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public SunBottomCounterView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        mPaint = new Paint();
        mPaint.setColor(getResources().getColor(R.color.rp_main_blue));
    }


    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        mHeight = h;
        mWidth = w;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        canvas.drawRect(0, 0, mtempwidth, mHeight, mPaint);
    }

    //duration 如果是计时就代表时间，如果是记个就代表个数
    public void setDuration(int duration, CountStatus countStatus) {
        this.duration = duration;
        this.mCountStatus = countStatus;
    }

    ValueAnimator valueAnimator;

    private void start() {
        //计时
        Temptime = 0;
        valueAnimator = ValueAnimator.ofFloat(0, 1f);
        valueAnimator.setDuration(duration * 1000);
        valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                mtempwidth = (float) animation.getAnimatedValue() * mWidth;
                int time = (int) animation.getCurrentPlayTime() / 1000;
                if (Temptime != time) {
                    Temptime = time;
                    mCountStatus.second(time);
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

            }

            @Override
            public void onAnimationRepeat(Animator animator) {

            }
        });
        valueAnimator.setInterpolator(new LinearInterpolator());
        valueAnimator.start();
    }


    public void startorpause() {
        if (valueAnimator == null) {
            start();
            return;
        }
        if (valueAnimator.isPaused()) {
            valueAnimator.resume();
        } else {
            valueAnimator.pause();
            mCountStatus.pause();
        }
    }


    void release() {
        if (valueAnimator != null) {
            valueAnimator.cancel();
        }
    }

    /**
     * 接口
     */
    CountStatus mCountStatus;

    public interface CountStatus {
        void start();

        void pause();

        void finish();

        void second(int current);
    }


}
