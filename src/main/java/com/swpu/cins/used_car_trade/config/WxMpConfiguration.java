package com.swpu.cins.used_car_trade.config;

import com.github.binarywang.wxpay.config.WxPayConfig;
import com.github.binarywang.wxpay.service.WxPayService;
import com.github.binarywang.wxpay.service.impl.WxPayServiceImpl;
import com.swpu.cins.used_car_trade.handler.LogHandler;
import com.swpu.cins.used_car_trade.handler.MenuHandler;
import com.swpu.cins.used_car_trade.handler.SubscribeHandler;
import lombok.Data;
import me.chanjar.weixin.common.api.WxConsts;
import me.chanjar.weixin.mp.api.WxMpMessageRouter;
import me.chanjar.weixin.mp.api.WxMpService;
import me.chanjar.weixin.mp.api.impl.WxMpServiceImpl;
import me.chanjar.weixin.mp.config.WxMpConfigStorage;
import me.chanjar.weixin.mp.config.impl.WxMpDefaultConfigImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import static me.chanjar.weixin.common.api.WxConsts.EventType.SUBSCRIBE;
import static me.chanjar.weixin.common.api.WxConsts.XmlMsgType.EVENT;

@Configuration
@Data
public class WxMpConfiguration {

    @Value("${wx.mp.config.appId}")
    private String appId;

    @Value("${wx.mp.config.secret}")
    private String secret;

    @Value("${wx.mp.config.token}")
    private String token;

    @Value("${wx.mp.config.aesKey}")
    private String aesKey;

    @Value("${wx.mp.config.mch_id}")
    private String mchId;

    @Value("${wx.mp.config.mch_key}")
    private String mchKey;

    @Value("${wx.mp.config.key_path}")
    private String keyPath;

    @Autowired
    private MenuHandler menuHandler;

    @Autowired
    private SubscribeHandler subscribeHandler;

    @Autowired
    private LogHandler logHandler;

    @Bean
    public WxMpConfigStorage wxMpConfigStorage() {
        WxMpDefaultConfigImpl configStorage = new WxMpDefaultConfigImpl();
        configStorage.setAppId(this.appId);
        configStorage.setSecret(this.secret);
        configStorage.setToken(this.token);
        configStorage.setAesKey(this.aesKey);
        return configStorage;
    }

    @Bean
    public WxMpService wxMpService() {
        WxMpService wxMpService = new WxMpServiceImpl();
        wxMpService.setWxMpConfigStorage(wxMpConfigStorage());
        return wxMpService;
    }

    @Bean
    public WxPayService wxService() {
        WxPayConfig payConfig = new WxPayConfig();
        payConfig.setAppId(this.appId);
        payConfig.setMchId(this.mchId);
        payConfig.setMchKey(this.mchKey);

        payConfig.setUseSandboxEnv(false);
        WxPayService wxPayService = new WxPayServiceImpl();
        wxPayService.setConfig(payConfig);
        return wxPayService;
    }


    @Bean
    public WxMpMessageRouter messageRouter(WxMpService wxMpService) {
        final WxMpMessageRouter newRouter = new WxMpMessageRouter(wxMpService);

        // 记录所有事件的日志 （异步执行）
        newRouter.rule().handler(this.logHandler).next();

        // 自定义菜单事件
        newRouter.rule().async(false).msgType(EVENT).event(WxConsts.EventType.CLICK).handler(this.menuHandler).end();

        // 关注事件
        newRouter.rule().async(false).msgType(EVENT).event(SUBSCRIBE).handler(this.subscribeHandler).end();

        return newRouter;
    }

}

