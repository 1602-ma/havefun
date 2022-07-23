package com.feng.server.test;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.apache.commons.codec.digest.DigestUtils;
import org.junit.Test;

import java.util.HashMap;
import java.util.Map;

/**
 * @author f
 * @date 2022/7/23 15:40
 */
public class JwtTest {

    @Test
    public void testJwt() {
        String secret = "ouyyyyyyy";

        Map<String, Object> claims = new HashMap<>();
        claims.put("mobile", "22");
        claims.put("id", "11");

        //生成token
        String jwt = Jwts.builder()
                .setClaims(claims)
                .signWith(SignatureAlgorithm.HS256, secret)
                .compact();

        System.out.println(jwt);

        //通过token解析数据
        Map<String, Object> body = Jwts.parser()
                .setSigningKey(secret)
                .parseClaimsJws(jwt)
                .getBody();

        System.out.println(body);
    }

    /**
     *生成jwt使用的密钥
     */
    @Test
    public void createSecret() {
        System.out.println(DigestUtils.md5Hex("hello_ou"));
    }
}
