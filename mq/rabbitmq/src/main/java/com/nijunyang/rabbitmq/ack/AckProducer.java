package com.nijunyang.rabbitmq.ack;

import com.nijunyang.rabbitmq.util.RabbitMQUtils;
import com.rabbitmq.client.AMQP;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import java.util.UUID;

/**
 * Description:
 * Created by nijunyang on 2020/6/7 13:07
 */
public class AckProducer {

    public static void main(String[] args) throws Exception {
        Connection connection = RabbitMQUtils.getConnection();
        Channel channel = connection.createChannel();
        String message = "hello rabbitMQ." + new Random().nextInt(100);

        String exchangeName = "ack.exchange";
        String routingKey = "ack.key";


        Map<String,Object> heads = new HashMap<>();
        heads.put("userName", "zhangsan");

        AMQP.BasicProperties basicProperties = new AMQP.BasicProperties().builder()
                .deliveryMode(2)//消息持久化
                .contentEncoding("UTF-8")
                .correlationId(UUID.randomUUID().toString())
                .headers(heads)//存放头信息
                .build();

        channel.basicPublish(exchangeName, routingKey, basicProperties, message.getBytes("utf-8"));
        channel.basicPublish(exchangeName, routingKey, null, message.getBytes("utf-8"));
        RabbitMQUtils.close(channel, connection);
    }
}
