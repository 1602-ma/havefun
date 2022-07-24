package com.feng.dubbo.api.mongo;

import com.feng.domain.mongo.RecommendUser;

/**
 * @author f
 * @date 2022/7/24 22:05
 */
public interface RecommendUserApi {

    /**
     * 查询某个用户，推荐之最高的人
     * @param toUserId userId
     * @return user
     */
    RecommendUser queryWithMaxScore(Long toUserId);
}
