package com.nijunyang.lambda;

import com.nijunyang.model.Student;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Supplier;

/**
 * @author: create by nijunyang
 * @date:2019/8/10
 */
public class LambdaTest {

    @Test
    public void testLambda() {
        //匿名内部类
        Runnable runnable1 = new Runnable() {
            @Override
            public void run() {
                System.out.println("匿名内部类");
            }
        };
        //lambda表达式
        Runnable runnable2 = () -> System.out.println("lambda");
    }

    /**
     * Lambda 表达式需要 函数式接口(接口中只有一个抽象方法的接口，称为函数式接口) 的支持,可以使用注解 @FunctionalInterface 修饰
     * 语法格式：
     * 只需要一个参数，可以省略小括号
     * Lambda体只有一条语句可以省略{}
     * 参数类型可以省略，编译器可以通过上下文推断参数类型
     */
    @Test
    public void testLambdaType() {
        //无参无返回
        Runnable runnable3 = () -> System.out.println("lambda");

        //有参无返回
        Consumer<String> consumer = (args) -> System.out.println(args);
        Consumer<String> consumer2 = args -> System.out.println(args);

        //有参有返回值
        Function<Integer, Integer> function = x -> x + 1;
        Function<Integer, Integer> function1 = (Integer x) -> x + 1;
        //lambda体内正常逻辑操作
        Function<Integer, Integer> function3 = (Integer x) -> {
            if (x < 100) {
                return x;
            }
            return x + 1;
        };
    }

    /**
     * 1.8内置函数式接口（java.util.function包下面）
     * 主要分为四种类型（主要就是对应几种方法类型，有参无返回，无参有返回，有参有返回）
     * Consumer<T> : 消费型接口
     * 		void accept(T t);
     *
     * Supplier<T> : 供应型接口
     * 		T get();
     *
     * Function<T, R> : 功能型接口
     * 		R apply(T t);
     *
     * Predicate<T> : 断言型接口
     * 		boolean test(T t);
     */


    /**
     * 方法引用:若 Lambda 体中的功能，已经有方法提供了实现，可以使用方法引用
     * <p>
     * 1. 对象实例::实例方法名
     * <p>
     * 2. 类名::静态方法名
     * <p>
     * 3. 类名::实例方法名
     * (Lambda 的参数列表的第一个参数，是实例方法的调用者，第二个参数(或无参)是实例方法的参数时，格式：ClassName::MethodName)
     */

    @Test
    public void testMethodRef() {
        Student student = new Student(101, "张三", 18, 95);

        Supplier<String> sup = () -> student.getName();
        System.out.println(sup.get());
        //对象实例::实例方法名 同上
        sup = student::getName;
        System.out.println(sup.get());

        Comparator<Integer> com = (x, y) -> Integer.compare(x, y);
        //类名::静态方法名 同上
        Comparator<Integer> com1 = Integer::compare;

        //类名::实例方法名 第一个参数是Student实例 lambda体调用Student的show方法ClassName::MethodName
        Function<Student, String> fun = Student::show;
        System.out.println(fun.apply(student));
        List<Integer> list = new ArrayList<>();
        list.sort((x, y) -> x.compareTo(y));
        list.sort(Integer::compare);
    }

    /**
     * 构造器引用 :构造器的参数列表，需要与函数式接口中参数列表保持一致！
     * 类名::new
     * <p>
     * 数组引用
     * 类型[]::new
     */
    @Test
    public void testConstructorRef() {
        //构造器引用
        Function<String, Student> fun1 = Student::new;

        Function<Integer, String[]> fun2 = (args) -> new String[args];
        String[] strs = fun2.apply(2);
        System.out.println(strs.length);
        //数组引用 效果同上
        Function<Integer, Student[]> fun3 = Student[]::new;
        Student[] emps = fun3.apply(20);
        System.out.println(emps.length);
    }
}
