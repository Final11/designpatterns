package com.wonders.ghy.lock8;

import java.util.concurrent.TimeUnit;

class Phone{
    public static synchronized void sendEmail() throws InterruptedException {
        TimeUnit.SECONDS.sleep(4);
        System.out.println("----sendEmail");
    }
    public synchronized void sendEMS(){
        System.out.println("----sendEMS");
    }
    public void sendHello(){
        System.out.println("----sendHello");
    }
}

/**
 * 题目：多线程8种情况锁
 * 1.标准访问：请问先打印邮件还是短信？ 答案：邮件->短信
 * 2.邮件方法暂停4秒钟，请问先打印邮件还是短信？ 答案： 先打印邮件后短信因为他们两个是同步方法，先进入邮件后对象就锁住了。短信进不去
 *      12锁笔记：一个对象里面如果有多个synchronized方法，某一个时刻内，只要一个线程去调用其中的一个synchronized方法了，
 *       其它的线程都只能等待，换句话说，某一个时刻内，只能有唯一一个线程去访问这些synchronized方法，
 *       锁的是当前对象this，被锁定后，其它的线程都不能进入到当前对象的其它的synchronized方法
 * 3.新增一个普通方法不带synchronized 请问先打印邮件还是hello 答案：先打印hello因为hello和邮件方法不冲突。
 *      3锁笔记： 加个普通方法后发现和同步锁无关
 * 4.两部手机，请问先打印邮件还是短信 答案：先打印短信，因为对象不同相互不阻塞。又因为发邮箱4秒延迟 所以先短信后邮箱
 *      4锁笔记：换成两个对象后，不是同一把锁了，情况立刻变化根本不互相竞争
 * 5.两个静态同步方法，同一部手机，请问先打印邮件还是短信？ 答案：先邮箱后短信 因为static锁的是类模板，不管是否是同一部手机，只要互相竞争都要锁住，又因为先执行邮件
 * 6.两个静态同步方法，2部手机，请问先打印邮件还是短信？ 答案：先邮箱后短信 因为static锁的是类模板，不管是否是同一部手机，只要互相竞争都要锁住，又因为先执行邮件
 *      56锁笔记：也就是说如果一个实例对象的非静态同步方法获取锁后，该实例对象的其他非静态同步方法必须等待获取锁的方法释放锁后才能获取锁，
 *       可是别的实例对象的非静态同步方法因为跟该实例对象的非静态同步方法用的是不同的锁，
 *       所以毋须等待该实例对象已获取锁的非静态同步方法释放锁就可以获取他们自己的锁。*
 * 7.一个静态同步方法，一个普通同步方法，1部手机，请问先打印邮件还是短信？答错了。。。 原分析 答案
 * 8.一个静态同步方法，一个普通同步方法，2部手机，请问先打印邮件还是短信？不会。。
 *      78锁笔记：所有的静态同步方法用的也是同一把锁—类对象本身，
 *       这两把锁（this、class）是两个不同的对象，所以静态同步方法与非静态同步方法之间是不会有竞态条件的。
 *       但是一旦一个静态同步方法获取锁后，其他的静态同步方法都必须等待该方法释放锁后才能获取锁，
 *       而不管是同一个实例对象的静态同步方法之间,还是不同的实例对象的静态同步方法之间，只要它们同一个类的实例对象!
 *
 *       当一个线程试图访问同步代码块时，它首先必须得到锁，退出或抛出异常时必须释放锁。
 */
public class Lock8 {
    public static void main(String[] args) throws InterruptedException {
        Phone phone = new Phone();
        Phone phone2 = new Phone();
        new Thread(()->{
            try {
                phone.sendEmail();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        },"A").start();
        Thread.sleep(200);
        new Thread(()-> {
            phone2.sendEMS();
            //phone.sendHello();
            //phone2.sendEMS();
            },"B").start();
    }
}
