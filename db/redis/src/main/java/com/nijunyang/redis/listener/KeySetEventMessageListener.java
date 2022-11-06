package com.nijunyang.redis.listener;

import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.ApplicationEventPublisherAware;
import org.springframework.data.redis.connection.Message;
import org.springframework.data.redis.core.RedisKeyExpiredEvent;
import org.springframework.data.redis.listener.KeyspaceEventMessageListener;
import org.springframework.data.redis.listener.PatternTopic;
import org.springframework.data.redis.listener.RedisMessageListenerContainer;
import org.springframework.data.redis.listener.Topic;
import org.springframework.lang.Nullable;

/**
 * Description:
 * Created by nijunyang on 2020/4/16 16:24
 */
public class KeySetEventMessageListener extends KeyspaceEventMessageListener implements ApplicationEventPublisherAware {

    private static final Topic KEY_EVENT_SET_TOPIC = new PatternTopic("__keyevent@*__:set");
    @Nullable
    private ApplicationEventPublisher publisher;

    public KeySetEventMessageListener(RedisMessageListenerContainer listenerContainer) {
        super(listenerContainer);
    }

    protected void doRegister(RedisMessageListenerContainer listenerContainer) {
        listenerContainer.addMessageListener(this, KEY_EVENT_SET_TOPIC);
    }

    protected void doHandleMessage(Message message) {
        this.publishEvent(new RedisKeyExpiredEvent(message.getBody()));
    }

    protected void publishEvent(RedisKeyExpiredEvent event) {
        if (this.publisher != null) {
            this.publisher.publishEvent(event);
        }
    }

    public void setApplicationEventPublisher(ApplicationEventPublisher applicationEventPublisher) {
        this.publisher = applicationEventPublisher;
    }


}
