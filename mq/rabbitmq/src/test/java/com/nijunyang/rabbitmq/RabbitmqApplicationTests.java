package com.nijunyang.rabbitmq;

import com.nijunyang.rabbitmq.model.Order;
import com.nijunyang.rabbitmq.producer.OrderSender;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.UUID;

@RunWith(SpringRunner.class)
@SpringBootTest
public class RabbitmqApplicationTests {

	@Autowired
	OrderSender sender;

	@Test
	public void send1() {
		Order order = new Order();
		order.setId("20191012001");
		order.setName("order1");
		order.setMsgId(UUID.randomUUID().toString());
		sender.sendOrder(order);
	}

	@Test
	public void contextLoads() {
	}


}
