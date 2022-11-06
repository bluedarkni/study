package com.nijunyang.rabbitmqconsumer.consumer;

import com.nijunyang.rabbitmq.model.Order;
import com.rabbitmq.client.Channel;
import org.springframework.amqp.rabbit.annotation.*;
import org.springframework.amqp.support.AmqpHeaders;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.Map;

/**
 * @author: create by nijunyang
 * @date:2019/10/12
 */
@Component
public class OrderReceiver {

    @RabbitHandler
    @RabbitListener(bindings = @QueueBinding(
            value = @Queue(value = "order-queues", durable = "true"),
            exchange = @Exchange(name = "order-exchange", durable = "true", type = "topic"),
            key = "order.#"
            )
    )
    public void getMsg(
            @Payload Order order,
            @Header Map<String, Object> headerMap,
            Channel channel) throws IOException {
        System.out.println(" ，开始消费");
        channel.basicAck((Long)headerMap.get(AmqpHeaders.DELIVERY_TAG), false);

    }
}
