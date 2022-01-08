package com.sunexample.demoforandroidxandkotlin.LottieAnimDemo

import android.animation.ValueAnimator
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.bumptech.glide.Glide
import com.sunexample.demoforandroidxandkotlin.R
import com.sunexample.demoforandroidxandkotlin.databinding.ActivityDialogDemoBinding
import com.sunexample.demoforandroidxandkotlin.databinding.ActivityLitepalDemoBinding
import com.sunexample.demoforandroidxandkotlin.databinding.ActivityLottieDemoBinding

class LottieDemoActivity : AppCompatActivity() {

    val TAG = "LottieDemoActivity"

    lateinit var binding: ActivityLottieDemoBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLottieDemoBinding.inflate(layoutInflater)
        setContentView(binding.root)

//        Glide.with(this).load("file:///android_asset/cpu/images/img_0.png").into(binding.testImg)

        binding.viewCooler.setImageAssetsFolder("cpu/images/");
        binding.viewCooler.playAnimation()
        binding.viewCooler.addAnimatorUpdateListener {
            Log.d(TAG, "viewCooler PLAY")
        }

    }
}