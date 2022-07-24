package com.feng.dubbo.api;

import com.feng.domain.db.Question;

/**
 * @author f
 * @date 2022/7/24 15:44
 */
public interface QuestionApi {

    /**
     * 根据用户id查询通知配置
     * @param userId userId
     * @return question
     */
    Question findByUserId(Long userId);

    void save(Question question);

    void update(Question question);
}
