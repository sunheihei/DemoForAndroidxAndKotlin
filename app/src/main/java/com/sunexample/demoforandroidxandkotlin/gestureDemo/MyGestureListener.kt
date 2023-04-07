package com.sunexample.demoforandroidxandkotlin.gestureDemo

import android.util.Log
import android.view.GestureDetector
import android.view.MotionEvent

class MyGestureListener : GestureDetector.SimpleOnGestureListener() {

    val TAG = "MyGestureListener"

    override fun onDown(e: MotionEvent?): Boolean {
        Log.d(TAG,"onDown")
        return super.onDown(e)
    }

    override fun onShowPress(e: MotionEvent?) {
        Log.d(TAG,"onShowPress")
        super.onShowPress(e)
    }

    override fun onSingleTapUp(e: MotionEvent?): Boolean {
        Log.d(TAG,"onSingleTapUp")
        return super.onSingleTapUp(e)
    }


    override fun onLongPress(e: MotionEvent?) {
        Log.d(TAG,"onLongPress")
        super.onLongPress(e)
    }


    override fun onDoubleTap(e: MotionEvent?): Boolean {
        Log.d(TAG,"onDoubleTap")
        return super.onDoubleTap(e)
    }

    override fun onDoubleTapEvent(e: MotionEvent?): Boolean {
        Log.d(TAG,"onDoubleTapEvent")
        return super.onDoubleTapEvent(e)
    }

    override fun onContextClick(e: MotionEvent?): Boolean {
        Log.d(TAG,"onContextClick")
        return super.onContextClick(e)
    }
}