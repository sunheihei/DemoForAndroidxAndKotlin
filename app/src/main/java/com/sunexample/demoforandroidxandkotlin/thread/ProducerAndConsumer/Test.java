package com.sunexample.demoforandroidxandkotlin.thread.ProducerAndConsumer;

public class Test {

    public static void main(String[] args) {
        Repository repository = new Repository(20);
        Thread producer = new Producer(repository);
        Thread consumer = new Consumer(repository);

        producer.start();
        consumer.start();

        System.out.println("main thread is out");
    }

}
