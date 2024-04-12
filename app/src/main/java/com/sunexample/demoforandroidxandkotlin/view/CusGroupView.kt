package com.sunexample.demoforandroidxandkotlin.view

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.FrameLayout
import com.sunexample.demoforandroidxandkotlin.databinding.TestViewBinding

class CusGroupView @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : FrameLayout(context, attrs, defStyleAttr) {


    private var binding: TestViewBinding = TestViewBinding.inflate(
        LayoutInflater.from(context), this, true
    )


}