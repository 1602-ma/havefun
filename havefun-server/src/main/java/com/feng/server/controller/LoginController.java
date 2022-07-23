package com.feng.server.controller;

import com.feng.server.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

/**
 * 用户控制层
 * @author f
 * @date 2022/7/20 22:49
 */
@RestController
@RequestMapping("/user")
public class LoginController {

    @Autowired
    private UserService userService;

    /**
     * 根据手机号查询用户
     * @param mobile mobile
     * @return user
     */
    @RequestMapping(value = "/findUser", method = RequestMethod.GET)
    public ResponseEntity findUser(String mobile) {
        return userService.findByMobile(mobile);
    }

    /**
     * 新增用户
     * @param param param
     * @return entity
     */
    @RequestMapping(value = "/findUser", method = RequestMethod.POST)
    public ResponseEntity saveUser(@RequestBody Map<String, Object> param) {
        String mobile = (String)param.get("mobile");
        String password = (String)param.get("password");
        return userService.saveUser(mobile, password);
    }

    /**
     * 用户登录发送验证码
     * @param param param
     * @return entity
     */
    @PostMapping("/login")
    public ResponseEntity sendValidateCode(@RequestBody Map<String, String> param) {
        String phone = param.get("phone");
        userService.sendValidateCode(phone);
        return ResponseEntity.ok(null);
    }

    /**
     * 登录验证码校验
     * @param param param
     * @return entity
     */
    @PostMapping("/loginVerification")
    public ResponseEntity loginVerification(@RequestBody Map<String, String> param) {
        String phone = param.get("phone");
        String verificationCode = param.get("verificationCode");
        Map<String, Object> map = userService.loginVerification(phone, verificationCode);
        return ResponseEntity.ok(map);
    }
}
