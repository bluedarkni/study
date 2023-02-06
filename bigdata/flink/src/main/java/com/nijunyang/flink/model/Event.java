package com.nijunyang.flink.model;

import com.alibaba.fastjson2.JSON;

import java.io.Serializable;
import java.time.Instant;

/**
 * Description: 事件
 * Created by nijunyang on 2022/12/15 9:08
 */
public class Event implements Serializable {

    private static final long serialVersionUID = -4617809545165023064L;


    /**
     * 事件触发者
     */
    public String trigger;

    /**
     * 事件名字
     */
    public String name;

    /**
     * 事件时间
     */
    public Instant timestamp;

    public Event() {
    }

    public Event(String trigger, String name, Instant timestamp) {
        this.trigger = trigger;
        this.name = name;
        this.timestamp = timestamp;
    }

    @Override
    public String toString() {
        return JSON.toJSONString(this);
    }


    public String getTrigger() {
        return trigger;
    }

    public void setTrigger(String trigger) {
        this.trigger = trigger;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Instant getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Instant timestamp) {
        this.timestamp = timestamp;
    }
}
