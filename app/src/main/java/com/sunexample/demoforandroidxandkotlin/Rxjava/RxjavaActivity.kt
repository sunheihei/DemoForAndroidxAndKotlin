package com.sunexample.demoforandroidxandkotlin.Rxjava

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.sunexample.demoforandroidxandkotlin.R
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.core.Observer
import io.reactivex.rxjava3.disposables.Disposable
import io.reactivex.rxjava3.kotlin.subscribeBy
import io.reactivex.rxjava3.kotlin.toObservable
import io.reactivex.rxjava3.schedulers.Schedulers
import java.util.concurrent.TimeUnit

/**
 * Rxjava操作符
 * fromArray
 * just
 * Range
 * Timer
 * Interval
 * Repeat
 * Defer
 * map
 * FlatMap
 * ConcatMap
 * GroupBy
 * Buffer
 * Window
 * Filter
 * ofType
 * First
 * firstOrDefault
 * takeFirst
 * single
 * Last
 * Skip
 * SkipLast
 * Merge  MergeDelayError  Concat
 * StartWith
 * Delay   delaySubscription
 * Do系列
 * Timeout
 * To系列
 */

/**
 * Rxjava使用demo Kotlin版本
 */

val TAG = "RxjavaActivity";

class RxjavaActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_rxjava)
//        Rxjava()
        Rxjava05()
    }

    private fun Rxjava() {

        //最基础写法
//        Observable.create<String> {
//            it.onNext("Hello Rxjava")
//        }.subscribeOn(Schedulers.newThread())
//            .observeOn(AndroidSchedulers.mainThread())
//            .subscribe(object : Observer<String> {
//                override fun onSubscribe(d: Disposable?) {
//
//                }
//
//                override fun onNext(t: String?) {
//
//                }
//
//                override fun onError(e: Throwable?) {
//
//                }
//
//                override fun onComplete() {
//
//                }
//
//            })
        //最基础写法简略写法
        Observable.create<String> {
            it.onNext("Hello Rxjava")
        }.subscribeOn(Schedulers.newThread())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeBy(
                onNext = { println(it) },
                onError = { it.printStackTrace() },
                onComplete = { println("Done!") }
            )


    }

    /**
     * List
     */

    private fun RxjavaWithList() {
        val list = listOf("Alpha", "Beta", "Gamma", "Delta", "Epsilon")
        list.toObservable() // extension function for Iterables
            .filter { it.length >= 5 }
            .map { it + "233" }
            .subscribeBy(  // named arguments for lambda Subscribers
                onNext = { println(it) },
                onError = { it.printStackTrace() },
                onComplete = { println("Done!") }
            )
    }

    /**
     * Delay,Just
     */
    private fun Rxjava01() {
        val integers = arrayOf(1, 2, 3, 4)

        Observable.just(integers)
            .delay(2, TimeUnit.SECONDS)
            .subscribeBy(
                onNext = { println(it) },
                onError = { it.printStackTrace() },
                onComplete = { println("Done!") }
            )

    }

    /**
     * Range
     * 该操作符创建特定整数序列的Observable，它接受两个参数，一个是范围的起始值，一个是范围的数据的数目。如果你将第二个参数设为0，将导致Observable不发射任何数据（如果设置为负数，会抛异常）。
     */
    private fun Rxjava02() {
        Observable.range(1, 4)
            .subscribe(object : Observer<Int?> {
                override fun onSubscribe(d: Disposable?) {

                }

                override fun onNext(t: Int?) {
                    Log.d(TAG, t.toString())
                }

                override fun onError(e: Throwable?) {

                }

                override fun onComplete() {

                }
            })
    }


    /**
     * Timer
     * Timer操作符创建一个在给定的时间段之后返回一个特殊值的Observable。它在延迟一段给定的时间后发射一个简单的数字0 (只执行一次)
     */
    private fun Rxjava03() {
//      Observable.timer(3,TimeUnit.SECONDS,AndroidSchedulers.mainThread())   //如果要更新ui，就必须切换主线程
        Observable.timer(3, TimeUnit.SECONDS)
            .subscribeBy(
                onNext = { println(it) },
                onError = { it.printStackTrace() },
                onComplete = { println("Done!") }
            )
    }

    /**
     * Interval
     * 该操作符按固定的时间间隔发射一个无限递增的整数序列，它接受一个表示时间间隔的参数和一个表示时间单位的参数，当然该操作符合Timer一样，是在computation调度器上执行的，若想更新UI需要指定Scheduler 为AndroidSchedulers.mainThread()。
     */
    private fun Rxjava04() {
//        Observable.interval(3,TimeUnit.SECONDS,AndroidSchedulers.mainThread()) 更新ui需要切换至主线程
        Observable.interval(1, TimeUnit.SECONDS)
            .subscribeBy(
                onNext = { println(it) },
                onError = { it.printStackTrace() },
                onComplete = { println("Done!") }
            )
    }

    /**
     * Repeat
     * 该操作符是重复的发射某个数据序列，并且可以自己设置重复的次数。当接收到onComplete()会触发重订阅再次重复发射数据,当重复发射数据次数到达后执行onCompleted()。
     */
    private fun Rxjava05() {
        val strs = arrayOf("也许当初忙着微笑和哭泣", "忙着追逐天空中的流星")
        Observable.fromArray(strs).repeat(2)
            .subscribeBy(
                onNext = { println(it) },
                onError = { it.printStackTrace() },
                onComplete = { println("Done!") }
            )

    }
    //结果：
//    onNext: 也许当初忙着微笑和哭泣
//    onNext: 忙着追逐天空中的流星
//    onNext: 也许当初忙着微笑和哭泣
//    onNext: 忙着追逐天空中的流星
//    onCompleted:







}