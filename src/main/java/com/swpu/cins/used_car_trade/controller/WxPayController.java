package com.swpu.cins.used_car_trade.controller;

import com.swpu.cins.used_car_trade.form.PrepaidForm;
import com.swpu.cins.used_car_trade.service.PrepaiddOrderService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/wx/pay")
@CrossOrigin
@Api(tags = "微信支付接口")
public class WxPayController {

    @Autowired
    private PrepaiddOrderService prepaiddOrderService;

    @ApiOperation("发起支付请求")
    @PostMapping("/creat")
    public Object creatOrder(PrepaidForm form, HttpServletRequest request){
        return prepaiddOrderService.creatOrder(form,request);
    }

    @ApiOperation("回调接口")
    @PostMapping("/notify")
    public Object notify(@RequestBody String xmlData){
        return prepaiddOrderService.notify(xmlData);
    }

}
