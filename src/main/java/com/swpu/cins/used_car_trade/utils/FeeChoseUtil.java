package com.swpu.cins.used_car_trade.utils;

import com.swpu.cins.used_car_trade.enums.ChargeEnum;

/**
 * @author hobo
 */
public class FeeChoseUtil {

    public static double judgeCarBrand(String brandName) {
        switch (brandName) {
            case "奔驰":
                return ChargeEnum.BEN_CHI.getFee();
            case "一汽大众":
                return ChargeEnum.YI_QI_DAZHONG.getFee();
            case "进口大众":
                return ChargeEnum.JIN_KOU_DAZHONG.getFee();
            case "长安福特":
            case "进口福特":
                return ChargeEnum.FU_TE.getFee();
            case "雷克萨斯":
                return ChargeEnum.LEI_KE_SA_SI.getFee();
            default:
                return ChargeEnum.COMMON.getFee();
        }
    }

    public static double judgePrepaidFee(double totalFee) {
        switch ((int) totalFee) {
            case 10000:
                return ChargeEnum.ONE_HUNDRED.getFee();
            case 30000:
                return ChargeEnum.THREE_HUNDRED.getFee();
            case 50000:
                return ChargeEnum.FIVE_HUNDRED.getFee();
            default:
                return totalFee;
        }
    }

}

