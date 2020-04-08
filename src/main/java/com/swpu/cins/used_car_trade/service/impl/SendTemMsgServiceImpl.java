package com.swpu.cins.used_car_trade.service.impl;

import com.swpu.cins.used_car_trade.enums.ResultEnum;
import com.swpu.cins.used_car_trade.service.SendTemMsgService;
import com.swpu.cins.used_car_trade.utils.ResultVOUtil;
import com.swpu.cins.used_car_trade.vo.ResultVO;
import lombok.extern.slf4j.Slf4j;
import me.chanjar.weixin.common.error.WxErrorException;
import me.chanjar.weixin.mp.api.WxMpService;
import me.chanjar.weixin.mp.bean.template.WxMpTemplateData;
import me.chanjar.weixin.mp.bean.template.WxMpTemplateMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class SendTemMsgServiceImpl implements SendTemMsgService {

    @Value("${wx.mp.template_id.query_res}")
    private String queryResId;

    @Value("${wx.mp.template_id.top_up}")
    private String topUpId;

    @Value("${wx.mp.template_id.have_refund}")
    private String refundId;

    @Autowired
    private WxMpService wxMpService;


    @Override
    public ResultVO sendQueryMsg(String openId, String orderNo, String brandName, String createTime, String res) {
        WxMpTemplateMessage templateMessage = WxMpTemplateMessage.builder()
                .toUser(openId)
                .templateId(queryResId)
                .url("http://a.acgweb.net/index.html#order")
                .build();
        templateMessage.addData(new WxMpTemplateData("keyword1", orderNo));
        templateMessage.addData(new WxMpTemplateData("keyword2", brandName));
        templateMessage.addData(new WxMpTemplateData("keyword3", createTime));
        templateMessage.addData(new WxMpTemplateData("keyword4", res));
        templateMessage.addData(new WxMpTemplateData("remark", "如有疑问，请联系管理员"));
        try {
            String result = wxMpService.getTemplateMsgService().sendTemplateMsg(templateMessage);
            return ResultVOUtil.success(result);
        } catch (WxErrorException e) {
            log.error("消息发送失败，失败原因:{}", e.getMessage());
            return ResultVOUtil.error(ResultEnum.SERVER_ERROR);
        }
    }

    @Override
    public boolean sendTopUpMsg(String openId, String rechargeNum, String balance) {
        WxMpTemplateMessage templateMessage = WxMpTemplateMessage.builder()
                .toUser(openId)
                .templateId(topUpId)
                .url("http://a.acgweb.net/index.html#user")
                .build();
        templateMessage.addData(new WxMpTemplateData("first", "尊敬的用户，您已充值成功"));
        templateMessage.addData(new WxMpTemplateData("keyword1", rechargeNum));
        templateMessage.addData(new WxMpTemplateData("keyword2", balance));
        templateMessage.addData(new WxMpTemplateData("remark", "如有疑问，请联系管理员"));
        try {
            wxMpService.getTemplateMsgService().sendTemplateMsg(templateMessage);
            return true;
        } catch (WxErrorException e) {
            log.error("消息发送失败，失败原因:{}", e.getMessage());
            return false;
        }
    }

    @Override
    public ResultVO sendRefundMsg(String openId, String num) {
        WxMpTemplateMessage templateMessage = WxMpTemplateMessage.builder()
                .toUser(openId)
                .templateId(refundId)
                .url("http://a.acgweb.net/index.html#user")
                .build();
        templateMessage.addData(new WxMpTemplateData("first", "您好，您的订单查询失败，已退款"));
        templateMessage.addData(new WxMpTemplateData("reason", "订单查询失败"));
        templateMessage.addData(new WxMpTemplateData("refund", num));
        templateMessage.addData(new WxMpTemplateData("remark", "如有疑问，请联系管理员"));
        try {
            String result = wxMpService.getTemplateMsgService().sendTemplateMsg(templateMessage);
            return ResultVOUtil.success(result);
        } catch (WxErrorException e) {
            log.error("消息发送失败，失败原因:{}", e.getMessage());
            return ResultVOUtil.error(ResultEnum.SERVER_ERROR);
        }

    }
}
