package com.sunexample.demoforandroidxandkotlin.bitmap

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.sunexample.demoforandroidxandkotlin.databinding.ActivityBitmapDemoBinding


class BitmapDemoActivity : AppCompatActivity() {


    lateinit var binding: ActivityBitmapDemoBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityBitmapDemoBinding.inflate(layoutInflater)

        setContentView(binding.root)


    }

}