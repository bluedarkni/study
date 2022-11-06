package com.nijunyang.mongo.model;

import com.nijunyang.aspect.annotation.SensitiveInfo;
import lombok.Data;
import org.springframework.data.mongodb.core.index.Indexed;

import java.time.Instant;

/**
 * Description: 用户基础信息
 * Created by nijunyang on 2020/4/30 13:47
 */
@Data
public class UserMetadata {

    private String nickname;

    private String avatarUrl;

    @SensitiveInfo
    private String realName;

    private Gender gender = Gender.UNKNOWN;

    @Indexed
    @SensitiveInfo
    private String phoneNumber;

    @SensitiveInfo
    @Indexed
    private String idCardNo;

    @SensitiveInfo
    private String birthday;

    @SensitiveInfo
    private String email;

    private Instant createdTime;

    private Instant updatedTime;

    private String remark;

    private String qqId;

    private String wechatId;

}
