package com.nijunyang.redis.controller;

import com.nijunyang.algorithm.redpackage.RedPackageUtils;
import com.nijunyang.redis.model.User;
import com.nijunyang.redis.service.RedisService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.ListOperations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * Description:
 * Created by nijunyang on 2019/12/11 13:57
 */
@RestController
@RequestMapping("/redis")
public class RedisController {

    private static final String SHARE_RED_PACKAGE_KEY = "shareRedPackage";

    @Autowired
    RedisService redisService;

    @Autowired
    HashOperations<String, String, Object> hashOperations;

    @Autowired
    ListOperations<String, Object> listOperations;


    @GetMapping("/count")
    public ResponseEntity<Long> count(){
        Long count = redisService.getCount();
        return new ResponseEntity<>(count, HttpStatus.OK);
    }

    @GetMapping("/addHash")
    public ResponseEntity<User> addHash(){
        User user = new User();
        user.setId(1);
        user.setName("张三");
        user.setAge(17);
        //第一个参数redisKey,第二个参数hashKey值(相当于Map的key),第三个参数data(相当于Map的value)
        //用对象的字段生成hashCode做key，如果直接用hashCode，可能会出现不然一样的数据不同的序列化方式还原的对象hash值不一样
        hashOperations.put("hash:user", user.createHashCode(), user);
        //第一个参数redisKey,第二个参数hashKey值(相当于Map的key),返回的value
        Object o = hashOperations.get("hash:user", user.createHashCode());
        redisService.expireKey("hash:user", 100, TimeUnit.SECONDS);
        System.out.println(o);
        return new ResponseEntity<>((User)o, HttpStatus.OK);
    }

    @GetMapping("/addList/{value}")
    public ResponseEntity<Long> addList1(@PathVariable String value){
        Long length = listOperations.leftPush("list", value);
        return new ResponseEntity<>(length, HttpStatus.OK);
    }

    @GetMapping("/addList/pushall/{number}")
    public ResponseEntity<Long> addList2(@PathVariable Integer number){
        String[] redPackages = new String[number];
        Arrays.fill(redPackages, "asc");
        Long length = listOperations.leftPushAll("pushall", redPackages);
        return new ResponseEntity<>(length, HttpStatus.OK);
    }

    @GetMapping("/add/redpackage/{money}/{number}")
    public ResponseEntity<Long> addRedPackage(@PathVariable Integer money, @PathVariable Integer number){

        List<BigDecimal> redPackageList = RedPackageUtils.shareMoney(BigDecimal.valueOf(money), number);
        /**
         * leftPushAll(K var1, Collection<V> var2)  以一个集合形式存放 并不是单个元素存放
         * leftPushAll(K var1, V... var2)  数组长度过大无法添加，会报IO异常，测试了下长度100万可以110万长度就会报错了
         */
        String[] redPackages = new String[redPackageList.size()];
        for (int i = 0; i < redPackages.length; i++) {
            redPackages[i] = redPackageList.get(i).toString();
        }
        Long length = listOperations.leftPushAll(SHARE_RED_PACKAGE_KEY, redPackages);
        return new ResponseEntity<>(length, HttpStatus.OK);
    }

    @GetMapping("/share/redpackage")
    public ResponseEntity<Object> share(){
        Object money = listOperations.leftPop(SHARE_RED_PACKAGE_KEY);
        if (money == null) {
            return new ResponseEntity<>("红包已瓜分完毕", HttpStatus.OK);
        }
        return new ResponseEntity<>(money, HttpStatus.OK);
    }




}
