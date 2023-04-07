package com.sunexample.demoforandroidxandkotlin.fragment

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.Fragment
import com.sunexample.demoforandroidxandkotlin.R

class FragmentActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_fragment)
        addFragment()
    }

    private fun addFragment() {
        val firstFragment = RightFragment.instance
        supportFragmentManager.beginTransaction()
            .add(R.id.right_layout, firstFragment).commit()
    }

    public fun replaceFragment(replace: Fragment) {
        supportFragmentManager.beginTransaction()
            .replace(R.id.right_layout, replace).commit()
    }

}