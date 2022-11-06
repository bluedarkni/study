package com.nijunyang.redis;

import org.apache.tomcat.util.http.parser.Host;
import redis.clients.jedis.HostAndPort;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisCluster;
import redis.clients.jedis.JedisPoolConfig;

import java.util.HashSet;
import java.util.Set;

/**
 * @author: create by nijunyang
 * @date:2019/7/1
 */
public class TestJedis {

    public static void main(String[] args)
    {
        //redis安装在docker容器中，映射到宿主机的端口，host直接127.0.0.1即可
//        Jedis jedis = new Jedis("127.0.0.1",6379);
        Jedis jedis = new Jedis("111.229.53.45",6379);

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
        jedis.close();

        //jedis集群连接
        Set<HostAndPort> jedisClusterNode = new HashSet<HostAndPort>();
        jedisClusterNode.add(new HostAndPort("192.168.0.67", 6379));
        jedisClusterNode.add(new HostAndPort("192.168.0.68", 6379));
        JedisPoolConfig config = new JedisPoolConfig();
        JedisCluster jedisCluster = new JedisCluster(jedisClusterNode, 6000, 5000, 10, "zhuge", config);
        jedisCluster.set("abc", "123");

    }
}
