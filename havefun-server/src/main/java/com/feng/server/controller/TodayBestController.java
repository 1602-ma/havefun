package com.feng.server.controller;

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
     * 今日家人
     * @return entity
     */
    @GetMapping("/todayBest")
    public ResponseEntity todayBest() {
        return todayBestService.queryTodayBest();
    }
}
