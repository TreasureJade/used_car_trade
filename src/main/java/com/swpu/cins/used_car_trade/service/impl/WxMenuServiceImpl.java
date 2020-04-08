package com.swpu.cins.used_car_trade.service.impl;

import com.swpu.cins.used_car_trade.config.WxMpConfiguration;
import com.swpu.cins.used_car_trade.enums.ResultEnum;
import com.swpu.cins.used_car_trade.service.WxMenuService;
import com.swpu.cins.used_car_trade.utils.ResultVOUtil;
import com.swpu.cins.used_car_trade.vo.ResultVO;
import lombok.extern.slf4j.Slf4j;
import me.chanjar.weixin.common.api.WxConsts;
import me.chanjar.weixin.common.bean.WxJsapiSignature;
import me.chanjar.weixin.common.bean.menu.WxMenu;
import me.chanjar.weixin.common.bean.menu.WxMenuButton;
import me.chanjar.weixin.common.error.WxErrorException;
import me.chanjar.weixin.mp.api.WxMpService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class WxMenuServiceImpl implements WxMenuService {

    @Autowired
    private WxMpService wxMpService;


    @Autowired
    private WxMpConfiguration configuration;

    @Override
    public ResultVO creatMenu() {
        WxMenu wxMenu = new WxMenu();
        WxMenuButton button1 = new WxMenuButton();
        button1.setType(WxConsts.MenuButtonType.VIEW);
        button1.setName("信息查询");

        WxMenuButton button11 = new WxMenuButton();
        button11.setType(WxConsts.MenuButtonType.VIEW);
        button11.setName("维修保养查询");
        button11.setUrl("http://a.acgweb.net#/maintenance");

        WxMenuButton button12 = new WxMenuButton();
        button12.setType(WxConsts.MenuButtonType.VIEW);
        button12.setName("出险记录查询");
        button12.setUrl("http://a.acgweb.net#/maintain");

        wxMenu.getButtons().add(button1);
        button1.getSubButtons().add(button11);
        button1.getSubButtons().add(button12);


        WxMenuButton button3 = new WxMenuButton();
        button3.setType(WxConsts.MenuButtonType.VIEW);
        button3.setName("我的查询");
        button3.setUrl("http://a.acgweb.net#/user");
        wxMenu.getButtons().add(button3);

        WxMenuButton button2 = new WxMenuButton();
        button2.setName("综合服务");

        WxMenuButton button21 = new WxMenuButton();
        button21.setName("联系客服");
        button21.setType(WxConsts.MenuButtonType.CLICK);
        button21.setKey("customer_service");

        WxMenuButton button22 = new WxMenuButton();
        button22.setName("快手账号");
        button22.setType(WxConsts.MenuButtonType.CLICK);
        button22.setKey("kuai_shou");

        WxMenuButton button23 = new WxMenuButton();
        button23.setName("抖音账号");
        button23.setType(WxConsts.MenuButtonType.CLICK);
        button23.setKey("dou_yin");


        wxMenu.getButtons().add(button2);
        button2.getSubButtons().add(button21);
        button2.getSubButtons().add(button22);
        button2.getSubButtons().add(button23);

        wxMpService.switchover(configuration.getAppId());
        try {
            return ResultVOUtil.success(wxMpService.getMenuService().menuCreate(wxMenu));
        } catch (WxErrorException e) {
            log.error("菜单创建失败，失败原因: {}", e.getMessage());
            return ResultVOUtil.error(ResultEnum.WX_MENU_CREAT_FILED);
        }
    }

    @Override
    public ResultVO creatJsApi(String url) {
        try {
            WxJsapiSignature wxJsapiSignature = wxMpService.createJsapiSignature(url);
            System.out.println(wxJsapiSignature.getSignature());
            return ResultVOUtil.success(wxMpService.createJsapiSignature(url));
        } catch (WxErrorException e) {
            e.printStackTrace();
        }
        return ResultVOUtil.error(ResultEnum.SERVER_ERROR);
    }

}
