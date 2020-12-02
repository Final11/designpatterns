package com.wonders.ghy.java8;

import org.junit.Test;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * 一、Stream的三个操作步骤：
 * 1.创建Stream
 * 2.中间操作
 *      （1）筛选与切片
 *      filter---接收Lambda，从流中排出某些元素
 *      limit---截断流，使元素不超过给定数量
 *      skip---跳过元素，返回一个扔掉了前n个元素的流。若流中元素不足n个，则返回一个空流，与limit(n)互补
 *      distinct---筛选，通过流所生成元素的hashCode()和equals()去重复元素
 *      （2）映射
 *      map---接收Lambda，将元素转换成其他形式或提取信息。接收一个函数作为参数，该函数会被应用到每一个元素，并
 *      将其映射成一个新的元素。
 *      flatMap---接收一个函数作为参数，将流中的每一个值都换成另一个流，然后把所有流连接成一个流
 *      （3）排序
 *      sorted()---自然排序
 *      sorted(Comparator)---定制排序
 * 3.终止操作（终端操作）
 *      （1）查找与匹配
 *      allMatch---检查是否匹配所有元素
 *      anyMatch---检查是否至少匹配一个元素
 *      noneMatch---检查是否有没有匹配所有元素
 *      findFirst---返回第一个元素
 *      findAny---返回当前流中的任意元素
 *      count---返回流中元素总个数
 *      min---返回流中最小值
 *      max---返回流中最大值
 *      （2）归约与收集
 *      reduce(T identity,BinaryOperator)/reduce(BinaryOperator)---可以将流中元素反复结合起来，得到一个值
 *      collect---将流转换成其他形式。接收一个Collector接口的实现，用于給Stream中元素做汇总的方法
 */
public class StreamAPI {

    //创建Stream
    @Test
    public void test1(){
        //1.可以通过Collection系列集合提供的stream()方法或parallelStream()
        List<String> list = new ArrayList<>();
        Stream<String> stream = list.stream();

        //2.通过Arrays中的静态方法stream() 方法
        Employee[] emps = new Employee[10];
        Stream<Employee> stream1 = Arrays.stream(emps);

        //3.通过Stream类中的静态方法 of()
        Stream<String>stream2 = Stream.of("a","b","c","d");

        //4.创建无限流
        //迭代
        Stream<Integer> iterate = Stream.iterate(0, (x) -> x + 2);
        //iterate.forEach(System.out::println);
        //iterate.limit(10).forEach(System.out::println);

        //生成
        Stream<Double> generate = Stream.generate(() -> Math.random());
        generate.limit(10).forEach(System.out::println);
    }

    List<Employee> employees = Arrays.asList(
            new Employee(1,"张三",18, Employee.Status.BUSY),
            new Employee(2,"李四",28,Employee.Status.FREE),
            new Employee(3,"王五",38,Employee.Status.FREE),
            new Employee(4,"田七",18,Employee.Status.FREE),
            new Employee(5,"田七",18,Employee.Status.VOCATION)
    );
    //中间操作
    /**
     * 筛选与切片
     * filter---接收Lambda，从流中排出某些元素
     * limit---截断流，使元素不超过给定数量
     * skip---跳过元素，返回一个扔掉了前n个元素的流。若流中元素不足n个，则返回一个空流，与limit(n)互补
     * distinct---筛选，通过流所生成元素的hashCode()和equals()去重复元素
     */
    // 内部迭代：迭代操作由Stream API完成
    @Test
    public void test2(){
        //中间操作：不会执行任何操作
        Stream<Employee> employeeStream = employees.stream().
                filter((e) ->{ System.out.println("中间操作");
                                return e.getAge() > 35;});
        employeeStream.forEach(System.out::println);
        //employees.stream().filter((e)->e.getAge()>35).forEach(System.out::println);
    }
    //外边迭代：
    @Test
    public void test3(){
        Iterator<Employee> iterator = employees.iterator();
        while (iterator.hasNext()){
            System.out.println(iterator.next());
        }
    }
    //skip()
    @Test
    public void test4(){
        employees.stream().filter((e)->e.getAge()>15).skip(1).forEach(System.out::println);
    }
    //distinct()
    @Test
    public void test5(){
        employees.stream().filter((e)->e.getAge()>15).skip(1).distinct().forEach(System.out::println);
    }

    /**
     *  映射
     *  map---接收Lambda，将元素转换成其他形式或提取信息。接收一个函数作为参数，该函数会被应用到每一个元素，并
     *  将其映射成一个新的元素。
     *  flatMap---接收一个函数作为参数，将流中的每一个值都换成另一个流，然后把所有流连接成一个流
     */
    //map()
    @Test
    public void test6(){
        List<String> list = Arrays.asList("a","b","c","d","e","f");
        list.stream().map((str)->str.toUpperCase()).forEach(System.out::println);
        System.out.println("--------------");
        employees.stream().map(Employee::getName).forEach(System.out::println);
    }
    //flatMap()
    @Test
    public void test7(){
        List<String> list = Arrays.asList("aa","bb","cc","dd","ee","ff");
        list.stream().map((str)->str.toUpperCase()).forEach(System.out::println);
        System.out.println("--------------");
        employees.stream().map(Employee::getName).forEach(System.out::println);
        System.out.println("--------------");
        Stream<Stream<Character>> streamStream = list.stream().map(StreamAPI::filterCharacter);
        streamStream.forEach((sm)->sm.forEach(System.out::println));
        System.out.println("--------------");
        Stream<Character> characterStream = list.stream().flatMap(StreamAPI::filterCharacter);
        characterStream.forEach(System.out::println);
    }
    public static Stream<Character> filterCharacter(String str){
        List<Character> list = new ArrayList<>();
        for (Character character : str.toCharArray()) {
            list.add(character);
        }
        return list.stream();
    }
    /**
     * 排序
     * sorted()---自然排序(Comparable)
     * sorted(Comparator)---定制排序
     */
    @Test
    public void test8() {
        List<String> list = Arrays.asList("aa", "bb", "cc", "dd", "ee", "ff");
        list.stream().sorted().forEach(System.out::println);
        System.out.println("--------------");
        employees.stream().sorted((x1, x2) -> {
            if (x1.getAge().equals(x2.getAge())) {
                return x1.getName().compareTo(x2.getName());
            } else {
                return x1.getAge().compareTo(x2.getAge());
            }
        }).forEach(System.out::println);
    }

    /**
     * 3.终止操作（终端操作）
     * 查找与匹配
     * allMatch---检查是否匹配所有元素
     * anyMatch---检查是否至少匹配一个元素
     * noneMatch---检查是否没有匹配所有元素
     * findFirst---返回第一个元素
     * findAny---返回当前流中的任意元素
     * count---返回流中元素总个数
     * min---返回流中最小值
     * max---返回流中最大值
     */
    @Test
    public void test9() {
        System.out.println(employees.stream().allMatch((e) -> e.getStatus().equals(Employee.Status.BUSY))); //false
        System.out.println(employees.stream().anyMatch((e) -> e.getStatus().equals(Employee.Status.BUSY))); //true
        System.out.println(employees.stream().noneMatch((e) -> e.getStatus().equals(Employee.Status.BUSY)));//false
        Optional<Employee> first = employees.stream().sorted(Comparator.comparingInt(Employee::getAge)).findFirst();
        System.out.println(first);
        Optional<Employee> first2 = employees.stream().sorted((e1,e2)-> -Integer.compare(e1.getAge(),e2.getAge())).findFirst();
        System.out.println(first2);
        //在空闲的员工里随便找一个
        System.out.println(employees.stream().filter((e) -> e.getStatus().equals(Employee.Status.FREE)).findAny());
        //在空闲的员工里随便找一个 并行流
        System.out.println(employees.parallelStream().filter((e) -> e.getStatus().equals(Employee.Status.FREE)).findAny());
        System.out.println(employees.stream().count());
        // 获取最大年龄的员工
        System.out.println(employees.stream().max(Comparator.comparingInt(Employee::getAge)).get());
        // 获取最大年龄的员工的年龄
        System.out.println(employees.stream().max(Comparator.comparingInt(Employee::getAge)).map(Employee::getAge).get());//先找最大的员工 后取年龄
        System.out.println(employees.stream().map(Employee::getAge).max(Integer::compareTo).get()); //先提取年龄后比较大小
    }
    /**
     *（2）归约
     * reduce(T identity,BinaryOperator)/reduce(BinaryOperator)---可以将流中元素反复结合起来，得到一个值
     */
    @Test
    public void test10() {
        List<Integer> list = Arrays.asList(1,2,3,4,5,6,7,8,9,10);
        System.out.println(list.stream().reduce(0, (x, y) -> x + y));
        System.out.println("-----------------------------");
        //累加所有员工的年龄
        System.out.println(employees.stream().map(Employee::getAge).reduce(Integer::sum).get());
    }
    /**
     * 收集
     * collect---将流转换成其他形式。接收一个Collector接口的实现，用于給Stream中元素做汇总的方法
     */
    @Test
    public void test11() {
        List<String> list = employees.stream().map(Employee::getName).collect(Collectors.toList());
        System.out.println(list);
        System.out.println("-----------------------------");
        Set<String> set = employees.stream().map(Employee::getName).collect(Collectors.toSet());
        System.out.println(set);
        System.out.println("-----------------------------");
        // 收集到任意数据结构中
        HashSet<String> collect = employees.stream().map(Employee::getName).collect(Collectors.toCollection(HashSet::new));
        System.out.println(collect);
        collect.forEach(System.out::println);
        System.out.println("-----------------------------");
        //总数
        System.out.println(employees.stream().collect(Collectors.counting()));
        //平均值
        System.out.println(employees.stream().collect(Collectors.averagingInt(Employee::getAge)));
        //总和
        System.out.println(employees.stream().collect(Collectors.summingInt(Employee::getAge)));
        //最大值
        Optional<Employee> collect1 = employees.stream().collect(Collectors.maxBy(Comparator.comparingInt(Employee::getAge)));
        System.out.println(collect1.get());
        // 分组  groupingBy()
        Map<Employee.Status, List<Employee>> collect2 = employees.stream().collect(Collectors.groupingBy(Employee::getStatus));
        System.out.println(collect2);
        //多级分组  groupingBy()
        System.out.println(employees.stream().collect(Collectors.groupingBy(Employee::getStatus, Collectors.groupingBy(Employee::getAge))));
        //分区  partitioningBy()
        System.out.println(employees.stream().collect(Collectors.partitioningBy((e) -> e.getAge() > 20)));
        // 一次性得到元素个数、总和、均值、最大值、最小值
        System.out.println(employees.stream().collect(Collectors.summarizingInt(Employee::getAge)));
        // 拼接
        System.out.println(employees.stream().map(Employee::getName).collect(Collectors.joining(",", "---", "+++")));
    }


    // 下面开始进行练习

    /**
     * 给定一个数字列表，返回每个数字的平方
     * [1,2,3,4,5,6]-----[1,4,9,16,25,46]
     */
    @Test
    public void test12() {
        Integer[] nums = new Integer[]{1,2,3,4,5,6};
        Arrays.stream(nums).map(x->x*x).forEach(System.out::println);
    }

    /**
     * 用map和reduce完成数出Employee的元素
     */
    @Test
    public void test13() {
        System.out.println(employees.stream().map((e) -> 1).reduce(Integer::sum).get());
    }
}
