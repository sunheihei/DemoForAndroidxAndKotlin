package com.sunexample.demoforandroidxandkotlin.CustomView.Scroll

import android.content.Context
import android.util.AttributeSet
import android.util.Log
import android.view.MotionEvent
import android.view.View
import android.widget.Scroller

class ScrollViewDemo : View {

    var TAG = "ScrollViewDemo"

    var lastx: Int = 0
    var lasty: Int = 0

    lateinit var mScroll: Scroller

    constructor(context: Context) : this(context, null)

    constructor(context: Context, attributeSet: AttributeSet?) : this(context, attributeSet, 0)

    constructor(context: Context, attributeSet: AttributeSet?, defStyleAttr: Int) : super(
        context,
        attributeSet,
        defStyleAttr
    ) {
        mScroll = Scroller(context)
        initView()
    }

    private fun initView() {

    }

    override fun computeScroll() {
        super.computeScroll()
        Log.d(TAG, "computeScroll")
        if (mScroll.computeScrollOffset()) {
            Log.d(TAG, "invalidate")
            (parent as View).scrollTo(mScroll.currX, mScroll.currY)
            invalidate()
        }
    }

    fun smoothScrollTo(destx: Int, desty: Int) {
        var scrollX = scrollX
        var deltax = destx - scrollX
        var scrollY = scrollY
        var deltay = desty - scrollX
        mScroll.startScroll(scrollX, scrollY, deltax, deltay, 5000)
        invalidate()
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)
    }


//    override fun onTouchEvent(event: MotionEvent?): Boolean {
//        event?.let {
//            var x = event.x.toInt()
//            var y = event.y.toInt()
//
//            when (event.action) {
//                MotionEvent.ACTION_DOWN -> {
//                    lastx = x
//                    lasty = y
//                }
//                MotionEvent.ACTION_MOVE -> {
//                    var offsetx = x - lastx
//                    var offsety = y - lasty
////                    layout(left + offsetx, top + offsety, right + offsetx, bottom + offsety)
//
//                    offsetLeftAndRight(offsetx)
//                    offsetTopAndBottom(offsety)
//
//                }
//            }
//        }
//        return true
//    }

}