package com.nijunyang.mongo.model;

import com.nijunyang.aspect.annotation.SensitiveInfo;
import lombok.Data;

/**
 * Description: 收货地址信息
 * Created by nijunyang on 2020/4/30 11:30
 */
@Data
public class ReceivingAddress {

    /**
     * 收货人
     */
    private String consignee;
    /**
     * 身份证
     */
    @SensitiveInfo
    private String idCardNo;
    /**
     * qq号
     */
    private String qqId;
    /**
     * 微信号
     */
    private String wechatId;
    /**
     * 国家
     */
    private String county;
    /**
     * 省区号
     */
    private String provinceCode;
    /**
     * 省
     */
    private String provinceName;
    /**
     * 市区号
     */
    private String cityCode;
    /**
     * 市
     */
    private String cityName;
    /**
     * 区、县区号
     */
    private String districtCode;
    /**
     * 区、县
     */
    private String districtName;
    /**
     * 详细地址
     */
    @SensitiveInfo
    private String detailAddress;
    /**
     * 手机号
     */
    @SensitiveInfo
    private String phoneNumber;

    /**
     * 邮箱
     */
    @SensitiveInfo
    private String email;
    /**
     * 邮编
     */
    private String postCode;
    /**
     * 备注
     */
    private String remark;


    private boolean defaultFlag;
}
