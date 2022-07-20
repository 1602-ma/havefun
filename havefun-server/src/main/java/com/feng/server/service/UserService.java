package com.feng.server.service;

import com.feng.domain.db.User;
import com.feng.dubbo.api.UserApi;
import org.apache.dubbo.config.annotation.Reference;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

/**
 * @author f
 * @date 2022/7/20 22:55
 */
@Service
public class UserService {

    @Reference
    private UserApi userApi;

    /**
     * 根据手机号查询用户
     * @param mobile mobile
     * @return user
     */
    public ResponseEntity findByMobile(String mobile) {
        User user = userApi.findByMobile(mobile);
        return ResponseEntity.ok(user);
    }

    /**
     * 新增用户
     * @param mobile mobile
     * @param password password
     * @return null
     */
    public ResponseEntity saveUser(String mobile, String password) {
        User user = new User();
        user.setMobile(mobile);
        user.setPassword(password);
        userApi.save(user);
        return ResponseEntity.ok(null);
    }
}
