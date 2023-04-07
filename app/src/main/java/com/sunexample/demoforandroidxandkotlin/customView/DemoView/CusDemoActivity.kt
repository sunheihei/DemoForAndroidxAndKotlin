package com.sunexample.demoforandroidxandkotlin.customView.DemoView

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.sunexample.demoforandroidxandkotlin.databinding.ActivityCusDemoBinding

class CusDemoActivity : AppCompatActivity() {

    lateinit var binding:ActivityCusDemoBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCusDemoBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }
}