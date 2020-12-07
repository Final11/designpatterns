package com.wonders.ghy.juc.volatile_不稳定的;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

/**
 *  Volatile是java虚拟机提供的一种轻量级同步机制
 *  1.1 保证可见性
 *  1.2 不保证原子性
 *  1.3 进制指令重排
 *
 *  JMM
 *  2.1 可见性
 *  2.2 原子性
 *  2.3 VolatileDemo代码演示可见性+原子性代码
 *  2.4 有序性
 **/
class MyData{
    volatile int num = 0;

    public void addT060(){
        this.num=60;
    }
    // 请注意,此事num前面是加了volatile关键字修饰,volatile不保证原子性
    public void addPlusPlus(){
        num++;
    }
    AtomicInteger atomicInteger = new AtomicInteger();
    public void addMyAtomic(){
        atomicInteger.getAndIncrement();
    }
}
/*
 * 1 验证Volatile的可见性
 *  1.1加入int num=0 num变量之前根本没有添加volatile关键字修饰,可就是说没有可见性
 *  1.2 添加了volatile可以解决可见性为题
 * 2 验证不保证原子性
 *  2.1原子性值的是什么意思?
 *      不可分割,完整性,也即某个线程正在做某个具体业务时,中间不可以被加塞或者被分割.
 *      需要整体完整,要么同时成功,要么同时失败
 *  2.2是否可以保证原子性的案例演示
 *  2.3为什么
 *  2.4如何解决?
 *      (1)加synchronized -- 不建议
 *      (2)使用JUC的 AtomicInteger
 */
public class VolatileDemo {
    public static void main(String[] args) {
        MyData myData = new MyData();
        for (int i = 0; i < 20; i++) {
            new Thread(()->{
                for (int i1 = 0; i1 < 1000; i1++) {
                    myData.addPlusPlus();
                    myData.addMyAtomic();
                }
            },String.valueOf(i)).start();
        }
        // 需要等待上面20个线程全部计算成功后再用main线程获得最终的结果值看是多少
        // 一个是main线程另一个是gc线程 如果大于2意思是上边的线程还没跑完.就礼让上面的线程
        while (Thread.activeCount()>2){
            Thread.yield();
        }
        System.out.println(myData.num);
        System.out.println(Thread.currentThread().getName()+"\t updated num value: "+myData.atomicInteger);
    }
    /**
     * 可以保证可见性,及时通知其他线程,主物理内存的值已经被修改
     */
    public static void seeOkByVolatile(){
        MyData myData = new MyData();
        new Thread(()->{
            try {TimeUnit.SECONDS.sleep(3);} catch (InterruptedException e) {e.printStackTrace(); }
            myData.addT060();
            System.out.println(Thread.currentThread().getName()+"\t updated num value: "+myData.num);
        },"AAA").start();

        // 第二个线程就是我们的main线程
        while (myData.num==0){
            // main 线程就一直在这里等待循环,直到num值不再等于0
        }
        System.out.println(Thread.currentThread().getName()+"已经改变了值");
    }
}
