package com.feng.commons;

import com.feng.commons.prperties.SmsProperties;
import com.feng.commons.templates.SmsTemplate;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author f
 * @date 2022/7/23 12:28
 */
@Configuration
@EnableConfigurationProperties({SmsProperties.class})
public class CommonsAutoConfiguration {

    @Bean
    public SmsTemplate smsTemplate(SmsProperties smsProperties) {
        SmsTemplate smsTemplate = new SmsTemplate(smsProperties);
        smsTemplate.init();;
        return smsTemplate;
    }
}
