package com.wonders.ghy.juc.锁_集合_map不安全类;


import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

class ShareResource{
    private int number =1;// 1:A 2:B 3:C
    private Lock lock = new ReentrantLock();
    // 一把锁三个钥匙
    private Condition condition1 = lock.newCondition();
    private Condition condition2 = lock.newCondition();
    private Condition condition3 = lock.newCondition();
    public void print5(){
        lock.lock();
        try {
            // 1 判断
            while (number!=1){
                condition1.await();
            }
            // 2 干活
            for (int i = 0; i < 5; i++) {
                System.out.println(Thread.currentThread().getName()+"打印 "+(i+1)+"次");
            }
            // 3 通知
            number=2;
            condition2.signal();
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            lock.unlock();
        }
    }

    public void print10(){
        lock.lock();
        try {
            // 1 判断
            while (number!=2){
                condition2.await();
            }
            // 2 干活
            for (int i = 0; i < 10; i++) {
                System.out.println(Thread.currentThread().getName()+"打印 "+(i+1)+"次");
            }
            // 3 通知
            number=3;
            condition3.signal();
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            lock.unlock();
        }
    }

    public void print15(){
        lock.lock();
        try {
            // 1 判断
            while (number!=3){
                condition3.await();
            }
            // 2 干活
            for (int i = 0; i < 15; i++) {
                System.out.println(Thread.currentThread().getName()+"打印 "+(i+1)+"次");
            }
            // 3 通知
            number=1;
            condition1.signal();
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            lock.unlock();
        }
    }
}
/**
 * 题目：多个线程之间按顺序调用 A->B->C
 * 三个线程启动，要求如下
 * A打印5次，B打印10次，C打印15次
 * 接着A打印5次，B打印10次，C打印15次
 * ...来10轮
 *
 * 1线程操控资源类
 * 2线程交互期间 判断/干活/通知
 * 3多线程交互中，必须要防止多线程的虚假唤醒，也即（不能用if 只能用while）
 * 4标志位
 */
public class ThreadOrderAccess {
    public static void main(String[] args) {
        ShareResource shareResource = new ShareResource();
        new Thread(()->{for (int i = 0; i < 10; i++) {shareResource.print5();}},"A").start();
        new Thread(()->{for (int i = 0; i < 10; i++) {shareResource.print10();}},"B").start();
        new Thread(()->{for (int i = 0; i < 10; i++) {shareResource.print15();}},"C").start();
    }
}
