package com.nijunyang.mongo.model;

import lombok.Data;
import org.springframework.data.annotation.Version;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Field;

import java.util.List;
import java.util.Map;

/**
 * Description:
 * Created by nijunyang on 2020/4/30 11:27
 */
@Data
public class User {

    @Indexed
    @Field("_id")
    private String id;

    @Version
    @Field("version")
    private int version;

    UserMetadata userMetadata;

    /**
     * 收货地址信息
     */
    private List<ReceivingAddress> receivingAddress;

    private Map<Long, PointDetail> pointDetailMap;
}
