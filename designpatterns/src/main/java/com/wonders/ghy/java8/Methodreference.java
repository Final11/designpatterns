package com.wonders.ghy.java8;

import org.junit.Test;

import java.io.PrintStream;
import java.util.Comparator;
import java.util.function.*;

/**
 * java8 lambda 方法引用和构造器引用
 *
 * 一：方法引用：若Lambda体中的内容有方法已经实现了，我们可以使用“方法引用”
 *          （可以理解为方法引用是Lambda表达式的另外一种表现形式）
 * 主要有三种语法格式：
 * 1.对象::实例方法名
 * 2.类::静态方法名
 * 3.类::实例方法名
 * 注意：
 * （1）Lambda 体中调用方法的参数列表与返回值类型，要与函数式接口中的抽象方法的函数列表和返回值类型保持一致
 * （2）若Lambda参数列表中的第一参数是实例方法的调用者，而第二个参数是实例方法的参数时，可以使用ClassName::method
 *
 * 二：构造器引用
 * 格式：
 * ClassName::new
 * 注意：需要调用的构造器的参数列表要与函数式接口中抽象方法的参数列表保持一致！
 *
 * 三：数组引用：
 * 格式：
 * Type::new
 *
 */
public class Methodreference {

    @Test
    public void test7(){
        //数组引用
        Function<Integer,String[]> fun= (x)->new String[x];
        String[] strs = fun.apply(10);
        System.out.println(strs.length);

        Function<Integer,String[]> fun2=String[]::new;
        String[] strs2 = fun.apply(20);
        System.out.println(strs2.length);
    }

    @Test
    public void test5(){
        //构造器引用
        Supplier<Employee> sup = ()->new Employee();

        Supplier<Employee> sup2 = Employee::new;
        System.out.println(sup2.get());
    }

    @Test
    public void test6(){
        //构造器引用
        // 需要调用的构造器的参数列表要与函数式接口中抽象方法的参数列表保持一致！
        Function<Integer,Employee> fun = (x)->new Employee(x);

        Function<Integer,Employee> fun2 = Employee::new;
        System.out.println(fun2.apply(1));

        BiFunction<Integer,String,Employee> bf = Employee::new;
        System.out.println(bf.apply(1,"GAOHONGYU"));
    }

    @Test
    public void test4(){
        //类::实例方法名
        // 若Lambda参数列表中的第一参数是实例方法的调用者，而第二个参数是实例方法的参数时，可以使用ClassName::method
        BiPredicate<String,String> bp = (x,y)->x.equals(y);

        BiPredicate<String,String> bp2 = String::equals;
        System.out.println(bp2.test("123","234"));
    }
    @Test
    public void test3(){
        //类::静态方法名
        Comparator<Integer> com = (x, y) -> Integer.compare(x,y);

        Comparator<Integer> com1 = Integer::compare;
        System.out.println(com1.compare(1,2));
    }


    @Test
    public void test1(){
        //对象::实例方法名
        Consumer<String> com = (x)-> System.out.println(x);

        PrintStream out = System.out;
        Consumer<String> com2 = out::println;
        com2.accept("123456");
    }

    @Test
    public void test2(){
        //对象::实例方法名
        Employee emp = new Employee();
        Supplier<String> sup =  ()->emp.getName();
        String str = sup.get();
        System.out.println(str);

        Supplier<Integer> sup2 = emp::getAge;
        Integer num = sup2.get();
        System.out.println(num);
    }
}
