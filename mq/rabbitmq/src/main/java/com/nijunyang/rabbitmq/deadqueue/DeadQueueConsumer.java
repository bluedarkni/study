package com.nijunyang.rabbitmq.deadqueue;

import com.nijunyang.rabbitmq.util.RabbitMQUtils;
import com.rabbitmq.client.*;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * Description:
 * Created by nijunyang on 2020/6/7 20:51
 */
public class DeadQueueConsumer {
    public static void main(String[] args) throws Exception{
        Connection connection = RabbitMQUtils.getConnection();
        Channel channel = connection.createChannel();

        //声明正常的队列
        String normalExchangeName = "normal.exchange";
        String exchangeType = "direct";
        String normalQueueName = "normal.queue";
        String routingKey = "normal.key";

        channel.exchangeDeclare(normalExchangeName, exchangeType);

        //申明死信队列
        String deadExchangeName = "dead.exchange";
        String deadExchangeType = "topic";
        String deadQueueName = "dead.queue";

        Map<String, Object> queueArgs = new HashMap<>();
        //正常队列上绑定死信队列信息
        queueArgs.put("x-dead-letter-exchange", deadExchangeName);
        queueArgs.put("x-max-length", 4); //队列的最大长度
        channel.queueDeclare(normalQueueName,true,false,false, queueArgs);
        channel.queueBind(normalQueueName, normalExchangeName, routingKey);

        //声明死信队列
        channel.exchangeDeclare(deadExchangeName, deadExchangeType);
        channel.queueDeclare(deadQueueName,true,false,false,null);
        channel.queueBind(deadQueueName, deadExchangeName,"#");

        Consumer consumer = new DefaultConsumer(channel) {
            @Override
            public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body) throws IOException {
                String message = new String(body, "UTF-8");
                System.out.println(message);
                channel.basicNack(envelope.getDeliveryTag(),false,false); //拒签
            }
        };
        channel.basicConsume(normalQueueName, false, consumer);

    }
}
