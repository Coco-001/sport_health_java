package com.coco.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;


@Configuration
@Slf4j
public class WebConfig implements WebMvcConfigurer {

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry
                //过滤所有请求
                .addMapping("/**")
                //配置跨域来源
                .allowedOrigins("**")
                //是否支持跨域Cookie
                .allowCredentials(true)
                //最大响应时间
                .maxAge(3600)
                //设置允许访问的方法
                .allowedMethods("GET", "POST", "PUT", "DELETE");
    }

}
