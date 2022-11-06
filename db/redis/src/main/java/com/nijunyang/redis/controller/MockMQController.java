package com.nijunyang.redis.controller;

import com.nijunyang.redis.model.PointBO;
import lombok.Data;
import org.springframework.boot.logging.LogLevel;
import org.springframework.data.redis.core.ListOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.data.redis.core.script.RedisScript;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.io.Serializable;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

/**
 * Description:
 * Created by nijunyang on 2020/5/19 13:58
 */
@RestController
@RequestMapping("mock-mq")
public class MockMQController {

    @Resource
    RedisTemplate redisTemplate;

    public static final String SYNC_POINT_KEY = "lz-sync-point";

    public static final String SYNC_POINT_LOCK_NOT_BLOCK_KEY = "lz-sync-point-lock";


    @GetMapping("/add")
    public void add() {
        PointBO pointBO = new PointBO();
        pointBO.setMobilePhone(UUID.randomUUID().toString());
        redisTemplate.opsForList().leftPush(SYNC_POINT_KEY, pointBO);
    }

    @GetMapping("/get")
    public ResponseEntity get() {
        String luaScript = "local result = redis.call('lrange', KEYS[1], ARGV[1], ARGV[2]);" +
                "redis.call('del', KEYS[1]);" +
                "return result";
        RedisScript<List<PointBO>> redisScript = RedisScript.of(luaScript, List.class);
        List<PointBO> pointBOList =
                (List<PointBO>) redisTemplate.execute(redisScript, Collections.singletonList(SYNC_POINT_KEY), 0, 10);



        ListOperations<String, Map> listOperations = redisTemplate.opsForList();
        Long size = listOperations.size("key");
        //lua脚本保证查询和清理key的原子性
        String luaScriptStr = "local result = redis.call('lrange', KEYS[1], ARGV[1], ARGV[2]);" +
                "redis.call('del', KEYS[1]);" +
                "return result";
        RedisScript<List<Map>> redisLuaScript = RedisScript.of(luaScriptStr, List.class);

        List<Map> requestParamList = (List<Map>) redisTemplate.execute(
                redisLuaScript, Collections.singletonList("key"), 0, size);

        return ResponseEntity.ok(pointBOList);
    }



    //定时消费
    @Scheduled(cron = "0 6 15 * * ?")
    public void consumer() {
        ValueOperations valueOperations = redisTemplate.opsForValue();
        if (valueOperations.setIfAbsent(
                SYNC_POINT_LOCK_NOT_BLOCK_KEY,
                Thread.currentThread().getName(),
                10000L,
                TimeUnit.MILLISECONDS)) {

            ListOperations listOperations = redisTemplate.opsForList();
            Long size = listOperations.size(SYNC_POINT_KEY);

            List<PointBO> pointBOList = listOperations.range(SYNC_POINT_KEY, 0, size);
            redisTemplate.delete(SYNC_POINT_KEY);
            System.out.println(pointBOList);
        }
        else {
            System.out.println("没有操作");
        }
    }

}
