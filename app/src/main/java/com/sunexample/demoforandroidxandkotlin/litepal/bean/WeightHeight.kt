package com.sunexample.demoforandroidxandkotlin.litepal.bean

import org.litepal.crud.LitePalSupport
import java.util.*

/**
 * @author chnehao
 * @datetime: 2019/1/15 10:22
 * @description:
 */
class WeightHeight : LitePalSupport() {
    var weight = 0f
    var isUnitKg = false
    var height = 0f
    var isUnitCm = false
    var bmi = 0f
    var date = ""
}