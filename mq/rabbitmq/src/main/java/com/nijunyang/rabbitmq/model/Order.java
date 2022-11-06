package com.nijunyang.rabbitmq.model;

import java.io.Serializable;

/**
 * @author: create by nijunyang
 * @date:2019/10/12
 */
public class Order implements Serializable{
    private static final long serialVersionUID = 3075748697242255388L;
    private String id;
    private String name;
    private String msgId;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getMsgId() {
        return msgId;
    }

    public void setMsgId(String msgId) {
        this.msgId = msgId;
    }
}
