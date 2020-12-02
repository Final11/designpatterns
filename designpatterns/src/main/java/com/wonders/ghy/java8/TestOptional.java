package com.wonders.ghy.java8;

import org.junit.Test;

import java.util.Optional;

public class TestOptional {
    /**
     * Optional容器类的常用方法:
     * Optional.of(T t) :创建一个Optional实例
     * Optional.empty() :创建一个空的Optional实例
     * Optional.ofNullable(T t):若t 不为null,创建Optional 实例,否则创建空实例
     * isPresent() :判断是否包含值
     * orElse(T t) :如果调用对象包含值,返回该值,否则返回t
     * orElseGet(Supplier s):如果调用对象包含值，返回该值，否则返回s 获取的值
     * map(Function f):如果有值对其处理，并返回处理后的Optional，否则返回Optional.empty()
     * flatMap(Function mapper):与map类似，要求返回值必须是Optional
     */


    @Test
    public void test1(){
        Optional<Employee> op = Optional.of(null);
        Employee emp = op.get();
        System.out.println(emp);
    }
}
