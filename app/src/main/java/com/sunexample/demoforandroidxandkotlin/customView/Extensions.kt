package com.sunexample.demoforandroidxandkotlin.customView

import android.content.res.Resources
import android.util.TypedValue

//Resources.getSystem() 可以拿到系统的一些配置，无法拿到string等项目专属资源
val Float.px
    get() = TypedValue.applyDimension(
        TypedValue.COMPLEX_UNIT_DIP,
        this,
        Resources.getSystem().displayMetrics
    )