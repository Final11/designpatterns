package com.wonders.ghy.juc;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class MyThreadPoolDemo {
    public static void main(String[] args) {
        // 固定数线程池
        //ExecutorService threadPool = Executors.newFixedThreadPool(5);
        //一池一个工作线程
        //ExecutorService threadPool = Executors.newSingleThreadExecutor();
        //可扩容线程池
        ExecutorService threadPool = Executors.newCachedThreadPool();
        try {
            // 模拟有10个顾客 过来银行办理也无语，目前池子里面有5个工作人员提供服务
            for (int i = 0; i < 10; i++) {
                /*try {
                    TimeUnit.MILLISECONDS.sleep(10000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }*/
                threadPool.execute(()->{
                    try {
                        TimeUnit.MILLISECONDS.sleep(10000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    System.out.println(Thread.currentThread().getName()+"\t办理业务");
                });
            }
        }finally {
            threadPool.shutdown();
        }
    }
}
