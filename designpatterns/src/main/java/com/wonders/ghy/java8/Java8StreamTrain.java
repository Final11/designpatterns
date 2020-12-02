package com.wonders.ghy.java8;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class Java8StreamTrain {
    private static List<Apple> arrayList = new ArrayList<Apple>();
    static {
        arrayList.add(new Apple(1,"red",500,"hunan"));
        arrayList.add(new Apple(1,"blue",400,"jinzhou"));
        arrayList.add(new Apple(1,"yellow",300,"beijing"));
        arrayList.add(new Apple(1,"red",600,"shanghai"));
        arrayList.add(new Apple(1,"red",500,"guangdong"));
    }

    public static void main(String[] args) {
        System.out.println(test().toString());
        test2();
    }
    // 筛选颜色是红色的苹果
    public static List<Apple> test(){
        List<Apple> list= arrayList.stream().filter(a->a.getColor().equals("red")).collect(Collectors.toList());
        return list;
    }

    // 求出每个颜色的平均重量
    public static void test2(){
        arrayList.stream().collect(Collectors.groupingBy(a -> a.getColor(),Collectors.averagingInt(a->a.getWeight()))).forEach((k,v)-> System.out.println(k+':'+v));
    }
}
