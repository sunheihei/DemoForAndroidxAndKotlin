package com.sunexample.demoforandroidxandkotlin

import android.content.Context

object SuperSp {

    const val TYPE_LIST_ONE_ROW = 0
    const val TYPE_GRID_TWO_ROW = 1


    private const val TAG = "SuperAdSp"

    private const val BASE_KEY1 = "BASE_KEY1"
    private const val BASE_KEY2 = "BASE_KEY2"
    private const val BASE_KEY3 = "BASE_KEY3"
    private const val BASE_KEY4 = "BASE_KEY4"
    private const val BASE_KEY5 = "BASE_KEY5"
    private const val BASE_KEY6 = "BASE_KEY6"

    private const val BASE_KEY7 = "BASE_KEY7"
    private const val BASE_KEY8 = "BASE_KEY8"
    private const val BASE_KEY9 = "BASE_KEY9"
    private const val BASE_KEY11 = "BASE_KEY11"

    private val sharedPreferences = (AppContext.appContext)!!.getSharedPreferences(
        (AppContext.appContext)!!.packageName + "_prefs_ads_internal", Context.MODE_PRIVATE
    )

    /**
     * 0:grid 1
     * 1:grid 3
     */
    var spListType
        get() = sharedPreferences.getInt(BASE_KEY1, 0)
        set(value) = sharedPreferences.edit().putInt(BASE_KEY1, value).apply()

    var spFacebook
        get() = sharedPreferences.getBoolean(BASE_KEY2, false)
        set(value) = sharedPreferences.edit().putBoolean(BASE_KEY2, value).apply()

    fun getBool(KEY: String): Boolean {
        return sharedPreferences.getBoolean(KEY, false)
    }

    fun getBoolTrue(KEY: String): Boolean? {
        return sharedPreferences.getBoolean(KEY, true)
    }

    fun setBool(KEY: String, value: Boolean?) {
        val editor = sharedPreferences.edit()
        editor.putBoolean(KEY, value!!)
        editor.apply()
    }

    fun setBoolNot(KEY: String) {
        val editor = sharedPreferences.edit()
        editor.putBoolean(KEY, !sharedPreferences.getBoolean(KEY, false))
        editor.apply()
    }

    fun setLong(KEY: String, value: Long) {
        val editor = sharedPreferences.edit()
        editor.putLong(KEY, value)
        editor.apply()
    }


    fun getLong(KEY: String, defaultValue: Long): Long {
        return sharedPreferences.getLong(KEY, defaultValue)
    }


}