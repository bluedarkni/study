package com.nijunyang.mybatis.model;

/**
 * @author: create by nijunyang
 * @date:2019/7/3
 */

public class User {

    private String userName;
    private String userCard;
    private int age;
    private String city;

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserCard() {
        return userCard;
    }

    public void setUserCard(String userCard) {
        this.userCard = userCard;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }
}
