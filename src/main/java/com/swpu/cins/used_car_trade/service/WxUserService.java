package com.swpu.cins.used_car_trade.service;

import com.swpu.cins.used_car_trade.form.UserInfoForm;
import com.swpu.cins.used_car_trade.vo.ResultVO;

public interface WxUserService {

    /**
     * 微信获取用户信息
     * @param form
     * @return
     */
    ResultVO getUserInfo(UserInfoForm form);

}
