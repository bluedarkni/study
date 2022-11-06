package com.nijunyang.redis;

import org.junit.Before;
import org.junit.Test;
import redis.clients.jedis.Jedis;

/**
 * @author: create by nijunyang
 * @date:2019/7/2
 */
public class JedisTest {
    Jedis jedis;

    @Before
    public void init() throws Exception{
        jedis = new Jedis("192.168.0.66",6379);
    }

    @Test
    public void keyOperate() throws Exception{
        //set 字符串
        jedis.set("name", "xiaohuaasdasd1212121");
        jedis.set("stu", "xiaohuaasdasd1212121");
        //获取字符串
        String value = jedis.get("name");
        System.out.println(value);
        //获取匹配的所有key
        System.out.println(jedis.keys("*"));
        //删除key
        jedis.del("name");
        System.out.println(jedis.keys("*"));
        //判断key是否存在
        jedis.exists("name");
        //查看key对应的value类型
        System.out.println(jedis.type("stu"));


        //清空所有的数据
//        jedis.flushDB();
//        jedis.close();
    }

    /***
     * 超时时间 秒
     * 返回 -2 key不存在
     * 返回 -1 key未设置超时
     */
    @Test
    public void testTtl() throws InterruptedException {
        String key1 = "city1";
        String key2 = "city2";
        String key3 = "city3";
        long ttl1 = jedis.ttl(key1);
        System.out.println("倒计时时间 :" + ttl1);
        jedis.set(key2,"chengdu");
        long ttl2 = jedis.ttl(key2);
        System.out.println("倒计时时间 :" + ttl2);
        jedis.set(key3,"leshan");
        //设置超时时间50s
        jedis.expire(key3,50);

        //获取超时时间 秒
        long ttl3 = jedis.ttl(key3);
        //毫秒
        long pttl3 = jedis.pttl(key3);

        System.out.println("倒计时时间 :" + ttl3);
        System.out.println("倒计时时间 :" + pttl3);
        Thread.sleep(5000);
        ttl3 = jedis.ttl(key3);
        pttl3 = jedis.pttl(key3);
        System.out.println("倒计时时间 :" + ttl3);
        System.out.println("倒计时时间 :" + pttl3);
    }


}
