package com.feng.server.interceptor;

import com.aliyuncs.utils.StringUtils;
import com.feng.domain.db.User;
import com.feng.server.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 自定义拦截器
 * @author f
 * @date 2022/7/24 14:54
 */
@Component
@Slf4j
public class TokenInterceptor implements HandlerInterceptor {

    @Autowired
    private UserService userService;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        log.info("================================================进入前置拦截器");
        String token = request.getHeader("Authorization");
        if (StringUtils.isNotEmpty(token)) {
            User user = userService.getUserByToken(token);
            if (null == user) {
                response.setStatus(401);
                return false;
            }
            UserHolder.setUser(user);
            return true;
        }
        return false;
    }
}
