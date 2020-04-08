package com.swpu.cins.used_car_trade.service.impl;

import com.github.binarywang.wxpay.bean.notify.WxPayOrderNotifyResult;
import com.github.binarywang.wxpay.bean.order.WxPayMpOrderResult;
import com.github.binarywang.wxpay.bean.request.WxPayUnifiedOrderRequest;
import com.github.binarywang.wxpay.exception.WxPayException;
import com.github.binarywang.wxpay.service.WxPayService;
import com.swpu.cins.used_car_trade.dao.PrepaidOrderDao;
import com.swpu.cins.used_car_trade.entity.PrepaidOrder;
import com.swpu.cins.used_car_trade.entity.User;
import com.swpu.cins.used_car_trade.enums.*;
import com.swpu.cins.used_car_trade.form.PrepaidForm;
import com.swpu.cins.used_car_trade.service.PrepaiddOrderService;
import com.swpu.cins.used_car_trade.service.SendTemMsgService;
import com.swpu.cins.used_car_trade.service.UserService;
import com.swpu.cins.used_car_trade.utils.FeeChoseUtil;
import com.swpu.cins.used_car_trade.utils.IpUtil;
import com.swpu.cins.used_car_trade.utils.RandomUtil;
import com.swpu.cins.used_car_trade.utils.ResultVOUtil;
import com.swpu.cins.used_car_trade.vo.ResultVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;

@Service
@Slf4j
public class PrepaidOrderServiceImpl implements PrepaiddOrderService {

    @Value("${wx.mp.config.notify_url}")
    private String notifyUrl;

    @Autowired
    private WxPayService wxPayService;


    @Autowired
    private PrepaidOrderDao orderDao;

    @Autowired
    private UserService userService;

    @Autowired
    private SendTemMsgService msgService;

    @Override
    public ResultVO creatOrder(PrepaidForm form, HttpServletRequest request) {
        String wxOrderNo = RandomUtil.getRandomStringByLength(32);
        String userIp = IpUtil.getIpAddr(request);
        WxPayUnifiedOrderRequest prepayInfo = WxPayUnifiedOrderRequest.newBuilder()
                .openid(form.getOpenId())
                .outTradeNo(wxOrderNo)
                .totalFee(form.getTotalFee())
                .body(PayBodyEnum.PREPAID.getBody())
                .tradeType("JSAPI")
                .spbillCreateIp(userIp)
                .notifyUrl(notifyUrl)
                .build();
        try {
            WxPayMpOrderResult result = wxPayService.createOrder(prepayInfo);
            log.info("预支付成功，订单号为：{}", wxOrderNo);

            // 订单存库
            PrepaidOrder order = new PrepaidOrder();
            order.setOpenId(prepayInfo.getOpenid());
            order.setTotalFee(prepayInfo.getTotalFee().doubleValue());
            order.setWxOrderNo(wxOrderNo);
            order.setPayStatu(PayStatuEnum.CREATE_SUCCESS.getPayStatu());
            order.setOrderStatu(OrderEnum.CREATE_SUCCESS.getStatus());
            orderDao.insert(order);

            return ResultVOUtil.success(result);

        } catch (WxPayException e) {
            log.info("预支付失败，原因:{} ", e.getMessage());
            return ResultVOUtil.error(ResultEnum.PAY_FAILED);
        }
    }

    @Override
    public ResultVO notify(String xmlData) {
        try {
            WxPayOrderNotifyResult notifyResult = this.wxPayService.parseOrderNotifyResult(xmlData);
            if (notifyResult != null) {
                PrepaidOrder order = orderDao.queryById(notifyResult.getOutTradeNo());
                if (order != null) {
                    // 订单修改状态存库
                    if (order.getPayStatu() != PayStatuEnum.PAY_SUCCESS.getPayStatu()
                            && order.getOrderStatu() != OrderEnum.FINISHED.getStatus()) {
                        // 账户充值
                        User user = userService.queryById(order.getOpenId());
                        double predFee = FeeChoseUtil.judgePrepaidFee(order.getTotalFee());
                        // 账户余额充值
                        if (order.getTotalFee() > 10000) {
                            user.setBalanceNum(user.getBalanceNum() + predFee);
                            if (userService.update(user)) {
                                log.info("用户:{} ,充值:{}", order.getOpenId(), order.getTotalFee());
                                order.setOrderStatu(OrderEnum.FINISHED.getStatus());
                                order.setPayStatu(PayStatuEnum.PAY_SUCCESS.getPayStatu());
                                orderDao.update(order);
                                if (msgService.sendTopUpMsg(user.getOpenId(), (order.getTotalFee() / 100 + "元"),
                                        (user.getBalanceNum() / 100) + "元")) {
                                    return ResultVOUtil.success(order);
                                }
                            }
                        }
                        user.setBalanceNum(user.getBalanceNum() + predFee);
                        if (userService.update(user)) {
                            log.info("用户:{} ,充值:{}", order.getOpenId(), order.getTotalFee());
                            order.setPayStatu(PayStatuEnum.PAY_SUCCESS.getPayStatu());
                            order.setOrderStatu(OrderEnum.FINISHED.getStatus());
                            orderDao.update(order);
                            return ResultVOUtil.success(order);
                        }
                        order.setOrderStatu(OrderEnum.Filed.getStatus());
                        orderDao.update(order);
                        return ResultVOUtil.error(ResultEnum.TOP_UP_FILED);
                    }
                    return ResultVOUtil.success(order);
                }
                return ResultVOUtil.error(ResultEnum.SERVER_ERROR);
            }
            return ResultVOUtil.error(ResultEnum.PAY_FAILED);
        } catch (WxPayException e) {
            log.error("支付失败，失败原因: {}", e.getMessage());
            return ResultVOUtil.error(ResultEnum.PAY_FAILED);
        }
    }
}
