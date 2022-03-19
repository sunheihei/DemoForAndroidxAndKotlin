package com.sunexample.demoforandroidxandkotlin

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.sunexample.demoforandroidxandkotlin.CustomView.CustomViewActivity
import com.sunexample.demoforandroidxandkotlin.Dialog.DialogDemoActivity
import com.sunexample.demoforandroidxandkotlin.Fragment.FragmentActivity
import com.sunexample.demoforandroidxandkotlin.GestureDemo.SimpleGestureActivity
import com.sunexample.demoforandroidxandkotlin.Leonids.LeonidsActivity
import com.sunexample.demoforandroidxandkotlin.ReadAndWrite.ReadAndWriteActivity
import com.sunexample.demoforandroidxandkotlin.Rxjava.RxjavaActivity
import com.sunexample.demoforandroidxandkotlin.StyleAndTheme.StyleAndThemeActivity
import com.sunexample.demoforandroidxandkotlin.TypeEvaluatorDemo.TypeEvaluatorActivity
import com.sunexample.demoforandroidxandkotlin.databinding.ActivityMainBinding
import com.sunexample.demoforandroidxandkotlin.litepal.LitepalDemoActivity


class MainActivity : AppCompatActivity() {

    lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(getLayoutInflater())
        setContentView(binding.root)

//        var user = User("sun", 20)

        binding.styleAndTheme.setOnClickListener {
            startActivity(Intent(this, StyleAndThemeActivity::class.java))
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
        binding.litepal.setOnClickListener {
            startActivity(Intent(this, LitepalDemoActivity::class.java))
        }
        binding.gesture.setOnClickListener {
            startActivity(Intent(this, SimpleGestureActivity::class.java))
        }
        binding.typeevaluator.setOnClickListener {
            startActivity(Intent(this, TypeEvaluatorActivity::class.java))
        }
        binding.leonids.setOnClickListener {
            startActivity(Intent(this, LeonidsActivity::class.java))
        }

    }
}