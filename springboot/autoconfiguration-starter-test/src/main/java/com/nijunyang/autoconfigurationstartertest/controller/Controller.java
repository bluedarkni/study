package com.nijunyang.autoconfigurationstartertest.controller;



import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author: create by nijunyang
 * @date:2019/7/15
 */
@RestController
public class Controller {

//    @Autowired
//    User user;
//
//    @GetMapping("/test")
//    public User getUser()
//    {
//        return user;
//    }

    @GetMapping("/test")
    public String getUser()
    {
        return "123";
    }
}
