package com.nijunyang.mongo.test;

import com.alibaba.fastjson.JSON;
import com.nijunyang.mongo.model.Gender;
import com.nijunyang.mongo.model.UserMetadata;

/**
 * Description:
 * Created by nijunyang on 2020/4/30 14:45
 */
public class Test {

    public static void main(String[] args){
        UserMetadata userMetadata = new UserMetadata();
        userMetadata.setIdCardNo("511129199312263324");
        userMetadata.setNickname("哪儿来哪儿去");
        userMetadata.setPhoneNumber("13888833213");
        userMetadata.setRealName("王屋太行");
        userMetadata.setGender(Gender.MAN);

        System.out.println(JSON.toJSONString(userMetadata));
    }
}
