package com.feng.server.controller;

import com.feng.server.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

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

    public ResponseEntity saveUser(@RequestBody Map<String, Object> param) {
        String mobile = (String)param.get("mobile");
        String password = (String)param.get("password");
        return userService.saveUser(mobile, password);
    }
}
