package com.feng.server.controller;

import com.feng.domain.db.User;
import com.feng.domain.vo.ErrorResult;
import com.feng.domain.vo.UserInfoVo;
import com.feng.server.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

/**
 * @author f
 * @date 2022/7/24 11:10
 */
@RestController
@RequestMapping("/users")
@Slf4j
public class UserInfoController {

    @Autowired
    private UserService userService;

    @GetMapping
    public ResponseEntity getUserInfo(Long userID, Long huanxinID, @RequestHeader("Authorization") String token) {
        log.info("===================================查询用户信息");
        Long userId = huanxinID;
        if (null == userId) {
            userId =userID;
        }
        if (null == userId) {
            User user = userService.getUserByToken(token);
            if (null == user) {
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(ErrorResult.error("000006", "请重新登录"));
            }
            userId = user.getId();
        }
        UserInfoVo userInfoVo = userService.findUserInfoById(userId);
        return ResponseEntity.ok(userInfoVo);
    }

    /**
     * 保存用户基本信息
     * @param vo vo
     * @param token token
     * @return entity
     */
    @PutMapping
    public ResponseEntity updateUserInfo(@RequestBody UserInfoVo vo, @RequestHeader("Authorization") String token) {
        log.info("===========================更新用户信息啦==============================");
        userService.updateUserInfo(vo, token);
        return ResponseEntity.ok(null);
    }

    @PostMapping("/header")
    public ResponseEntity header(MultipartFile headPhoto, @RequestHeader("Authorization") String token) {
        log.info("==============================更新头像=====================");
        userService.header(headPhoto, token);
        return ResponseEntity.ok(null);
    }
}
