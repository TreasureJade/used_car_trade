package com.swpu.cins.used_car_trade.entity;

import java.util.Date;
import java.io.Serializable;

/**
 * (MaintenanceRecordOrder)实体类
 *
 * @author makejava
 * @since 2020-03-16 20:24:23
 */
public class MaintenanceRecordOrder implements Serializable {
    private static final long serialVersionUID = -49307144525251561L;
    
    private String vin;
    
    private String openId;
    
    private String orderNo;
    
    private String brandName;
    
    private Date creatTime;
    
    private Double cost;
    
    private Integer orderStatus;
    
    private Integer payStatu;


    public String getVin() {
        return vin;
    }

    public void setVin(String vin) {
        this.vin = vin;
    }

    public String getOpenId() {
        return openId;
    }

    public void setOpenId(String openId) {
        this.openId = openId;
    }

    public String getOrderNo() {
        return orderNo;
    }

    public void setOrderNo(String orderNo) {
        this.orderNo = orderNo;
    }

    public String getBrandName() {
        return brandName;
    }

    public void setBrandName(String brandName) {
        this.brandName = brandName;
    }

    public Date getCreatTime() {
        return creatTime;
    }

    public void setCreatTime(Date creatTime) {
        this.creatTime = creatTime;
    }

    public Double getCost() {
        return cost;
    }

    public void setCost(Double cost) {
        this.cost = cost;
    }

    public Integer getOrderStatus() {
        return orderStatus;
    }

    public void setOrderStatus(Integer orderStatus) {
        this.orderStatus = orderStatus;
    }

    public Integer getPayStatu() {
        return payStatu;
    }

    public void setPayStatu(Integer payStatu) {
        this.payStatu = payStatu;
    }

}