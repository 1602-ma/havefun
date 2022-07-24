package com.feng.dubbo.api;

/**
 * @author f
 * @date 2022/7/24 15:35
 */

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.feng.domain.db.Settings;
import com.feng.dubbo.mapper.SettingsMapper;
import org.apache.dubbo.config.annotation.Service;
import org.springframework.beans.factory.annotation.Autowired;

@Service
public class SettingsApiImpl implements SettingsApi{

    @Autowired
    private SettingsMapper settingsMapper;

    /**
     * 根据用户id查询通用配置
     * @param userId userId
     * @return settings
     */
    @Override
    public Settings findByUserId(Long userId) {
        QueryWrapper<Settings> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("user_id", userId);
        return settingsMapper.selectOne(queryWrapper);
    }

    @Override
    public void save(Settings settings) {
        settingsMapper.insert(settings);
    }

    @Override
    public void update(Settings settings) {
        settingsMapper.updateById(settings);
    }
}
