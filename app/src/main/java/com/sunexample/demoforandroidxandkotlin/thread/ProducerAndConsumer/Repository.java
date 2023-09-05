package com.sunexample.demoforandroidxandkotlin.thread.ProducerAndConsumer;

import java.util.ArrayDeque;

public class Repository {

    private ArrayDeque<String> list = null;
    private int limit;     //仓库容量

    public Repository(int limit){
        this.limit = limit;
        list = new ArrayDeque<String>(this.limit);
    }

    //仓库提供给生产者存入操作
    public synchronized void addGoods(String data) throws InterruptedException {
        while(list.size() == limit){
            //说明仓库已经满了
            wait();
        }
        list.add(data);
        System.out.println("i produce a product:"+data);
        notifyAll();
    }

    //仓库提供给消费者取出操作
    public synchronized String getGoods() throws InterruptedException {
        while(list.isEmpty()){
            //说明仓库已经空了
            wait();
        }
        String result = list.poll();
        System.out.println("i consume a product:"+ result);
        notifyAll();
        return result;
    }

}
