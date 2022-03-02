package com.sunexample.demoforandroidxandkotlin.LottieAnimDemo

import android.animation.Animator
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


//        binding.viewCooler.setImageAssetsFolder("cpu/images/");
//        binding.viewCooler.setAnimation("cpu/data.json");

//        binding.viewCooler.setImageAssetsFolder("battery/images/");
//        binding.viewCooler.setAnimation("battery/data.json");
//
//        binding.viewCooler.setImageAssetsFolder("boost/images/");
//        binding.viewCooler.setAnimation("boost/data.json");

//        binding.viewCooler.setImageAssetsFolder("clean/images/");
//        binding.viewCooler.setAnimation("clean/data.json");


//        binding.viewCooler.setImageAssetsFolder("completed/images/");
//        binding.viewCooler.setAnimation("completed/data.json");


//        binding.viewCooler.setImageAssetsFolder("scan/images/");
//        binding.viewCooler.setAnimation("scan/data.json");

//        binding.viewCooler.setImageAssetsFolder("security/images/");
//        binding.viewCooler.setAnimation("security/data.json");

//        binding.viewCooler.setImageAssetsFolder("security_init/images/");
//        binding.viewCooler.setAnimation("security_init/data.json");


        binding.viewCooler.repeatCount = 2
        binding.viewCooler.playAnimation()

//        binding.viewCooler.addAnimatorUpdateListener {
//            Log.d(TAG, "viewCooler PLAY")
//        }
        binding.viewCooler.addAnimatorListener(object : Animator.AnimatorListener {
            override fun onAnimationStart(animation: Animator?) {
                Log.d(TAG, "onAnimationStart")
            }

            override fun onAnimationEnd(animation: Animator?) {
                Log.d(TAG, "onAnimationEnd")
            }

            override fun onAnimationCancel(animation: Animator?) {
                Log.d(TAG, "onAnimationCancel")
            }

            override fun onAnimationRepeat(animation: Animator?) {
                Log.d(TAG, "onAnimationRepeat")
            }

        })

    }
}