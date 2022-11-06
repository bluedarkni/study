package com.nijunyang.kafka.model;

import lombok.Getter;
import lombok.Setter;

/**
 * Description:
 * Created by nijunyang on 2019/12/7 22:25
 */
@Getter
@Setter
public class User {

    private String name;
    private int age;

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
}
