package com.swpu.cins.used_car_trade.controller;

import com.swpu.cins.used_car_trade.config.WxMpConfiguration;
import com.swpu.cins.used_car_trade.service.WxMenuService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import jdk.nashorn.internal.objects.annotations.Getter;
import me.chanjar.weixin.common.api.WxConsts;
import me.chanjar.weixin.common.bean.menu.WxMenu;
import me.chanjar.weixin.common.bean.menu.WxMenuButton;
import me.chanjar.weixin.common.error.WxErrorException;
import me.chanjar.weixin.mp.api.WxMpService;
import me.chanjar.weixin.mp.bean.message.WxMpXmlOutTextMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/wx/menu")
@CrossOrigin
@Api(tags = "微信公众号菜单接口")
public class WxMenuController {


    @Autowired
    private WxMenuService menuService;

    @ApiOperation("创建菜单")
    @GetMapping("/create")
    public Object menuCreate() throws WxErrorException {
        return menuService.creatMenu();
    }

    @ApiOperation("创建JsApi")
    @PostMapping("/jsApi/creat")
    public Object creatJsApi(String url) {
        return menuService.creatJsApi(url);
    }

}
