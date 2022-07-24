package com.feng.dubbo.ocnfig;

import com.baomidou.mybatisplus.extension.plugins.PaginationInterceptor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author f
 * @date 2022/7/24 15:24
 */
@Configuration
public class MybatisPlusConfig {

    /**
     * 启用分页插件
     * @return page
     */
    @Bean
    public PaginationInterceptor paginationInterceptor() {
        return new PaginationInterceptor();
    }
}
