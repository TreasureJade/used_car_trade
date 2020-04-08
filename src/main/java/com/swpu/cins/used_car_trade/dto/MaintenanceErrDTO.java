package com.swpu.cins.used_car_trade.dto;

import lombok.Data;

@Data
public class MaintenanceErrDTO {
    private String orderId;

    private String returncode;

    private String message;
}
