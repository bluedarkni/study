package com.nijunyang.rabbitmq.deadqueue;

import com.nijunyang.rabbitmq.util.RabbitMQUtils;
import com.rabbitmq.client.AMQP;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.ConfirmListener;
import com.rabbitmq.client.Connection;

import java.io.IOException;

/**
 * Description:
 * Created by nijunyang on 2020/6/7 20:48
 */
public class DeadQueueProducer {
    public static void main(String[] args) throws Exception {
        Connection connection = RabbitMQUtils.getConnection();
        Channel channel = connection.createChannel();

        String exchangeName = "normal.exchange";
        String routingKey = "normal.key";

        //设置消息的过期时间10s
        AMQP.BasicProperties basicProperties = new AMQP.BasicProperties().builder()
                .deliveryMode(2)
                .expiration("10000")
                .build();
        for (int i = 0; i < 20; i++) {
            String message = "dead rabbitMQ." + i;
            channel.basicPublish(exchangeName, routingKey, basicProperties, message.getBytes("utf-8"));
        }
        RabbitMQUtils.close(channel, connection);
    }
}
