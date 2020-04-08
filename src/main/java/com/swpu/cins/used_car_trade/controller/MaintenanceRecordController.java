package com.swpu.cins.used_car_trade.controller;

import com.swpu.cins.used_car_trade.form.CheckBrandForm;
import com.swpu.cins.used_car_trade.form.GetAllInsuranceOrderNoForm;
import com.swpu.cins.used_car_trade.form.GetInsuranceReportForm;
import com.swpu.cins.used_car_trade.form.PurchaseReportForm;
import com.swpu.cins.used_car_trade.service.MaintenanceRecordService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

/**
 * @author hobo
 */

@RestController
@RequestMapping("/maintenance")
@Api(tags = "维保记录查询")
@CrossOrigin
public class MaintenanceRecordController {

    @Autowired
    private MaintenanceRecordService recordService;

    @ApiOperation("查询品牌是否支持查询")
    @PostMapping("/check")
    public Object checkBrand(CheckBrandForm form) {
        return recordService.checkBrand(form);
    }

    @ApiOperation("购买维检报告，获取订单号")
    @PostMapping(name = "购买维检报告", value = "/buy")
    public Object buyReport(PurchaseReportForm form) {
        return recordService.purchaseReport(form);
    }


    @ApiOperation("查询用户所有维检订单")
    @PostMapping(name = "查询用户所有维检订单",value = "/getAll")
    public Object getAllOrder(GetAllInsuranceOrderNoForm form){
        return recordService.getAllOrderNo(form);
    }

    @ApiOperation("获取维保记录")
    @PostMapping(name = "获取维保记录", value = "/get")
    public Object getObject(GetInsuranceReportForm form) {
        return recordService.accessToReport(form);
    }

    @ApiOperation("查询服务回调接口")
    @PostMapping(value = "/callBack",produces = "application/json;charset=UTF-8")
    public Object callBack(HttpServletRequest request){
        return recordService.callBack(request);
    }
}
