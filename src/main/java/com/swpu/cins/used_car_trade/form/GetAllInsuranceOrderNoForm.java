package com.swpu.cins.used_car_trade.form;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class GetAllInsuranceOrderNoForm {

    @ApiModelProperty("用户openId")
    private String openId;

}
