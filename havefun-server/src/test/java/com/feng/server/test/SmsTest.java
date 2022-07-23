package com.feng.server.test;

import com.feng.commons.templates.SmsTemplate;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Map;

/**
 * @author f
 * @date 2022/7/23 12:34
 */
@SpringBootTest
@RunWith(SpringRunner.class)
public class SmsTest {

    @Autowired
    private SmsTemplate smsTemplate;

    @Test
    public void testSms() {
        Map<String, String> result = smsTemplate.sendValidateCode("46546", "777");
        System.out.println(result);
    }
}
