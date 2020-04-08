package com.swpu.cins.used_car_trade.service;

import com.swpu.cins.used_car_trade.vo.ResultVO;

/**
 * @author hobo
 * 微信菜单管理
 */
public interface WxMenuService {

    /**
     * 创建菜单
     * @return
     */
    ResultVO creatMenu();

    ResultVO creatJsApi(String url);



}
