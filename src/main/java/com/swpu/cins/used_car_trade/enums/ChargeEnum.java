package com.swpu.cins.used_car_trade.enums;

import lombok.Getter;
import me.chanjar.weixin.mp.bean.message.WxMpXmlMessage;

@Getter
public enum ChargeEnum {

    INSURANCE_ENQUIRY_FEE(2200),

    BEN_CHI(2500),
    YI_QI_DAZHONG(1600),
    JIN_KOU_DAZHONG(3500),
    FU_TE(2500),
    LEI_KE_SA_SI(1500),
    COMMON(800),

    ONE_HUNDRED(13000),
    THREE_HUNDRED(40000),
    FIVE_HUNDRED(70000),
    ;


    ChargeEnum(double fee) {
        this.fee = fee;
    }

    private double fee;
}
