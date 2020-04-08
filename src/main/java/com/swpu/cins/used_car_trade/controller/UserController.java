package com.swpu.cins.used_car_trade.controller;

import com.swpu.cins.used_car_trade.entity.User;
import com.swpu.cins.used_car_trade.enums.ResultEnum;
import com.swpu.cins.used_car_trade.service.UserService;
import com.swpu.cins.used_car_trade.utils.ResultVOUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/user")
@Api(tags = "用户接口")
@CrossOrigin
public class UserController {

    @Autowired
    private UserService userService;

    @ApiOperation("获取用户信息")
    @PostMapping("/get")
    public Object getUserInfo(String openId) {
        User user = userService.queryById(openId);
        if (user != null) {
            return ResultVOUtil.success(user);
        }
        return ResultVOUtil.error(ResultEnum.SERVER_ERROR);
    }

}
