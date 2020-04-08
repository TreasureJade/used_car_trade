package com.swpu.cins.used_car_trade;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan("com.swpu.cins.used_car_trade.dao")
public class UsedCarTradeApplication {

    public static void main(String[] args) {
        SpringApplication.run(UsedCarTradeApplication.class, args);
    }

}
