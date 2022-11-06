package com.nijunyang.rabbitmq.producer;

import com.nijunyang.rabbitmq.util.RabbitMQUtils;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;

import java.util.Random;

/**
 * Description:
 * Created by nijunyang on 2020/5/27 10:18
 */
public class RabbitMQProducer {
    public static void main(String[] args) throws Exception {

        Connection connection = RabbitMQUtils.getConnection();
        Channel channel = connection.createChannel();


        /*------------------*/
        //1：默认消息投递
//        //参数String exchange, String routingKey, AMQP.BasicProperties props, byte[] body
//        //不设置交换机会发送到默认上面
//        channel.basicPublish("", "hello.queue", null, message.getBytes("utf-8"));
        /*------------------*/
        //2：直连交换机
//        String exchangeName = "njy.directchange";
//        String routingKey = "directchange.key"; //发送和消费的要一模一样
        /*------------------*/
        //3.topic交换机
        String exchangeName = "njy.topicchange";
        String routingKey = "directchange.key"; //.分割，#匹配多个，*匹配一个

        for (int i = 0; i < 20; i++) {
            String message = "hello rabbitMQ." + i;
            channel.basicPublish(exchangeName, routingKey, null, message.getBytes("utf-8"));
        }
        /*------------------*/
        RabbitMQUtils.close(channel, connection);
    }
}
