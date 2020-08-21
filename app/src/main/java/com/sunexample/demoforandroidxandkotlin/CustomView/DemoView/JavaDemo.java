package com.sunexample.demoforandroidxandkotlin.CustomView.DemoView;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;

//用作和kotlin对照

import androidx.annotation.Nullable;

public class JavaDemo extends View {
    public JavaDemo(Context context) {
        this(context, null);
    }

    public JavaDemo(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public JavaDemo(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }


}
