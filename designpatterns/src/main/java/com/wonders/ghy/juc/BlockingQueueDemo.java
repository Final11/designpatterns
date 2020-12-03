package com.wonders.ghy.juc;

import java.util.Collection;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.TimeUnit;

/**
 * 阻塞队列
 * 1 两个数据结构
 *  1.1栈 后劲先出
 *  1.1队列 先进先出
 * 2 阻塞队列
 *  2.1 阻塞 必须要阻塞/不得不阻塞
 *
 * 3 how
 * 4 BlockingQueue核心方法
 *      方法类型    抛出异常    特殊值     阻塞      超时
 *      插入        add(e)      offer(e)   put(e)    offer(e,time.unit)
 *      移除        remove()    poll()     take()    poll(time,unit)
 *      检查        element()   peek()     不可用    不可用
 *
 *  抛出异常：当阻塞队列满时，再往队列里add插入元素会抛illegalStateException:Queue full
 *            当阻塞队列空时，再往队列里remove移除元素会抛NoSuchElementException
 *  特殊值：插入方法，成功true失败false
 *          移除方法，成功返回出队列的元素，队列里没有就返@null
 *  一直阻塞：当阻塞队列满时，生产者线程继续往队列里put元素，队列会一直阻塞生产者线程直到put数据or响应中断退出
 *            当阻塞队列空时，消费者线程试图从队列里take元素， 队列会一直阻塞消费者线程直到队列可用
 *  超时退出：当阻塞队列满时，队列会阻塞生产者线程一定时间，超过限时后生产者线程会退出
 */
public class BlockingQueueDemo {
    public static void main(String[] args) throws InterruptedException {
        BlockingQueue<String> bq = new ArrayBlockingQueue(3);
        /*System.out.println(bq.add("a"));
        System.out.println(bq.add("b"));
        System.out.println(bq.add("c"));
        //System.out.println(bq.add("d"));
        System.out.println(bq.remove());
        // 检查下一个被移除的元素/检查队列第一个元素
        System.out.println(bq.element());*/


        /*System.out.println(bq.offer("a"));
        System.out.println(bq.offer("b"));
        System.out.println(bq.offer("c"));
        System.out.println(bq.offer("d"));
        System.out.println(bq.poll());
        System.out.println(bq.poll());
        System.out.println(bq.poll());
        System.out.println(bq.poll());
        // 检查下一个被移除的元素/检查队列第一个元素
        System.out.println(bq.peek());*/

        /*bq.put("a");
        bq.put("b");
        bq.put("c");
        //bq.put("d");
        System.out.println(bq.take());
        System.out.println(bq.take());
        System.out.println(bq.take());
        //System.out.println(bq.take());*/

        /*System.out.println(bq.offer("a", 2, TimeUnit.SECONDS));
        System.out.println(bq.offer("b", 2, TimeUnit.SECONDS));
        System.out.println(bq.offer("c", 2, TimeUnit.SECONDS));
        System.out.println(bq.offer("d", 2, TimeUnit.SECONDS));
        System.out.println(bq.poll(2, TimeUnit.SECONDS));
        System.out.println(bq.poll(2, TimeUnit.SECONDS));
        System.out.println(bq.poll(2, TimeUnit.SECONDS));
        System.out.println(bq.poll(2, TimeUnit.SECONDS));*/
    }
}
