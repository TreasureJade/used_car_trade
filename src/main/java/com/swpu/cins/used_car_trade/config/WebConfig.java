package com.swpu.cins.used_car_trade.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
@Configuration
public class WebConfig implements WebMvcConfigurer {

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        // 配置访问前缀
        registry.addResourceHandler("/home/hobo/project/**")
                //配置文件真实路径
                .addResourceLocations("file:/home/hobo/project/");

        registry.addResourceHandler("MP_verify_4xKJSFqC2TORkl8X.txt")
                .addResourceLocations("file:/home/hobo/project/dist/MP_verify_4xKJSFqC2TORkl8X.txt");
    }
}
