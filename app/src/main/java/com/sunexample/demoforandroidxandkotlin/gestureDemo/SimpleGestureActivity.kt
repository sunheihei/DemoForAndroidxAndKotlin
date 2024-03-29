package com.sunexample.demoforandroidxandkotlin.gestureDemo

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.GestureDetector
import android.view.MotionEvent
import android.view.View
import com.sunexample.demoforandroidxandkotlin.databinding.ActivitySimpleGestureBinding

class SimpleGestureActivity : AppCompatActivity() {

    private lateinit var mGestureDetector: GestureDetector

    lateinit var binding: ActivitySimpleGestureBinding

    @SuppressLint("ClickableViewAccessibility")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySimpleGestureBinding.inflate(getLayoutInflater())
        setContentView(binding.root)


        mGestureDetector = GestureDetector(this, MyGestureListener())

        binding.btnGesture.setOnTouchListener { p0, p1 -> mGestureDetector.onTouchEvent(p1); }

    }
}