package com.swpu.cins.used_car_trade.service.impl;

import com.swpu.cins.used_car_trade.entity.User;
import com.swpu.cins.used_car_trade.form.UserInfoForm;
import com.swpu.cins.used_car_trade.service.UserService;
import com.swpu.cins.used_car_trade.service.WxUserService;
import com.swpu.cins.used_car_trade.utils.ResultVOUtil;
import com.swpu.cins.used_car_trade.vo.ResultVO;
import lombok.extern.slf4j.Slf4j;
import me.chanjar.weixin.common.error.WxErrorException;
import me.chanjar.weixin.mp.api.WxMpService;
import me.chanjar.weixin.mp.bean.result.WxMpOAuth2AccessToken;
import me.chanjar.weixin.mp.bean.result.WxMpUser;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class WxUserServiceImpl implements WxUserService {

    @Autowired
    private WxMpService wxMpService;

    @Autowired
    private UserService userService;

    @Override
    public ResultVO getUserInfo(UserInfoForm form) {
        log.info("【微信网页授权】code={}", form.getCode());
        WxMpOAuth2AccessToken oAuth2AccessToken = null;
        try {
            oAuth2AccessToken = wxMpService.oauth2getAccessToken(form.getCode());
        } catch (WxErrorException e) {
            log.error("e:{}", e);
        }
        WxMpUser wxMpUser = null;
        try {
            wxMpUser = wxMpService.oauth2getUserInfo(oAuth2AccessToken, null);
        } catch (WxErrorException e) {
            e.printStackTrace();
        }
        User user = new User();
        if (wxMpUser != null) {
            BeanUtils.copyProperties(wxMpUser, user);
            if (userService.queryById(wxMpUser.getOpenId()) == null) {
                user.setBalanceNum((double) 0);
                userService.insert(user);
            }
        }

        return ResultVOUtil.success(user);
    }
}
