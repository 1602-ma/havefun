package com.feng.server.controller;

import com.feng.domain.db.UserInfo;
import com.feng.domain.vo.UserInfoVo;
import com.feng.server.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

/**
 * @author f
 * @date 2022/7/23 22:53
 */
@RestController
@RequestMapping("/user")
@Slf4j
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping("/loginReginfo")
    public ResponseEntity loginReginfo(@RequestBody UserInfoVo userInfoVo, @RequestHeader("Authorization") String token) {
        UserInfo userInfo = new UserInfo();
        BeanUtils.copyProperties(userInfoVo, userInfo);
        userService.saveUserInfo(userInfo, token);
        return ResponseEntity.ok(null);
    }

    /**
     * 上传用户头像
     * @param headPhoto photo
     * @param token toekn
     * @return entity
     */
    @PostMapping("loginReginfo/head")
    public ResponseEntity uploadAvatar(MultipartFile headPhoto, @RequestHeader("Authorization") String token) {
        userService.updateUserAvatar(headPhoto, token);
        return ResponseEntity.ok(null);
    }
}
