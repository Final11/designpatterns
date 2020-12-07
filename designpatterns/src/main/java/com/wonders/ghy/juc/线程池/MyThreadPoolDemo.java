package com.wonders.ghy.juc.线程池;

import java.util.concurrent.*;

public class MyThreadPoolDemo {
    public static void main(String[] args) {
        // 线程池最大的容纳线程数 = 最大线程数maximumPoolSize+阻塞队列capacity 这里为5+3=8

        /**
         * 线程池最大线程数（maximumPoolSize）应该怎么去配置呢？ 看服务器是属于cpu密集型还是io密集型
         * 如果是cpu密集型：电脑的CPU相比内存和硬盘差 一般最大线程数为CPU核数+1或+2
         * 如果是io密集型：电脑的CPU相比内存和硬盘强  一般最大线程数为CPU核数的两倍
         */
        System.out.println("CPU的核数为:"+Runtime.getRuntime().availableProcessors());
        ThreadPoolExecutor tpe = new ThreadPoolExecutor(
                2,//核心线程数
                5,//最大线程数
                2L,//保持存活时间
                TimeUnit.SECONDS,//保持存活时间单位
                new LinkedBlockingQueue<>(3),//阻塞队列大小
                Executors.defaultThreadFactory(),//默认线程工厂
                // 中止策略：默认的，直接抛出RejectedExecutionException异常阻止系统正常运行
                //new ThreadPoolExecutor.AbortPolicy());
                // 呼叫者策略：该策略既不会抛弃任务，也不会抛出异常,而是将某些任务回退到调用者,从而降低新任务的流量。当前线程池无法执行时回退给调用这个线程的线程去执行
                //new ThreadPoolExecutor.CallerRunsPolicy());
                // 丢弃策略：DiscardPolicy:该策略默默地丢弃无法处理的任务，不予任何处理也不抛出异常。如果允许任务丢失，这是最好的一种策略。
                //new ThreadPoolExecutor.DiscardPolicy());
                // 丢弃最老策略：丢弃队列中等待最久的任务，然后把当前任务加入队列中尝试再次提交当前任务
                new ThreadPoolExecutor.DiscardOldestPolicy());
        try {
            // 模拟有10个顾客 过来银行办理业务，目前池子里面有5个工作人员提供服务
            for (int i = 0; i < 10; i++) {
                tpe.execute(()->{
                    System.out.println(Thread.currentThread().getName()+"\t办理业务");
                });
            }
        }finally {
            tpe.shutdown();
        }
    }
    public static void initPool(){
        /**
         * 阿里巴巴手册规定 线程池不能使用以下的方式创建
         * 因为这几种方式的线程池 要么阻塞队列的最大值默认为int的最大值
         * 要么默认最大线程数位int的最大值。会导致OOM内存溢出
         * 所以必须手写线程池
         */
        // 固定数线程池
        //ExecutorService threadPool = Executors.newFixedThreadPool(5);
        //一池一个工作线程
        //ExecutorService threadPool = Executors.newSingleThreadExecutor();
        //可扩容线程池
        ExecutorService threadPool = Executors.newCachedThreadPool();
        try {
            // 模拟有10个顾客 过来银行办理业务，目前池子里面有5个工作人员提供服务
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
