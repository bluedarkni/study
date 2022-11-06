package com.nijunyang;

import com.nijunyang.model.Student;

import java.util.Arrays;
import java.util.List;

/**
 * @author: create by nijunyang
 * @date:2019/8/4
 */
public class Test {
    public static void main(String[] args) {
        List<Student> employeeList = Arrays.asList(
                new Student(101, "张三", 18, 69),
                new Student(102, "李四", 59, 78.5),
                new Student(103, "王五", 28, 95),
                new Student(104, "赵六", 8, 23),
                new Student(105, "田七", 38, 55.5)
        );
        employeeList.stream()
                .filter((e) -> e.getScore() >= 60)
                .forEach(System.out::println);
        System.out.println("------------------");
        employeeList.stream()
                .filter((e) -> e.getScore() >= 20)
                .limit(3)
                .forEach(System.out::println);
        employeeList.stream()
                .map(Student::getName)
                .forEach(System.out::println);

        employeeList.stream()
                .map((x) -> x.getName())
                .forEach(System.out::println);

        employeeList.stream()
                .map(Student::getName)
                .forEach(System.out::println);

    }
}
