package com.nijunyang.mybatis.mapper;

import com.nijunyang.mybatis.model.Student;

import java.util.List;

/**
 * Description: 
 * Created by nijunyang on 2020/1/9 10:28
 */
public interface StudentMapper {

    List<Student> findStudentWithTeacher(Integer tid);

    void addStudent(Student student);
}
