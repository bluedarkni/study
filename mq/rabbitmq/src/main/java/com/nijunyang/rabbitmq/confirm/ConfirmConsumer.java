package com.nijunyang.rabbitmq.confirm;

import com.nijunyang.rabbitmq.util.RabbitMQUtils;
import com.rabbitmq.client.*;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

/**
 * Description:
 * Created by nijunyang on 2020/6/7 19:51
 */
public class ConfirmConsumer {
    public static void main(String[] args) throws IOException, TimeoutException {
        Connection connection = RabbitMQUtils.getConnection();
        Channel channel = connection.createChannel();
        String exchangeName = "confirm.exchange";
        String exchangeType = "direct";
        String routingKey = "confirm.key";
        String queueName = "confirm.queue";

        channel.exchangeDeclare(exchangeName, exchangeType);
        channel.queueDeclare(queueName,true,false,false,null);
        channel.queueBind(queueName, exchangeName, routingKey);

        com.rabbitmq.client.Consumer consumer = new DefaultConsumer(channel) {
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
