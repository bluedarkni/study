package com.nijunyang.rabbitmq.limit;

import com.nijunyang.rabbitmq.util.RabbitMQUtils;
import com.rabbitmq.client.*;
import org.springframework.util.StringUtils;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

/**
 * Description:
 * Created by nijunyang on 2020/6/7 13:07
 */
public class LimitConsumer {
    public static void main(String[] args) throws IOException, TimeoutException {
        Connection connection = RabbitMQUtils.getConnection();
        Channel channel = connection.createChannel();
        String exchangeName = "limit.exchange";
        String exchangeType = "direct";
        String routingKey = "limit.key";
        String queueName = "limit.queue";

        channel.exchangeDeclare(exchangeName, exchangeType);
        channel.queueDeclare(queueName,true,false,false,null);
        channel.queueBind(queueName, exchangeName, routingKey);

        /**
         * 限流设置:
         * prefetchSize：每条消息大小的设置
         * prefetchCount:标识每次推送多少条消息
         * global:false标识channel级别的  true:标识消费的级别的
         */
        channel.basicQos(0,5,false);

        Consumer consumer = new DefaultConsumer(channel) {
            @Override
            public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body) throws IOException {
                String message = new String(body, "UTF-8");
                System.out.println(message);
                //手动签收消息, 否则就会一直阻塞了
                channel.basicAck(envelope.getDeliveryTag(), false);

            }
        };

        //autoAck参数 true：开启自动签收，false:关闭自动签收功能
        channel.basicConsume(queueName,false, consumer);


    }
}
