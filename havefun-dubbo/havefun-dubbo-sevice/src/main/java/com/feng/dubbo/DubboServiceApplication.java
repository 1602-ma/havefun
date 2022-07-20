package com.feng.dubbo;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * 服务提供者启动类
 * @author f
 * @date 2022/7/20 22:39
 */
@SpringBootApplication
@MapperScan("com.feng.dubbo.mapper")
public class DubboServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(DubboServiceApplication.class, args);
        System.out.println("-------------------------------------------------------------------------------------");
        System.out.println("                                  start success                                      ");
        System.out.println("-------------------------------------------------------------------------------------");
    }
}
