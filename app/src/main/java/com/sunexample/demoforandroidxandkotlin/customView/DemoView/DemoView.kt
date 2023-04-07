package com.sunexample.demoforandroidxandkotlin.customView.DemoView

import android.content.Context
import android.graphics.*
import android.util.AttributeSet
import android.util.Log
import android.view.View
import com.sunexample.demoforandroidxandkotlin.customView.px


class DemoView : View {

    val TAG = "DemoView"

    var mHeight: Float = 0f
    var mWidth: Float = 0f
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
        mHeight = h.toFloat()
        mWidth = w.toFloat()
        Log.d(TAG, "mHeight:$mHeight")
        Log.d(TAG, "mWidth:$mWidth")
    }


    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)
    }

    override fun onDraw(canvas: Canvas) {
        canvas.drawCircle(mWidth / 2, mHeight / 2, 100f.px, mPaint)

        super.onDraw(canvas)
    }


}

