package com.feng.server.controller;

import com.feng.domain.vo.PageResult;
import com.feng.domain.vo.RecommendUserQueryParam;
import com.feng.domain.vo.TodayBestVo;
import com.feng.server.service.TodayBestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author f
 * @date 2022/7/24 21:57
 */
@RestController
@RequestMapping("/tanhua")
public class TodayBestController {

    @Autowired
    private TodayBestService todayBestService;

    /**
     * 今日佳人
     * @return entity
     */
    @GetMapping("/todayBest")
    public ResponseEntity todayBest() {
        return todayBestService.queryTodayBest();
    }

    /**
     * 推荐列表
     * @param queryParam param
     * @return entity
     */
    @GetMapping("/recommendation")
    public ResponseEntity recommendList(RecommendUserQueryParam queryParam) {
        PageResult<TodayBestVo> pageResult = todayBestService.recommendList(queryParam);
        return ResponseEntity.ok(pageResult);
    }
}
