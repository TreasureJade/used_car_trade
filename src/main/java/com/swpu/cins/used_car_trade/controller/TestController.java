package com.swpu.cins.used_car_trade.controller;

import com.swpu.cins.used_car_trade.config.WxMpConfiguration;
import com.swpu.cins.used_car_trade.service.InsuranceQueryService;
import com.swpu.cins.used_car_trade.service.UserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import me.chanjar.weixin.mp.api.WxMpService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

@Slf4j
@RestController
@RequestMapping("/wx/portal")
@Api(tags = "测试")
public class TestController {

    @Autowired
    protected WxMpService wxService;

    @Autowired
    private WxMpConfiguration wxMpConfiguration;

    @ApiOperation("接收微信服务器测试消息")
    @GetMapping(value = "/test")
    public String authGet(
            @RequestParam(name = "signature", required = false) String signature,
            @RequestParam(name = "timestamp", required = false) String timestamp,
            @RequestParam(name = "nonce", required = false) String nonce,
            @RequestParam(name = "echostr", required = false) String echostr) {

        log.info("\n接收到来自微信服务器的认证消息：[{}, {}, {}, {}]", signature,
                timestamp, nonce, echostr);
        if (StringUtils.isAnyBlank(signature, timestamp, nonce, echostr)) {
            throw new IllegalArgumentException("请求参数非法，请核实!");
        }

        if (wxService.switchover(wxMpConfiguration.getAppId())) {
            throw new IllegalArgumentException(String.format("未找到对应appid=[%s]的配置，请核实！", wxMpConfiguration.getAppId()));
        }
        if (wxService.checkSignature(timestamp, nonce, signature)) {
            return echostr;
        }

        return "非法请求";
    }



}
