package com.wonders.ghy.juc.线程计数器加减红绿灯;

import java.util.concurrent.CountDownLatch;

/**
 * 线程计数器
 */
public class CountDownLatchDemo {
    public static void main(String[] args) throws InterruptedException {
        // 定义一个计数器 数字为6
        CountDownLatch countDownLatch = new CountDownLatch(6);

        closeDoor(countDownLatch);
    }

    private static void closeDoor(CountDownLatch countDownLatch) throws InterruptedException {
        for (int i = 0; i < 6; i++) {
            new Thread(()->{
                System.out.println(Thread.currentThread().getName()+"离开教室");
                //走一个线程 计数器数量-1
                countDownLatch.countDown();
            },String.valueOf(i)).start();
        }
        // 一直等待线程计数器的计数为0
        countDownLatch.await();
        System.out.println(Thread.currentThread().getName()+" 班长关门走人");
    }
}
