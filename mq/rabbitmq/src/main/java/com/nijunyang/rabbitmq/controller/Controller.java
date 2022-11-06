package com.nijunyang.rabbitmq.controller;

import com.nijunyang.rabbitmq.model.Order;
import com.nijunyang.rabbitmq.producer.OrderSender;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.time.Instant;
import java.util.UUID;

/**
 * Description:
 * Created by nijunyang on 2020/5/26 14:25
 */
@RestController
@RequestMapping
public class Controller {

    @Resource
    OrderSender orderSender;

    @GetMapping("/send-order")
    public ResponseEntity<Void> send() {
        Order order = new Order();
        order.setId(String.valueOf(Instant.now().toEpochMilli()));
        order.setName("order1");
        order.setMsgId(UUID.randomUUID().toString());
        orderSender.sendOrder(order);
        return ResponseEntity.ok().build();
    }
}
