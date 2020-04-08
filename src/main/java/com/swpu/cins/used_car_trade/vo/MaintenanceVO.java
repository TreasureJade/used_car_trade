package com.swpu.cins.used_car_trade.vo;

import lombok.Data;

import java.util.Date;

@Data
public class MaintenanceVO {

    private String vin;

    private String openId;

    private String orderNo;

    private String brandName;

    private Date creatTime;

    private Double cost;

    private Integer orderStatus;

}
