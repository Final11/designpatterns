package com.wonders.ghy.juc.锁_集合_map不安全类;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.CopyOnWriteArraySet;

/**
 * 题目：请举例说明集合类是不安全的
 * 1 故障现象
 *      java.util.ConcurrentModificationException 并发修改异常
 * 2 导致原因
 *
 * 3 解决方案
 *      3.1 Vector
 *      3.2 Collections.synchronizedCollection(new ArrayList<>())
 *      3.3 CopyOnWriteArrayList()
 *      写时复制源码：
 *      public boolean add(E e) {
 *         final ReentrantLock lock = this.lock;
 *         lock.lock();
 *         try {
 *             Object[] elements = getArray();
 *             int len = elements.length;
 *             Object[] newElements = Arrays.copyOf(elements, len + 1);
 *             newElements[len] = e;
 *             setArray(newElements);
 *             return true;
 *         } finally {
 *             lock.unlock();
 *         }
 *     }
 *     原理：CopyOnWrite容器就是写时复制的容器。往一个容器添加元素的时候，不直接往当前容器
 *     Object[]添加，而是现将当前容器进行Copy，复制出一个新的容器，然后往新的容器里添加元素
 *     再将原容器的引用指向新的容器 setArray() 这样做的好处就是可以对CopyOnWrite容器进行
 *     并发的读，而不需要锁。因为当前容器不会添加任何元素。所以CopyOnWrite容器也是一种读写分离
 *     的思想，读和写不同的容器
 * 4 优化建议
 */
public class NotSafeDemo {
    public static void main(String[] args) {
        mapNotSafe();
        setNotSafe();
        listNotSafe();

    }

    /**
     * hashMap的底层数据结构是 node类型的数组加链表/红黑树
     * 默认长度是16 负载因子是0.75 扩容为原来的一倍
     */
    public static void mapNotSafe(){
        Map<String,String> map = new ConcurrentHashMap();//Collections.synchronizedMap(new HashMap<>());//new HashMap<>();
        //Map<String,String> map2 = new HashMap<>(1000);
        for (int i = 0; i < 30; i++) {
            new Thread(()->{
                map.put(Thread.currentThread().getName(),UUID.randomUUID().toString().substring(0,8));
                System.out.println(map);
            },String.valueOf(i)).start();
        }
    }
    /**
     * hashSet底层数据结构是hashmap hashset的add方法就是用的hashMap的put方法
     * 只不过put的是hashMap的key value是一个常量Object
     */
    public static void setNotSafe(){
        Set<String> set = new CopyOnWriteArraySet<>();//Collections.synchronizedSet(new HashSet<>());//new HashSet<>();
        for (int i = 0; i < 30; i++) {
            new Thread(()->{
                set.add(UUID.randomUUID().toString().substring(0,8));
                System.out.println(set);
            },String.valueOf(i)).start();
        }
    }

    /**
     * ArrayList扩容为原来的一半
     */
    public static void listNotSafe(){
        List<String> list = new CopyOnWriteArrayList();//Collections.synchronizedCollection(new ArrayList<>());//new Vector<>();//new ArrayList<>();
        for (int i = 0; i < 30; i++) {
            new Thread(()->{
                list.add(UUID.randomUUID().toString().substring(0,8));
                System.out.println(list);
            },String.valueOf(i)).start();
        }
    }
}
