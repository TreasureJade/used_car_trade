package com.swpu.cins.used_car_trade;

import com.swpu.cins.used_car_trade.utils.BaiduUtil;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class UsedCarTradeApplicationTests {

    @Test
    void contextLoads() {
        System.out.println(BaiduUtil.getAuth());
    }

}
