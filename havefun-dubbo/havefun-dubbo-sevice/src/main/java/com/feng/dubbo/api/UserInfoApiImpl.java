package com.feng.dubbo.api;

import com.feng.domain.db.UserInfo;
import com.feng.dubbo.mapper.UserInfoMapper;
import org.apache.dubbo.config.annotation.Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.yaml.snakeyaml.events.Event;

/**
 * @author f
 * @date 2022/7/23 22:51
 */
@Service
public class UserInfoApiImpl implements UserInfoApi{

    @Autowired
    private UserInfoMapper userInfoMapper;

    /**
     * 保存用户基本信息
     * @param userInfo user
     */
    @Override
    public void save(UserInfo userInfo) {
        userInfoMapper.insert(userInfo);
    }

    /**
     * 通过id更新用户基本信息
     * @param userInfo user
     */
    @Override
    public void update(UserInfo userInfo) {
        userInfoMapper.updateById(userInfo);
    }

    /**
     * 通过id查询用户基本信息
     * @param userId userId
     * @return userInfo
     */
    @Override
    public UserInfo findById(Long userId) {
        return userInfoMapper.selectById(userId);
    }
}
