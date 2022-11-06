package com.nijunyang.autoconfig.model;

/**
 * @author: create by nijunyang
 * @date:2019/7/15
 */
public class User {
    private String userName;
    private Integer age;
    private Double height;

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public Double getHeight() {
        return height;
    }

    public void setHeight(Double height) {
        this.height = height;
    }
}
