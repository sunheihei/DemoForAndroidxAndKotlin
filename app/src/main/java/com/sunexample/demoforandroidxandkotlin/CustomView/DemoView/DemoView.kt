package com.sunexample.demoforandroidxandkotlin.CustomView.DemoView

import android.content.Context
import android.graphics.*
import android.util.AttributeSet
import android.util.Log
import android.view.View


class DemoView : View {

    val TAG = "DemoView"

    var mHeight: Int = 0
    var mWidth: Int = 0
    lateinit var mPaint: Paint
    lateinit var mTextPaint: Paint
    lateinit var mPath: Path
    var text = "Hello HenCoder"

    constructor(context: Context) : this(context, null)

    constructor(context: Context, attributeSet: AttributeSet?) : this(context, attributeSet, 0)

    constructor(context: Context, attributeSet: AttributeSet?, defStyleAttr: Int) : super(
        context,
        attributeSet,
        defStyleAttr
    ) {
        initView()
    }

    private fun initView() {
        mPaint = Paint().apply {
            color = Color.BLACK
            style = Paint.Style.STROKE
            isAntiAlias = true
            strokeWidth = 2f
//            pathEffect = CornerPathEffect(30f)f
//            pathEffect = DashPathEffect(floatArrayOf(20f, 10f, 5f, 10f), 0f)
        }

        mTextPaint = Paint().apply {
            color = Color.BLACK
            style = Paint.Style.STROKE
            isAntiAlias = true
            strokeWidth = 2f
            textSize = 50f
        }

        mPath = Path()
    }


    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        super.onSizeChanged(w, h, oldw, oldh)
        mHeight = h
        mWidth = w
        Log.d(TAG, "mHeight:$mHeight")
        Log.d(TAG, "mWidth:$mWidth")
    }


    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)
    }

    override fun onDraw(canvas: Canvas?) {
        mPath.moveTo(150f, 1100f)
        mPath.lineTo(300f, 900f)
        mPath.lineTo(450f, 1100f)
        mPath.lineTo(600f, 900f)
        mPath.lineTo(750f, 1100f)
        mPath.lineTo(900f, 900f)
        canvas?.let {
            it.drawPath(mPath, mPaint)
            canvas.drawTextOnPath("Hello HeCoder", mPath, 0f, 0f, mTextPaint);
            it.drawText(text, 200f, 100f, mTextPaint)
        }

        super.onDraw(canvas)
    }


}

