package com.feng.server.service;

import com.aliyuncs.utils.StringUtils;
import com.baomidou.mybatisplus.core.toolkit.CollectionUtils;
import com.feng.domain.db.UserInfo;
import com.feng.domain.mongo.RecommendUser;
import com.feng.domain.vo.PageResult;
import com.feng.domain.vo.RecommendUserQueryParam;
import com.feng.domain.vo.TodayBestVo;
import com.feng.dubbo.api.UserInfoApi;
import com.feng.dubbo.api.mongo.RecommendUserApi;
import com.feng.server.interceptor.UserHolder;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.RandomUtils;
import org.apache.dubbo.config.annotation.Reference;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * @author f
 * @date 2022/7/24 21:58
 */
@Service
@Slf4j
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

    /**
     * 推荐列表
     * @param queryParam param
     * @return best
     */
    public PageResult<TodayBestVo> recommendList(RecommendUserQueryParam queryParam) {
        log.info("================================今日推荐");
        //根据token查询当前登录的用户信息
        Long userId = UserHolder.getUserId();
        PageResult result = recommendUserApi.findPage(queryParam.getPage(),queryParam.getPagesize(),userId);
        List<RecommendUser> records = (List<RecommendUser>) result.getItems();
        // 如果未查询到，需要使用默认推荐列表
        if (CollectionUtils.isEmpty(records)) {
            result = new PageResult(10L,queryParam.getPagesize().longValue(),1l,1l,null);
            records = defaultRecommend();
        }
        List<TodayBestVo> todayBests = new ArrayList<>();
        for (RecommendUser record : records) {
            TodayBestVo best = new TodayBestVo();
            // 补全用户信息
            UserInfo userInfo = this.userInfoApi.findById(record.getUserId());
            BeanUtils.copyProperties(userInfo, best);
            best.setId(record.getUserId());
            best.setFateValue(record.getScore().longValue());
            best.setTags(org.apache.commons.lang3.StringUtils.split(userInfo.getTags(), ','));
            todayBests.add(best);
        }
        //构造VO返回
        result.setItems(todayBests);
        return result;
    }

    /**
     * 构造默认数据
     */
    private List<RecommendUser> defaultRecommend() {
        String ids = "1,2,3,4,5,6,7,8,9,10";
        List<RecommendUser> records = new ArrayList<>();
        for (String id : ids.split(",")) {
            RecommendUser recommendUser = new RecommendUser();
            recommendUser.setUserId(Long.valueOf(id));
            recommendUser.setScore(RandomUtils.nextDouble(70, 98));
            records.add(recommendUser);
        }
        return records;
    }
}
