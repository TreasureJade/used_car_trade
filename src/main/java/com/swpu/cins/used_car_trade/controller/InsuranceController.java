package com.swpu.cins.used_car_trade.controller;

import com.swpu.cins.used_car_trade.form.GetAllInsuranceOrderNoForm;
import com.swpu.cins.used_car_trade.form.GetInsuranceReportForm;
import com.swpu.cins.used_car_trade.form.PurchaseReportForm;
import com.swpu.cins.used_car_trade.service.InsuranceQueryService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/insurance")
@Api(tags = "车险报告接口")
@CrossOrigin
public class InsuranceController {

    @Autowired
    private InsuranceQueryService queryService;

    @ApiOperation("购买车险报告，获取订单号")
    @PostMapping(name = "购买车险报告",value = "/buy")
    public Object buyReport(PurchaseReportForm form) {
        return queryService.purchaseReport(form);
    }

    @ApiOperation("获取车险报告")
    @PostMapping(name = "获取车险报告",value = "/get")
    public Object getObject(GetInsuranceReportForm form){
        return queryService.accessToReport(form);
    }

    @ApiOperation("查询用户所有车险订单")
    @PostMapping(name = "查询用户所有车险订单",value = "/getAll")
    public Object getAllOrder(GetAllInsuranceOrderNoForm form){
        return queryService.getAllOrderNo(form);
    }

    @ApiOperation("识别行驶证")
    @PostMapping(name = "识别行驶证",value = "/identification")
    public Object identificationCarCode(MultipartFile file) {
        return queryService.identificationCarCode(file);
    }

    @ApiOperation("查询服务回调接口")
    @PostMapping(value = "/callBack",produces = "application/json;charset=UTF-8")
    public Object callBack(HttpServletRequest request){
        return queryService.callBack(request);
    }

}
