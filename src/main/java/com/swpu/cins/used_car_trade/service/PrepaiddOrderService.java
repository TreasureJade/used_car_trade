package com.swpu.cins.used_car_trade.service;

import com.swpu.cins.used_car_trade.form.PrepaidForm;
import com.swpu.cins.used_car_trade.vo.ResultVO;

import javax.servlet.http.HttpServletRequest;


public interface PrepaiddOrderService {


    /**
     * 生成预支付请求
     *
     * @param form 前端请求参数
     * @return ResultVO
     */
    ResultVO creatOrder(PrepaidForm form, HttpServletRequest request);


    /**
     * 异步回调
     * @param xmlData 微信传来xml字符串
     * @return ResultVO
     */
    ResultVO notify(String xmlData);
}
