package com.swpu.cins.used_car_trade.handler;

import lombok.extern.slf4j.Slf4j;
import me.chanjar.weixin.common.api.WxConsts;
import me.chanjar.weixin.common.error.WxErrorException;
import me.chanjar.weixin.common.session.WxSessionManager;
import me.chanjar.weixin.mp.api.WxMpService;
import me.chanjar.weixin.mp.bean.message.WxMpXmlMessage;
import me.chanjar.weixin.mp.bean.message.WxMpXmlOutMessage;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

import java.util.Map;

@Configuration
@Slf4j
public class MenuHandler extends AbstractHandler {

    @Override
    public WxMpXmlOutMessage handle(WxMpXmlMessage wxMpXmlMessage, Map<String, Object> map, WxMpService wxMpService, WxSessionManager wxSessionManager) throws WxErrorException {
        String msg = "";
        log.info("进入回复");
        if ("customer_service".equals(wxMpXmlMessage.getEventKey())) {
            return WxMpXmlOutMessage.IMAGE().mediaId("_KAg0KfSXsCQSzi-3eZwOH_uRCWCpc9OQzSCIqBQvfA")
                    .fromUser(wxMpXmlMessage.getToUser()).toUser(wxMpXmlMessage.getFromUser())
                    .build();
        } else if ("kuai_shou".equals(wxMpXmlMessage.getEventKey())) {
            msg = "快手id: 房轩说车， 快手账号 gourende";
            return WxMpXmlOutMessage.TEXT().content(msg)
                    .fromUser(wxMpXmlMessage.getToUser()).toUser(wxMpXmlMessage.getFromUser())
                    .build();
        } else if ("dou_yin".equals(wxMpXmlMessage.getEventKey())) {
            msg = "抖音id: 房轩说车，抖音账号 192016797";
            return WxMpXmlOutMessage.TEXT().content(msg)
                    .fromUser(wxMpXmlMessage.getToUser()).toUser(wxMpXmlMessage.getFromUser())
                    .build();
        }

        return WxMpXmlOutMessage.TEXT().content(msg)
                .fromUser(wxMpXmlMessage.getToUser()).toUser(wxMpXmlMessage.getFromUser())
                .build();
    }

}
