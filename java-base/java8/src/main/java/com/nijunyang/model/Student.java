package com.nijunyang.model;

/**
 * @author: create by nijunyang
 * @date:2019/8/10
 */
public class Student {
    private int id;
    private String name;
    private int age;
    private double score;
    private Clazz clazz;

    public Student(String name) {
        this.name = name;
    }

    public Student(int id, String name, int age, double score) {
        this.id = id;
        this.name = name;
        this.age = age;
        this.score = score;
    }

    public Student(int id, String name, int age, double score, Clazz clazz) {
        this.id = id;
        this.name = name;
        this.age = age;
        this.score = score;
        this.clazz = clazz;
    }

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

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public double getScore() {
        return score;
    }

    public void setScore(double score) {
        this.score = score;
    }

    public Clazz getClazz() {
        return clazz;
    }

    public void setClazz(Clazz clazz) {
        this.clazz = clazz;
    }

    public String show() {
        return "测试方法引用";
    }

    @Override
    public int hashCode() {
        final int prime = 11;
        int hashCode = 1;
        hashCode = prime * hashCode + age;
        hashCode = prime * hashCode + id;
        hashCode = prime * hashCode + ((name == null) ? 0 : name.hashCode());
        long temp;
        temp = Double.doubleToLongBits(score);
        hashCode = prime * hashCode + (int) (temp ^ (temp >>> 32));
        return hashCode;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }

        if (getClass() != obj.getClass()) {
            return false;
        }
        Student other = (Student) obj;
        if (age != other.age) {
            return false;
        }

        if (id != other.id) {
            return false;
        }
        if (name == null) {
            if (other.name != null) {
                return false;
            }
        } else if (!name.equals(other.name)) {
            return false;
        }
        if (Double.doubleToLongBits(score) != Double.doubleToLongBits(other.score)) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "[id=" + id + ", name=" + name + ", age=" + age + ", score=" + score + ", Class=" + clazz + "]";
    }

    public static enum Clazz{
        Clazz1,
        Clazz2,
        Clazz3,
    }
}
