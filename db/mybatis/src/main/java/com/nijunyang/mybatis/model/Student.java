package com.nijunyang.mybatis.model;

import java.util.List;

/**
 * Description: 
 * Created by nijunyang on 2020/1/9 10:17
 */
public class Student {
    private int id;
    private String name;
    private List<Teacher> teachers;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Teacher> getTeachers() {
        return teachers;
    }

    public void setTeachers(List<Teacher> teachers) {
        this.teachers = teachers;
    }

    @Override public String toString() {
        return "Student{" + "id=" + id + ", name='" + name + '\'' + ", teachers=" + teachers + '}';
    }
}
