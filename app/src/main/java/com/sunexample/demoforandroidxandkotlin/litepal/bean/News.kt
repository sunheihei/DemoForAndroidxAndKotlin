package com.sunexample.demoforandroidxandkotlin.litepal.bean

import org.litepal.crud.LitePalSupport
import java.util.*


class News : LitePalSupport() {
    var id = 0
    var title: String? = null
    var content: String? = null
    var commentCount = 0
}