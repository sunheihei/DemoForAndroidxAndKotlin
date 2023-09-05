package com.sunexample.demoforandroidxandkotlin.typeEvaluatorDemo

import android.animation.ValueAnimator
import android.graphics.Point
import android.os.Bundle
import android.util.Log
import android.view.animation.LinearInterpolator
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.ConstraintLayout
import com.sunexample.demoforandroidxandkotlin.databinding.ActivityTypeEvaluatorBinding

class TypeEvaluatorActivity : AppCompatActivity() {

    lateinit var binding: ActivityTypeEvaluatorBinding

    val TAG = "TypeEvaluatorActivity"


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityTypeEvaluatorBinding.inflate(layoutInflater)
        setContentView(binding.root)
        
    }


    override fun onWindowFocusChanged(hasFocus: Boolean) {
        super.onWindowFocusChanged(hasFocus)
        val startposition = IntArray(2)
        binding.view.getLocationInWindow(startposition)
        var point2 = Point(startposition[0], startposition[1])

        Log.d(TAG, "x:${point2.x} y:${point2.y}")

        val circleEvaluator = CircleEvaluator().apply {
            setCenPoint(point2)
        }

        val animator = ValueAnimator.ofObject(circleEvaluator, Point(0, 0), Point(0, 0))
        val params = binding.view.layoutParams as ConstraintLayout.LayoutParams

        animator.addUpdateListener {
            val point = animator.animatedValue as Point
            params.leftMargin = point.x
            params.topMargin = point.y
            binding.view.layoutParams = params
        }

        animator.duration = 2000
        animator.interpolator = LinearInterpolator()
        animator.start()
    }

}