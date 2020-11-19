package com.sunexample.demoforandroidxandkotlin.Thread

import androidx.core.util.Pools
import io.reactivex.rxjava3.internal.schedulers.ExecutorScheduler
import java.util.concurrent.*
import java.util.concurrent.atomic.AtomicInteger

fun main() {
//    thread()
//    thradFactory()
    Callable()
}

fun thread() {
    val thread = Thread { run { println("Thread Start") } }.start()
}


fun Runnable() {
    val runnable = Runnable {
        run {
            println("Runnable Start")
        }
    }
    Thread(runnable).start()
}


fun thradFactory() {
    val threadFactory = ThreadFactory {
        val count = AtomicInteger(0)
        return@ThreadFactory Thread(it, "Thread - ${count.incrementAndGet()}")
    }

    val runnable = Runnable {
        println("${Thread.currentThread().name} started")
    }

    val thread = threadFactory.newThread(runnable).start()
    val thread2 = threadFactory.newThread(runnable).start()

}

fun executor() {
    val runnable = Runnable {
        run {
            println("Runnable Start")
        }
    }

    val executor = Executors.newCachedThreadPool()
    executor.execute(runnable)
    executor.execute(runnable)
    executor.execute(runnable)


    //创建1只含一个线程的线程池
    Executors.newSingleThreadExecutor()
    //创建固定数量线程的线程池，用于短时间内爆发的数据处理，然后马上执行回收
    Executors.newFixedThreadPool(10);

    val myExecutor = ThreadPoolExecutor(5, 20, 60L, TimeUnit.SECONDS, SynchronousQueue<Runnable>())
    myExecutor.execute(runnable)

}

fun Callable() {
    val callable = java.util.concurrent.Callable<String> {
        try {
            Thread.sleep(3000)
        } catch (e: Exception) {
        }
        return@Callable "Done"
    }
    val executor = Executors.newCachedThreadPool()
    val future  = executor.submit(callable)
    try {
        val result = future.get()
        println(result)
    } catch (e: Exception) {
    }

    println("AAA")
}


