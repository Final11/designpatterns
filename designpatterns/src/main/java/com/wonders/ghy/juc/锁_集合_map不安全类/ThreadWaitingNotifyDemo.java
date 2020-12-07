package com.wonders.ghy.juc.锁_集合_map不安全类;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

class AirConditioner{
    private int number=0;

    private Lock lock = new ReentrantLock();
    private Condition condition=lock.newCondition();

    public void increment(){
        lock.lock();
        try {
            while (number!=0) {
                condition.await();
            }
            // 干活
            number++;
            System.out.println(Thread.currentThread().getName()+" "+number);
            condition.signalAll();
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            lock.unlock();
        }
    }

    public void decrement(){
        lock.lock();
        try {
            while (number==0) {
                condition.await();
            }
            // 干活
            number--;
            System.out.println(Thread.currentThread().getName()+" "+number);
            condition.signalAll();
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            lock.unlock();
        }
    }
//    public synchronized void increment() throws InterruptedException {
////        if (number!=0){
//        while (number!=0) {
//            this.wait();
//        }
//        // 干活
//        number++;
//        System.out.println(Thread.currentThread().getName()+" "+number);
//        //通知
//        this.notifyAll();
//    }
//    public synchronized void decrement() throws InterruptedException {
////        if (number==0){
//        while (number==0){
//            this.wait();
//        }
//        number--;
//        System.out.println(Thread.currentThread().getName()+" "+number);
//        this.notifyAll();
//    }
}
/**
 * 现在有两个线程。可以操作初始值为0的一个变量
 * 实现一个线程时对该变量+1，一个线程对该变量-1
 * 实现交替，来10轮，变量初始值为0
 * 1线程操控资源类
 * 2线程交互期间 判断/干活/通知
 * 3多线程交互中，必须要防止多线程的虚假唤醒，也即（不能用if 只能用while）
 */
public class ThreadWaitingNotifyDemo {
    public static void main(String[] args) {
        AirConditioner airConditioner = new AirConditioner();
        new Thread(()-> {
            try {
                for (int i=0;i<10;i++){
                    airConditioner.decrement();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        },"消费1").start();
        new Thread(()-> {
            try {
                for (int i=0;i<10;i++){
                    airConditioner.increment();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        },"生产1").start();

        new Thread(()-> {
            try {
                for (int i=0;i<10;i++){
                    airConditioner.decrement();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        },"消费2").start();
        new Thread(()-> {
            try {
                for (int i=0;i<10;i++){
                    airConditioner.increment();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        },"生产2").start();
    }
}
