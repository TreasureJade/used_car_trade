package com.swpu.cins.used_car_trade.utils;

import com.swpu.cins.used_car_trade.enums.ResultEnum;
import com.swpu.cins.used_car_trade.vo.ResultVO;
import lombok.Data;


public class ReturnCodeUtil {

    public static ResultVO switchCode(Integer code) {
        switch (code) {
            case 1101:
                return ResultVOUtil.error(ResultEnum.SUCCESS_BUT_NO_MSG);
            case 1105:
                return ResultVOUtil.error(ResultEnum.REPORT_GENERATION_FAILED);
            case 1100:
                return ResultVOUtil.error(ResultEnum.BRAND_IS_CURRENTLY_NOT_SUPPORTED);
            case 1300:
                return ResultVOUtil.error(ResultEnum.QUERY_SERVER_ERROR);

            default: return ResultVOUtil.error(ResultEnum.SERVER_ERROR);

        }
    }

}
