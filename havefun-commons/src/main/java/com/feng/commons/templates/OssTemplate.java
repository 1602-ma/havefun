package com.feng.commons.templates;

import com.aliyun.oss.OSS;
import com.aliyun.oss.OSSClientBuilder;
import com.feng.commons.prperties.OssProperties;

import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;

/**
 * Oss上传模板类
 * @author f
 * @date 2022/7/23 21:54
 */
public class OssTemplate {

    private OssProperties ossProperties;

    public OssTemplate(OssProperties ossProperties) {
        this.ossProperties = ossProperties;
    }

    /**
     * 上传文件
     * @param filename 文件名
     * @param is io流
     * @return url
     */
    public String upload(String filename, InputStream is) {
        String endPoint = ossProperties.getEndPoint();
        String accessKeyId = ossProperties.getAccessKeyId();
        String accessKeySecret = ossProperties.getAccessKeySecret();

        OSS ossClient = new OSSClientBuilder().build(endPoint, accessKeyId, accessKeySecret);
        String suffix = filename.substring(filename.lastIndexOf("."));
        String ymd = new SimpleDateFormat("yyyy/MM/dd").format(new Date());
        String name = "images/" + ymd + "/" + UUID.randomUUID().toString() + suffix;

        ossClient.putObject(ossProperties.getBucketName(), name, is);
        ossClient.shutdown();
        return ossProperties.getUrl() + "/" + name;
    }
}
