package com.nijunyang.rabbitmq.consumer;

import com.nijunyang.rabbitmq.model.Order;
import com.rabbitmq.client.Channel;
import org.springframework.amqp.rabbit.annotation.*;
import org.springframework.amqp.rabbit.listener.MessageListenerContainer;
import org.springframework.amqp.rabbit.listener.RabbitListenerEndpointRegistry;
import org.springframework.amqp.rabbit.listener.SimpleMessageListenerContainer;
import org.springframework.amqp.support.AmqpHeaders;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.io.IOException;
import java.util.Map;

/**
 * @author: create by nijunyang
 * @date:2019/10/12
 */
@Component
public class OrderReceiver {

//    @Resource
//    RabbitListenerEndpointRegistry registry;

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
        System.out.println(order.getId());
        channel.basicAck((Long)headerMap.get(AmqpHeaders.DELIVERY_TAG), false);

    }

//    @Scheduled(cron = "0 31 14 * * ?")
//    public void startListener() {
//        //开启监听 SimpleMessageListenerContainer
//        MessageListenerContainer container = registry.getListenerContainer("order-exchange");
//        if (!container.isRunning()) {
//            container.start();
//        }
//        System.out.println("开启监听");
//    }
//
//    @Scheduled(cron = "0 32 14 * * ?")
//    public void shutdownListener() {
//        MessageListenerContainer container = registry.getListenerContainer("order-exchange");
//        container.stop();
//        System.out.println("停止监听");
//    }

}
