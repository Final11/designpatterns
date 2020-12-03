package com.wonders.ghy.juc;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

class B{
    private Integer status=1;//1 A ,2 B ,3 C
    Lock lock = new ReentrantLock();
    Condition condition1 = lock.newCondition();
    Condition condition2 = lock.newCondition();
    Condition condition3 = lock.newCondition();

    public void send5(){
        lock.lock();
        try {
            while (status!=1){
                condition1.await();
            }
            while (status==1){
                for (int i = 0; i < 5; i++) {
                    System.out.println(Thread.currentThread().getName()+"打印 "+(i+1)+"次");
                }
                status=2;
                condition2.signal();

            }


        }catch (Exception e){
            e.printStackTrace();
        }finally {
            lock.unlock();
        }

    }

    public void send10(){
        lock.lock();
        try {
            while (status!=2){
                condition2.await();
            }
            while (status==2){
                for (int i = 0; i < 10; i++) {
                    System.out.println(Thread.currentThread().getName()+"打印 "+(i+1)+"次");
                }
                status=3;
                condition3.signal();
            }


        }catch (Exception e){
            e.printStackTrace();
        }finally {
            lock.unlock();
        }


    }


    public void send15(){
        lock.lock();
        try {
            while (status!=3){
                condition3.await();
            }
            while (status==3){
                for (int i = 0; i < 15; i++) {
                    System.out.println(Thread.currentThread().getName()+"打印 "+(i+1)+"次");
                }
                status=1;
                condition1.signal();
            }


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
public class ThreadOrderAccess2 {
    public static void main(String[] args) throws  Exception{
        B b = new B();
        new Thread(()->{for (int i = 0; i < 10; i++) {b.send5();}},"A").start();
        new Thread(()->{for (int i = 0; i < 10; i++) {b.send10();}},"B").start();
        new Thread(()->{for (int i = 0; i < 10; i++) {b.send15();}},"C").start();
    }
}
