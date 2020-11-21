package com.sunexample.demoforandroidxandkotlin.jetapck.common

import com.sunexample.demoforandroidxandkotlin.jetapck.common.Status.SUCCESS
import com.sunexample.demoforandroidxandkotlin.jetapck.common.Status.ERROR
import com.sunexample.demoforandroidxandkotlin.jetapck.common.Status.LOADING

/**
 * A generic class that holds a value with its loading status.
 * @param <T>
</T> */
data class Resource<out T>(val status: Status, val data: T?, val message: String?) {
    companion object {
        fun <T> success(data: T?): Resource<T> {
            return Resource(SUCCESS, data, null)
        }

        fun <T> error(msg: String, data: T?): Resource<T> {
            return Resource(ERROR, data, msg)
        }

        fun <T> loading(data: T?): Resource<T> {
            return Resource(LOADING, data, null)
        }
    }
}
