package com.wonders.ghy.juc.读写锁;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

class MyCache{
    private volatile Map<String,Object> map=new HashMap<>();
    /**
     * 在没有加读写锁之前，我们执行会发现某一个线程正在写的时候就有其他线程
     * 来读这就破坏了原子性，必须要开始写并完成，其他线程才可以读或写
     */
    private ReadWriteLock readWriteLock = new ReentrantReadWriteLock();

    public void put(String key,Object value){
        readWriteLock.writeLock().lock();
        try {
            System.out.println(Thread.currentThread().getName()+"\t 写入数据"+key);
            // 暂停一会线程
            try {
                TimeUnit.MICROSECONDS.sleep(300);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            map.put(key,value);
            System.out.println(Thread.currentThread().getName()+"\t 写入完成");
        }finally {
            readWriteLock.writeLock().unlock();
        }
    }

    public void get(String key){
        readWriteLock.readLock().lock();
        try {
            System.out.println(Thread.currentThread().getName()+"\t 读取数据");
            // 暂停一会线程
            try {
                TimeUnit.MICROSECONDS.sleep(300);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            map.get(key);
            System.out.println(Thread.currentThread().getName()+"\t 读取完成");
        }finally {
            readWriteLock.readLock().unlock();
        }

    }
}
/**
 * 多个线程同时读一个资源类 没有任何问题，所以为了满足并发量，读取共享资源
 * 应该可以同时进行。
 * 但是如果有一个线程想去写共享资源，就不应该再有其他线程可以对资源进行读或写
 * 总结：  读-读能共存
 *         读-写不能共存
 *         写-写不能共存
 */
public class ReadWriteLockDemo {
    public static void main(String[] args) {
        MyCache myCache = new MyCache();
        for (int i = 0; i < 5; i++) {
            final int tempInt =i;
            new Thread(()->{myCache.put(tempInt+"",tempInt+"");},String.valueOf(i)).start();
        }
        for (int i = 0; i < 5; i++) {
            final int tempInt =i;
            new Thread(()->{myCache.get(tempInt+"");},String.valueOf(i)).start();
        }
    }
}
