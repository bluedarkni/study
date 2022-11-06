package com.nijunyang.mysql.controller;

import com.nijunyang.mysql.dao.UserDao;
import com.nijunyang.mysql.enums.Month;
import com.nijunyang.mysql.enums.Week;
import com.nijunyang.mysql.model.User;
import com.nijunyang.mysql.myclass.MyList;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * Description:
 * Created by nijunyang on 2020/2/4 17:40
 */
@RestController
public class TestController {

    @Autowired
    UserDao userDao;

    @PostMapping("test/time")
    public ResponseEntity<User> test (@RequestBody User user) {
        System.out.println(user.getCreateTime());
        userDao.insert(user);
        return new ResponseEntity<>(user, HttpStatus.OK);
    }

    @GetMapping("test/add")
    public ResponseEntity<User> add() {
        User user = new User();
        MyList<Week> weekList = new MyList<>();
        weekList.add(Week.MONDAY);
        weekList.add(Week.SATURDAY);
        weekList.add(Week.SUNDAY);
        user.setCycle(weekList);

        MyList<Month> monthList = new MyList<>();
        monthList.add(Month.JUNE);
        monthList.add(Month.JULY);
        monthList.add(Month.MAY);
        user.setLikeMonth(monthList);
        userDao.insert(user);
        return new ResponseEntity<>(user, HttpStatus.OK);
    }

    @GetMapping("test/{id}")
    public ResponseEntity<User> add (@PathVariable Integer id) {
        User user = userDao.selectById(id);
        return ResponseEntity.ok(user);
    }



}
