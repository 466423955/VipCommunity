package com.hasaki.vip.vipcommunity;

import org.apache.ibatis.annotations.Mapper;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan(basePackages = "com.hasaki.vip.vipcommunity.mapper")
public class VipcommunityApplication {

    public static void main(String[] args) {
        SpringApplication.run(VipcommunityApplication.class, args);
    }

}
