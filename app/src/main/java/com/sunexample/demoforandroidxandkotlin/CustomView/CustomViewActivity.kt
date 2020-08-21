package com.sunexample.demoforandroidxandkotlin.CustomView

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.sunexample.demoforandroidxandkotlin.CustomView.DemoView.CusDemoActivity
import com.sunexample.demoforandroidxandkotlin.CustomView.Scroll.ScrollDemoActivity
import com.sunexample.demoforandroidxandkotlin.R
import kotlinx.android.synthetic.main.activity_custom.*

class CustomViewActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_custom)

        cusdemo.setOnClickListener {
            startActivity(Intent(this, CusDemoActivity::class.java))
        }

        srollview.setOnClickListener {
            startActivity(Intent(this, ScrollDemoActivity::class.java))
        }

    }
}