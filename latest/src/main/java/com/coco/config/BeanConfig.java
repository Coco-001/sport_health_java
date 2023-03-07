package com.coco.config;

import com.qiniu.storage.Region;
import com.qiniu.storage.UploadManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


@Configuration
public class BeanConfig {

    @Bean
    public UploadManager uploadManager() {
        com.qiniu.storage.Configuration cfg = new com.qiniu.storage.Configuration(Region.region2());
        return new UploadManager(cfg);
    }

}
