package com.sunexample.demoforandroidxandkotlin.gestureDemo

import android.util.Log
import android.view.GestureDetector
import android.view.MotionEvent

class MyGestureListener : GestureDetector.SimpleOnGestureListener() {

    val TAG = "MyGestureListener"

    override fun onDoubleTap(e: MotionEvent): Boolean {
        return super.onDoubleTap(e)
    }

    override fun onLongPress(e: MotionEvent) {
        super.onLongPress(e)
    }

    override fun onDown(e: MotionEvent): Boolean {
        return super.onDown(e)
    }

    override fun onShowPress(e: MotionEvent) {
        super.onShowPress(e)
    }

    override fun onSingleTapUp(e: MotionEvent): Boolean {
        return super.onSingleTapUp(e)
    }

    override fun onScroll(
        e1: MotionEvent,
        e2: MotionEvent,
        distanceX: Float,
        distanceY: Float
    ): Boolean {
        return super.onScroll(e1, e2, distanceX, distanceY)
    }

    override fun onFling(
        e1: MotionEvent,
        e2: MotionEvent,
        velocityX: Float,
        velocityY: Float
    ): Boolean {
        return super.onFling(e1, e2, velocityX, velocityY)
    }

    override fun onSingleTapConfirmed(e: MotionEvent): Boolean {
        return super.onSingleTapConfirmed(e)
    }

    override fun onDoubleTapEvent(e: MotionEvent): Boolean {
        return super.onDoubleTapEvent(e)
    }

    override fun onContextClick(e: MotionEvent): Boolean {
        return super.onContextClick(e)
    }
}