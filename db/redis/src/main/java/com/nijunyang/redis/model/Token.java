package com.nijunyang.redis.model;

import lombok.Data;

/**
 * Description:
 * Created by nijunyang on 2020/2/24 9:25
 */
@Data
public class Token {

    private String appId;
    private String openId;
    private Long eseId;
    private String sessionKey;
    private String userId;
    private Long memberId;
}
