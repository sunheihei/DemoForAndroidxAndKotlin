package com.sunexample.demoforandroidxandkotlin.Thread.ProducerAndConsumer;

public class Producer extends Thread {

    //生产者线程不停的生产产品直到仓库满

    private Repository repository = null;

    public Producer(Repository r){
        this.repository = r;
    }

    int count = 0;
    @Override
    public void run(){
        while(true){
            try {
                repository.addGoods(String.valueOf(count));
                count++;
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}