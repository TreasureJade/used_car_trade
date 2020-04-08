package com.swpu.cins.used_car_trade.handler;

import lombok.extern.slf4j.Slf4j;
import me.chanjar.weixin.common.error.WxErrorException;
import me.chanjar.weixin.common.session.WxSessionManager;
import me.chanjar.weixin.mp.api.WxMpService;
import me.chanjar.weixin.mp.bean.message.WxMpXmlMessage;
import me.chanjar.weixin.mp.bean.message.WxMpXmlOutMessage;
import org.springframework.context.annotation.Configuration;

import java.util.Map;

@Configuration
@Slf4j
public class SubscribeHandler extends AbstractHandler {
    @Override
    public WxMpXmlOutMessage handle(WxMpXmlMessage wxMpXmlMessage, Map<String, Object> map, WxMpService wxMpService, WxSessionManager wxSessionManager) throws WxErrorException {
        String msg = "感谢关注车八方4S记录 出险查询 我们是专业的汽车查询服务平台，致力于通过产品服务、数据技术及资源为二手车行业打造公正、透明的交易环境。我们向广大二手车商、汽车电商、金融平台、个人车主提供全面、真实、高效、透明的车辆维修保养记录及相关历史车况信息查询服务，帮助用户进行车况判定、车辆信息真实性核查。   \n" +
                "        \n" +
                "用户通过小程序输入车辆VIN码，便可查询到目标车辆的维修保养历史记录，我们提供的专业维保报告将详细解读目标车辆重要部件的损伤情况，以及目标车辆是否有事故、召回、火烧、水淹及调表等异常情形，为车辆交易、车辆金融、车辆价值评估等业务提供重要的决策参考依据。\n" +
                "\n" +
                "如需进一步沟通，请联系客服微信：19931146362  15232156519。另出售正品宇问 林上 果欧二手车漆膜仪 一年质保 七天无理由退换货。   普通维保记录8元 出险22元  一单一结算！充值更优惠100送30 300送100 500送200元  价格低至冰点 最低维保5元 出险14.66 只有1个星期的优惠哦 后面会调整哦 ";
        return WxMpXmlOutMessage.TEXT().content(msg)
                .fromUser(wxMpXmlMessage.getToUser()).toUser(wxMpXmlMessage.getFromUser())
                .build();
    }
}
