package com.nijunyang.mongo.model;

import lombok.Data;

/**
 * Description: 积分信息
 * Created by nijunyang on 2020/4/30 13:40
 */
@Data
public class PointDetail {

    /**
     * 积分类型
     */
    private Long pointType;
    /**
     * 过期的
     */
    private Integer overdueNumber;
    /**
     * 总收入
     */
    private Integer totalIncomeNumber;
    /**
     * 总的使用
     */
    private Integer totalConsumeNumber;
    /**
     * 可用数量
     */
    private Integer availableNumber;


}
