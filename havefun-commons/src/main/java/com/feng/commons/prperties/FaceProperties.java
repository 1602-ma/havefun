package com.feng.commons.prperties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * @author f
 * @date 2022/7/23 22:11
 */
@Data
@ConfigurationProperties(prefix = "havefun.face")
public class FaceProperties {
    private String appId;
    private String apiKey;
    private String secretKey;
}
