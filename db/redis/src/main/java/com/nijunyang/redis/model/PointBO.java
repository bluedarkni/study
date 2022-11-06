package com.nijunyang.redis.model;

import lombok.Data;

/**
 * Description:
 * Created by nijunyang on 2020/5/19 14:52
 */
@Data
public class PointBO  {

    private String mobilePhone;
    /**
     * 产品组织ID
     */
    private String prodOrgCode;


    private String unionId;
    /**
     * 积分值
     */
    private String pointNum;
    /**
     * 积分类型 Accrual（应记）或Redemption（偿还）
     */
    private String pointType;
    /**
     * 源系统该记录唯一ID
     */
    private String sourceId;
    /**
     * 系统来源
     */
    private String source;
    /**
     * 物料编码
     */
    private String prodCode;

    /**
     * 积分类别Grading (定级积分)、Exchange (兑换积分)
     */
    private String pointCategory;
}
