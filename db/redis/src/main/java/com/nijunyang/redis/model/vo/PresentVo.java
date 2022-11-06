package com.nijunyang.redis.model.vo;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;
import java.io.Serializable;

/**
 * Description:
 * Created by nijunyang on 2020/2/21 16:15
 */
@Getter
@Setter
public class PresentVo implements Serializable {
    private static final long serialVersionUID = 9201231051606619986L;

    @NotNull
    private Long presentId;
    @NotNull
    private String name;
    @NotNull
    private int pointUnitPrice;
    @NotNull
    private int number;
    @NotNull
    private String mallCode;

}
