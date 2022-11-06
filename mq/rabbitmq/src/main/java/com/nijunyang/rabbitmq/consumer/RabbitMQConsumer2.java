package com.nijunyang.rabbitmq.consumer;

import com.nijunyang.rabbitmq.util.RabbitMQUtils;
import com.rabbitmq.client.*;

import java.io.IOException;

/**
 * Description:
 * Created by nijunyang on 2020/6/7 23:45
 */
public class RabbitMQConsumer2 {
    public static void main(String[] args) throws Exception {
        Connection connection = RabbitMQUtils.getConnection();
        Channel channel = connection.createChannel();
        /*------------------*/
        //1：默认消息投递 消费
//        String queueName = "hello.queue";
//        //声明一个队列（如果没有这个队列，第一次是在消费者这边在声明的，所以要先启动消费者，生产者消息才有地方投递）
//        channel.queueDeclare(queueName,true,false,true,null);
        /*------------------*/
        //2：直连交换机
//        String exchangeName = "njy.directchange";
////        String exchangeType = "direct";
////
////        String routingKey = "directchange.key";//发送和消费的要一模一样
////        String queueName = "direct.queue";
////        //声明一个交换机和队列
////        channel.exchangeDeclare(exchangeName, exchangeType,true,false,null);
////        channel.queueDeclare(queueName,true,false,false,null);
////        //队列和交换机通过routingKey绑定
////        channel.queueBind(queueName, exchangeName, routingKey);
////        /*------------------*/
        //3：topic交换机
        String exchangeName = "njy.topicchange";
        String routingKey = "directchange.#"; //.分割，#匹配多个，*匹配一个
        String exchangeType = "topic";
        String queueName = "topic.queue";
        //声明一个交换机和队列
        channel.exchangeDeclare(exchangeName, exchangeType,true,false,null);
        channel.queueDeclare(queueName,true,false,false,null);
        //队列和交换机通过routingKey绑定
        channel.queueBind(queueName, exchangeName, routingKey);
        /*------------------*/
        //创建消费者
        Consumer consumer = new DefaultConsumer(channel) {
            //回调
            @Override
            public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body) throws IOException {
                String message = new String(body, "UTF-8");
                System.out.println(message);
            }
        };
        channel.basicConsume(queueName,true, consumer);
    }
}
