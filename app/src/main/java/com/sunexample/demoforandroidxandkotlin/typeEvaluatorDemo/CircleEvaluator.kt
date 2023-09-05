package com.sunexample.demoforandroidxandkotlin.typeEvaluatorDemo

import android.animation.TypeEvaluator
import android.graphics.Point
import android.util.Log
import kotlin.math.cos
import kotlin.math.sin

/**
 * Created by Sun on 2017/8/31.
 */
class CircleEvaluator : TypeEvaluator<Point> {

    lateinit var mCenterPoint: Point

    fun setCenPoint(mCenterPoint: Point) {
        this.mCenterPoint = mCenterPoint
    }

    override fun evaluate(t: Float, startValue: Point, endValue: Point): Point {
        var x = 0 + cos(Math.toRadians((360f * t).toDouble())) * 50f
        var y = 0 - sin(Math.toRadians((360f * t).toDouble())) * 50f

        Log.d("TypeEvaluatorActivity", "arc : ${360f * t}")
        Log.d("TypeEvaluatorActivity", "cos : ${cos(Math.toRadians((360f * t).toDouble()))}")

        return Point(x.toInt(), y.toInt())
    }


}