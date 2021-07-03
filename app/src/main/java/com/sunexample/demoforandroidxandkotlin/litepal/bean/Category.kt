package com.sunexample.demoforandroidxandkotlin.litepal.bean

import org.litepal.crud.LitePalSupport

class Category : LitePalSupport() {
    // 自动生成get、set方法
    var id = 0
    var name: String? = null
}