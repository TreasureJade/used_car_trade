package com.swpu.cins.used_car_trade.enums;

import lombok.Getter;

@Getter
public enum PayBodyEnum {
    PREPAID("用户充值"),
    ;
    private String body;

    PayBodyEnum(String body) {
        this.body = body;
    }
}
