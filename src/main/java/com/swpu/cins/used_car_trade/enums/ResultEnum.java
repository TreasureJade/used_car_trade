package com.swpu.cins.used_car_trade.enums;

import lombok.Getter;

/**
 * @ClassName ResultEnum
 * @Author hobo
 * @Date 19-4-22 下午3:45
 * @Description
 **/
@Getter
public enum ResultEnum {

    LACK_OF_BALANCE(1, "账户余额不足，请充值"),
    VIN_NOT_DETERMINED_TO_SUPPORT_THE_QUERY(2,"此车架号尚未判断是否支持查询"),
    WX_MENU_CREAT_FILED(3,"微信菜单创建失败 " ),
    PAY_FAILED(4,"支付失败" ),
    AUTHENTICATION_ERROR(401, "用户认证失败,请重新登录"),
    PERMISSION_DENNY(403, "权限不足"),
    NOT_FOUND(404, "url错误,请求路径未找到"),
    SERVER_ERROR(500, "服务器未知错误"),
    BIND_ERROR(511, "参数校验错误"),
    REQUEST_METHOD_ERROR(550, "不支持的请求方式"),


    IN_THE_QUERY(2001, "订单查询中"),
    SUCCESS_BUT_NO_MSG(2002, "品牌维护中"),
    REPORT_GENERATION_FAILED(4002, "报告生成失败"),
    BRAND_IS_CURRENTLY_NOT_SUPPORTED(4004, "暂不支持此品牌"),
    QUERY_SERVER_ERROR(1300, "查询失败"),
    TOP_UP_FILED(5,"充值失败，请联系管理员");

    private Integer code;

    private String msg;

    private ResultEnum(Integer code, String msg) {
        this.code = code;
        this.msg = msg;
    }
}
