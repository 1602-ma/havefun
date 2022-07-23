package com.feng.commons.prperties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * 阿里云短信配置类
 * @author f
 * @date 2022/7/23 11:37
 */
@Data
@ConfigurationProperties(prefix = "havefun.sms")
public class SmsProperties {

    /** 签名 */
    private String signName;

    /** 模板中的参数名 */
    private String parameterName;

    /** 验证码 短信模板 */
    private String validateCodeTemplateCode;

    /** key secret */
    private String accessKeyId;
    private String accessKeySecret;
}
