package com.nijunyang.redis.model.vo;

import com.nijunyang.redis.model.vo.PresentVo;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

/**
 * Description:
 * Created by nijunyang on 2020/2/21 17:13
 */
@Data
public class ShoppingCart {

    /**
     * 商品列表
     */
    private List<PresentVo> presentList = new ArrayList<>();
    /**
     * 购物车积分总价
     */
    private int pointTotalPrice;
    /**
     * 购物车redis key
     */
    private String key;
}
