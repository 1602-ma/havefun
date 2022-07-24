package com.feng.server.test;

import com.feng.commons.templates.OssTemplate;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.FileInputStream;
import java.io.FileNotFoundException;

/**
 * @author f
 * @date 2022/7/23 22:06
 */
@SpringBootTest
@RunWith(SpringRunner.class)
public class OssTest {

    @Autowired
    private OssTemplate ossTemplate;

    @Test
    public void testOss() throws FileNotFoundException {
        FileInputStream fis = new FileInputStream("D:\\picture\\小鹿.jpg");
        ossTemplate.upload("小鹿.jpg", fis);
    }
}
