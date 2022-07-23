package com.feng.commons.templates;

import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.IAcsClient;
import com.aliyuncs.dysmsapi.model.v20170525.SendSmsRequest;
import com.aliyuncs.dysmsapi.model.v20170525.SendSmsResponse;
import com.aliyuncs.exceptions.ClientException;
import com.aliyuncs.http.MethodType;
import com.aliyuncs.profile.DefaultProfile;
import com.feng.commons.prperties.SmsProperties;
import lombok.extern.slf4j.Slf4j;

import java.util.HashMap;
import java.util.Map;

/**
 * 短信模板类，用来发送短信
 * @author f
 * @date 2022/7/23 11:43
 */
@Slf4j
public class SmsTemplate {

    /** 用来返回给调用者，短信发送结果的key */
    public static final String SMSRESPONSE_CODE = "Code";
    public static final String SMSRESPONSE_MESSAGE = "Message";

    private SmsProperties smsProperties;
    private IAcsClient acsClient;

    public SmsTemplate(SmsProperties smsProperties) {
        this.smsProperties = smsProperties;
    }

    public void init() {
        //设置超时时间
        System.setProperty("sun.net.client.defaultConnectTimeout", "100000");
        System.setProperty("sun.net.client.defaultReadTimeout", "100000");

        //初始化acsClient需要的几个参数
        /** 短信api产品名称（短信产品名固定，无需修改） */
        final String product = "Dysmsapi";
        /** 短信api产品域名（接口地址固定，无需修改）*/
        final String domain = "dysmsapi.aliyuncs.com";
        DefaultProfile profile = DefaultProfile.getProfile("cn-hangzhou", smsProperties.getAccessKeyId(), smsProperties.getAccessKeySecret());
        try {
            DefaultProfile.addEndpoint("cn-hangzhou", "cn-hangzhou", product, domain);
            acsClient = new DefaultAcsClient(profile);
        } catch (ClientException e) {
            log.error("初始化阿里云短信失败", e);
        }
    }

    /**
     * 发送验证码
     * @param phoneNumber phone
     * @param validateCode code
     * @return map
     */
    public Map<String, String> sendValidateCode(String phoneNumber, String validateCode) {
        return sendSms(smsProperties.getValidateCodeTemplateCode(), phoneNumber, validateCode);
    }

    /**
     * 发送短信
     * @param templateCode 验证码的模板code
     * @param phoneNumber 接收的手机号
     * @param param 短信模板中参数对应的值
     * @return code = ok 代表发送成功
     */
    public Map<String, String> sendSms(String templateCode, String phoneNumber, String param) {
        //组装请求对象
        SendSmsRequest request = new SendSmsRequest();
        //使用post提交
        request.setMethod(MethodType.POST);
        request.setPhoneNumbers(phoneNumber);
        request.setSignName(smsProperties.getSignName());
        request.setTemplateCode(templateCode);
        request.setTemplateParam(String.format("{\"%s\":\"%s\"}", smsProperties.getParameterName(), param));

        //将发送结果封装到map
        Map<String, String> result =  new HashMap<>();
        try {
            SendSmsResponse sendSmsResponse = acsClient.getAcsResponse(request);
            if ("OK".equals(sendSmsResponse.getCode())) {
                result.put(SmsTemplate.SMSRESPONSE_CODE, sendSmsResponse.getCode());
                result.put(SmsTemplate.SMSRESPONSE_MESSAGE, sendSmsResponse.getMessage());
            }
        } catch (ClientException e) {
            log.error("发送短信失败", e);
            result.put(SmsTemplate.SMSRESPONSE_CODE, "FAIL");
        }
        return result;
    }
}
