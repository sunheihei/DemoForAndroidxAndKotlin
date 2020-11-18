package com.sunexample.demoforandroidxandkotlin.CustomView.DemoView

import android.content.Context
import android.graphics.*
import android.util.AttributeSet
import android.view.View

import com.sunexample.demoforandroidxandkotlin.CustomView.px
import java.time.chrono.HijrahEra
import kotlin.math.cos
import kotlin.math.sin

const val OPEN_ANGEL = 120f
val RADIUS = 150f.px
val LENGTH_DASH = 120f.px
val DASH_WIDTH = 2f.px
val DASH_LENGTH = 10f.px


class DashBoardView(context: Context, attributes: AttributeSet) : View(context, attributes) {


    private val mPaint = Paint(Paint.ANTI_ALIAS_FLAG)
    private var mRect: RectF? = null
    private val Dash = Path()
    private val ArcPath = Path()
    private lateinit var pathEffect: PathDashPathEffect


    init {
        mPaint.strokeWidth = 3f.px
        mPaint.style = Paint.Style.STROKE
        Dash.addRect(0f, 0f, DASH_WIDTH, DASH_LENGTH, Path.Direction.CW)
    }


    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        super.onSizeChanged(w, h, oldw, oldh)
        mRect = RectF(
            (width / 2) - RADIUS,
            (height / 2) - RADIUS,
            (width / 2) + RADIUS,
            (height / 2) + RADIUS
        )
        ArcPath.addArc(
            mRect!!,
            (90 + OPEN_ANGEL / 2f),
            (360 - OPEN_ANGEL)
        )
        val pathMeasure = PathMeasure(ArcPath, false)
        pathEffect = PathDashPathEffect(
            Dash,
            (pathMeasure.length - DASH_WIDTH) / 20,
            0f,
            PathDashPathEffect.Style.ROTATE
        )
    }


    override fun onDraw(canvas: Canvas) {
        //画弧线
        canvas.drawPath(ArcPath, mPaint)

        //画刻度
        mPaint.pathEffect = pathEffect
        canvas.drawPath(ArcPath, mPaint)
        mPaint.pathEffect = null

        canvas.drawLine(
            width / 2f,
            height / 2f,
            width / 2f + LENGTH_DASH * cos(MarktoRadius(10)).toFloat(),
            height / 2f + LENGTH_DASH * sin(MarktoRadius(10)).toFloat(),
            mPaint
        )

    }

    private fun MarktoRadius(mark:Int) =
        Math.toRadians((90 + OPEN_ANGEL / 2f + (360 - OPEN_ANGEL) / 20f * mark).toDouble())


}