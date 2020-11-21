package com.sunexample.demoforandroidxandkotlin.Thread.ProducerAndConsumer;

public class Consumer extends Thread {

    //消费者线程不停的从仓库中取出产品直到仓库空

    private Repository repository = null;

    public Consumer(Repository r) {
        this.repository = r;
    }

    @Override
    public void run() {
        while (true) {
            try {
                String result = repository.getGoods();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}