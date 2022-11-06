package com.nijunyang.rabbitmq.confirm;

import com.nijunyang.rabbitmq.util.RabbitMQUtils;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.ConfirmListener;
import com.rabbitmq.client.Connection;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

/**
 * Description:
 * Created by nijunyang on 2020/6/7 19:53
 */
public class ConfirmProducer {
    public static void main(String[] args) throws Exception {
        Connection connection = RabbitMQUtils.getConnection();
        Channel channel = connection.createChannel();

        //设置消息投递模式(确认模式)
        channel.confirmSelect();
        /**
         * 消息确认监听绑定
         */
        channel.addConfirmListener(new ConfirmListener() {
            @Override
            public void handleAck(long deliveryTag, boolean multiple) throws IOException {
                System.out.println("消息投递成功");
            }

            @Override
            public void handleNack(long deliveryTag, boolean multiple) throws IOException {
                System.out.println("消息投递失败");
            }
        });

        String exchangeName = "confirm.exchange";
        String routingKey = "confirm.key";

        for (int i = 0; i < 20; i++) {
            String message = "limit rabbitMQ." + i;
            channel.basicPublish(exchangeName, routingKey, null, message.getBytes("utf-8"));
            channel.basicPublish(exchangeName,routingKey,true,null, message.getBytes());
        }

        //设置了消息投递确认就不能关闭channel和连接了
//        RabbitMQUtils.close(channel, connection);
    }
}
