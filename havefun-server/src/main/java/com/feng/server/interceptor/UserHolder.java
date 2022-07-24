package com.feng.server.interceptor;

import com.feng.domain.db.User;

/**
 * 登录用户信息持有者
 * 通过 ThreadLocal的形式，存储登录用户的数据
 * @author f
 * @date 2022/7/24 14:59
 */
public class UserHolder {

    private static ThreadLocal<User> userThreadLocal = new ThreadLocal<>();

    /**
     * 向当前线程中存入用户数据
     * @param user user
     */
    public static void setUser(User user) {
        userThreadLocal.set(user);
    }

    /**
     * 从当前线程中获取用户数据
     * @return user
     */
    public static User getUser() {
        return userThreadLocal.get();
    }

    /**
     * 获取登录用户id
     * @return id
     */
    public static Long getUserId() {
        return userThreadLocal.get().getId();
    }
}
