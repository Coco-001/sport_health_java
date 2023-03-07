package com.coco;

import org.mybatis.spring.annotation.MapperScan;
import org.mybatis.spring.annotation.MapperScans;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@MapperScan("com.coco.mapper")
@SpringBootApplication
public class JavaSportApplication {

    public static void main(String[] args) {
        SpringApplication.run(JavaSportApplication.class, args);
    }

}
