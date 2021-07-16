package com.sunexample.demoforandroidxandkotlin.CustomView.Scroll

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.sunexample.demoforandroidxandkotlin.databinding.ActivityScrollDemoBinding

class ScrollDemoActivity : AppCompatActivity() {
    lateinit var binding: ActivityScrollDemoBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityScrollDemoBinding.inflate(layoutInflater)
        setContentView(binding.root)


//        srcollview.smoothScrollTo(-400, -300)

    }
}