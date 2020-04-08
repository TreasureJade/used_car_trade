package com.swpu.cins.used_car_trade.entity;

import java.util.Date;
import java.io.Serializable;

/**
 * (PrepaidOrder)实体类
 *
 * @author makejava
 * @since 2020-03-16 22:09:26
 */
public class PrepaidOrder implements Serializable {
    private static final long serialVersionUID = -55444027891621566L;
    
    private String wxOrderNo;
    
    private String openId;
    
    private Double totalFee;
    
    private Integer orderStatu;
    
    private Date creatTime;
    
    private Integer payStatu;


    public String getWxOrderNo() {
        return wxOrderNo;
    }

    public void setWxOrderNo(String wxOrderNo) {
        this.wxOrderNo = wxOrderNo;
    }

    public String getOpenId() {
        return openId;
    }

    public void setOpenId(String openId) {
        this.openId = openId;
    }

    public Double getTotalFee() {
        return totalFee;
    }

    public void setTotalFee(Double totalFee) {
        this.totalFee = totalFee;
    }

    public Integer getOrderStatu() {
        return orderStatu;
    }

    public void setOrderStatu(Integer orderStatu) {
        this.orderStatu = orderStatu;
    }

    public Date getCreatTime() {
        return creatTime;
    }

    public void setCreatTime(Date creatTime) {
        this.creatTime = creatTime;
    }

    public Integer getPayStatu() {
        return payStatu;
    }

    public void setPayStatu(Integer payStatu) {
        this.payStatu = payStatu;
    }

}