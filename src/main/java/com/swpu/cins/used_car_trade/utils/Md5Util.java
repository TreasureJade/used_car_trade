package com.swpu.cins.used_car_trade.utils;

import org.bouncycastle.util.encoders.UrlBase64Encoder;

import java.net.URL;
import java.net.URLEncoder;
import java.security.MessageDigest;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @author hobo
 */
public class Md5Util {
    /**
     * @param encryptStr 明文
     * @return 32位密文
     */
    public static String encryption(String encryptStr) {
        MessageDigest md5;
        try {
            md5 = MessageDigest.getInstance("MD5");
            byte[] md5Bytes = md5.digest(encryptStr.getBytes());
            StringBuffer hexValue = new StringBuffer();
            for (int i = 0; i < md5Bytes.length; i++) {
                int val = ((int) md5Bytes[i]) & 0xff;
                if (val < 16) {
                    hexValue.append("0");
                }
                hexValue.append(Integer.toHexString(val));
            }
            encryptStr = hexValue.toString();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return encryptStr;

    }

    /**
     * 组装 购买报告sn
     *
     * @param partnerId id
     * @param vin       车架号
     * @param secret    秘钥
     * @return 组装用于加密的字符串
     */
    public static String assemblyBuyReportString(String partnerId, String vin, String secret, String ts, String callBackUrl) {
        String url = URLEncoder.encode(callBackUrl);
        System.out.println(url);
        return "callbackUrl=" + url + "&" + "partner_id=" + partnerId + "&" + "ts=" + ts + "&" + "vin=" + vin + "&partner_key=" + secret;
    }

    /**
     * 组装 获取报告sn
     *
     * @param cId     id
     * @param orderNo 订单号
     * @param secret  秘钥
     * @return 组装用于加密的字符串
     */
    public static String assemblyGetReportString(int cId, String orderNo, String secret) {
        return "customer_id=" + cId + "&" + "order_no=" + orderNo + secret + getNowTime();
    }

    public static String assmBlyBuyReportString(String orderNo, String partnerId, String ts, String secret) {
        return "orderNo=" + orderNo + "&" + "partner_id=" + partnerId + "&" + "ts=" + ts + "&" + "partner_key=" + secret;
    }

    public static String assmBlyBuyReportString(String url, String orderNo, String partnerId, String ts, String vin, String secret) {
        return "callbackurl=" + url + "&" + "orderid=" + orderNo + "&" + "partner_id=" + partnerId + "&" +
                "ts=" + ts + "&" + "vin=" + vin + "&" +
                "partner_key=" + secret;
    }

    public static String getNowTime() {
        return Long.toString(System.currentTimeMillis() / 1000L);
    }
}
