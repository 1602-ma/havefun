package com.feng.server;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.mongo.MongoAutoConfiguration;
import org.springframework.context.annotation.ComponentScan;

/**
 * 消费者启动类
 * @author f
 * @date 2022/7/20 23:06
 */
@SpringBootApplication(exclude = MongoAutoConfiguration.class)
public class HaveFunServerApplication {

    public static void main(String[] args) {
        SpringApplication.run(HaveFunServerApplication.class, args);
        System.out.println("-------------------------------------------------------------------------------------");
        System.out.println("                                  start success                                      ");
        System.out.println("-------------------------------------------------------------------------------------");
    }
}
