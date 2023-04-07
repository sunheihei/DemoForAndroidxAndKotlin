package com.sunexample.demoforandroidxandkotlin.recycleView

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.sunexample.demoforandroidxandkotlin.recycleView.bean.AdBean
import com.sunexample.demoforandroidxandkotlin.recycleView.bean.Content
import com.sunexample.demoforandroidxandkotlin.databinding.ActivityRecycleViewBinding

class ComplexRecycleViewActivity : AppCompatActivity() {

    //复杂RecycleView,从数据入手,在数据处理阶段,将不同的item类型做成完成的数据，继承自Any（Object）,在展示时再去进一步判断所需要的页面

    lateinit var binding: ActivityRecycleViewBinding

    val TAG = "RecycleViewActivity"

    var ListData = mutableListOf<Any>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRecycleViewBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initdata()

        initRecycleView()
    }

    private fun initdata() {
        for (index in 0 until 10) {
            if (index % 2 == 1) {
                ListData.add(AdBean("ad:$index"))
            } else {
                ListData.add(Content("content:$index"))
            }
        }
        Log.d(TAG, ListData.toString())
    }


    private fun initRecycleView() {
        val adapter = ContentAdapter()
        binding.complexRecycle.adapter = adapter
        adapter.setData(this, ListData)
    }

}