package com.nijunyang.stream;

import com.nijunyang.model.Student;
import org.junit.Test;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.LongStream;
import java.util.stream.Stream;

/**
 * @author: create by nijunyang
 * @date:2019/8/11
 */
public class StreamTest {

    List<Student> studentList = Arrays.asList(
            new Student(1, "张三", 12, 69),
            new Student(2, "李四", 12, 78.5),
            new Student(3, "王五", 13, 95),
            new Student(4, "赵六", 14, 23),
            new Student(5, "孙七", 11, 55.5),
            new Student(5, "孙七", 11, 55.5)
    );

    @Test
    public void testStreamCreate() {
        //集合创建流
        Stream<Student> stream1 = studentList.stream();

        //Stream静态方法创建流
        Stream<Student> stream2 = Stream.of(
                new Student(1, "张三", 12, 69),
                new Student(2, "李四", 12, 78.5),
                new Student(3, "王五", 13, 95),
                new Student(4, "赵六", 14, 23),
                new Student(5, "孙七", 11, 55.5)
        );
        //数组创建流
        Stream<Student> stream = Arrays.stream(new Student[]{});
    }


    //流操作

    /**
     * 筛选和分割
     * filter(Predicate p) 接收 Lambda ， 从流中排除某些元素。
     * distinct() 筛选，通过流所生成元素的 hashCode() 和 equals() 去除重复元素，切记重写
     * limit(long maxSize) 截断流，使其元素不超过给定数量。
     * skip(long n) 跳过元素，返回一个扔掉了前 n 个元素的流。若流中元素不足 n 个，则返回一个空流。与 limit(n) 互补
     */

    @Test
    public void testStream1() {
        //filter过滤年龄大于12的
        studentList.stream()
                .filter(student -> student.getAge() > 12)
                .forEach(System.out::println);
        System.out.println("--------------");

        //distinct去重
        studentList.stream()
                .distinct()
                .forEach(System.out::println);
        System.out.println("--------------");

        //过滤之后取前2，有点类似mysql的limit了,
        // limit取值找到符合条件的就结束了 不会继续查找所有，下面的输出语句只会执行2次，因为前面两个就是满足条件的
        studentList.stream()
                .filter((student) -> {
                        System.out.println("执行过滤");
                        return student.getAge() >= 12;
                })
                .limit(2)
                .forEach(System.out::println);
        System.out.println("--------------");

        //skip跳过满足条件前两个
        studentList.stream()
                .filter(student ->  student.getAge() >= 12)
                .skip(2)
                .forEach(System.out::println);
    }

    /**
     * 映射
     * map(Function f) 接收一个函数作为参数，该函数会被应用到每个元素上，并将其映射成一个新的元素。
     * flatMap(Function f) 接收一个函数作为参数，将流中的每个值都换成另一个流，然后把所有流连接成一个流
     * 有点类似list的 add方法和addAll，一个添加单个元素到集合中，一个是将集合中元素逐一添加到另一个集合中
     */

    @Test
    public void testStream2() {

        //将map中的函数应用到每个元素上，生成一个新的元素，也就是收集每个student的名字组成一个新的元素
        studentList.stream()
                .distinct()
                .map(student -> student.getName())
                .forEach(System.out::println);
        System.out.println("--------");
        //调用creatStream方法将每个Student对象都转换成一个流，然后flatMap将每个流汇总成一个流
        studentList.stream()
                .distinct()
                .flatMap(StreamTest::creatStream)
                .forEach(System.out::println);
    }
    public static Stream<Student> creatStream(Student student){
        return Stream.of(student);
    }

    /**
     * 排序
     * sorted() 产生一个新流，其中按自然顺序排序
     * sorted(Comparator comp) 产生一个新流，其中按比较器顺序排序
     */

    //流结果获取
    /**
     * allMatch(Predicate p) 检查是否匹配所有元素
     * anyMatch(Predicate p) 检查是否至少匹配一个元素
     * noneMatch(Predicate p) 检查是否没有匹配所有元素
     * findFirst() 从流的操作中返回第一个元素(封装在Optional中)
     * findAny() 返回当前流中的任意元素(封装在Optional中)，并行流结果有随机性
     * count() 返回流中元素总数
     * max(Comparator c) 返回流中最大值 (封装在Optional中)
     * min(Comparator c) 返回流中最小值 (封装在Optional中)
     * forEach(Consumer c) 内部迭代
     */
    @Test
    public void testStream3() {
        //allMatch(Predicate p) 检查是否匹配所有元素 是否所有student age > 12
        boolean result = studentList.stream()
                .allMatch(student -> student.getAge() > 12);
        System.out.println(result);

        //anyMatch(Predicate p) 检查是否至少匹配一个元素
        result = studentList.stream()
                .anyMatch(student -> student.getAge() == 12);
        System.out.println(result);

        //noneMatch(Predicate p) 检查是否没有匹配所有元素
        result = studentList.stream()
                .noneMatch(student -> student.getAge() < 11);
        System.out.println(result);
        //findFirst() 从流的操作中返回第一个元素(封装在Optional中)
        //按score升序 之后取第一个
        Student student = studentList.stream()
                .sorted((student1, student2) -> Double.compare(student1.getScore(), student2.getScore()))
                .findFirst()
                .get();
        System.out.println(student);

        student = studentList.stream()
                .findAny()
                .get();
        System.out.println(student);
    }

    /**
     * 归约
     * reduce(T iden, BinaryOperator b) 可以将流中元素反复结合起来，得到一个值。返回 T
     * reduce(BinaryOperator b) 可以将流中元素反复结合起来，得到一个值。返回 Optional<T>
     * map 和 reduce 的连接通常称为 map-reduce 模式
     */
    @Test
    public void testStream4() {
        //累加，将流中元素进行求和
        List<Integer> list = Arrays.asList(1,2,3,4,5,6,7,8,9,10);
        Integer sum = list.stream()
                .reduce(0, (x, y) -> x + y);
        System.out.println(sum);

        //求student的总分
        Double allScore = studentList.stream()
                .map(student -> student.getScore())
                .reduce(Double::sum)
                .get();
        System.out.println(allScore);
    }

    /**
     * 收集
     * collect(Collector c) 将流转换为其他形式。接收一个 Collector接口（收集器）的实现，用于给Stream中元素做汇总的方法
     * Collector 接口中方法的实现决定了如何对流执行收集操作(如收集到 List、Set、Map)。
     * 1.8提供了Collectors 工具类可以方便地创建常见收集器实例
     *
     */

    @Test
    public void testStream5() {

        List<Student> studentList = Arrays.asList(
                new Student(1, "张三", 12, 69, Student.Clazz.Clazz2),
                new Student(2, "李四", 12, 78.5,Student.Clazz.Clazz2),
                new Student(3, "王五", 13, 95, Student.Clazz.Clazz3),
                new Student(4, "赵六", 14, 23, Student.Clazz.Clazz3),
                new Student(5, "孙七", 11, 55.5, Student.Clazz.Clazz1),
                new Student(5, "孙七", 11, 55.5, Student.Clazz.Clazz1)
        );

        //toList() 收集到List集合中
        List<String> nameList = studentList.stream()
                .map(student -> student.getName())
                .collect(Collectors.toList());
        System.out.println(nameList);
        //toSet() 收集到Set集合中 自动去重 默认返回的HashSet
        Set<String> nameSet= studentList.stream()
                .map(student -> student.getName())
                .collect(Collectors.toSet());
        System.out.println(nameSet);
        System.out.println(nameSet.getClass());
        //返回指定集合类型
        TreeSet<String> nameTreeSet= studentList.stream()
                .map(student -> student.getName())
                .collect(Collectors.toCollection(TreeSet::new));
        System.out.println(nameTreeSet.getClass());

        //分组groupingBy 返回map集合 类似sql group by了
        //根据年级分组
        Map<Student.Clazz, List<Student>> map = studentList.stream()
                .collect(Collectors.groupingBy(Student::getClazz));
        System.out.println(map);
        //多次分组
        //先按年级再按年龄
        Map<Student.Clazz, Map<Integer, List<Student>>> map2 = studentList.stream()
                .collect(Collectors.groupingBy(Student::getClazz, Collectors.groupingBy(Student::getAge)));
        System.out.println(map2);

        //分区partitioningBy 入参是个断言型接口
        studentList.stream()
                .collect(Collectors.partitioningBy((student) -> student.getScore() >= 60));

        //summarizingDouble 对某个值进行 数据统计输出
        //对score进行统计
        DoubleSummaryStatistics statistics = studentList.stream()
                .collect(Collectors.summarizingDouble(Student::getScore));
        statistics.getAverage();
        statistics.getCount();
        statistics.getMax();
        statistics.getMin();
        statistics.getSum();

    }


    //并行流parallel/sequential
    @Test
    public void TestParallel()
    {
        long start = System.currentTimeMillis();

        long sum = 0L;
        for (long i = 0L; i <= 10000000000L; i++) {
            sum += i;
        }
        System.out.println(sum);
        long end = System.currentTimeMillis();
        System.out.println("for耗费的时间为: " + (end - start));

        Long sum1 = LongStream.rangeClosed(0L, 10000000000L)
                .sum();
        System.out.println(sum1);
        long end1 = System.currentTimeMillis();
        System.out.println("串行流耗费的时间为: " + (end1 - end));

        Long sum2 = LongStream.rangeClosed(0L, 10000000000L)
                .parallel()
                .sum();
        System.out.println(sum2);
        long end2 = System.currentTimeMillis();
        System.out.println("并行流耗费的时间为: " + (end2 - end1));

    }



}
