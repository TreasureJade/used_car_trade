package com.swpu.cins.used_car_trade.utils;

import java.util.Random;

/**
 * 随机字符工具类
 */
public class RandomUtil {

    /**
     * @return java.lang.String
     * 获取一定长度的字符串，范围0~9，a-z
     * @Param [length] 指定字符串长度
     **/
    public static String getRandomStringByLength(int length) {
        String base = "abcdefghijklmnopqrstuvwxyz0123456789";
        Random random = new Random();
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < length; i++) {
            int num = random.nextInt(base.length());
            sb.append(base.charAt(num));
        }
        return sb.toString();
    }
}
