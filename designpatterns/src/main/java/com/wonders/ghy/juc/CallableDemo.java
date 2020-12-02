package com.wonders.ghy.juc;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.FutureTask;
import java.util.concurrent.TimeUnit;

class MyThread implements Runnable{

    @Override
    public void run() {

    }
}

/**
 * 两者的区别：1是否有返回值 2方法名不同 3是否抛异常
 */
class MyThread2 implements Callable<Integer>{

    @Override
    public Integer call() throws Exception {
        System.out.println("come in here");
        TimeUnit.SECONDS.sleep(4);
        return 1024;
    }
}
/**
 * 多线程中，第三种获取多线程的方式
 * get()方法一般放在最后一行
 */
public class CallableDemo {
    public static void main(String[] args) throws ExecutionException, InterruptedException {
        FutureTask futureTask=new FutureTask(new MyThread2());
        new Thread(futureTask,"A").start();
        // 同一个futureTask对象只会跑一次
        new Thread(futureTask,"B").start();
        System.out.println(futureTask.get());

        System.out.println(Thread.currentThread().getName()+"计算完成");
    }
}
