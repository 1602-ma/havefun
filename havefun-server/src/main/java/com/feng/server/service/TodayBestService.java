package com.feng.server.service;

import com.aliyuncs.utils.StringUtils;
import com.feng.domain.db.UserInfo;
import com.feng.domain.mongo.RecommendUser;
import com.feng.domain.vo.TodayBestVo;
import com.feng.dubbo.api.UserInfoApi;
import com.feng.dubbo.api.mongo.RecommendUserApi;
import com.feng.server.interceptor.UserHolder;
import org.apache.dubbo.config.annotation.Reference;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

/**
 * @author f
 * @date 2022/7/24 21:58
 */
@Service
public class TodayBestService {

    @Reference
    private RecommendUserApi recommendUserApi;

    @Reference
    private UserInfoApi userInfoApi;

    public ResponseEntity queryTodayBest() {
        Long userId = UserHolder.getUserId();
        RecommendUser user = recommendUserApi.queryWithMaxScore(userId);
        if (null == user) {
            user = new RecommendUser();
            user.setUserId(2L);
            user.setScore(95D);
        }
        UserInfo userInfo = userInfoApi.findById(user.getUserId());
        TodayBestVo best = new TodayBestVo();
        BeanUtils.copyProperties(userInfo, best);
        best.setFateValue(user.getScore().longValue());
        if (!StringUtils.isEmpty(userInfo.getTags())) {
            best.setTags(userInfo.getTags().split(","));
        }
        return ResponseEntity.ok(best);
    }
}
