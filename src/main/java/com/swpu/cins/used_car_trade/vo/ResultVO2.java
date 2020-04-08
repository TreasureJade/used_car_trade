package com.swpu.cins.used_car_trade.vo;

import lombok.Data;

@Data
public class ResultVO2<T> {

    private int returncode;

    private String message;

    private String orderId;

    private T result;

}
