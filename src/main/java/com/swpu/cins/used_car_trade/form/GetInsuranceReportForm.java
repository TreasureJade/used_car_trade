package com.swpu.cins.used_car_trade.form;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class GetInsuranceReportForm {

    @ApiModelProperty("用户openId")
    private String openId;

    @ApiModelProperty("订单号")
    private String orderNo;

}
