package com.nijunyang.mysql.controller;

import com.nijunyang.mysql.dao.UserDao;
import com.nijunyang.mysql.model.User;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * Description:
 * Created by nijunyang on 2020/2/4 17:40
 */
@RestController
@Tag(name = "测试时间")
public class Test {

    @Autowired
    UserDao userDao;

    @PostMapping("test/time")
    @Operation(summary = "测试")
    public void test (@RequestBody User user) {
        userDao.insert(user);
    }
}
