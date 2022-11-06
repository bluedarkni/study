package com.nijunyang.rabbitmq.producer;

import com.nijunyang.rabbitmq.model.Order;
import org.springframework.amqp.rabbit.connection.CorrelationData;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @author: create by nijunyang
 * @date:2019/10/12
 */
@Component
public class OrderSender {

    @Autowired
    private RabbitTemplate rabbitTemplate;

    public void sendOrder(Order order){
        CorrelationData correlationData = new CorrelationData();
        correlationData.setId(order.getId());
        rabbitTemplate.convertAndSend(
                "order-exchange",
                "order.abcd",
                order,
                correlationData);
    }
}
