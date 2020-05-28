package com.study.cn.security_database;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@MapperScan(value = "com.study.cn.security_database.dao")
@SpringBootApplication
public class SecurityDatabaseApplication {

    public static void main(String[] args) {
        SpringApplication.run(SecurityDatabaseApplication.class, args);
    }

}
