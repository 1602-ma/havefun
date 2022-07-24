package com.feng.dubbo.api;

import com.feng.domain.db.UserInfo;

/**
 * @author f
 * @date 2022/7/23 22:49
 */
public interface UserInfoApi {

    /**
     * 保存用户基本信息
     * @param userInfo user
     */
    void save(UserInfo userInfo);

    /**
     * 通过id更新用户信息
     * @param userInfo user
     */
    void update(UserInfo userInfo);

    /**
     * 通过ia查询用户基本信息
     * @param userId userId
     * @return userInfo
     */
    UserInfo findById(Long userId);
}
