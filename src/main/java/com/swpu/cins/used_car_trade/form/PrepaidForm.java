package com.swpu.cins.used_car_trade.form;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author hobo
 *
 * 用于微信支付--账户充值
 */
@Data
public class PrepaidForm {

    @ApiModelProperty("用户openId")
    private String openId;

    @ApiModelProperty("总价，单位为分")
    private Integer totalFee;

}
