package com.nijunyang.spring.model;

import org.springframework.beans.factory.annotation.Autowired;

/**
 * @author: create by nijunyang
 * @date:2019/10/6
 */

public class Student {

    @Autowired
    private Teacher teacher;
}
