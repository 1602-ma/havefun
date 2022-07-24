package com.feng.dubbo.api;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.feng.domain.db.User;
import com.feng.dubbo.mapper.UserMapper;
import org.apache.dubbo.config.annotation.Service;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Date;

/**
 * @author f
 * @date 2022/7/20 22:14
 */
@Service
public class UserApiImpl implements UserApi{

    @Autowired
    private UserMapper userMapper;

    /**
     * 添加用户
     * @param user user
     * @return id
     */
    @Override
    public Long save(User user) {
        user.setCreated(new Date());
        user.setUpdated(new Date());
        userMapper.insert(user);
        return user.getId();
    }

    /**
     * 通过手机号查询
     * @param mobile mobile
     * @return user
     */
    @Override
    public User findByMobile(String mobile) {
        QueryWrapper<User> userQueryWrapper = new QueryWrapper<>();
        userQueryWrapper.eq("mobile", mobile);
        return userMapper.selectOne(userQueryWrapper);
    }

    /**
     * 修改手机号码
     * @param userId
     * @param phone
     */
    @Override
    public void updateMobile(Long userId, String phone) {
        User user = new User();
        user.setMobile(phone);
        user.setId(userId);
        userMapper.updateById(user);
    }
}
