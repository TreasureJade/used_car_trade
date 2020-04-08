package com.swpu.cins.used_car_trade.entity;

import java.util.Date;
import java.io.Serializable;

/**
 * (InsuranceOrder)实体类
 *
 * @author makejava
 * @since 2020-03-16 20:24:23
 */
public class InsuranceOrder implements Serializable {
    private static final long serialVersionUID = 841062928730724614L;
    
    private String orderNo;
    
    private String openId;
    
    private Integer orderStatus;
    
    private Date createDate;
    
    private String vin;
    
    private Integer payStatu;


    public String getOrderNo() {
        return orderNo;
    }

    public void setOrderNo(String orderNo) {
        this.orderNo = orderNo;
    }

    public String getOpenId() {
        return openId;
    }

    public void setOpenId(String openId) {
        this.openId = openId;
    }

    public Integer getOrderStatus() {
        return orderStatus;
    }

    public void setOrderStatus(Integer orderStatus) {
        this.orderStatus = orderStatus;
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    public String getVin() {
        return vin;
    }

    public void setVin(String vin) {
        this.vin = vin;
    }

    public Integer getPayStatu() {
        return payStatu;
    }

    public void setPayStatu(Integer payStatu) {
        this.payStatu = payStatu;
    }

}