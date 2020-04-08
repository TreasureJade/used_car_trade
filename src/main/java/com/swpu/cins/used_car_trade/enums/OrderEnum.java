package com.swpu.cins.used_car_trade.enums;

import lombok.Getter;

@Getter
public enum OrderEnum {
    QUERYING(0),
    FINISHED(1),
    Filed(2),
    DID_NOT_SEE(3),

    CREATE_SUCCESS(4)
    ;
    private int status;

    OrderEnum(int status) {
        this.status = status;
    }
}
