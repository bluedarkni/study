package com.nijunyang.kafka.job;

import com.nijunyang.kafka.model.User;
import com.nijunyang.util.JsonUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.kafka.ConcurrentKafkaListenerContainerFactoryConfigurer;
import org.springframework.context.annotation.Bean;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.config.KafkaListenerEndpointRegistry;
import org.springframework.kafka.core.ConsumerFactory;
import org.springframework.kafka.listener.MessageListenerContainer;
import org.springframework.kafka.support.Acknowledgment;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.IOException;
import java.util.List;

/**
 * Description:
 * Created by nijunyang on 2019/12/6 20:50
 */
@Component
@Slf4j
public class KafkaJob {

    @Autowired
    private KafkaListenerEndpointRegistry registry;

    @KafkaListener(id = "test-topic", topics = {"test-topic"}, groupId = "njy", containerFactory = "kafkaListenerContainerFactory")
    public void luzhouSyncPoint(List<String> consumerRecordList, Acknowledgment ack) throws IOException {
        System.out.println("消费消息");
        for (String str : consumerRecordList) {
            User user = JsonUtils.readJson2Entity(str, User.class);
            System.out.println(user.getAge());
        }
        ack.acknowledge();
    }

    @Scheduled(cron = "0 8 12 * * ?")
    public void startListener() {
        //开启监听
        MessageListenerContainer container = registry.getListenerContainer("test-topic");
        if (!container.isRunning()) {
            container.start();
        }
        container.resume();
        System.out.println("开启监听");
    }

    @Scheduled(cron = "0 12 12 * * ?")
    public void shutdownListener() {
        MessageListenerContainer container = registry.getListenerContainer("test-topic");
        container.pause();
        System.out.println("停止监听");
    }

//    @Bean(name = "pointFactory")
    @Bean
    public ConcurrentKafkaListenerContainerFactory<?, ?> kafkaListenerContainerFactory(
            ConcurrentKafkaListenerContainerFactoryConfigurer configurer,
            ConsumerFactory consumerFactory) {
        ConcurrentKafkaListenerContainerFactory<Object, Object> factory = new ConcurrentKafkaListenerContainerFactory<>();
        factory.setConsumerFactory(consumerFactory);
        //开启批量消费功能
        factory.setBatchListener(true);
        factory.setAutoStartup(false);
        configurer.configure(factory, consumerFactory);
        return factory;
    }
}
