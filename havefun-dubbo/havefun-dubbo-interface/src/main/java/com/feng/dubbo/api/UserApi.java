package com.feng.dubbo.api;

import com.feng.domain.db.User;
import com.feng.domain.db.UserInfo;

/**
 * @author f
 * @date 2022/7/20 22:12
 */
public interface UserApi {

    /**
     * 添加用户
     * @param user user
     * @return id
     */
    Long save(User  user);

    /**
     * 通过手机号查询用户
     * @param mobile mobile
     * @return user
     */
    User findByMobile(String mobile);
}
