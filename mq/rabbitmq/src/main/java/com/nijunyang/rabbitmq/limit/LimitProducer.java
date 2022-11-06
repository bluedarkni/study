package com.nijunyang.rabbitmq.limit;

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
public class LimitProducer {

    public static void main(String[] args) throws Exception {
        Connection connection = RabbitMQUtils.getConnection();
        Channel channel = connection.createChannel();


        String exchangeName = "limit.exchange";
        String routingKey = "limit.key";


        for (int i = 0; i < 20; i++) {
            String message = "limit rabbitMQ." + i;
            channel.basicPublish(exchangeName, routingKey, null, message.getBytes("utf-8"));
        }

        RabbitMQUtils.close(channel, connection);
    }
}
