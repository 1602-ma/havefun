package com.feng.commons.prperties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * 阿里Oss
 * @author f
 * @date 2022/7/23 21:52
 */
@Data
@ConfigurationProperties(prefix = "havefun.oss")
public class OssProperties {
    private String endPoint;
    private String accessKeyId;
    private String accessKeySecret;
    private String bucketName;
    private String url;
}
