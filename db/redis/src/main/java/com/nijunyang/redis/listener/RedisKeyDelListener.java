package com.nijunyang.redis.listener;

import org.springframework.data.redis.connection.Message;
import org.springframework.data.redis.listener.RedisMessageListenerContainer;
import org.springframework.stereotype.Component;

/**
 * Description:
 * Created by nijunyang on 2020/4/17 10:42
 */
@Component
public class RedisKeyDelListener extends KeyDeletedEventMessageListener{


    public RedisKeyDelListener(RedisMessageListenerContainer listenerContainer) {
        super(listenerContainer);
    }

    @Override
    public void onMessage(Message message, byte[] pattern) {
        String key = message.toString();
        System.out.println(key);
    }
}
