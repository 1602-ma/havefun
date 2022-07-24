package com.feng.dubbo.api;

import com.feng.domain.db.Settings;

/**
 * @author f
 * @date 2022/7/24 15:34
 */
public interface SettingsApi {

    /**
     * 根据用户id查询通知配置
     * @param userId userId
     * @return settings
     */
    Settings findByUserId(Long userId);

    /**
     * 保存
     * @param settings setting
     */
    void save(Settings settings);

    /**
     * 更新
     * @param settings setting
     */
    void update(Settings settings);
}
