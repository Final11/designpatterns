package com.wonders.ghy.jvm;

public class Hello0222 {
    private native void start0();

    public static void main(String[] args) {
        Object o =new Object();
        System.out.println(o.getClass().getClassLoader());
        String str = new String();
        Hello0222 hello0222 = new Hello0222();
        System.out.println(hello0222.getClass().getClassLoader());
        System.out.println(hello0222.getClass().getClassLoader().getParent());
        System.out.println(hello0222.getClass().getClassLoader().getParent().getParent());

        // 线程和语言无关 只和系统有关
        Thread t1 = new Thread();
        t1.start();
    }
}
