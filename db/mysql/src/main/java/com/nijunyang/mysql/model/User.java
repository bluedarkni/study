package com.nijunyang.mysql.model;

import com.alibaba.fastjson.JSON;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.nijunyang.mysql.enums.Month;
import com.nijunyang.mysql.enums.Week;
import com.nijunyang.mysql.myclass.MyList;
import lombok.Data;

import java.time.Instant;

/**
 * @author: create by nijunyang
 * @date:2019/7/3
 */
@Data
public class User {

    private int id;
    private String userName;
    private String userCard;
    private int age;
    private String city;

    @JsonFormat(timezone = "GMT+8")
    private Instant createTime;

    private MyList<Week> cycle;

    private MyList<Month> likeMonth;


    public static void main(String[] args){
        User user = new User();
        user.userName = "zhangsan";
        user.userCard = "511129";
        user.age = 18;
        user.createTime = Instant.now();
//        long epochSecond = user.createTime.getEpochSecond();
//        System.out.println(epochSecond);
//        System.out.println(user.createTime.plusSeconds(60*60*24*2).getEpochSecond());
//        System.out.println(System.currentTimeMillis());

        String userStr = JSON.toJSONString(user);
        System.out.println(userStr);
        User user1 = JSON.parseObject(userStr, User.class);
        System.out.println(user1.getCreateTime());
//        System.out.println(JsonUtils.write2JsonString(user));
    }

}
