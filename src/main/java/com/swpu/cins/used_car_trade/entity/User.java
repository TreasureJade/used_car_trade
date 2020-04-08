package com.swpu.cins.used_car_trade.entity;

import java.io.Serializable;

/**
 * (User)实体类
 *
 * @author makejava
 * @since 2020-02-21 17:33:05
 */
public class User implements Serializable {
    private static final long serialVersionUID = -71894793747557237L;
    
    private String openId;
    
    private String nickname;
    
    private Integer sex;
    
    private String city;
    
    private String headImgUrl;
    
    private Double balanceNum;


    public String getOpenId() {
        return openId;
    }

    public void setOpenId(String openId) {
        this.openId = openId;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public Integer getSex() {
        return sex;
    }

    public void setSex(Integer sex) {
        this.sex = sex;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getHeadImgUrl() {
        return headImgUrl;
    }

    public void setHeadImgUrl(String headImgUrl) {
        this.headImgUrl = headImgUrl;
    }

    public Double getBalanceNum() {
        return balanceNum;
    }

    public void setBalanceNum(Double balanceNum) {
        this.balanceNum = balanceNum;
    }

    @Override
    public String toString() {
        return "User{" +
                "openId='" + openId + '\'' +
                ", nickname='" + nickname + '\'' +
                ", sex=" + sex +
                ", city='" + city + '\'' +
                ", headImgUrl='" + headImgUrl + '\'' +
                ", balanceNum=" + balanceNum +
                '}';
    }
}