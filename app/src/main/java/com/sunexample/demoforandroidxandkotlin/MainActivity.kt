package com.sunexample.demoforandroidxandkotlin

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.sunexample.demoforandroidxandkotlin.StyleAndTheme.StyleAndThemeActivity
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        styleAndTheme.setOnClickListener {
            startActivity(Intent(this, StyleAndThemeActivity::class.java))
        }

    }
}