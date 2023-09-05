package com.sunexample.demoforandroidxandkotlin.customView

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.sunexample.demoforandroidxandkotlin.customView.DemoView.CusDemoActivity
import com.sunexample.demoforandroidxandkotlin.customView.Scroll.ScrollDemoActivity
import com.sunexample.demoforandroidxandkotlin.databinding.ActivityCustomBinding

class CustomViewActivity : AppCompatActivity() {

    lateinit var binding: ActivityCustomBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCustomBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.cusdemo.setOnClickListener {
            startActivity(Intent(this, CusDemoActivity::class.java))
        }

        binding.srollview.setOnClickListener {
            startActivity(Intent(this, ScrollDemoActivity::class.java))
        }

    }
}