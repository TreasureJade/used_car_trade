package com.swpu.cins.used_car_trade.enums;

import lombok.Getter;

@Getter
public enum PayStatuEnum {

    CREATE_SUCCESS(0),
    PAY_SUCCESS(1),
    PAY_FILED(2),
    HAVE_REFUND(3)
    ;

    private int payStatu;

    PayStatuEnum(int payStatu) {
        this.payStatu = payStatu;
    }
}
