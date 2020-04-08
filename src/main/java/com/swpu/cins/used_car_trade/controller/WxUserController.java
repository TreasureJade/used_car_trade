package com.swpu.cins.used_car_trade.controller;

import com.swpu.cins.used_car_trade.form.UserInfoForm;
import com.swpu.cins.used_car_trade.service.WxUserService;
import com.swpu.cins.used_car_trade.utils.ResultVOUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import me.chanjar.weixin.common.api.WxConsts;
import me.chanjar.weixin.mp.api.WxMpService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/wx/user")
@Slf4j
@CrossOrigin
@Api(tags = "微信公众号授权接口")
public class WxUserController {


    @Autowired
    private WxMpService wxMpService;

    @Autowired
    private WxUserService wxUserService;

    @GetMapping("/authorize")
    @ApiOperation("请求授权")
    public Object authorize(@RequestParam("returnUrl") String returnUrl) {
        String redirectUrl = wxMpService.oauth2buildAuthorizationUrl(returnUrl, WxConsts.OAuth2Scope.SNSAPI_USERINFO, null);
        log.info("【微信网页授权】获取code,redirectURL={}", redirectUrl);
        return ResultVOUtil.success(redirectUrl);
    }

    @GetMapping("/userInfo")
    @ApiOperation("微信授权获取用户信息")
    public Object userInfo(UserInfoForm form) throws Exception {
        return wxUserService.getUserInfo(form);
    }

}
