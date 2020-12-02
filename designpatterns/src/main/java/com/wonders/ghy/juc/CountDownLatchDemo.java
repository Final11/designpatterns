package com.wonders.ghy.juc;

import java.util.concurrent.CountDownLatch;

/**
 * 线程计数器
 */
public class CountDownLatchDemo {
    public static void main(String[] args) throws InterruptedException {
        CountDownLatch countDownLatch = new CountDownLatch(6);

        closeDoor(countDownLatch);
    }

    private static void closeDoor(CountDownLatch countDownLatch) throws InterruptedException {
        for (int i = 0; i < 6; i++) {
            new Thread(()->{
                System.out.println(Thread.currentThread().getName()+"离开教室");
                countDownLatch.countDown();
            },String.valueOf(i)).start();
        }
        countDownLatch.await();
        System.out.println(Thread.currentThread().getName()+" 班长关门走人");
    }
}
