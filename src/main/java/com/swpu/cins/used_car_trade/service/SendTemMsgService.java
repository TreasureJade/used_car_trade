package com.swpu.cins.used_car_trade.service;

import com.swpu.cins.used_car_trade.vo.ResultVO;

public interface SendTemMsgService {

    ResultVO sendQueryMsg(String openId,String orderNo,String brandName,String creatTime,String res);

    boolean sendTopUpMsg(String openId, String rechargeNum, String balance);

    ResultVO sendRefundMsg(String openId,String num);
}
