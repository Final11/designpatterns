package com.wonders.ghy.java8;

import org.junit.Before;
import org.junit.Test;

import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public class TestTransaction {
    List<Transaction> transactions = null;
    @Before
    public void before(){
        Trader raoul = new Trader("raoul","Cambridge");
        Trader mario = new Trader("mario","Milan");
        Trader alan = new Trader("alan","Cambridge");
        Trader brian = new Trader("brian","Cambridge");
        transactions= Arrays.asList(
                new Transaction(brian,2011,300),
                new Transaction(raoul,2012,1000),
                new Transaction(raoul,2011,400),
                new Transaction(mario,2012,710),
                new Transaction(mario,2012,700),
                new Transaction(alan,2012,950)
        );
    }

    //1.找出2011年发生的所有交易，并按交易额排序 从低到高
    @Test
    public void test1(){
        transactions.stream().filter((t)->t.getYear()==2011).sorted(Comparator.comparingInt(Transaction::getValue)).forEach(System.out::println);
    }
    //2.交易员都在哪些不同的城市工作过
    @Test
    public void test2(){
        System.out.println(transactions.stream().map((t) -> t.getTrader().getCity()).distinct().collect(Collectors.toList()));
    }
    //3.查找所有来自剑桥的交易员，并按照姓名排序
    @Test
    public void test3(){
        transactions.stream().filter((e) -> e.getTrader().getCity().equals("Cambridge")).map(Transaction::getTrader).sorted(Comparator.comparing(Trader::getName)).distinct().forEach(System.out::println);
    }
    //4.返回所有交易员的姓名字符串，按字母顺序排序
    @Test
    public void test4(){
        transactions.stream().map((t)->t.getTrader().getName()).sorted().forEach(System.out::println);
        System.out.println("----------------------");
        System.out.println(transactions.stream().map((t) -> t.getTrader().getName()).sorted().reduce("", String::concat));
    }
    //5.有没有交易员是在米兰工作的
    @Test
    public void test5(){
        System.out.println(transactions.stream().anyMatch(t -> t.getTrader().getCity().equals("Milan")));
    }
    //6.打印生活在剑桥的交易员的所有交易额
    @Test
    public void test6(){
        System.out.println(transactions.stream().filter(e -> e.getTrader().getCity().equals("Cambridge")).map(e -> e.getValue()).reduce(Integer::sum).get());
    }
    //7.所有交易中,最高的交易额是多少
    @Test
    public void test7(){
        System.out.println(transactions.stream().map(e -> e.getValue()).max(Integer::compare).get());
    }
    //8.找到交易额最小的交易
    @Test
    public void test8(){
        System.out.println(transactions.stream().min((t1, t2) -> Integer.compare(t1.getValue(), t2.getValue())).get());
    }
}
