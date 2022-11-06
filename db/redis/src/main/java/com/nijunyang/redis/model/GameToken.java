package com.nijunyang.redis.model;

import lombok.Data;

/**
 * Description: 早餐奶游戏认证信息
 * Created by nijunyang on 2020/6/9 18:50
 */
@Data
public class GameToken {

    private String gameToken;
    /**
     * 秒
     */
    private long createTime;
    /**
     * 使用次数
     */
    private int useTime;

    public GameToken() {
    }

    public GameToken(String gameToken, long createTime) {
        this.gameToken = gameToken;
        this.createTime = createTime;
    }
}
