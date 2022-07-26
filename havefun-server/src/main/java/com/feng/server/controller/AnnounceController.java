package com.feng.server.controller;

import com.feng.server.service.AnnounceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * 公告
 * @author f
 * @date 2022/7/26 21:51
 */
@RestController
@RequestMapping("/messages")
public class AnnounceController {

    @Autowired
    private AnnounceService announceService;

    /**
     * 查询公告列表
     * @param page page
     * @param pageSize pageSize
     * @return entity
     */
    @GetMapping("/announcements")
    public ResponseEntity announcements(@RequestParam(defaultValue = "1") int page,
                                        @RequestParam(defaultValue = "10") int pageSize) {
        return announceService.announcets(page, pageSize);
    }
}
