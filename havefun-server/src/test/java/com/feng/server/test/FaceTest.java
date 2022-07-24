package com.feng.server.test;

import com.feng.commons.templates.FaceTemplate;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;

/**
 * @author f
 * @date 2022/7/23 22:31
 */
@SpringBootTest
@RunWith(SpringRunner.class)
public class FaceTest {

    @Autowired
    private FaceTemplate faceTemplate;

    @Test
    public void testFace() throws IOException {
        faceTemplate.detect(Files.readAllBytes(new File("D:\\picture\\小鹿.jpg").toPath()));
    }
}
