package com.coco.sport.util;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

/**
 * @Author: Mr.Shan
 * @Date: 2022/6/21 15:39
 * @ClassName: WebConfig
 * @Description: WebConfig
 * @Version: v1.0
 */
@Configuration
public class WebConfig extends WebMvcConfigurerAdapter {
    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")
                .allowedOrigins("http://localhost:8080", "null")
                .allowedMethods("GET", "POST", "PUT", "OPTIONS", "DELETE")
                .allowCredentials(true) //允许携带其他内容，比如token
                .maxAge(3600); //最大相应时间
    }
}
