package com.swpu.cins.used_car_trade.form;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class CheckBrandForm {

    @ApiModelProperty("用户openId")
    private String openId;

    @ApiModelProperty("车架号")
    private String vin;

}
