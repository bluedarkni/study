package com.nijunyang.rabbitmq.ack;

import com.nijunyang.rabbitmq.util.RabbitMQUtils;
import com.rabbitmq.client.*;
import org.springframework.util.StringUtils;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

/**
 * Description:
 * Created by nijunyang on 2020/6/7 13:07
 */
public class AckConsumer {
    public static void main(String[] args) throws IOException, TimeoutException {
        Connection connection = RabbitMQUtils.getConnection();
        Channel channel = connection.createChannel();
        String exchangeName = "ack.exchange";
        String exchangeType = "direct";
        String routingKey = "ack.key";
        String queueName = "ack.queue";

        channel.exchangeDeclare(exchangeName, exchangeType);
        channel.queueDeclare(queueName,true,false,false,null);
        channel.queueBind(queueName, exchangeName, routingKey);


        Consumer consumer = new DefaultConsumer(channel) {

            @Override
            public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body) throws IOException {

                try {
                    Object userName = properties.getHeaders().get("userName");
                    if (!StringUtils.isEmpty(userName)) {
                        //用发送时候放的 头信息模拟业务问题
                        String message = new String(body, "UTF-8");
                        System.out.println(message);
                        //手动签收消息
                        channel.basicAck(envelope.getDeliveryTag(),false);
                    }
                    else {
                        throw new RuntimeException();
                    }
                }catch (Exception e) {
                    //requeue参数 true 重回队列，，false不重回队列, 或者做其他处理
                    channel.basicNack(envelope.getDeliveryTag(),false,false);
                }
            }
        };

        //autoAck参数 true：开启自动签收，false:关闭自动签收功能
        channel.basicConsume(queueName,false, consumer);


    }
}
