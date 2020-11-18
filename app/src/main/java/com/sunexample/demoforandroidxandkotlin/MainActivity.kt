package com.sunexample.demoforandroidxandkotlin

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.sunexample.demoforandroidxandkotlin.CustomView.CustomViewActivity
import com.sunexample.demoforandroidxandkotlin.Dialog.DialogDemoActivity
import com.sunexample.demoforandroidxandkotlin.Fragment.FragmentActivity
import com.sunexample.demoforandroidxandkotlin.ReadAndWrite.ReadAndWriteActivity
import com.sunexample.demoforandroidxandkotlin.StyleAndTheme.StyleAndThemeActivity
import com.sunexample.demoforandroidxandkotlin.FitnessTest.VideoTestActivity
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

//        var user = User("sun", 20)

        styleAndTheme.setOnClickListener {
            startActivity(Intent(this, StyleAndThemeActivity::class.java))
        }
        dialogdemo.setOnClickListener {
            startActivity(Intent(this, DialogDemoActivity::class.java))
        }
        customview.setOnClickListener {
            startActivity(Intent(this, CustomViewActivity::class.java))
        }
        writeandread.setOnClickListener {
            startActivity(Intent(this, ReadAndWriteActivity::class.java))
        }
        fragment.setOnClickListener {
            startActivity(Intent(this, FragmentActivity::class.java))
        }
        videotest.setOnClickListener {
            startActivity(Intent(this, VideoTestActivity::class.java))
        }
    }
}