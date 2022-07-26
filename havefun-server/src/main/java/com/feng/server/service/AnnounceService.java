package com.feng.server.service;

import com.feng.domain.db.Announcement;
import com.feng.domain.vo.AnnouncementVo;
import com.feng.domain.vo.PageResult;
import com.feng.dubbo.api.AnnouncementApi;
import lombok.extern.slf4j.Slf4j;
import org.apache.dubbo.config.annotation.Reference;
import org.springframework.beans.BeanUtils;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

/**
 * @author f
 * @date 2022/7/26 21:52
 */
@Service
@Slf4j
public class AnnounceService {

    @Reference
    private AnnouncementApi announcementApi;

    /**
     * 查询公告列表
     * @param page 第几页
     * @param pageSize 每页数
     * @return 公告列表
     */
    public ResponseEntity announcets(int page, int pageSize) {
        log.info("===================================查询公告列表");
        PageResult<Announcement> pageResult = announcementApi.findAll(page, pageSize);
        List<Announcement> records = pageResult.getItems();
        List<AnnouncementVo> list = new ArrayList<>();
        AnnouncementVo vo = new AnnouncementVo();
        for (Announcement record : records) {
            vo = new AnnouncementVo();
            BeanUtils.copyProperties(record, vo);
            if (null != record.getCreated()) {
                vo.setCreateDate(new SimpleDateFormat("yyyy-MM-dd hh:mm").format(record.getCreated()));
            }
            list.add(vo);
        }
        PageResult resultVo = new PageResult(pageResult.getCounts(), pageResult.getPagesize(), pageResult.getPages(), pageResult.getPage(), list);
        return ResponseEntity.ok(resultVo);
    }
}
