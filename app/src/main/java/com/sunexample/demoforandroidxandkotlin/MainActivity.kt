package com.sunexample.demoforandroidxandkotlin

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.sunexample.demoforandroidxandkotlin.bitmap.BitmapDemoActivity
import com.sunexample.demoforandroidxandkotlin.customView.CustomViewActivity

import com.sunexample.demoforandroidxandkotlin.databinding.ActivityMainBinding
import com.sunexample.demoforandroidxandkotlin.dialog.DialogDemoActivity
import com.sunexample.demoforandroidxandkotlin.flow.FlowActivity
import com.sunexample.demoforandroidxandkotlin.fragment.FragmentActivity
import com.sunexample.demoforandroidxandkotlin.readAndWrite.ReadAndWriteActivity
import com.sunexample.demoforandroidxandkotlin.recycleView.ComplexRecycleViewActivity
import com.sunexample.demoforandroidxandkotlin.rxjava.RxjavaActivity
import com.sunexample.demoforandroidxandkotlin.styleAndTheme.StyleAndThemeActivity
import com.sunexample.demoforandroidxandkotlin.typeEvaluatorDemo.TypeEvaluatorActivity


class MainActivity : AppCompatActivity() {

    lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.styleAndTheme.setOnClickListener {
            startActivity(Intent(this, StyleAndThemeActivity::class.java))
        }
        binding.recycleview.setOnClickListener {
            startActivity(Intent(this, ComplexRecycleViewActivity::class.java))
        }
        binding.dialogdemo.setOnClickListener {
            startActivity(Intent(this, DialogDemoActivity::class.java))
        }
        binding.customview.setOnClickListener {
            startActivity(Intent(this, CustomViewActivity::class.java))
        }
        binding.writeandread.setOnClickListener {
            startActivity(Intent(this, ReadAndWriteActivity::class.java))
        }
        binding.fragment.setOnClickListener {
            startActivity(Intent(this, FragmentActivity::class.java))
        }
        binding.rxjava.setOnClickListener {
            startActivity(Intent(this, RxjavaActivity::class.java))
        }
        binding.typeevaluator.setOnClickListener {
            startActivity(Intent(this, TypeEvaluatorActivity::class.java))
        }
        binding.flow.setOnClickListener {
            startActivity(Intent(this, FlowActivity::class.java))
        }
        binding.bitmap.setOnClickListener {
            startActivity(Intent(this, BitmapDemoActivity::class.java))
        }
        binding.activityAnim.setOnClickListener {
//            startActivity(Intent(this, A::class.java))
        }
    }
}